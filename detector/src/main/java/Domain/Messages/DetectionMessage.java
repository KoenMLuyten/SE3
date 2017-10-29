package Domain;

import java.util.Date;

public class DetectionMessage {
    private int rideId;
    private int sectionId;
    private int blockNr;
    private Date timestamp;

    public DetectionMessage(int rideId, int sectionId, int blockNr, Date timestamp){
        this.rideId = rideId;
        this.sectionId = sectionId;
        this.blockNr = blockNr;
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getRideId() {
        return rideId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public int getBlockNr(){
        return blockNr;
    }

}
