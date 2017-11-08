package Services.Implementations;

import Domain.Infrastructure.Route;
import Domain.Infrastructure.RouteSection;
import Domain.ServiceException;
import Services.Interfaces.IRouteService;
import be.kdg.se3.services.railway.RouteServiceProxy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class RouteService implements IRouteService {

    private RouteServiceProxy proxy;
    private final String connectionString;

    public RouteService(String connectionString){
        this.connectionString = connectionString;
        this.proxy = new RouteServiceProxy();
    }

    @Override
    public Route getRoute(int rideId) throws ServiceException{
        Route out;
        try {
            String routeString = proxy.get(connectionString + rideId);
            JSONObject jsonObject = new JSONObject(routeString);
            if (!jsonObject.has("error")){
                int routeId = jsonObject.getInt("rideId");
                JSONArray jsonRouteSections = jsonObject.getJSONArray("routeSections");
                ArrayList<RouteSection> routeSections = new ArrayList<>(jsonRouteSections.length());
                for (int i = 0; i < jsonRouteSections.length(); i++){
                    JSONObject jsonSection = jsonRouteSections.getJSONObject(i);
                    RouteSection routeSection = new RouteSection(jsonSection.getInt("section"), jsonSection.getInt("speed"));
                    routeSections.add(routeSection);
                }
                out = new Route(routeId,routeSections);
            }
            else {
                throw new ServiceException(jsonObject.getString("description"), new Exception());
            }
        }
        catch (java.io.IOException e){
            throw new ServiceException("Unable to connect to RouteService", e);
        }
        catch (JSONException e){
            throw new ServiceException("Unable to convert JSONObject", e);
        }
        return out;
    }
}
