package bugBust.transitgo.controller;

import bugBust.transitgo.exception.RateNotFoundException;
import bugBust.transitgo.model.RateReviews;
import bugBust.transitgo.repository.RateRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000/")
public class RateController {
    @Autowired
    private RateRepository rateRepository;


    //have to add @valid before @RequestBody to valid while posting data
    @PostMapping("rate")
    RateReviews newRateReviews(@Valid @RequestBody RateReviews newRateReviews){

        return rateRepository.save(newRateReviews);
    }

    @GetMapping("/rates")
    List<RateReviews> getAllReviews(){

       return rateRepository.findAll();
    }

    @GetMapping("/rates/{busId}")
    List<RateReviews> getAllReviewsFromBusId(@PathVariable  int busId){
        return rateRepository.findByBusId(busId);
    }

    @GetMapping("/rate/{id}") //to edit specific rate with id
    RateReviews geRateReviewsById(@PathVariable Long id){
        return rateRepository.findById(id)
                .orElseThrow(() -> new RateNotFoundException(id));
    }

    //have to add @valid before @RequestBody to valid while edit
    @PutMapping("/rate/{id}")  //edit rates
    RateReviews updateRateReviews(@Valid @RequestBody RateReviews newRateReviews, @PathVariable Long id) {
        return rateRepository.findById(id)
                .map(rateReviews -> {
                    rateReviews.setRate(newRateReviews.getRate());
                    rateReviews.setReview(newRateReviews.getReview());
                    rateReviews.setCreatedAt(LocalDateTime.now());  // Update the createdAt timestamp
                    return rateRepository.save(rateReviews);
                }).orElseThrow(() -> new RateNotFoundException(id));
    }

    @DeleteMapping("/rate/{id}") //delete user with specific id
    String deleteRateReview(@PathVariable Long id){
        if (!rateRepository.existsById(id)) {
            throw new RateNotFoundException(id);
        }
        rateRepository.deleteById(id);
        return "User with id " + id + " has been deleted successfully.";
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
