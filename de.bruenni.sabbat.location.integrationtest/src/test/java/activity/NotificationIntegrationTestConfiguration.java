package activity;

import account.User;
import infrastructure.util.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import rx.Observable;
import rx.subjects.AsyncSubject;
import sabbat.location.infrastructure.AmqpClientAutoConfiguration;
import sabbat.location.infrastructure.AmqpServiceAutoConfiguration;
import sabbat.location.infrastructure.CassandraAutoConfiguration;

import java.util.concurrent.Executor;


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
public class NotificationIntegrationTestConfiguration {

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySource()
	{
		return new PropertySourcesPlaceholderConfigurer();
	}
}
