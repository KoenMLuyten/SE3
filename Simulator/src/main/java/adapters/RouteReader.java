package adapters;

import be.kdg.se3.services.railway.RouteServiceProxy;
import domain.DataException;
import domain.Route;
import domain.RouteService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Zeger Nuitten on 25/10/2017.
 */
public class RouteReader implements RouteService {
    @Override
    public Route getRoute(int id, String routeString) throws DataException {
        RouteServiceProxy routeServiceProxy = new RouteServiceProxy();
        String routeJson = null;


        try {
            routeJson = routeServiceProxy.get(routeString + id);

            JSONObject jsonObj = null;
            jsonObj = new JSONObject(routeJson);

            int rideId;
            rideId = jsonObj.getInt("rideId");

            //array van sections wordt uitgelezen
            JSONArray jsonMainArr = jsonObj.getJSONArray("routeSections");
            int[] routeSections = new int[jsonMainArr.length()];
            int[] speedInSection = new int[jsonMainArr.length()];
            for (int i = 0; i < jsonMainArr.length(); i++) {
                JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
                routeSections[i] = childJSONObject.getInt("section");
                speedInSection[i] = childJSONObject.getInt("speed");

            }

            Route route = new Route(routeSections, rideId, speedInSection);
            return route;

        } catch (Exception e) {
            throw new DataException("Problem with retrieving the route data from json", e);
        }

    }

}