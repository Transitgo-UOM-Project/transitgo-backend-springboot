package bugBust.transitgo.services;

import bugBust.transitgo.config.JwtService;
import bugBust.transitgo.dto.UserDto;
import bugBust.transitgo.model.User;
import bugBust.transitgo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    public UserDto getMyProfile(String email){
        UserDto response = new UserDto();
        try{
            Optional<User> userOptional = userRepository.findByEmail(email);
            if(userOptional.isPresent()){
                        response.setFname(userOptional.get().getFname());
                        response.setLname(userOptional.get().getLname());
                        response.setUname(userOptional.get().getUname());
                        response.setEmail(userOptional.get().getEmail());
                        response.setType(userOptional.get().getType());
                        response.setId(userOptional.get().getId());
                        response.setMessage("User profile loaded successfully");
                        response.setStatusCode(200);
            }else {
                      response.setMessage("Failed to load the profile");
                      response.setStatusCode(404);
            }
        }catch (Exception e) {
            response.setMessage("Error occurred while loading profile : " + e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }


    public UserDto getUsers(){
        UserDto response = new UserDto();
        try{
            List<User> result = userRepository.findAll();
            if (!result.isEmpty()){
                response.setUserList(result);
                response.setStatusCode(200);
                response.setMessage("Successful");
            }else {
                response.setStatusCode(404);
                response.setMessage("No Users found");
            }
            return response;
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred: " + e.getMessage());
            return response;
        }
    }

    public UserDto deleteEmployee(Long id){
        UserDto response = new UserDto();
        try{
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()){
                userRepository.deleteById(id);
                response.setStatusCode(200);
                response.setMessage("User Deleted Successfully");
            }else {
                response.setStatusCode(404);
                response.setMessage("User not found for deletion");
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return response;
    }


    public UserDto updateUser(long id, User updatedUser){
        UserDto response = new UserDto();
        try{
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()){
                User existingUser = userOptional.get();
                existingUser.setFname(updatedUser.getFname());
                existingUser.setLname(updatedUser.getLname());
                existingUser.setUname(updatedUser.getUname());
                existingUser.setBusid(updatedUser.getBusid());
                existingUser.setPhone(updatedUser.getPhone());

                User savedUser = userRepository.save(existingUser);

                response.setFname(savedUser.getFname());
                response.setLname(savedUser.getLname());
                response.setUname(savedUser.getUname());
                response.setPhone(savedUser.getPhone());
                response.setStatusCode(200);
                response.setMessage("User Updated Successfully");
            }else {
                response.setStatusCode(404);
                response.setMessage("User not found for Update");
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred while updating user : " + e.getMessage());
        }
        return response;
    }

    public UserDto getUserById(long id){
        UserDto response = new UserDto();
        try{
            User userById = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
            response.setUser(userById);
            response.setStatusCode(200);
            response.setMessage("User with id '"+ id + "'found successfully");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred : "+ e.getMessage());
        }
        return response;
    }


    public UserDto getEmpByBusID(String busId){
        UserDto response = new UserDto();
        try {
            User emp = userRepository.findByBusid(busId).orElseThrow(() -> new RuntimeException("Employee not Found"));
            response.setUser(emp);
            response.setMessage("Employee found successfully");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred : "+ e.getMessage());
        }
        return response;
    }


    public String verifyPassword(String password, String email){
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isPresent()){
            User present = userOpt.get();
            if(passwordEncoder.matches(password, present.getPassword())){
                   return ("Password Verified");
            }else{
                return ("Incorrect Password");
            }
        }else {
            return ("User Not Found");
        }

    }

    public String deleteUser(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            Long id = user.getId();
            userRepository.deleteById(id);
            return "User Deleted";
        }
        return "Error Deleting User";
    }
}
