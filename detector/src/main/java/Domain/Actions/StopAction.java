package Domain.Actions;

import Domain.Actions.CheckAction;
import Domain.Messages.OutGoingStopMessage;
import Domain.ServiceException;
import Services.Interfaces.OutgoingMessageService;

public class StopAction implements CheckAction {

    private int rideId;
    private static final String ACTION_QUEUENAME="Stop_Messages";
    private OutgoingMessageService messageService;

    public StopAction(int rideId){
        this.rideId = rideId;
    }

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
