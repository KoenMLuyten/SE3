package Domain.Core.Checks;

import Domain.Entitities.Actions.CheckAction;
import Domain.Entitities.Actions.SpeedAction;
import Domain.Entitities.Infrastructure.RouteSection;
import Domain.Core.ServiceHandlers.InfrastructureHandler;
import Domain.Core.Maps.DetectionMap;
import Domain.Entitities.Messages.DetectionMessage;
import Domain.Core.ServiceHandlers.RouteHandler;

import java.util.ArrayList;


/*
 * This class is responsible for checking the incoming detectionmessage does not suggest an imminent Head 2 Tail collision
 */
public class H2TChecker implements IChecker{
    private RouteHandler routeHandler;
    private DetectionMap mapInstance;
    private InfrastructureHandler infraHandler;
    private final int minBlocks;
    private final float speedReduction;

    public H2TChecker(RouteHandler routeHandler, InfrastructureHandler infraHandler, int minBlocks, float speedReduction){
        this.routeHandler = routeHandler;
        this.mapInstance = DetectionMap.getMapInstance();
        this.infraHandler = infraHandler;
        this.minBlocks = minBlocks;
        this.speedReduction = speedReduction;
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
                            int speed = Math.round(sectionToCheck.getSpeed() * (speedReduction/otherTrain.getBlockNr() - blockNr));
                            actions.add(new SpeedAction(rideId, speed, sectionToCheck.getSectionID()));
                        }
                    }
                    else{
                        if(currentBlockDistance + otherTrain.getBlockNr() < minBlocks){
                            int speed = Math.round(sectionToCheck.getSpeed() * (speedReduction/otherTrain.getBlockNr() - blockNr));
                            actions.add(new SpeedAction(rideId, speed, sectionToCheck.getSectionID()));
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
