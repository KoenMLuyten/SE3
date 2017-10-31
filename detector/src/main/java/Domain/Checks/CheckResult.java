package CollisionControl;

public class CheckResult {
    private boolean actionRequired;
    private CheckAction[] requiredActions;


    public boolean isActionRequired() {
        return actionRequired;
    }

    public CheckAction[] getRequiredActions() {
        return requiredActions;
    }
}
