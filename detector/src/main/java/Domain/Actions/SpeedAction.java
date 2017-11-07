package Domain.Actions;

import Domain.Messages.OutgoingSpeedMessage;
import Domain.ServiceException;
import Services.Interfaces.OutgoingMessageService;

public class SlowAction implements CheckAction{

    private int rideId;
    private int speed;


    public SlowAction(int rideId, int speed){
        this.rideId = rideId;
        this.speed = speed;
    }
    @Override
    public void execute(OutgoingMessageService messageService, String queueName) {
        try {
            messageService.setupQueue(queueName);
            OutgoingSpeedMessage message = new OutgoingSpeedMessage(rideId,speed);
            messageService.putOnQueue(message, queueName);
        }
        catch (ServiceException e){
            e.printStackTrace();
        }

    }

    @Override
    public int getRideId() {
        return rideId;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == SlowAction.class){
            return (this.rideId == ((SlowAction) obj).getRideId() && (this.speed == ((SlowAction) obj).speed || ((SlowAction) obj).speed == 0));
        }
        else {return false;}
    }
}
