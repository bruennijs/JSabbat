package activity;

import account.User;
import notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import rx.Observable;
import sabbat.location.core.LocationCoreConfiguration;


/**
 * Created by bruenni on 01.06.17.
 */
@Configuration
@Profile("test")
@ImportResource(locations =
	{
		"classpath:spring/spring-location-integrationtest-alias.xml"
	})
//@Import(LocationCoreConfiguration.class)
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
