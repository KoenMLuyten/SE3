package Domain.Maps;

import Domain.Messages.SignalisationMessage;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class SignalisationMap extends ConcurrentHashMap<Integer, ArrayList<Integer>> {


    private static final SignalisationMap mapInstance = new SignalisationMap();

    public static SignalisationMap getMapInstance() {
        return mapInstance;
    }

    private SignalisationMap(){}



}
