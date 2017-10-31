package CollisionControl;

import Domain.Messages.DetectionMessage;
import Domain.Messages.IMessage;

public interface IChecker {
    CheckResult check(IMessage message, DetectionCache cache);
}
