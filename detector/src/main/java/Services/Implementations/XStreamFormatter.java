package Services.Implementations;

import Domain.Entitities.Messages.IncomingMessageDTO;
import Domain.Entitities.Messages.OutGoingMessage;
import Domain.Entitities.Exceptions.ServiceException;
import Services.Interfaces.MessageFormatter;
import com.thoughtworks.xstream.XStream;

/*
* This class is responsible for formatting an incomming message as an xml-string to an incomingMessageDTO and unformatting an OutgoingMessage to an xml-string
* */
public class XStreamFormatter implements MessageFormatter{
    private XStream xStream;

    public XStreamFormatter(XStream xStream){
        this.xStream = xStream;
    }

    @Override
    public IncomingMessageDTO format(String messageContent) throws ServiceException {
        IncomingMessageDTO out;
        try {
            out = (IncomingMessageDTO) xStream.fromXML(messageContent);
        }
        catch (Exception e){
            throw new ServiceException("Error during message conversion", e);
        }
        return out;
    }

    @Override
    public String unformat(OutGoingMessage message) throws ServiceException {
        String out;
        try {
            out = xStream.toXML(message);
        }
        catch (Exception e){
            throw new ServiceException("Error during message conversion", e);
        }
        return out;
    }

}
