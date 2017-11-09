package Domain.Entitities.Actions;

import Domain.Entitities.Messages.OutgoingSpeedMessage;
import Domain.Entitities.Exceptions.ServiceException;
import Services.Interfaces.OutgoingMessageService;


/*
  *Class representing the action of sending out a speed message to the simulator
  * */
public class SpeedAction implements CheckAction, Runnable{

    private int rideId;
    private int speed;
    private int sectionId;
    private final static String ACTION_QUEUENAME="Speed Messages";
    private OutgoingMessageService messageService;


    public SpeedAction(int rideId, int speed, int sectionId){
        this.rideId = rideId;
        this.speed = speed;
        this.sectionId = sectionId;
    }


    /*
    * Method overriden from Runnable, puts the message this class represents on queue
    * When there is a serviceEcxeption while trying to put message on queue it keeps triying until it's thread is interuppted*/
    @Override
    public void run() {
        try {
            messageService.setupQueue(ACTION_QUEUENAME);
            OutgoingSpeedMessage message = new OutgoingSpeedMessage(rideId,speed, sectionId);
            messageService.putOnQueue(message);
        }
        catch (ServiceException e){
            run();
        }
    }

    @Override
    public int getRideId() {
        return rideId;
    }


    public int getSpeed() {
        return speed;
    }

    public void setMessageService(OutgoingMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == SpeedAction.class){
            return (this.rideId == ((SpeedAction) obj).getRideId() && (this.speed == ((SpeedAction) obj).speed || ((SpeedAction) obj).speed == 0));
        }
        else {return false;}
    }
}
