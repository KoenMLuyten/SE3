package adapters;

import be.kdg.se3.services.railway.InfrastructureServiceProxy;
import domain.DataException;
import domain.InfrastructureService;
import domain.Route;
import domain.Section;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Zeger Nuitten on 25/10/2017.
 */
public class InfrastructureReader implements InfrastructureService {

    @Override
    public Section[] getSectionData(Route route, String infraString) throws DataException {
        int[] secties = route.getSections();
        Section[] sections = new Section[secties.length];

        InfrastructureServiceProxy infrastructureServiceProxy = new InfrastructureServiceProxy();
        String infrJson = null;

        for (int i = 0 ; i<secties.length;i++) {
            try {
                infrJson = infrastructureServiceProxy.get(infraString + secties[i]);
            } catch (Exception e) {
                throw new DataException("Problem with retreiving the infrastrucure Json string", e);
            }
            try {
            JSONObject jsonObj = null;

                jsonObj = new JSONObject(infrJson);

                int sectionId;
                sectionId = jsonObj.getInt("sectionId");

                int blockLength;
                blockLength = jsonObj.getInt("blockLength");

                int[] speeds = route.getSpeeds();
                int speed = speeds[i];

                int numberOfBlocks;
                numberOfBlocks = jsonObj.getInt("numberOfBlocks");

                Section section = new Section(numberOfBlocks, sectionId, speed, blockLength);
                sections[i] = section;
            }catch (Exception e) {
                throw new DataException("Problem with converting the infrastrucure json Object",e);
            }
        }
        return sections;
    }
}
