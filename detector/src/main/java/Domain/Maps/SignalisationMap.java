package Domain.Maps;


import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


/*This class represents a map on which all currently closed crossings are represented by their sectionId and blockNr*/
public class SignalisationMap extends ConcurrentHashMap<Integer, ArrayList<Integer>> {


    private static final SignalisationMap mapInstance = new SignalisationMap();

    public static SignalisationMap getMapInstance() {
        return mapInstance;
    }

    private SignalisationMap(){}



}
