package bugBust.transitgo.controller;

import bugBust.transitgo.exception.ItemNotFoundException;
import bugBust.transitgo.model.FoundItems;
import bugBust.transitgo.repository.FoundRepository;
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
@CrossOrigin("http://localhost:3000")  // to connect with frontend
public class FoundController {
    @Autowired  //to inject repository interface
    private FoundRepository foundRepository;

    @PostMapping("/found")  //for sending data  (path)
    FoundItems newFoundItems(@Valid @RequestBody FoundItems newFoundItems){
        newFoundItems.setDateTime(LocalDateTime.now());
        return  foundRepository.save(newFoundItems); // this save the data and return the data what we have posted
    }

    @GetMapping("/founds")  //for getting data
    public ResponseEntity<List<FoundItems>> getAllFounds() {
        try {
            List<FoundItems> items = foundRepository.findAll()
                    .stream()
                    .sorted((a, b) -> b.getDateTime().compareTo(a.getDateTime()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            e.printStackTrace();  // log the error
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/found/{id}")
    FoundItems getFoundById(@PathVariable Long id) {
        return foundRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    @PutMapping("/found/{id}") //update data
    public ResponseEntity<FoundItems> updateFound(@Valid @RequestBody FoundItems newFoundItems, @PathVariable Long id) {
        FoundItems updatedItem = foundRepository.findById(id)
                .map(foundItems -> {
                    foundItems.setName(newFoundItems.getName());
                    foundItems.setMobile_Number(newFoundItems.getMobile_Number());
                    foundItems.setBus_Description(newFoundItems.getBus_Description());
                    foundItems.setItem_Description(newFoundItems.getItem_Description());
                    if (newFoundItems.getDateTime() != null) {
                        foundItems.setDateTime(newFoundItems.getDateTime());
                    } else {
                        foundItems.setDateTime(LocalDateTime.now());
                    }
                    return foundRepository.save(foundItems);
                }).orElseThrow(() -> new ItemNotFoundException(id));
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/found/{id}") //delete data
    public ResponseEntity<String> deleteItem(@PathVariable Long id){
        if (!foundRepository.existsById(id)){
            return ResponseEntity.status(404).body("Item not found");
        }
        foundRepository.deleteById(id);
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
