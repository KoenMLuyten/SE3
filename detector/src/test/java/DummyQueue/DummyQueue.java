package DummyQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DummyQueue {

    private String connectionString;
    private String queueName;

    public DummyQueue(String connectionString, String queueName){
        this.connectionString = connectionString;
        this.queueName = queueName;
    }

    public void start(){
        Connection connection;
        Channel channel;
        try{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(connectionString);

            //Recommended settings
            factory.setRequestedHeartbeat(30);
            factory.setConnectionTimeout(30000);

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(queueName, false, false, false, null);
            String message1 = "<detectionMessage>\n" +
                    "  <rideId>1</rideId>\n" +
                    "  <blockNr>1</blockNr>\n" +
                    "  <timestamp>2017-11-02 16:14:28.399</timestamp>\n" +
                    "  <sectionId>1</sectionId>\n" +
                    "</detectionMessage>";

            String message2 = "<detectionMessage>\n" +
                    "  <rideId>1</rideId>\n" +
                    "  <blockNr>2</blockNr>\n" +
                    "  <timestamp>2017-11-02 16:14:28.399</timestamp>\n" +
                    "  <sectionId>1</sectionId>\n" +
                    "</detectionMessage>";
            channel.basicPublish("", queueName, null, message1.getBytes());
            channel.basicPublish("", queueName, null, message2.getBytes());

            channel.close();
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}
