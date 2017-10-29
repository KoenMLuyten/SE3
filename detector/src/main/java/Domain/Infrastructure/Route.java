package Domain;

import java.util.ArrayList;

public class Route {
    private int rideId;
    private ArrayList<RouteSection> routeSections;

    public Route(int rideID, ArrayList<RouteSection> routeSections){
        this.rideId = rideID;
        this.routeSections = routeSections;
    }

    public int getRideId() {
        return rideId;
    }

    public ArrayList<RouteSection> getRouteSections() {
        return routeSections;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public void setRouteSections(ArrayList<RouteSection> routeSections) {
        this.routeSections = routeSections;
    }
}
