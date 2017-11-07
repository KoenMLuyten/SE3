package Domain;

import Domain.Infrastructure.Route;

import java.util.concurrent.ConcurrentHashMap;

public class RouteCache extends ConcurrentHashMap<Integer, Route>{

    private static final RouteCache routeInstance = new RouteCache();

    private RouteCache(){}

    public static RouteCache getRouteInstance(){return routeInstance;}

}
