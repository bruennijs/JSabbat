package activity;

import account.User;
import notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import rx.Observable;
import sabbat.location.core.LocationCoreConfiguration;
import sabbat.location.infrastructure.AmqpClientAutoConfiguration;
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
	AmqpServiceAutoConfiguration.class})
@ComponentScan
public class GroupActivityIntegrationTestConfiguration {

	private static Logger Log = LoggerFactory.getLogger(GroupActivityIntegrationTestConfiguration.class);

	@Bean(name = "stubNotificationService")
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public NotificationService notificationService()
	{
		return new NotificationService() {
			public Observable notify(User user, String message) {
				Log.info("[%1s] -> [%2s]", user.toString(), message);
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
