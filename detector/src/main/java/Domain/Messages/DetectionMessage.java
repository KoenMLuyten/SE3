package Domain.Messages;
        import java.sql.Timestamp;
        import java.util.Date;
        import java.util.function.Supplier;


/*
* This class represents a detectionMessage as send out by the simulator
* */
public class DetectionMessage {

    private int sectionId;
    private int blockNr;
    private Timestamp timestamp;
    private int rideId;


    public DetectionMessage( int sectionId, int blockNr, Timestamp timestamp, int rideId){
        this.sectionId = sectionId;
        this.blockNr = blockNr;
        this.timestamp = timestamp;
        this.rideId = rideId;
    }

    public DetectionMessage(IncomingMessageDTO message){
        this.sectionId = message.getSectionId();
        this.blockNr = message.getBlockNr();
        this.timestamp= message.getTimestamp();
        this.rideId = message.getLastAttribute();
    }

    public int getSectionId() {
        return sectionId;
    }

    public int getBlockNr(){
        return blockNr;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public int getRideId() {
        return rideId;
    }


    @Override
    public boolean equals(Object obj) {

        boolean isEqual = false;
        if (obj != null && obj instanceof DetectionMessage){
            isEqual = this.rideId == ((DetectionMessage) obj).rideId;
        }
        return isEqual;

    }
}
