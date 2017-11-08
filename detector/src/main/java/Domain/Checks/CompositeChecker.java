package Domain.Checks;

import Domain.Messages.DetectionMessage;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class CompositeChecker implements IChecker{

    private ArrayList<IChecker> checkers;

    public CompositeChecker(ArrayList<IChecker> checkers){
        this.checkers = checkers;
    }

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
                System.out.println("interruptedException");
                // TODO : Log
            }
            catch (ExecutionException e){
                System.out.println("executionException");
                // TODO : Log
            }
        }
        CheckResult out = CheckResolver.resolve(results);
        return out;
    }
}
