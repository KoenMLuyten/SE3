package Domain.Core.Checks;

import Domain.Entitities.Messages.DetectionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.*;
/*
* This class holds all the different Checks that need to be done on an incomingDetectionMessage
* It resolves there different results trough CheckResolver and returns a single CheckResult to be actioned on
* */
public class CompositeChecker implements IChecker{

    private ArrayList<IChecker> checkers;
    private Logger logger = LoggerFactory.getLogger(CompositeChecker.class);
    public CompositeChecker(ArrayList<IChecker> checkers){
        this.checkers = checkers;
    }

    /*
    * This method runs all the checks in it's checkerlist concurrently, waits for them all to return a result
    * and then passes the list with results to CheckResolver to have them resolved.
    * @Param DetectionMessage message
    * @returns Checkresult result
    * */
    @Override
    public CheckResult check(DetectionMessage message) {
        ArrayList<CheckResult> results = new ArrayList<>();
        ExecutorCompletionService<CheckResult> executorCompletionService = new ExecutorCompletionService<CheckResult>(Executors.newFixedThreadPool(checkers.size()));
        for (IChecker checker : checkers) {
            Callable<CheckResult> task = () -> {
                return checker.check(message);
            };
            executorCompletionService.submit(task);
        }
        for (int i = 0; i < checkers.size(); i++) {
            try {
                Future<CheckResult> resultFuture = executorCompletionService.take();
                results.add(resultFuture.get());
            }
            catch (InterruptedException e){
                logger.warn("CompositeChecker encountered an interrupted Exception while trying to check Message: " + message, e);
            }
            catch (ExecutionException e){
                logger.warn("CompositeChecker encountered an ExecutionException while trying to check Message: " + message, e);
            }
        }
        CheckResult out = CheckResolver.resolve(results);
        return out;
    }
}
