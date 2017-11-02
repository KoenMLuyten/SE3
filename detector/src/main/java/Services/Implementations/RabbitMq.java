package Services;

import Domain.MessageListener;
import Domain.ServiceExeption;
import ServiceInterfaces.MessageFormatter;
import com.rabbitmq.client.*;
import ServiceInterfaces.IMessageService;

import java.io.IOException;
import java.util.ArrayList;

public class RabbitMq implements IMessageService {

    private final String connectionString = "amqp://hjnwvnem:IZWi2g4eMtJlxOVGMNbMkvzYvuu7patt@elephant.rmq.cloudamqp.com/hjnwvnem";
    private final String queueName;
    private final MessageFormatter formatter;

    public RabbitMq(String connectionString, String queueName, MessageFormatter formatter) {
        //this.connectionString = connectionString;
        this.queueName = queueName;
        this.formatter = formatter;
    }

    private Connection connection;
    private Channel channel;
    @Override
    public void initialize(MessageListener listener) throws ServiceExeption {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(connectionString);

            //Recommended settings
            factory.setRequestedHeartbeat(30);
            factory.setConnectionTimeout(30000);

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(queueName,
                    false, /* non-durable */
                    false, /* non-exclusive */
                    false, /* do not auto delete */
                    null); /* no other construction arguments */

            //logger.info("Using uri '" + connectionString + "'");
            //logger.info("Using queue '" + queueName + "'");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    //logger.info("Received message from RabbitMQ queue " + queueName);
                    String content = new String(body, "UTF-8");
                    //logger.debug("Message content: " + content);
                    if (listener != null) {
                        try {
                            listener.onReceive(formatter.format(content));
                            //logger.info("Delivered message to listener");
                        } //catch (ServiceExeption e) {
                            //logger.error("Exception during format conversion", e);
                            //listener.onError(e); // log & throw anti-pattern....}
                        catch (Exception e) {
                            //logger.error("Exception during callback to listener", e);
                        }
                    }
                }
            };
            channel.basicConsume(queueName, true, consumer);

        } catch (Exception e) {
            throw new ServiceExeption("Error during RabbitMQ channel initialisation", e);
        }
    }

    public void shutDown() throws ServiceExeption {
        try {
            channel.close();
            connection.close();
        } catch (Exception e) {
            throw new ServiceExeption("Unable to close connection to RabbitMQ", e);
        }
    }
}


