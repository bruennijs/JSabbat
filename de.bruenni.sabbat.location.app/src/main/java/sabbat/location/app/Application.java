package sabbat.location.app;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.ApplicationContext;
import sabbat.location.core.LocationCoreConfiguration;
import sabbat.location.infrastructure.AmqpClientAutoConfiguration;
import sabbat.location.infrastructure.AmqpServiceAutoConfiguration;

import java.util.Arrays;

/**
 * Created by bruenni on 03.07.16.
 */

@SpringBootApplication(exclude = { org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration.class, CassandraDataAutoConfiguration.class })
public class Application {

    static Logger logger = org.slf4j.LoggerFactory.getLogger("console");

    public static void main(String[] args) {

        Arrays.stream(args).forEach(arg -> logger.info("arg=" + arg));

        logger.info("Starting location app...");
        ApplicationContext applicationContext = SpringApplication.run(new Object[]
                {
                        Application.class,
                        AppConfig.class,
                        sabbat.location.infrastructure.CassandraAutoConfiguration.class,
                        LocationCoreConfiguration.class,
                        AmqpServiceAutoConfiguration.class
                }, args);

        //Session session = ((Cluster)applicationContext.getBean("cassandraCluster")).connect();
        //session.getCluster().getMetadata().getAllHosts().stream().forEach(c -> logger.info(c.toString()));
    }
}
