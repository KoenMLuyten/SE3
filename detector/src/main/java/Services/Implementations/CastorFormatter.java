package Services;
import Domain.Messages.IncomingMessage;
import Domain.Messages.IncomingMessageDTO;
import Domain.ServiceExeption;
import ServiceInterfaces.MessageFormatter;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class CastorFormatter {
    public class CastorMessageFormatter implements MessageFormatter {

        private Mapping mapping;

        public CastorMessageFormatter(String mappingString) throws ServiceExeption{
            try {
                mapping.loadMapping(mappingString);
            }
            catch (Exception e){
                throw new ServiceExeption("Error loading conversionMapping", e);
            }
        }

        @Override
        public IncomingMessage format(String messageString) throws ServiceExeption {
            IncomingMessage out = new IncomingMessageDTO();
            try {
                Reader reader = new StringReader(messageString);
                Unmarshaller unmar = new Unmarshaller(mapping);
                out = (IncomingMessageDTO) unmar.unmarshal(IncomingMessageDTO.class, reader);
            } catch (Exception e) {
                throw new ServiceExeption("Error during conversion to DTO", e);
            }
            return out;
        }
    }

}
