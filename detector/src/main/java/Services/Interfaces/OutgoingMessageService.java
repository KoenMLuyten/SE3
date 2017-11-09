package Services.Interfaces;

import Domain.Recorder.MessageRecorder;
import Domain.Entitities.Messages.OutGoingMessage;
import Domain.Entitities.Exceptions.ServiceException;

public interface OutgoingMessageService {
    void setupQueue(String queueName) throws ServiceException;
    void putOnQueue(OutGoingMessage message) throws ServiceException;
    void closeQueue() throws ServiceException;
    void setRecorder(MessageRecorder recorder);
}
