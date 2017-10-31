package CollisionControl;

import Domain.Messages.IMessage;

public class DetectionHandler implements MessageListener {

    private IChecker checker;
    private DetectionCache cache;

    public DetectionHandler(IChecker checker, DetectionCache cache){
        this.checker = checker;
        this.cache = cache;
    }

    @Override
    public void onreceive(IMessage message) {
        checker.check(message, cache);
    }
}
