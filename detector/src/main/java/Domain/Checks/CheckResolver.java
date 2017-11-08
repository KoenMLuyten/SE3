package Domain.Checks;

import Domain.Actions.CheckAction;
import Domain.Actions.SpeedAction;
import Domain.Actions.StopAction;

import java.util.ArrayList;

public class CheckResolver {
    public static CheckResult resolve(ArrayList<CheckResult> toResolve){
        CheckResult finalOut;
        ArrayList<CheckAction> actionsOut = new ArrayList<>();
        for (CheckResult result : toResolve) {
            if (result.isActionRequired()){
                result.getRequiredActions().forEach(checkAction -> {
                    if (checkAction.getClass() == StopAction.class){
                        if(!(actionsOut.contains(checkAction))){
                            actionsOut.add(checkAction);
                        }
                        if(!(actionsOut.contains(new SpeedAction(checkAction.getRideId(), 0)))){
                            actionsOut.forEach(slowAction -> {
                                if(slowAction instanceof SpeedAction && checkAction.getRideId() == slowAction.getRideId()){
                                    actionsOut.remove(slowAction);
                                }
                            });
                        }
                    }
                    if (checkAction.getClass() == SpeedAction.class){
                        if(!actionsOut.contains(new StopAction(checkAction.getRideId()))){
                            if(actionsOut.contains(new SpeedAction(checkAction.getRideId(), 0))){
                                actionsOut.forEach(slowAction -> {
                                    if (slowAction.getClass() == SpeedAction.class && slowAction.equals(checkAction) && ((SpeedAction)slowAction).getSpeed() < ((SpeedAction) checkAction).getSpeed()){
                                        actionsOut.remove(slowAction);
                                    }
                                });
                            }
                            if(!actionsOut.contains(new SpeedAction(checkAction.getRideId(), 0))){
                                actionsOut.add(checkAction);
                            }
                        }
                    }
                });
            }
        }

        if(actionsOut.size()>0){
            finalOut = new CheckResult(true, actionsOut);
        }
        else {
            finalOut = new CheckResult(false, actionsOut);
        }
        return finalOut;
    }
}
