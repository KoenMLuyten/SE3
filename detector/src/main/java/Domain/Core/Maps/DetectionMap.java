package Domain.Core.Maps;

import Domain.Entitities.Messages.DetectionMessage;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


/*
* This class represents a map on which all currently moving trains are represented by their last DetectionMessage
* */
public class DetectionMap extends ConcurrentHashMap<Integer, ArrayList<DetectionMessage>> {

    private static final DetectionMap mapInstance = new DetectionMap();

    public static DetectionMap getMapInstance() {return mapInstance;}

    private DetectionMap(){}

}
