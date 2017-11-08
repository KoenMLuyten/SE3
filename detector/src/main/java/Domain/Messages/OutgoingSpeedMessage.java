package Domain.Messages;

import Domain.Checks.CheckResult;

public class OutgoingSpeedMessage implements OutGoingMessage{

    private int rideId;
    private double speed;

    public OutgoingSpeedMessage(int rideId, double speed){
        this.rideId = rideId;
        this.speed = speed;
    }


    public int getRideId(){
        return rideId;
    }

    public double getSpeed(){
        return speed;
    }
}
