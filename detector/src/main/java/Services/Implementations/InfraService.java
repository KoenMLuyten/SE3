package Services.Implementations;

import Domain.Infrastructure.Section;
import Domain.ServiceException;
import Services.Interfaces.IInfrastructureService;
import be.kdg.se3.services.railway.InfrastructureServiceProxy;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/*
* This class adapts the given InfrastructureServiceProxy
* It can be called to request a certain Section and adapts the JSON-object returned by InfrastructureServiceProxy as a Section object.
* */
public class InfraService implements IInfrastructureService {

    private final String connectionString;
    private InfrastructureServiceProxy serviceProxy;



    public InfraService(String connectionString){
        this.connectionString = connectionString;
        this.serviceProxy = new InfrastructureServiceProxy();
    }

    @Override
    public Section getSection(int sectionId) throws ServiceException{
        Section out;
        try {
            String sectionString = serviceProxy.get(connectionString + sectionId);
            JSONObject jsonObject = new JSONObject(sectionString);
            if (!jsonObject.has("error")){
                int outSectionId = jsonObject.getInt("sectionId");
                int numberOfBlocks = jsonObject.getInt("numberOfBlocks");
                int blockLength = jsonObject.getInt("blockLength");
                String crossingsString = jsonObject.getString("crossings");
                int[] crossings;
                if (crossingsString != null){
                    String[] crossingStringArray = crossingsString.split("-");
                    crossings = new int[crossingStringArray.length];
                    for (int i = 0; i < crossingStringArray.length; i++) {
                        crossings[i] = Integer.parseInt(crossingStringArray[i]);
                    }
                }
                else {crossings = null;}
                boolean singleDirection = jsonObject.getBoolean("singleDirection");
                String[] highBlocknumberSectionStrings;
                int[] highBlocknumberSectionIds;
                if (!singleDirection){
                    highBlocknumberSectionStrings = jsonObject.getString("highBlocknumberSectionIds").split("-");
                    highBlocknumberSectionIds = new int[highBlocknumberSectionStrings.length];
                    for (int i = 0; i < highBlocknumberSectionStrings.length; i++) {
                        highBlocknumberSectionIds[i] = Integer.parseInt(highBlocknumberSectionStrings[i]);
                    }
                }
                else {highBlocknumberSectionIds = null;}
                out = new Section(outSectionId,numberOfBlocks,blockLength,crossings,singleDirection,highBlocknumberSectionIds);
            }
            else {
                throw new ServiceException(jsonObject.getString("description"), new Exception());
            }
        }
        catch (IOException e){
            throw new ServiceException("Unable to connect to Proxy", e);
        }
        catch (JSONException e){
            throw new ServiceException("Unable to convert JSON object", e);
        }
        return out;
    }
}
