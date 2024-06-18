package bugBust.transitgo.exception;

public class PackageNotFoundException extends RuntimeException{
    public PackageNotFoundException(Long PackageID){
        super("Could not found the package with id "+PackageID);
    }
}
