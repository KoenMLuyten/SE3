package Services.Interfaces;

import Domain.Entitities.Messages.IncomingMessageDTO;
import Domain.Entitities.Messages.OutGoingMessage;
import Domain.Entitities.Exceptions.ServiceException;

/*
* interface representing a MessageFormatter which turns an incoming message string into a IncomingMessageDTO
* and an OutgoingMessage to a string
* */
public interface MessageFormatter {
    IncomingMessageDTO format(String messageContent) throws ServiceException;
    String unformat(OutGoingMessage message) throws ServiceException;
}
