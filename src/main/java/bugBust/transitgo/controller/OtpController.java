package bugBust.transitgo.controller;

import bugBust.transitgo.dto.UserDto;
import bugBust.transitgo.services.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000" , "http://localhost:8081"})
@RestController
@RequestMapping("/api/v1/auth")
public class OtpController {

    private final OtpService otpService;

    @PostMapping("/forgot-password")
    public ResponseEntity<UserDto> forgotPassword(@RequestParam String email){
        return ResponseEntity.ok(otpService.sendOtp(email));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp){
        String result = otpService.verifyOtp(email, otp);
        return switch (result) {
            case "OTP Verified" -> ResponseEntity.ok("OTP Verified");
            case "Invalid OTP" -> ResponseEntity.ok("Invalid OTP");
            case "OTP Expired" -> ResponseEntity.ok("OTP Expired");
            default -> ResponseEntity.ok("User not found");
        };
    }

    @PostMapping("/new-password")
    public ResponseEntity<UserDto> saveNewPassword(@RequestParam String email, @RequestParam String password){
        return  ResponseEntity.ok(otpService.saveNewPassword(email, password));
    }
}
