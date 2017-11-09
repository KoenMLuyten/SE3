package Domain.Entitities.Messages;


import java.sql.Timestamp;


/*
* This class represents a signalMessage as send out by the simulator
* */
public class SignalisationMessage {

    private int sectionId;
    private int blockNr;
    private Timestamp timestamp;
    private int closed;


    public SignalisationMessage(int isClosed, int sectionId, int blockNr, Timestamp timestamp){
            this.sectionId = sectionId;
            this.blockNr = blockNr;
            this.timestamp = timestamp;
            this.closed = isClosed;
    }

    public SignalisationMessage(IncomingMessageDTO message){
            this.sectionId = message.getSectionId();
            this.blockNr = message.getBlockNr();
            this.timestamp= message.getTimestamp();
            this.closed = message.getLastAttribute();
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

    public int getClosed() {
        return closed;
    }



}


