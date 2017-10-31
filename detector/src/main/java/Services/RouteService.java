package JSONServices;

import Domain.Infrastructure.Route;
import be.kdg.se3.services.railway.RouteServiceProxy;

public class RouteServiceJSON implements Services.RouteService{

    private RouteServiceProxy proxy;

    public RouteServiceJSON(RouteServiceProxy proxy){
        this.proxy = proxy;
    }

    @Override
    public Route getRoute(int rideId) {
        /*try {
            proxy.get("url" + rideId);
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }*/
        return null;

    }
}
