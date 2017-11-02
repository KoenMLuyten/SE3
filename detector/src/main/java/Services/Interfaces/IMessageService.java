package ServiceInterfaces;

import Domain.MessageListener;
import Domain.ServiceExeption;

public interface IMessageService {
    void initialize(MessageListener listener) throws ServiceExeption;
    void shutDown() throws ServiceExeption;
}
