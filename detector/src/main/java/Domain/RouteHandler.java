package Domain;

import Domain.Infrastructure.Route;
import Services.Interfaces.IRouteService;
/*
* This class handles RouteService, it checks whether it has the requested Route cached and otherwise makes a call to RouteService to get te requested section, caching it in the process
* */
public class RouteHandler implements IRouteService{

    IRouteService service;
    RouteCache cache;

    public RouteHandler(IRouteService service){
        this.service = service;
        this.cache = RouteCache.getCache();
    }

    public Route getRoute(int rideId){
        Route cachedRoute = cache.get(rideId);
        if (cachedRoute != null){
            return cachedRoute;
        }
        else {
            try {
                cachedRoute = service.getRoute(rideId);
            }
            catch (ServiceException e){

            }
            cache.put(cachedRoute.getRideId(), cachedRoute);
            return cachedRoute;
        }

    }

}
