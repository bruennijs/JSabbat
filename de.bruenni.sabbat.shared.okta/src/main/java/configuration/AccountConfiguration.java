package configuration;

import account.IAccountService;
import account.OktaAccountService;
import com.okta.sdk.client.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by bruenni on 15.06.17.
 */
@Configuration
@Import(CacheConfiguration.class)
public class AccountConfiguration {

	@Bean(name = "oktaAccountService")
	public IAccountService oktaAccountService(Client oktaApiClient)
	{
		return new OktaAccountService(oktaApiClient);
	}

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySource()
	{
		return new PropertySourcesPlaceholderConfigurer();
	}
}
