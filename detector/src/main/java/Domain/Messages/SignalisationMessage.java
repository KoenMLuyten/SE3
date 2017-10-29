package Domain;

import java.util.Date;

public class SignalisationMessage {

        private int isClosed;
        private int sectionId;
        private int blockNr;
        private Date timestamp;

        public SignalisationMessage(int isClosed, int sectionId, int blockNr, Date timestamp){
            this.isClosed = isClosed;
            this.sectionId = sectionId;
            this.blockNr = blockNr;
            this.timestamp = timestamp;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public int IsClosed() {
            return isClosed;
        }

        public int getSectionId() {
            return sectionId;
        }

        public int getBlockNr(){
            return blockNr;
        }
}


