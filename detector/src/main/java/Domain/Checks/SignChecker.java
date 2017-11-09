package Domain.Checks;

import Domain.Actions.CheckAction;
import Domain.Actions.StopAction;
import Domain.Infrastructure.Route;
import Domain.Infrastructure.RouteSection;
import Domain.InfrastructureHandler;
import Domain.Maps.SignalisationMap;
import Domain.Messages.DetectionMessage;
import Domain.RouteHandler;


import java.util.ArrayList;

public class SignChecker implements IChecker {

    private InfrastructureHandler infraHandler;
    private RouteHandler routeHandler;
    private int minBlocks;
    private SignalisationMap mapInstance;


    /*
    * This class is responsible for checking the incoming detectionmessage does not suggest this train heading toward an open crossing
    * */
    public SignChecker(InfrastructureHandler infraHandler, RouteHandler routeHandler, int minBlocks){
        this.infraHandler = infraHandler;
        this.routeHandler = routeHandler;
        this.minBlocks = minBlocks;
        this.mapInstance = SignalisationMap.getMapInstance();
    }

    @Override
    public CheckResult check(DetectionMessage message) {
        int rideId = message.getRideId();
        int sectionId = message.getSectionId();
        int blockNr = message.getBlockNr();


        CheckResult out = new CheckResult(false, null);
        ArrayList<CheckAction> actions = new ArrayList<>();
        boolean allClear = true;

        Route currentRoute = routeHandler.getRoute(rideId);
        ArrayList<RouteSection> remainingSections = new ArrayList<>();
        remainingSections.addAll(currentRoute.getRouteSections().subList(
                                                                currentRoute.getRouteSections().indexOf(new RouteSection(sectionId, 0)),
                                                                currentRoute.getRouteSections().size())
                                                                );

        int sectionIndex = 0;
        int blockDistance = 0;

        while(blockDistance < minBlocks && allClear){

            int currentSectionId = remainingSections.get(sectionIndex).getSectionID();
            int[] currentCrossings = infraHandler.getSection(currentSectionId).getCrossings();
            ArrayList<Integer> signals = mapInstance.get(currentSectionId);
            if (sectionIndex == 0){
                for ( int crossingBlock: currentCrossings) {
                    if (crossingBlock < blockNr){ continue; }
                    else {
                        if(signals == null || (!signals.contains(crossingBlock)) && (crossingBlock-blockNr < minBlocks)){
                            allClear = false;
                            break;
                        }
                    }
                }
                blockDistance = infraHandler.getSection(currentSectionId).getNumberOfBlocks() - blockNr;
            }
            else {
                for ( int crossingBlock : currentCrossings) {
                    if(!signals.contains(crossingBlock) && (blockDistance + crossingBlock < minBlocks)){
                        allClear = false;
                        break;
                    }
                }
                blockDistance = blockDistance + infraHandler.getSection(currentSectionId).getNumberOfBlocks();
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
