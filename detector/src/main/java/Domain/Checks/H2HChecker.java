package Domain.Checks;

import Domain.Actions.CheckAction;
import Domain.Actions.StopAction;
import Domain.Infrastructure.RouteSection;
import Domain.Infrastructure.Section;
import Domain.InfrastructureHandler;
import Domain.Maps.DetectionMap;
import Domain.Messages.DetectionMessage;
import Domain.RouteHandler;

import java.util.ArrayList;

public class H2HChecker implements IChecker {

    private final DetectionMap mapInstance;
    private InfrastructureHandler infraHandler;
    private RouteHandler routeHandler;

    public H2HChecker(InfrastructureHandler infraHandler, RouteHandler routeHandler){
        this.mapInstance = DetectionMap.getMapInstance();
        this.infraHandler = infraHandler;
        this.routeHandler = routeHandler;
    }

    @Override
    public CheckResult check(DetectionMessage message) {
        int sectionId = message.getSectionId();
        int rideId = message.getRideId();
        CheckResult out = new CheckResult(false, null);
        ArrayList<CheckAction> actions = new ArrayList<>();
        Section currentSection = infraHandler.getSection(sectionId);
        if (currentSection.isSingleDirection()){
            return out;
        }
        else{
            if (mapInstance.get(sectionId).size()>1){
                ArrayList<RouteSection> checkRoute = routeHandler.getRoute(rideId).getRouteSections();
                int previousSectionindex = checkRoute.indexOf(new RouteSection(sectionId, 0)) - 1;
                int previousSectionId = checkRoute.get(previousSectionindex).getSectionID();
                mapInstance.get(sectionId).forEach(detectionMessage ->  {
                    if(detectionMessage.getRideId() != rideId){
                        ArrayList<RouteSection> problemRoute = routeHandler.getRoute(detectionMessage.getRideId()).getRouteSections();
                        int comingFromSectionIndex = problemRoute.indexOf(new RouteSection(sectionId,0)) - 1;
                        if (problemRoute.get(comingFromSectionIndex).getSectionID() != previousSectionId){
                         actions.add(new StopAction(detectionMessage.getRideId()));
                        }
                    }
                });

            }
            if (actions.size() > 0){
                out.setActionRequired(true);
                actions.add(new StopAction(rideId));
                out.setRequiredActions(actions);
            }
            return out;
        }
    }
}
