package ServiceInterfaces;

import Domain.Messages.IncomingMessage;
import Domain.ServiceExeption;

public interface MessageFormatter {
    IncomingMessage format(String messageContent) throws ServiceExeption;
}
