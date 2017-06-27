package sabbat.location.app;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * Created by bruenni on 03.07.16.
 */

@SpringBootApplication(exclude =
        {
                org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration.class,
                CassandraDataAutoConfiguration.class,
                org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration.class
        })
public class Application {

    static Logger logger = org.slf4j.LoggerFactory.getLogger("console");

    public static void main(String[] args) {

        Arrays.stream(args).forEach(arg -> logger.info("arg=" + arg));

        logger.info("Starting location app...");
        ApplicationContext applicationContext = SpringApplication.run(new Object[]
                {
                        Application.class,
                        AppConfig.class
                }, args);

        /*MessageListenerContainer messageListenerContainer = applicationContext.getBean(MessageListenerContainer.class);
        messageListenerContainer.start();*/

        //Session session = ((Cluster)applicationContext.getBean("cassandraCluster")).connect();
        //session.getCluster().getMetadata().getAllHosts().stream().forEach(c -> logger.info(c.toString()));
    }
}
