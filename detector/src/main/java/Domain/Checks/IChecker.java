package Domain.Checks;

import Domain.Messages.DetectionMessage;

public interface IChecker {
    CheckResult check(DetectionMessage message);
}
