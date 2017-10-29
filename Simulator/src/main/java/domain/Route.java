package domain;

import java.util.Map;

/**
 * Created by Zeger Nuitten on 22/10/2017.
 */
public class Route {
    private int[] sections;
    private int[] speeds;
    private int rideId;

    public Route(int[] sections, int rideId, int[] speeds) {
        this.sections = sections;
        this.rideId = rideId;
        this.speeds = speeds;
    }


    public int[] getSections() {
        return sections;
    }

    public void setSections(int[] sections) {
        this.sections = sections;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }
    public int[] getSpeeds() {
        return speeds;
    }
    public Route () {

    }
}
