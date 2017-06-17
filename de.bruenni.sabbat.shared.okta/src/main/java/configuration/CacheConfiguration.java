package configuration;

import account.IAccountService;
import account.OktaAccountService;
import com.okta.sdk.client.Client;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Arrays;

/**
 * Created by bruenni on 15.06.17.
 */
@Configuration
public class CacheConfiguration {

	@Bean(name = "defaultCacheManager")
	public CacheManager cacheManager()

	{
		SimpleCacheManager cache = new SimpleCacheManager();
		cache.setCaches(Arrays.asList(new ConcurrentMapCache("users"), new ConcurrentMapCache("groups")));
		return cache;
	}
}
