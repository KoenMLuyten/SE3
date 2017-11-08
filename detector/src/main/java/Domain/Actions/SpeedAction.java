package Domain.Actions;

import Domain.Messages.OutgoingSpeedMessage;
import Domain.ServiceException;
import Services.Interfaces.OutgoingMessageService;

public class SpeedAction implements CheckAction, Runnable{

    private int rideId;
    private int speed;
    private final static String ACTION_QUEUENAME="speed_messages";
    private OutgoingMessageService messageService;


    public SpeedAction(int rideId, int speed){
        this.rideId = rideId;
        this.speed = speed;
    }

    @Override
    public void run() {
        try {
            messageService.setupQueue(ACTION_QUEUENAME);
            OutgoingSpeedMessage message = new OutgoingSpeedMessage(rideId,speed);
            messageService.putOnQueue(message);
        }
        catch (ServiceException e){
            //
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
