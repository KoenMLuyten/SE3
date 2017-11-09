package Domain.Core.MessageListeners;


import Domain.Entitities.Messages.IncomingMessageDTO;


public interface MessageListener {

    void onReceive(IncomingMessageDTO message);

    void start();

    void stop();


}
