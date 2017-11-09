package Services.Interfaces;

import Domain.MessageListener;
import Domain.MessageRecorder;
import Domain.ServiceException;


/*
* Interface representing an Incoming message Service
* */
public interface IncomingMessageService {
    void initialize(MessageListener listener, String queuename) throws ServiceException;
    void shutDown() throws ServiceException;
    void setRecorder(MessageRecorder recorder);
}
