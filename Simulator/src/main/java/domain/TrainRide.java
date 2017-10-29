package domain;

import javax.json.*;
import be.kdg.se3.services.railway.RouteServiceProxy;
import jdk.nashorn.internal.parser.JSONParser;

import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Zeger Nuitten on 22/10/2017.
 */
public class TrainRide implements Runnable {
    private int rideId;
    private int delay;


    public TrainRide(int rideId, int delay) {
        this.rideId = rideId;
        this.delay = delay;
    }

    public int getRideId() {
        return rideId;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public void run() {

    }
}
