package bugBust.transitgo.exception;

public class RateNotFoundException extends RuntimeException{
    public RateNotFoundException(Long id){
   super("Could not found the user with id "+id);}
}
