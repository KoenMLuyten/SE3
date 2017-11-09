package Domain;

/*
* General Exception thrown by the services in this package.
*/
public class ServiceException extends Exception {
    public ServiceException(String message, Exception e){
        super(message, e);
    }
}
