package Services.Implementations;

import Domain.MessageListener;
import Domain.MessageRecorder;
import Domain.Messages.IncomingMessageDTO;
import Domain.ServiceException;
import Services.Interfaces.MessageFormatter;
import com.rabbitmq.client.*;
import Services.Interfaces.IncomingMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class IncomingRabbitMq implements IncomingMessageService {

    private final String connectionString;
    private final MessageFormatter formatter;
    private Connection connection;
    private Channel channel;
    private Logger logger = LoggerFactory.getLogger(IncomingRabbitMq.class);
    private MessageRecorder recorder;

    public IncomingRabbitMq(String connectionString, String queueName, MessageFormatter formatter) {
        this.connectionString = connectionString;
        this.formatter = formatter;
    }

    @Override
    public void setRecorder(MessageRecorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void initialize(MessageListener listener, String queueName) throws ServiceException {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(connectionString);

            //Recommended settings
            factory.setRequestedHeartbeat(30);
            factory.setConnectionTimeout(30000);

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(queueName, false, false, false, null);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException { ;
                    logger.info("Received message from RabbitMQ queue " + queueName);
                    String content = new String(body, "UTF-8");
                    if (recorder != null){
                        recorder.record(content);
                    }
                    if (listener != null) {
                        try {
                            IncomingMessageDTO messageDTO = formatter.format(content);
                            listener.onReceive(messageDTO);
                            logger.info("Delivered message to listener");
                        } catch (ServiceException e) {
                            logger.error("Exception during format conversion", e);
                        }
                        catch (Exception e) {
                            logger.error("Exception during callback to listener", e);
                        }
                    }
                }
            };
            channel.basicConsume(queueName, true , consumer);

        } catch (Exception e) {
            throw new ServiceException("Error during RabbitMQ channel initialisation", e);
        }
    }

    public void shutDown() throws ServiceException {
        try {
            channel.close();
            connection.close();
        } catch (Exception e) {
            throw new ServiceException("Unable to close connection to RabbitMQ", e);
        }
    }
}



