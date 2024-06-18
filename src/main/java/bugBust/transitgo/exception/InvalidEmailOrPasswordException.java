package bugBust.transitgo.exception;

public class InvalidEmailOrPasswordException extends Throwable{

    public InvalidEmailOrPasswordException(String message){
        super(message);
    }
}
