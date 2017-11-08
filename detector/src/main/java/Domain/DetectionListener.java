package Domain;

import Domain.Actions.CheckAction;
import Domain.Checks.CheckResult;
import Domain.Messages.DetectionMessage;
import Domain.Messages.IncomingMessageDTO;
import Services.Interfaces.IncomingMessageService;
import Services.Interfaces.OutgoingMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.NoSuchElementException;


public class DetectionListener implements MessageListener {


    private IncomingMessageService incomingMessageService;
    private OutgoingMessageService outgoingMessageService;
    private DetectionHandler messageHandler;
    private final String MESSAGE_QUEUE;
    private final int MAX_THREADS;
    LinkedList<DetectionMessage> buffer;
    private MessageRecorder recorder;
    private Logger logger = LoggerFactory.getLogger(DetectionListener.class);

    public DetectionListener(DetectionHandler messageHandler, IncomingMessageService incomingMessageService, OutgoingMessageService outgoingMessageService, String MESSAGE_QUEUE, int maxThreads){
        this.incomingMessageService = incomingMessageService;
        this.outgoingMessageService = outgoingMessageService;
        this.messageHandler = messageHandler;
        this.MESSAGE_QUEUE = MESSAGE_QUEUE;
        this.MAX_THREADS = maxThreads;
        buffer = new LinkedList<>();
    }

    @Override
    public void onReceive(IncomingMessageDTO DTOmessage) {
        DetectionMessage message = new DetectionMessage(DTOmessage);
        if (buffer.size() == 0 && Thread.activeCount() < MAX_THREADS){
            Runnable handle = () -> {
                handleResults(messageHandler.handle(message));
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

    private void handleResults(CheckResult result){
        for (CheckAction a: result.getRequiredActions()) {
            a.setMessageService(outgoingMessageService);
            Thread thread = new Thread(a);
            thread.start();
            try{
                thread.join(30000);
                if(thread.isAlive()){
                    thread.interrupt();
                    outgoingMessageService.closeQueue();
                    logger.info("Outgoing message thread stayed allive for too long, manually interrupted");
                }
            }
            catch (InterruptedException e){
                logger.error("Interrupted while trying to send outgoing message", e);
            }
            catch (ServiceException e){
                logger.error("Exception while shutting down queue", e);
            }

        }
    }

    public void nextFromBuffer(){
        try {
            DetectionMessage message = buffer.remove();
            Runnable handle = () ->{
              handleResults(messageHandler.handle(message));
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
