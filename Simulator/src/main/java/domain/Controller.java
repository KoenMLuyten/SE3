package domain;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Created by Zeger Nuitten on 23/10/2017.
 */
public class Controller {
    private RouteService routeService;
    private InfrastructureService infrastructureService;
    private MessageService messageService;

    private Logger logger = LoggerFactory.getLogger(Controller.class);

    public Controller(RouteService routeService, InfrastructureService infrastructureService, MessageService messageService) {
        this.routeService = routeService;
        this.infrastructureService = infrastructureService;
        this.messageService = messageService;
    }

    public void startTrainRide(TrainRide trainRide, String routeString, String infraString, int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Route route = getRoute(trainRide.getRideId(), routeString);
        Section[] sections = getInfrastructure(route, infraString);
        messageService.generateMessage(route, sections);
    }



    public Route getRoute(int id, String routeString) {
        try {
            return routeService.getRoute(id,routeString);
        } catch (DataException e) {
            logger.error("Problem with retrieving the route data");
        }
        return null;
    }

    public Section[] getInfrastructure(Route route, String infraString) {
        try {
            return infrastructureService.getSectionData(route, infraString);
        } catch (DataException e) {
            logger.error("Problem with retrieving Infrastructure data");
        }
        return null;
    }

    public static void sendMessage(DetectionMessage message) {
        //TODO message op de queue zetten
        DetectionMessage message1 = message;
        int id = message1.getRideId();
    }
}
