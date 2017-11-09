package Domain.Messages;



/*
* This class represents a message to adjust the speed of a given train to put on the OutgoingMessageService
* */
public class OutgoingSpeedMessage implements OutGoingMessage{

    private int rideId;
    private int speed;
    private int sectionId;

    public OutgoingSpeedMessage(int rideId, int speed, int sectionId){
        this.rideId = rideId;
        this.speed = speed;
        this.sectionId = sectionId;
    }


    public int getRideId(){
        return rideId;
    }

    public double getSpeed(){
        return speed;
    }

}
