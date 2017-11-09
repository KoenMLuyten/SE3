package Domain;

import Domain.Checks.CheckResult;
import Domain.Checks.IChecker;
import Domain.Infrastructure.Route;
import Domain.Maps.DetectionMap;
import Domain.Messages.DetectionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/*
* This class is responsible for handling a single detection message
* it updates the detectionmap and returns a single CheckResult that can be actioned upon if needed
* */
public class DetectionHandler {

    private IChecker checker;
    private DetectionMap map;
    RouteHandler routeHandler;

    public DetectionHandler(IChecker checker, RouteHandler routeHandler){
        this.checker = checker;
        this.map = DetectionMap.getMapInstance();
        this.routeHandler = routeHandler;
    }


    public CheckResult handle(DetectionMessage message) {
        updateDetectionMap(message);
        return checker.check(message);
    }

    private void updateDetectionMap(DetectionMessage message){
        ArrayList<DetectionMessage> currentSection = map.get(message.getSectionId());
        if (currentSection == null) {
            currentSection = new ArrayList<>();
            currentSection.add( message);
            map.put(message.getSectionId(), currentSection);
        }
        else {
            if (currentSection.contains(message)){
                currentSection.remove(message);
                currentSection.add(message);
            }
            else{
                Route route = routeHandler.getRoute(message.getRideId());
                for (int i = 0; i < route.getRouteSections().size(); i++){
                    if (route.getRouteSections().get(i).getSectionID() == message.getSectionId() && i != 0){
                        int prevSectionId = route.getRouteSections().get(i-1).getSectionID();
                        ArrayList prevSection = map.get(prevSectionId);
                        if (prevSection != null && prevSection.contains(message)) {
                            prevSection.remove(message);
                            map.replace(prevSectionId, prevSection);
                        }
                        break;
                    }
                }
                currentSection.add(message);
            }
            map.replace(message.getSectionId(), currentSection);
        }
    }
}
