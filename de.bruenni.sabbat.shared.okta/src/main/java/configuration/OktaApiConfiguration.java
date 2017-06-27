package configuration;

import com.okta.sdk.authc.credentials.ClientCredentials;
import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.ClientBuilder;
import com.okta.sdk.client.Clients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by bruenni on 15.06.17.
 */
@SpringBootConfiguration
public class OktaApiConfiguration {

	@Value("${sabbat.shared.okta.apitoken}")
	public String apiToken;

	@Value("${sabbat.shared.okta.url}")
	public String url;

	@Bean(name = "oktaApiClient")
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public Client oktaApiClient()
	{
		return getClient();
	}


	private Client getClient() {
		ClientCredentials clientCredentials = new TokenClientCredentials(apiToken);

		// Instantiate a builder for your Client. If needed, settings like Proxy and Caching can be defined here.
		ClientBuilder builder = Clients.builder();

// No need to define anything else; build the Client instance. The ClientCredential information will be automatically found
// in pre-defined locations.
		return builder
			.setOrgUrl(url)
			.setConnectionTimeout(30000)
			.setClientCredentials(clientCredentials).build();
	}
}
