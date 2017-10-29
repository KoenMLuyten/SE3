package adapters;

import domain.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Zeger Nuitten on 25/10/2017.
 */
public class MessageGenerator implements MessageService {
    @Override
    public void generateMessage(Route route, Section[] section) {
        for (int j = 0; j < section.length; j++) {
            for (int i = 0; i < section[j].getNumberOfBlocks(); i++) {
                int rideId = route.getRideId();
                int sectionId = section[j].getSectionId();
                int blockNr = i+1;
                float speed = section[j].getSpeed();
                float lengtOfBlock = section[j].getBlockLength();
                //TODO Delay ergens anders berekenen?
                float delay = (lengtOfBlock / speed) * 1000;
                Date date = new java.sql.Timestamp(System.currentTimeMillis());
                System.out.println("RideId: " + rideId + " SectionId: " + sectionId + " blockNr: " + blockNr + " " + date);
                //Message wordt aangemaakt
                DetectionMessage message = new DetectionMessage(rideId, blockNr, date, sectionId);

                try {
                    System.out.println("waiting " + delay / 1000 + " seconds");
                    Thread.sleep((long) delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Controller.sendMessage(message);
                //return message;
            }
        }
        System.out.println("\n Train with id: " + route.getRideId() + " Has arrived!\n");
    }
}