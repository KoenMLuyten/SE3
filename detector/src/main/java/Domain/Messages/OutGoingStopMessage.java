package Domain.Messages;

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
