package bugBust.transitgo.controller;

import bugBust.transitgo.exception.ItemNotFoundException;
import bugBust.transitgo.exception.UnauthorizedException;
import bugBust.transitgo.model.LostItems;
import bugBust.transitgo.model.Role;
import bugBust.transitgo.model.User;
import bugBust.transitgo.repository.ActivityLogRepository;
import bugBust.transitgo.repository.LostRepository;
import bugBust.transitgo.services.ActivityLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController

@CrossOrigin("http://localhost:3000")
public class LostController {

    @Autowired
    private LostRepository lostRepository;
    @Autowired
    private ActivityLogRepository activityLogRepository;
    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    public LostController(LostRepository lostRepository) {

        this.lostRepository = lostRepository;
    }

    @PostMapping("/lost")
    public LostItems newLostItems(@Valid @RequestBody LostItems newLostItems, Principal principal) {
        newLostItems.setCreatedBy(principal.getName());
        newLostItems.setDateTime(LocalDateTime.now());
        LostItems savedLostItem = lostRepository.save(newLostItems);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = ((User) userDetails).getId();
        activityLogService.logActivity(userId, "Lost Item", savedLostItem.getItem_Description(), "", savedLostItem.getId());

        return savedLostItem;
    }

    @GetMapping("/losts")
    public ResponseEntity<List<LostItems>> getAllLosts() {
        try {
            List<LostItems> items = lostRepository.findAll()
                    .stream()
                    .sorted((a, b) -> b.getDateTime().compareTo(a.getDateTime()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/lost/{id}")
    public ResponseEntity<LostItems> getLostById(@PathVariable Long id) {
        try {
            LostItems item = lostRepository.findById(id)
                    .orElseThrow(() -> new ItemNotFoundException(id));
            return ResponseEntity.ok(item);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/lost/{id}")
    public ResponseEntity<LostItems> updateLost(@Valid @RequestBody LostItems newLostItems, @PathVariable Long id,Principal principal) {
        try {
            LostItems existingItem = lostRepository.findById(id)
                    .orElseThrow(() -> new ItemNotFoundException(id));

            existingItem.setName(newLostItems.getName());
            existingItem.setMobile_Number(newLostItems.getMobile_Number());
            existingItem.setBus_Description(newLostItems.getBus_Description());
            existingItem.setItem_Description(newLostItems.getItem_Description());
            if (newLostItems.getDateTime() != null) {
                existingItem.setDateTime(newLostItems.getDateTime());
            } else {
                existingItem.setDateTime(LocalDateTime.now());
            }
            LostItems updatedItem = lostRepository.save(existingItem);

            // Update activity log
            activityLogRepository.findByActivityId(id).ifPresent(activityLog -> {
                activityLog.setDescription(newLostItems.getItem_Description());
                activityLogRepository.save(activityLog);
            });

            return ResponseEntity.ok(updatedItem);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/lost/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id, Principal principal) {
//        if (!lostRepository.existsById(id)) {
//            return ResponseEntity.status(404).body("Item not found");
//        }

        LostItems lost = lostRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
        if (!isAuthorizedToModify(principal, lost)) {
            throw new UnauthorizedException("You are not authorized to update this review");
        }

        lostRepository.deleteById(id);
        activityLogService.deleteActivityByActivityId(id);
        return ResponseEntity.ok("Item with id " + id + " has been deleted successfully");
    }

    // to give a detailed error message for valid -notnull-400 bad request
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    private boolean isAuthorizedToModify(Principal principal, LostItems lostItems) {
        String username = principal.getName();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return lostItems.getCreatedBy().equals(username) || userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Roleadmin"));
    }
}