import Domain.Core.Checks.*;
import Domain.Core.MessageHandlers.DetectionHandler;
import Domain.Core.MessageHandlers.SignalisationHandler;
import Domain.Core.MessageListeners.DetectionListener;
import Domain.Core.MessageListeners.MessageListener;
import Domain.Core.MessageListeners.SignalisationListener;
import Domain.Entitities.Messages.IncomingMessageDTO;
import Domain.Entitities.Messages.OutGoingStopMessage;
import Domain.Entitities.Messages.OutgoingSpeedMessage;
import Domain.Recorder.MessageRecorder;
import Domain.Core.ServiceHandlers.InfrastructureHandler;
import Domain.Core.ServiceHandlers.RouteHandler;
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
        xStream.alias("StopMessageDTO", OutGoingStopMessage.class);
        xStream.aliasField("rideid",OutGoingStopMessage.class, "rideId");
        xStream.alias("SpeedMessageDTO", OutgoingSpeedMessage.class);
        xStream.aliasField("sectionid",IncomingMessageDTO.class, "SectionId");
        xStream.alias("detectionMessage", IncomingMessageDTO.class);
        xStream.alias("signalMessage", IncomingMessageDTO.class);


        MessageFormatter formatter = new XStreamFormatter(xStream);

        final String inMessageString = "amqp://wumilmvn:puj89wkPxtXV26mV9u16tQ6r-PywF3v4@elephant.rmq.cloudamqp.com/wumilmvn";
        final String detectionQueueName = "Detection Messages";
        final String signalisationQueueName = "Signal Messages";
        final String outMessageString = "amqp://zhghektw:gOYMs97z_HaHm9ARlsNKQvePouKZfycj@elephant.rmq.cloudamqp.com/zhghektw";

        IncomingMessageService inMessageService = new IncomingRabbitMq(inMessageString, detectionQueueName, formatter);
        OutgoingMessageService outMessageService = new OutgoingRabbitMq(outMessageString, formatter);

        MessageRecorder inRecorder = new MessageRecorder("input.txt");
        MessageRecorder outRecorder = new MessageRecorder("output.txt");

        inMessageService.setRecorder(inRecorder);
        outMessageService.setRecorder(outRecorder);

        ArrayList<IChecker> checkers = new ArrayList<>();
        checkers.add(new H2HChecker(infrastructureHandler, routeHandler));
        checkers.add(new H2TChecker(routeHandler, infrastructureHandler, 5, 0.5f));
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
