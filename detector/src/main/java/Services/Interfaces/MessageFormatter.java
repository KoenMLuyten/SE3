package Services.Interfaces;

import Domain.Messages.IncomingMessageDTO;
import Domain.Messages.OutGoingMessage;
import Domain.ServiceException;

/*
* interface representing a MessageFormatter which turns an incoming message string into a IncomingMessageDTO
* and an OutgoingMessage to a string
* */
public interface MessageFormatter {
    IncomingMessageDTO format(String messageContent) throws ServiceException;
    String unformat(OutGoingMessage message) throws ServiceException;
}
