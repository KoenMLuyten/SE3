package Domain;


import Domain.Messages.IncomingMessageDTO;

public interface MessageListener {

    void onReceive(IncomingMessageDTO message);

    void start();

    void stop();


}
