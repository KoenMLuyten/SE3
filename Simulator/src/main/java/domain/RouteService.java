package domain;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Zeger Nuitten on 25/10/2017.
 */
public interface RouteService {
    Route getRoute(int id, String routeString) throws DataException;
}
