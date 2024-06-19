package bugBust.transitgo.services;

import bugBust.transitgo.dto.UserDto;
import bugBust.transitgo.model.User;
import bugBust.transitgo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;
    private final static Integer LENGTH = 4;
    private final static Duration OTP_EXPIRY = Duration.ofMillis(5);

    public String generateOtp(){
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0 ; i < LENGTH ; i++){
            int randomNum = random.nextInt(10);
            otp.append(randomNum);
        }
        //String finalOtp = String.valueOf(Integer.parseInt(otp.toString().trim()));
        return String.valueOf(otp);
    }

    public UserDto sendOtp(String email){
        UserDto response = new UserDto();
        String otp = generateOtp();
        try {
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()){
                User user = userOpt.get();
                user.setOtp(otp);
                user.setOtpTimestamp(LocalDateTime.now());
                userRepository.save(user);

                emailService.sendOtpEmail(email, "OTP - Password Reset", "Your OTP is: " + otp);

                response.setOtp(otp);
                response.setMessage("OTP sent successfully");
            } else {
                response.setMessage("Email does not exist");
            }
        } catch (Exception e){
            response.setMessage("Error occurred while sending otp");
        }

        return response;
    }

    public String verifyOtp(String email, String otp){
        Optional<User>  userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()){
            User user = userOpt.get();
            LocalDateTime otpTimeStamp = user.getOtpTimestamp();
            if(otpTimeStamp != null && Duration.between(otpTimeStamp, LocalDateTime.now()).compareTo(OTP_EXPIRY) >= 0){
              if (user.getOtp().equals(otp)) {
                user.setOtp(null);
                user.setOtpTimestamp(null);
                userRepository.save(user);
                return "OTP Verified";
              }else {
                  return "Invalid OTP";
              }
            }else {
                  return "OTP Expired";
            }
        }else{
            return "User not found";
        }

    }

    public UserDto saveNewPassword(String email, String password){
        UserDto response = new UserDto();
      try {
          Optional<User> userOpt = userRepository.findByEmail(email);
          if (userOpt.isPresent()) {
              User user = userOpt.get();
              user.setPassword(passwordEncoder.encode(password));
              userRepository.save(user);
              response.setMessage("Password Saved");
          }else {
              response.setMessage("User does not exist");
          }
      }catch (Exception e){
          response.setMessage("Error occurred while updating password");
      }
      return  response;
    }
}
