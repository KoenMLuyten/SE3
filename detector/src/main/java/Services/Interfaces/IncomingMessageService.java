package Services.Interfaces;

import Domain.Core.MessageListeners.MessageListener;
import Domain.Recorder.MessageRecorder;
import Domain.Entitities.Exceptions.ServiceException;


/*
* Interface representing an Incoming message Service
* */
public interface IncomingMessageService {
    void initialize(MessageListener listener, String queuename) throws ServiceException;
    void shutDown() throws ServiceException;
    void setRecorder(MessageRecorder recorder);
}
