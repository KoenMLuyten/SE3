package Domain.Checks;

import Domain.Actions.CheckAction;
import Domain.Actions.StopAction;
import Domain.Infrastructure.Route;
import Domain.Infrastructure.RouteSection;
import Domain.Maps.SignalisationMap;
import Domain.Messages.DetectionMessage;
import Domain.RouteHandler;
import Services.Interfaces.IInfrastructureService;


import java.util.ArrayList;

public class SignCheck implements IChecker {

    private IInfrastructureService infraService;
    private RouteHandler routeHandler;
    private int minBlocks;
    private SignalisationMap mapInstance;

    public SignCheck(IInfrastructureService infraService, RouteHandler routeHandler, int minBlocks){
        this.infraService = infraService;
        this.routeHandler = routeHandler;
        this.minBlocks = minBlocks;
        this.mapInstance = SignalisationMap.getMapInstance();
    }

    @Override
    public CheckResult check(DetectionMessage message) {
        int rideId = message.getLastAttribute();
        int sectionId = message.getSectionId();
        int blockNr = message.getBlockNr();


        CheckResult out = new CheckResult(false, null);
        ArrayList<CheckAction> actions = new ArrayList<>();
        boolean allClear = true;

        Route currentRoute = routeHandler.getRoute(rideId);
        ArrayList<RouteSection> remainingSections = (ArrayList<RouteSection>)
                currentRoute.getRouteSections().subList(
                currentRoute.getRouteSections().indexOf(new RouteSection(sectionId, 0)),
                currentRoute.getRouteSections().size());

        int sectionIndex = 0;
        int blockDistance = 0;

        while(blockDistance < minBlocks){

            int currentSectionId = remainingSections.get(sectionIndex).getSectionID();
            int[] currentCrossings = infraService.getSection(currentSectionId).getCrossings();
            ArrayList<Integer> signals = mapInstance.get(sectionIndex);
            if (sectionIndex == 0){
                for ( int crossingBlock: currentCrossings) {
                    if (crossingBlock < blockNr){ continue; }
                    else {
                        if((!signals.contains(crossingBlock)) && (crossingBlock-blockNr < minBlocks)){
                            allClear = false;
                            break;
                        }
                    }
                }
                blockDistance = infraService.getSection(currentSectionId).getNumberOfBlocks() - blockNr;
            }
            else {
                for ( int crossingBlock : currentCrossings) {
                    if(!signals.contains(crossingBlock) && (blockDistance + crossingBlock < minBlocks)){
                        allClear = false;
                        break;
                    }
                }
                blockDistance = blockDistance + infraService.getSection(currentSectionId).getNumberOfBlocks();
            }
            sectionIndex++;
        }
        if (!allClear){
            out.setActionRequired(true);
            actions.add(new StopAction(rideId));
            out.setRequiredActions(actions);
        }
        return out;
    }
}
