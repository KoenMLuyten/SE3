package Domain.Actions;


import Domain.Messages.OutGoingStopMessage;
import Domain.ServiceException;
import Services.Interfaces.OutgoingMessageService;


/*
* Class representing the action of sending out a StopMessage to the signaler;
* */
public class StopAction implements CheckAction {

    private int rideId;
    private static final String ACTION_QUEUENAME="Stop_Messages";
    private OutgoingMessageService messageService;

    public StopAction(int rideId){
        this.rideId = rideId;
    }

    /*
    * Method overriden from Runnable, puts the message this class represents on queue
    * When there is a serviceEcxeption while trying to put message on queue it keeps triying until it's thread is interuppted*/
    public void run(){
        try {
            messageService.setupQueue(ACTION_QUEUENAME);
            OutGoingStopMessage message = new OutGoingStopMessage(rideId);
            messageService.putOnQueue(message);
        }
        catch (ServiceException e){
            run();
        }
    }

    public int getRideId() {
        return rideId;
    }

    public void setMessageService(OutgoingMessageService service){
        this.messageService = service;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == StopAction.class){
            return this.rideId == ((StopAction) obj).getRideId();
        }
        else {return false;}
    }
}
