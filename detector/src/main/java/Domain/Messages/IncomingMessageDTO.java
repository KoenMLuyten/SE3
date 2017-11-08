package Domain.Messages;

import java.sql.Timestamp;

public class IncomingMessageDTO {

    private int sectionId;
    private int blockNr;
    private String timestamp;
    private int lastAttribute;

    public int getSectionId() {
        return sectionId;
    }

    public int getBlockNr(){
        return blockNr;
    }

    public Timestamp getTimestamp() { return Timestamp.valueOf(timestamp);}

    public int getLastAttribute() {
        return lastAttribute;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public void setBlockNr(int blockNr) {
        this.blockNr = blockNr;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setLastAttribute(int lastAttribute) {
        this.lastAttribute = lastAttribute;
    }
}
