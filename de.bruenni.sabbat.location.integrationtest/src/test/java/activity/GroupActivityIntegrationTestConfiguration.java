package activity;

import account.User;
import infrastructure.util.Tuple2;
import notification.NotificationContent;
import notification.NotificationMessage;
import notification.UserNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import rx.Observable;
import rx.internal.schedulers.ExecutorScheduler;
import rx.subjects.AsyncSubject;
import rx.subjects.Subject;
import sabbat.location.core.LocationCoreConfiguration;
import sabbat.location.infrastructure.AmqpClientAutoConfiguration;
import sabbat.location.infrastructure.AmqpServiceAutoConfiguration;
import sabbat.location.infrastructure.CassandraAutoConfiguration;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


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
	public UserNotificationService notificationService()
	{
		return new UserNotificationService() {
			@Override
			public Observable<NotificationMessage<? extends NotificationContent>> notify(NotificationMessage<? extends NotificationContent> message) {
				Log.info("[%1s]",  message);
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
