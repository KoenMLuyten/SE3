package be.kdg.se3.examen;

import adapters.InfrastructureReader;
import adapters.MessageGenerator;
import adapters.RouteReader;
import domain.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zeger Nuitten on 22/10/2017.
 */
public class Test {
    public static void main(String[] args) throws DataException {
        RouteService routeService = new RouteReader();
        InfrastructureService infrastructureService = new InfrastructureReader();
        MessageService messageService = new MessageGenerator();
        Controller controller = new Controller(routeService, infrastructureService, messageService);

        //Initializing TrainRides
        List<TrainRide> trainRides = new ArrayList<>();
            trainRides.add(new TrainRide(1,3000));
            trainRides.add(new TrainRide(2,1000));
           // trainRides.add(new TrainRide(5,4000));

        for (TrainRide trainRide: trainRides) {
            System.out.println("New train departure...(" + trainRide.getDelay() + " millieseconds)\n");
            String routeString = "www.services4se3.com/railway/route/";
            String infraString = "www.services4se3.com/railway/infrastructure/sections/";
            //Trainrides starting in parallel
            new Thread(() -> {
                controller.startTrainRide(trainRide, routeString, infraString, trainRide.getDelay());
            }).start();
        }
    }
}
