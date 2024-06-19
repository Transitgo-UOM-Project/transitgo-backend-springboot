package bugBust.transitgo.controller;

import bugBust.transitgo.dto.UserDto;
import bugBust.transitgo.model.User;
import bugBust.transitgo.services.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserManagementController {

    private final UserManagementService userManagementService;

    @GetMapping("/user/profile")
   public ResponseEntity<UserDto> getMyProfile(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String email = authentication.getName();
       UserDto response = userManagementService.getMyProfile(email);
       return ResponseEntity.status(response.getStatusCode()).body(response);
   }

   @GetMapping("/admin/users")
   public ResponseEntity<UserDto> getUsers(){

        return ResponseEntity.ok(userManagementService.getUsers());
   }

   @DeleteMapping("admin/delete/{id}")
   public ResponseEntity<UserDto> deleteUser(@PathVariable long id){
        return ResponseEntity.ok(userManagementService.deleteUser(id));
   }

   @PutMapping("/admin-user/update/{id}")
   public ResponseEntity<UserDto> updateUSer(@PathVariable long id, @RequestBody User user){
        return ResponseEntity.ok(userManagementService.updateUser(id, user));
   }

   @GetMapping("/admin/get-user/{id}")
   public ResponseEntity<UserDto> getUserById(@PathVariable long id){
        return ResponseEntity.ok(userManagementService.getUserById(id));
   }


}
