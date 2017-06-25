package configuration;

import account.IAccountService;
import account.OktaAccountService;
import com.okta.sdk.client.Client;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by bruenni on 15.06.17.
 */
@Configuration
@Profile({"dev"})
@PropertySource(
		value = {"classpath:sabbat-shared-okta-dev.properties"},
		ignoreResourceNotFound = true)
@Import(CacheConfiguration.class)
public class SabbatSharedOktaDevAutoConfiguration {

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySource()
	{
		return new PropertySourcesPlaceholderConfigurer();
	}
}
