package bugBust.transitgo.controller;

import bugBust.transitgo.exception.ItemNotFoundException;
import bugBust.transitgo.model.LostItems;
import bugBust.transitgo.repository.LostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
public class LostController {

    private final LostRepository lostRepository;

    @Autowired
    public LostController(LostRepository lostRepository) {
        this.lostRepository = lostRepository;
    }

    @PostMapping("/lost")
    public LostItems newLostItems(@Valid @RequestBody LostItems newLostItems) {
        newLostItems.setDateTime(LocalDateTime.now());
        return lostRepository.save(newLostItems);
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
    public ResponseEntity<LostItems> updateLost(@Valid @RequestBody LostItems newLostItems, @PathVariable Long id) {
        try {
            LostItems updatedItem = lostRepository.findById(id)
                    .map(lostItems -> {
                        lostItems.setName(newLostItems.getName());
                        lostItems.setMobile_Number(newLostItems.getMobile_Number());
                        lostItems.setBus_Description(newLostItems.getBus_Description());
                        lostItems.setItem_Description(newLostItems.getItem_Description());
                        if (newLostItems.getDateTime() != null) {
                            lostItems.setDateTime(newLostItems.getDateTime());
                        } else {
                            lostItems.setDateTime(LocalDateTime.now());
                        }
                        return lostRepository.save(lostItems);
                    }).orElseThrow(() -> new ItemNotFoundException(id));
            return ResponseEntity.ok(updatedItem);
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/lost/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        if (!lostRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Item not found");
        }
        lostRepository.deleteById(id);
        return ResponseEntity.ok("Item with id " + id + " has been deleted successfully");
    }

    // to give a detailed error message for valid -notnull-400 bad request
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error)  -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
