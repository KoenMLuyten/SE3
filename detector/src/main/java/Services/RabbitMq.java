package XMLServices;

import Domain.Messages.IMessage;
import com.rabbitmq.client.*;

import java.util.ArrayList;

public class RabbitMqXMLService implements Services.MessageService{


    @Override
    public ArrayList<IMessage> getMessages() {
        return null;
    }

    @Override
    public void postMessages(ArrayList<IMessage> messages) {

    }
}
