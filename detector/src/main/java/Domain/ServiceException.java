package Domain;

public class ServiceExeption extends Exception {
    public ServiceExeption(String message, Exception e){
        super(message, e);
    }
}
