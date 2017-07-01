package activity;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
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
	AmqpServiceAutoConfiguration.class})
@ComponentScan
public class NotificationIntegrationTestConfiguration {

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySource()
	{
		return new PropertySourcesPlaceholderConfigurer();
	}
}
