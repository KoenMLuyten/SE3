package Domain.Entitities.Messages;



/*
* This class represents a message to stop a given train to put on the OutgoingMessageService
* */
public class OutGoingStopMessage implements OutGoingMessage{

    private final int rideId;

    public OutGoingStopMessage(int rideId){
        this.rideId = rideId;
    }

    @Override
    public int getRideId() {
        return rideId;
    }
}
