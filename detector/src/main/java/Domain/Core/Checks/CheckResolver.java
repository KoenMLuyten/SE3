package Domain.Core.Checks;

import Domain.Entitities.Actions.CheckAction;
import Domain.Entitities.Actions.SpeedAction;
import Domain.Entitities.Actions.StopAction;

import java.util.ArrayList;
import java.util.Iterator;

/*
* Class responsible for resolving all the checkresults CompositeChecker receives from it's different checks
* */
public class CheckResolver {

    /*
    * This method resolves the inputted checkresults to the a single checkResult containing all the actions needed.
    * @Param toResolve Arraylist<CheckResult>
    * @return CheckResult
    * */
    public static synchronized CheckResult resolve(ArrayList<CheckResult> toResolve){
        CheckResult finalOut;
        ArrayList<CheckAction> actionsOut = new ArrayList<>();
        for (CheckResult result : toResolve) {
            if (result.isActionRequired()){
                Iterator<CheckAction> checkActionIterator= result.getRequiredActions().iterator();
                while (checkActionIterator.hasNext()) {
                    CheckAction checkAction = checkActionIterator.next();
                    if (checkAction.getClass() == StopAction.class){                                                    //Checks whether a certain StopAction needs to be included in the output
                        if(!(actionsOut.contains(checkAction))){                                                        //If there's no stopAction there it should be included
                            actionsOut.add(checkAction);
                        }
                        if(!(actionsOut.contains(new SpeedAction(checkAction.getRideId(), 0, 0)))){     //If there's SpeedActions already in the output with the same rideId, remove these
                            Iterator<CheckAction> outIterator = actionsOut.iterator();
                            while (outIterator.hasNext()) {
                                CheckAction slowAction = outIterator.next();
                                if(slowAction instanceof SpeedAction && checkAction.getRideId() == slowAction.getRideId()){
                                    actionsOut.remove(slowAction);
                                }
                            }
                        }
                    }
                    if (checkAction instanceof SpeedAction ){                                                           //Checks whether a certain SpeedActions needs to be included in the output
                        if(!actionsOut.contains(new StopAction(checkAction.getRideId()))){                              //If there's already a stopAction with this rideId included in the output, don't include this SpeedAction
                            if(actionsOut.contains(new SpeedAction(checkAction.getRideId(), 0, 0))){
                                Iterator<CheckAction> outIterator = actionsOut.iterator();
                                while (outIterator.hasNext()) {
                                    CheckAction slowAction = outIterator.next(); {
                                        if (slowAction instanceof SpeedAction && slowAction.equals(checkAction) && ((SpeedAction)slowAction).getSpeed() < ((SpeedAction) checkAction).getSpeed()){
                                            actionsOut.remove(slowAction);                                                  // if there are SpeedActions concerning the same rideId as this, but with higher speed, remove them.
                                        }
                                    }
                                }
                                if(!actionsOut.contains(new SpeedAction(checkAction.getRideId(), 0, 0))){
                                    actionsOut.add(checkAction);                                                            // if there are no other speedActions left in the output (ergo, this one was the slowest) include it in the output
                                }
                            }
                        }
                    }
                }
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
