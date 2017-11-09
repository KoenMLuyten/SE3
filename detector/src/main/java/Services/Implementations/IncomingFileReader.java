package Services.Implementations;

import Domain.Entitities.Exceptions.ServiceException;
import Domain.Core.MessageListeners.MessageListener;
import Services.Interfaces.MessageFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/*
* This class reads a file of detection/signalisation messages in XML-format and notifies the listener
* It is intented for regression testing/ load testing the application
* */
public class IncomingFileReader {

    private MessageListener detectionListener;
    private MessageListener signalisationListener;
    private MessageFormatter formatter;
    private Logger logger = LoggerFactory.getLogger(IncomingFileReader.class);

    public IncomingFileReader(MessageListener detectionListener, MessageListener signalisationListener, MessageFormatter formatter){
        this.detectionListener = detectionListener;
        this.signalisationListener = signalisationListener;
        this.formatter = formatter;
    }



    public void start(String inputfile) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(inputfile));
            String newLine;
            String nextLine;
            while ((newLine = reader.readLine()) != null){

                if (newLine.trim().equals("<detectionMessage>")){
                    while (!(nextLine=reader.readLine()).trim().equals("</detectionMessage>")){
                        newLine = newLine + "\n" + nextLine;
                    }
                    newLine = newLine + "\n</detectionMessage>\n";
                    detectionListener.onReceive(formatter.format(newLine));
                    newLine = "";
                }
                if (newLine.equals("<detectionMessage>")){
                    while (!(nextLine=reader.readLine()).trim().equals("</signalisationMessage>")){
                        newLine = newLine + "\n" + nextLine;
                    }
                    newLine = newLine + "</detectionMessage>";
                    signalisationListener.onReceive(formatter.format(newLine));
                    newLine = "";
                }
            }

        }
        catch (FileNotFoundException e){
            logger.error("Input file not found",e);
        }
        catch (IOException e){
            logger.error("IO Exception during file input");
        }
        catch (ServiceException e){
            logger.error("Error during formatting XML Message");
        }
    }


}
