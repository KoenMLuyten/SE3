package Domain.Core.ServiceCaches;

import Domain.Entitities.Infrastructure.Route;
import java.util.concurrent.ConcurrentHashMap;

public class RouteCache extends ConcurrentHashMap<Integer, Route>{

    private static final RouteCache CACHE = new RouteCache();

    private RouteCache(){}

    public static RouteCache getCache(){return CACHE;}

}
