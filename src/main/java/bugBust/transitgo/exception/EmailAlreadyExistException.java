package bugBust.transitgo.exception;

public class EmailAlreadyExistException extends Throwable {
    public EmailAlreadyExistException(String message){
        super(message);
    }
}
