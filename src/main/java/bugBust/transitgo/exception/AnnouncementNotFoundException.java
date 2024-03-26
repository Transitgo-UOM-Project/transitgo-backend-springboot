package bugBust.transitgo.exception;

public class AnnouncementNotFoundException extends RuntimeException{
    public AnnouncementNotFoundException(Long id){
        super("could not found theannouncement");
    }
}
