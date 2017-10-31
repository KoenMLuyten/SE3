package CollisionControl;

import Domain.Messages.DetectionMessage;
import Domain.Messages.IMessage;

public class CompositeChecker implements IChecker {

    private DetectionCache cache;
    private IChecker[] checkers;

    public CompositeChecker(){
        this.cache = new DetectionMap();
    }

    @Override
    public CheckResult check(IMessage message, DetectionCache cache) {
        cache.add((DetectionMessage) message);
        for (IChecker checker : checkers) {
            checker.check(message, cache);
        }
        return null;
    }
}
