package Services.Interfaces;

import Domain.MessageListener;
import Domain.MessageRecorder;
import Domain.ServiceException;

public interface IncomingMessageService {
    void initialize(MessageListener listener, String queuename) throws ServiceException;
    void shutDown() throws ServiceException;
    void setRecorder(MessageRecorder recorder);
}
