package Services;

import Domain.Messages.IMessage;

import java.util.ArrayList;

public interface MessageService {
    ArrayList<IMessage> getMessages();
    void postMessages(ArrayList<IMessage> messages);
}
