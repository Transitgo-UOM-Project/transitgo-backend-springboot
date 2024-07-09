package bugBust.transitgo.exception;

public class RouteAlreadyExistsException extends  RuntimeException{
    public RouteAlreadyExistsException(String message) {
        super(message);
    }
}
