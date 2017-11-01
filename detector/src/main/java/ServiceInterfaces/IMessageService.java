package ServiceInterfaces;

import Domain.MessageListener;

public interface MessageService {
    void initialize(MessageListener listener);
}
