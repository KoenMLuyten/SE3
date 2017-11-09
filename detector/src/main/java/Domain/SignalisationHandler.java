package Domain;

import Domain.Maps.SignalisationMap;
import Domain.Messages.IncomingMessageDTO;
import Domain.Messages.SignalisationMessage;
import Services.Interfaces.IncomingMessageService;

import java.util.ArrayList;


/*
* This class is responsible for handling a single signalisation message
* by updating the signalisationMap
* */
public class SignalisationHandler {

    private final SignalisationMap map = SignalisationMap.getMapInstance();


    public SignalisationHandler(){
    }


    public void handle(SignalisationMessage message) {
        updateMap(message);
    }


    private void updateMap(SignalisationMessage message){
        int key = message.getSectionId();
        int closed = message.getClosed();
        ArrayList<Integer> results = map.get(key);
        if (closed == 0) {
            if (results == null) {
                results = new ArrayList<>();
                results.add(message.getBlockNr());
                map.put(key, results);
            } else {
                results.add(message.getBlockNr());
                map.replace(key, results);
            }
        }
        else {
            results.remove(message.getBlockNr());
            map.replace(key, results);
        }

    }
}
