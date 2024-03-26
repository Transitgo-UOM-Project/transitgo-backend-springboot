package bugBust.transitgo.exception;

import bugBust.transitgo.repository.AnnouncementRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AnnouncementNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(AnnouncementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> exceptionHnadler (AnnouncementNotFoundException exception){
        Map<String,String> errorMap= new HashMap<>();
        errorMap.put("error massage",exception.getMessage());

        return errorMap;
    }


}
