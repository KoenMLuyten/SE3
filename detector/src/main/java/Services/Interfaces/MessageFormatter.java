package Services.Interfaces;

import Domain.Messages.IncomingMessageDTO;
import Domain.Messages.OutGoingMessage;
import Domain.ServiceException;

public interface MessageFormatter {
    IncomingMessageDTO format(String messageContent) throws ServiceException;
    String unformat(OutGoingMessage message) throws ServiceException;
}
