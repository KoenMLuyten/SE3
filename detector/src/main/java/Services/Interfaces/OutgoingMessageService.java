package Services.Interfaces;

import Domain.MessageRecorder;
import Domain.Messages.OutGoingMessage;
import Domain.ServiceException;

public interface OutgoingMessageService {
    void setupQueue(String queueName) throws ServiceException;
    void putOnQueue(OutGoingMessage message) throws ServiceException;
    void closeQueue() throws ServiceException;
    void setRecorder(MessageRecorder recorder);
}
