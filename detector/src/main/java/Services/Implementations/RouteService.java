package Services;

import Domain.Infrastructure.Route;
import ServiceInterfaces.IRouteService;
import be.kdg.se3.services.railway.RouteServiceProxy;

public class RouteService implements IRouteService {

    private RouteServiceProxy proxy;

    public RouteService(){
        this.proxy = new RouteServiceProxy();
    }

    @Override
    public Route getRoute(int rideId) {
        try {
            String routeString = proxy.get("url" + rideId);
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }
        return null;

    }
}
