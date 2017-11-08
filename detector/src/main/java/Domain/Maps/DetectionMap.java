package Domain.Maps;

import Domain.Messages.DetectionMessage;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class DetectionMap extends ConcurrentHashMap<Integer, ArrayList<DetectionMessage>> {

    private static final DetectionMap mapInstance = new DetectionMap();

    public static DetectionMap getMapInstance() {return mapInstance;}

    private DetectionMap(){}

}
