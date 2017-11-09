package Domain.Messages;

import java.sql.Timestamp;


/*This class represents a DTO for the incomingMessageService to pass to the appropriate listener who then casts it to the specific message it needs*/
public class IncomingMessageDTO {

    private int sectionId;
    private int blockNr;
    private Timestamp timestamp;
    private int lastAttribute;

    public int getSectionId() {
        return sectionId;
    }

    public int getBlockNr(){
        return blockNr;
    }

    public Timestamp getTimestamp() { return timestamp;}

    public int getLastAttribute() {
        return lastAttribute;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public void setBlockNr(int blockNr) {
        this.blockNr = blockNr;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setLastAttribute(int lastAttribute) {
        this.lastAttribute = lastAttribute;
    }
}
