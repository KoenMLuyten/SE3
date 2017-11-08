import Domain.*;
import Domain.Checks.*;
import Domain.Messages.IncomingMessageDTO;
import Domain.Messages.OutGoingStopMessage;
import Domain.Messages.OutgoingSpeedMessage;
import DummyQueue.DummyQueue;
import Services.Implementations.*;
import Services.Interfaces.*;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;


public class Test {
    public static void main(String[] args) {
        IRouteService routeService = new RouteService("www.services4se3.com/railway/route/");
        IInfrastructureService infrastructureService = new InfraService("www.services4se3.com/railway/infrastructure/sections/");
        XStream xStream = new XStream();

        InfrastructureHandler infrastructureHandler = new InfrastructureHandler(infrastructureService);
        RouteHandler routeHandler = new RouteHandler(routeService);

        xStream.aliasField("rideId", IncomingMessageDTO.class, "lastAttribute");
        xStream.aliasField("closed",IncomingMessageDTO.class, "lastAttribute");
        xStream.alias("StopMessage", OutGoingStopMessage.class);
        xStream.alias("SpeedMessage", OutgoingSpeedMessage.class);
        xStream.alias("detectionMessage", IncomingMessageDTO.class);
        xStream.alias("signalisationMessage", IncomingMessageDTO.class);

        MessageFormatter formatter = new XStreamFormatter(xStream);

        final String inMessageString = "amqp://hjnwvnem:IZWi2g4eMtJlxOVGMNbMkvzYvuu7patt@elephant.rmq.cloudamqp.com/hjnwvnem";
        final String detectionQueueName = "detection_queue";
        final String signalisationQueueName = "signal_queue";
        final String outMessageString = "amqp://zhghektw:gOYMs97z_HaHm9ARlsNKQvePouKZfycj@elephant.rmq.cloudamqp.com/zhghektw";

        IncomingMessageService inMessageService = new IncomingRabbitMq(inMessageString, detectionQueueName, formatter);
        OutgoingMessageService outMessageService = new OutgoingRabbitMq(outMessageString, formatter);

        MessageRecorder inRecorder = new MessageRecorder("input.txt");
        MessageRecorder outRecorder = new MessageRecorder("output.txt");

        inMessageService.setRecorder(inRecorder);
        outMessageService.setRecorder(outRecorder);

        ArrayList<IChecker> checkers = new ArrayList<>();
        checkers.add(new H2HChecker(infrastructureHandler, routeHandler));
        checkers.add(new H2TChecker(routeHandler, infrastructureHandler, 5));
        checkers.add(new SignChecker(infrastructureHandler, routeHandler, 5));
        IChecker checker = new CompositeChecker(checkers);


        DetectionHandler detectionHandler = new DetectionHandler(checker, routeHandler);
        SignalisationHandler signalisationHandler = new SignalisationHandler();

        MessageListener detectionListener = new DetectionListener(detectionHandler, inMessageService, outMessageService, detectionQueueName, 20);
        MessageListener signalisationListener = new SignalisationListener(signalisationHandler, inMessageService, signalisationQueueName, 20);


        DummyQueue dummyQueue = new DummyQueue(inMessageString,detectionQueueName);

        //signalisationListener.start();
        //detectionListener.start();
        //dummyQueue.start();

        IncomingFileReader reader = new IncomingFileReader(detectionListener,signalisationListener,formatter);
        reader.start("input.txt");



    }
}
