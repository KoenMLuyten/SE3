package Domain.Checks;

import Domain.Actions.CheckAction;
import Domain.Actions.SpeedAction;
import Domain.Infrastructure.RouteSection;
import Domain.InfrastructureHandler;
import Domain.Maps.DetectionMap;
import Domain.Messages.DetectionMessage;
import Domain.RouteHandler;

import java.util.ArrayList;

public class H2TChecker implements IChecker{
    private RouteHandler routeHandler;
    private DetectionMap mapInstance;
    private InfrastructureHandler infraHandler;
    private final int minBlocks;

    public H2TChecker(RouteHandler routeHandler, InfrastructureHandler infraHandler, int minBlocks){
        this.routeHandler = routeHandler;
        this.mapInstance = DetectionMap.getMapInstance();
        this.infraHandler = infraHandler;
        this.minBlocks = minBlocks;
    }

    @Override
    public CheckResult check(DetectionMessage message) {
        int rideId = message.getRideId();
        int sectionId = message.getSectionId();
        int blockNr = message.getBlockNr();

        int maxBlocksCurrentSection = infraHandler.getSection(sectionId).getNumberOfBlocks();
        int currentBlockDistance = maxBlocksCurrentSection - blockNr;

        CheckResult out = new CheckResult(false, null);
        ArrayList<CheckAction> actions = new ArrayList<>();

        ArrayList<RouteSection> sectionsToCome = routeHandler.getRoute(rideId).getRouteSections();
        int currentSectionIndex = sectionsToCome.indexOf(new RouteSection(sectionId, 0));
        ArrayList<RouteSection> sectionsToCheck = new ArrayList<>();
        sectionsToCheck.addAll(sectionsToCome.subList(currentSectionIndex,sectionsToCome.size()));

        for (RouteSection sectionToCheck : sectionsToCheck){
            ArrayList<DetectionMessage> otherTrains = mapInstance.get(sectionToCheck.getSectionID());
            if(otherTrains != null){
                for (DetectionMessage otherTrain : otherTrains) {
                    if (otherTrain.getSectionId() == sectionId){
                        if(blockNr < otherTrain.getBlockNr() && otherTrain.getBlockNr() - blockNr <= minBlocks){
                            //TODO : SPEED implementeren
                            int speed = blockNr*2;
                            actions.add(new SpeedAction(rideId, speed));
                        }
                    }
                    else{
                        if(currentBlockDistance + otherTrain.getBlockNr() < minBlocks){
                            //TODO : SPEED implementeren
                            int speed = blockNr*2;
                            actions.add(new SpeedAction(rideId, speed));
                        }
                    }

                }
            }
            if(currentBlockDistance > minBlocks){
                break;
            }
            if(sectionId != sectionToCheck.getSectionID()){
                currentBlockDistance = currentBlockDistance + infraHandler.getSection(sectionToCheck.getSectionID()).getNumberOfBlocks();
            }
        }
        if(actions.size()>0){
            out.setActionRequired(true);
            out.setRequiredActions(actions);
        }
        return out;
    }
}
