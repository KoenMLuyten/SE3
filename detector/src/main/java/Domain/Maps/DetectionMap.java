package CollisionControl;

import Domain.Messages.DetectionMessage;
import Domain.Messages.IMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetectionMap extends HashMap<Integer, ArrayList<DetectionMessage>> implements DetectionCache {


    public Object put(Integer key, DetectionMessage value) {
        ArrayList<DetectionMessage> results = super.get(key);
        if (results == null) {
            results = new ArrayList<>();
            results.add(value);
            super.put(key, results);
        }
        else {
            results.add(value);
            super.put(key, results);
        }
        return null;
    }

    @Override
    public void add(DetectionMessage message) {
        try{
            this.put(message.getSectionId(), message);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<DetectionMessage> find(DetectionMessage message) {
        return this.get(message.getSectionId());
    }
}
