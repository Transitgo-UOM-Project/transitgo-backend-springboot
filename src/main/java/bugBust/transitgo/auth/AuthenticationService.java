package bugBust.transitgo.auth;

import bugBust.transitgo.config.JwtService;
import bugBust.transitgo.exception.EmailAlreadyExistException;
import bugBust.transitgo.exception.InvalidEmailOrPasswordException;
import bugBust.transitgo.model.Role;
import bugBust.transitgo.model.User;
import bugBust.transitgo.repository.UserRepository;
import bugBust.transitgo.services.EmailService;
import bugBust.transitgo.services.UserManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    @Autowired
    private EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    public AuthenticationResponse register(RegisterRequest request, HttpServletRequest httpServletRequest) throws EmailAlreadyExistException {

        if (repository.existsByEmail(request.getEmail())){
            User existingUser = repository.findByEmail(request.getEmail()).orElseThrow();

            if(!existingUser.isEnabled()){
                throw new EmailAlreadyExistException("Email already registered but not verified. Please verify your email ");
            }
            throw new EmailAlreadyExistException("Email already in use");
        }

        // Get the user's role from the request
            String userRole = request.getType();
            Role role = null;
        switch (userRole.toLowerCase()) {
            case "admin" -> role = Role.admin;
            case "employee" -> role = Role.employee;
            default -> role = Role.passenger;
        }

        if(role == Role.employee) {
            if (repository.existsByBusid(request.getBusid())) {
                throw new EmailAlreadyExistException("Bus already assigned");
            }
        }
            //Role role = Role.valueOf((userRole.equals("employee")) ?  "employee" : "passenger");

        var user = User.builder()
                .fname(request.getFname())
                .lname(request.getLname())
                .email(request.getEmail())
                .phone(request.getPhone())
                .uname(request.getUname())
                .busid(request.getBusid())
                .password(passwordEncoder.encode(request.getPassword()))
                .type(role)
                .enabled(true)//email.v
                .build();
        repository.save(user);

        if (role == Role.passenger){
            //generate verification token and send email
            String token = UUID.randomUUID().toString();
            user.setVerificationToken(token);
            user.setEnabled(false);
            user.setOtpTimestamp(LocalDateTime.now());
            repository.save(user);

            String origin = httpServletRequest.getHeader("Origin");
            String confirmationURL;
            if ("http://localhost:8081".equals(origin)) {
                confirmationURL = "http://localhost:8081/verify-email?token=" + token;
            } else {
                confirmationURL = "http://localhost:3000/verify-email?token=" + token;
            }

            emailService.sendEmail(user.getEmail(),"TransitGo Account Verification", "Hi "+request.getUname()+" Click the link to verify your email : (Link expires in 24 hours) "+confirmationURL);
        }

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws InvalidEmailOrPasswordException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    ));

            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new InvalidEmailOrPasswordException("Invalid Email or Password"));

            if (!user.isEnabled()) {
                throw new InvalidEmailOrPasswordException("User is not enabled. Please verify your email.");
            }

            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .user(user)
                    .build();
        } catch (BadCredentialsException e) {
            throw new InvalidEmailOrPasswordException("Invalid Email or Password");
        } catch (InvalidEmailOrPasswordException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidEmailOrPasswordException("An unexpected error occurred during authentication, User Email not Verified");
        }
    }


    public String validateVerificationToken(String token){
        User user = repository.findByVerificationToken(token).orElse(null);
        if (user == null){
            return "invalid";
        }

        user.setEnabled(true);
        user.setOtpTimestamp(null);
        user.setVerificationToken(null);
        repository.save(user);
        return "valid";
    }
}
