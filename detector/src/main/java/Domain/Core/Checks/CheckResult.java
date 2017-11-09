package Domain.Core.Checks;

import Domain.Entitities.Actions.CheckAction;

import java.util.ArrayList;

public class CheckResult {
    private boolean actionRequired;
    private ArrayList<CheckAction> requiredActions;

    public CheckResult(boolean actionRequired, ArrayList<CheckAction> actions){
        this.actionRequired = actionRequired;
        this.requiredActions = actions;
    }

    public boolean isActionRequired() {
        return actionRequired;
    }

    public ArrayList<CheckAction> getRequiredActions() {
        return requiredActions;
    }

    public void setActionRequired(boolean actionRequired) {
        this.actionRequired = actionRequired;
    }

    public void setRequiredActions(ArrayList<CheckAction> actions){
        this.requiredActions = actions;
    }
}
