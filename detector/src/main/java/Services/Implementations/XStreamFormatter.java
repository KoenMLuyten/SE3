package Services.Implementations;

import Domain.Messages.IncomingMessageDTO;
import Domain.Messages.OutGoingMessage;
import Domain.ServiceException;
import Services.Interfaces.MessageFormatter;
import com.thoughtworks.xstream.XStream;

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
