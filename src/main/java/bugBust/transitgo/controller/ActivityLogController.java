package bugBust.transitgo.controller;

import bugBust.transitgo.dto.ActivityLogDto;
import bugBust.transitgo.exception.UnauthorizedException;
import bugBust.transitgo.model.ActivityLog;
import bugBust.transitgo.model.User;
import bugBust.transitgo.services.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000" , "http://localhost:8081"})
@RestController
@RequestMapping("/api/user")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    @GetMapping("/{userId}/activity-logs")
    public ResponseEntity<ActivityLogDto> getActivityLogs(@PathVariable Long userId, Principal principal){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long loggedUser = ((User) userDetails).getId();

        if (!loggedUser.equals(userId)){
            throw new RuntimeException("You are not authenticated to view these activity log");
        }
        return ResponseEntity.ok(activityLogService.getActivityLogsForUser(userId));
    }
}
