package sabbat.location.app;

import org.springframework.boot.SpringApplication;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import sabbat.location.infrastructure.RabbitMqQueueBinder;

import java.util.Arrays;

/**
 * Created by bruenni on 03.07.16.
 */

//@SpringBootApplication    // contains componentscan to find @Configuratuion annotated class -> instead give spring boot these classes by calling SpringApplication.run(...) with these classes
@EnableAutoConfiguration
//@ComponentScan(basePackages = "sabbat.apigateway.location.config")
public class Application {

    static Logger logger = org.slf4j.LoggerFactory.getLogger("location.console");

    public static void main(String[] args) {

        Arrays.stream(args).forEach(arg -> logger.info("arg=" + arg));

        logger.info("Starting location app...");
        ApplicationContext applicationContext = SpringApplication.run(new Object[]
                {
                        Application.class,
                        AppConfig.class
                }, args);

/*        SimpleMessageListenerContainer container = (SimpleMessageListenerContainer) applicationContext.getBean(SimpleMessageListenerContainer.class);
        container.start();*/

        /*logger.info("Bind queues to exchanges...");*/

/*        RabbitMqQueueBinder binder = (RabbitMqQueueBinder) applicationContext.getBean("activityCommandBinder");
        binder.bindQueuesToExchanges();*/
    }
}
