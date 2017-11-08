package Domain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessageRecorder {
    private final String FILENAME;
    private final Logger logger = LoggerFactory.getLogger(MessageRecorder.class);

    public MessageRecorder(String fileName){
        this.FILENAME = fileName;
    }

    public void record(String message){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true))){
            String out = String.format("%s \n",message);
            bw.write(out);
        }
        catch (IOException e){
            logger.warn("IOException during filewrite", e);
        }
    }
}
