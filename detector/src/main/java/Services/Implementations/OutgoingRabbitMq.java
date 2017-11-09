package Services.Implementations;

import Domain.MessageRecorder;
import Domain.Messages.OutGoingMessage;
import Domain.ServiceException;
import Services.Interfaces.MessageFormatter;
import Services.Interfaces.OutgoingMessageService;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
* This class is a publisher for RabbitMQ
* it takes an OutogoingMessage and puts it on the appropriate queue
* */
public class OutgoingRabbitMq implements OutgoingMessageService {

    private final String connectionString;
    private com.rabbitmq.client.Connection connection;
    private com.rabbitmq.client.Channel channel;
    private MessageFormatter formatter;
    private String queueName;
    private Logger logger = LoggerFactory.getLogger(OutgoingRabbitMq.class);
    private MessageRecorder recorder;

    public OutgoingRabbitMq(String connectionString, MessageFormatter formatter) {
        this.connectionString = connectionString;
        this.formatter = formatter;
    }

    @Override
    public void setRecorder(MessageRecorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void setupQueue(String queueName) throws ServiceException {

        this.queueName = queueName;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(connectionString);
            factory.setRequestedHeartbeat(30);
            factory.setConnectionTimeout(30000);

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(queueName,
                    false, /* non-durable */
                    false, /* non-exclusive */
                    false, /* do not auto delete */
                    null); /* no other construction arguments */
        } catch (Exception e) {
            throw new ServiceException("Couldn't setup the queue",e);
        }
    }


    public synchronized void putOnQueue(OutGoingMessage message) throws ServiceException {
        try {
            String xmlMessage = formatter.unformat(message);
            channel.basicPublish("", queueName, null, xmlMessage.getBytes());
            recorder.record(xmlMessage);
            logger.info("Message published");
        }catch (ServiceException e){
         throw e;
        }
        catch (Exception e) {
            throw new ServiceException("Failed to put message on queue", e);
        }
    }

    public void closeQueue() throws ServiceException {
        try {
            channel.close();
            connection.close();
        } catch (Exception e) {
            throw new ServiceException("Unable to close connection", e);
        }
    }

}
