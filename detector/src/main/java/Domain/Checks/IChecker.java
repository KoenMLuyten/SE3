package Domain.Checks;

import Domain.Messages.DetectionMessage;

/*
Interface representing a check that needs to be done on a given DetectionMessage and returns in a CheckResult the actions needed as a result of this check
 */
public interface IChecker {
    CheckResult check(DetectionMessage message);
}
