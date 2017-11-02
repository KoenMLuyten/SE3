package Services;

import Domain.Messages.IncomingMessage;
import Domain.Messages.IncomingMessageDTO;
import Domain.ServiceExeption;
import ServiceInterfaces.MessageFormatter;
import com.thoughtworks.xstream.XStream;

public class XStreamFormatter implements MessageFormatter{
    private XStream xStream;

    public XStreamFormatter(XStream xStream){
        this.xStream = xStream;
    }

    @Override
    public IncomingMessage format(String messageContent) throws ServiceExeption {
        IncomingMessageDTO out;
        try {
            out = (IncomingMessageDTO) xStream.fromXML(messageContent);
        }
        catch (Exception e){
            throw new ServiceExeption("Error during message conversion", e);
        }
        return out;
    }
}
