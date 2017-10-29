package domain;

import java.util.Date;

/**
 * Created by Zeger Nuitten on 22/10/2017.
 */
public class DetectionMessage {
    private int rideId;
    private int blockNr;
    private Date timestamp;
    private int sectionId;

    public DetectionMessage(int rideId, int blockNr, Date timestamp, int sectionId) {
        this.rideId = rideId;
        this.blockNr = blockNr;
        this.timestamp = timestamp;
        this.sectionId = sectionId;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }
    public int getBlockNr() {
        return blockNr;
    }

    public void setBlockNr(int blockNr) {
        this.blockNr = blockNr;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
}
