package Domain;

import Domain.Messages.DetectionMessage;
import Domain.Messages.IncomingMessageDTO;
import Domain.Messages.SignalisationMessage;
import Services.Interfaces.IncomingMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class SignalisationListener implements MessageListener {

    private IncomingMessageService incomingMessageService;
    private final String MESSAGE_QUEUE;
    private final int MAX_THREADS;
    private SignalisationHandler messageHandler;
    LinkedList<SignalisationMessage> buffer;
    private Logger logger = LoggerFactory.getLogger(DetectionListener.class);

    public SignalisationListener(SignalisationHandler messageHandler, IncomingMessageService incomingMessageService, String MESSAGE_QUEUE, int maxThreads){
        this.incomingMessageService = incomingMessageService;
        this.messageHandler = messageHandler;
        this.MESSAGE_QUEUE = MESSAGE_QUEUE;
        this.MAX_THREADS = maxThreads;
        buffer = new LinkedList<>();
    }

    @Override
    public void onReceive(IncomingMessageDTO DTOmessage) {
        SignalisationMessage message = new SignalisationMessage(DTOmessage);
        if (buffer.size() == 0 && Thread.activeCount() < MAX_THREADS){
            Runnable handle = () -> {
                messageHandler.handle(message);
                if (buffer.size() != 0){
                    nextFromBuffer();
                }
            };
            Thread thread = new Thread(handle);
            thread.start();
        }
        else {
            buffer.add(message);
            logger.info("Max threads reached, message buffered");
        }
    }

    public void nextFromBuffer(){
        try {
            SignalisationMessage message = buffer.remove();
            Runnable handle = () ->{
                messageHandler.handle(message);
                nextFromBuffer();
            };
            Thread thread = new Thread(handle);
            thread.start();
        }
        catch (NoSuchElementException e){
            logger.warn("Tried to read empty messagebuffer", e);
        }
    }

    public void start(){
        try {
            incomingMessageService.initialize(this, MESSAGE_QUEUE);
        }
        catch (ServiceException e){
            logger.error("Exception during message service initialization", e);
        }
    }

    public void stop(){
        try {
            incomingMessageService.shutDown();
        }
        catch (ServiceException e){
            logger.error("Exception during message service shutdown", e);
        }
    }
}
