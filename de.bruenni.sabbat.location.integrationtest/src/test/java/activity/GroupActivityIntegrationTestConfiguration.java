package activity;

import notification.NotificationContent;
import notification.NotificationMessage;
import notification.UserNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import rx.Observable;
import sabbat.location.api.AmqpClientAutoConfiguration;
import sabbat.location.infrastructure.AmqpServiceAutoConfiguration;
import sabbat.location.infrastructure.CassandraAutoConfiguration;


/**
 * Created by bruenni on 01.06.17.
 */
@Configuration
@ImportResource(locations =
	{
		"classpath:spring/spring-location-integrationtest-alias.xml"
	})
@EnableAutoConfiguration(exclude = {
	CassandraDataAutoConfiguration.class,
	CassandraAutoConfiguration.class,
        AmqpClientAutoConfiguration.class,
	AmqpServiceAutoConfiguration.class,
	org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration.class})
public class GroupActivityIntegrationTestConfiguration {

	private static Logger Log = LoggerFactory.getLogger(GroupActivityIntegrationTestConfiguration.class);

	@Bean(name = "stubNotificationService")
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public UserNotificationService notificationService()
	{
		return new UserNotificationService() {
			@Override
			public Observable<NotificationMessage<? extends NotificationContent>> notify(NotificationMessage<? extends NotificationContent> message) {
				Log.info(String.format("[%1s]",  message));
				return Observable.empty();
			}
		};
	}

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySource()
	{
		return new PropertySourcesPlaceholderConfigurer();
	}
}
