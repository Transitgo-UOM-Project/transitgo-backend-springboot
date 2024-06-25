package bugBust.transitgo.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String s){
        super("You are not authorized to perform this action");
    }
}
