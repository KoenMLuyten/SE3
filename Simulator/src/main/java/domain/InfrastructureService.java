package domain;

import org.json.JSONException;

/**
 * Created by Zeger Nuitten on 25/10/2017.
 */
public interface InfrastructureService {

    Section[] getSectionData(Route route, String infraString) throws DataException;
}
