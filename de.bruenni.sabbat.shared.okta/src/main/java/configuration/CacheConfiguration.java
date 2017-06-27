package configuration;


import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.time.Duration;
import java.util.Arrays;

/**
 * Created by bruenni on 15.06.17.
 */
@SpringBootConfiguration
@EnableCaching
public class CacheConfiguration {

	@Value(value = "${sabbat.shared.okta.cache.ttlInSeconds}")
	public long ttlInSeconds;

	@Bean(name = "cacheManager")
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public CacheManager cacheManager()
	{
		Duration ttlDuration = Duration.ofSeconds(ttlInSeconds);

		//Caffeine.from(CaffeineSpec.parse("some key value pair https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-caching.html#boot-features-caching-provider-caffeine"))
		Caffeine<Object, Object> caffeineConfig = Caffeine.newBuilder()
			.expireAfter(new Expiry<Object, Object>() {
				@Override
				public long expireAfterCreate(Object o, Object o2, long l) {
					return ttlDuration.toNanos();
				}

				@Override
				public long expireAfterUpdate(Object o, Object o2, long l, long durationInNs) {
					return durationInNs;
				}

				@Override
				public long expireAfterRead(Object o, Object o2, long currentTimeInNs, long durationInNs) {
					//return Math.max(0, ttlDuration.minus(Duration.ofNanos(durationInNs)).toNanos());
					//return ttlDuration.minus(Duration.ofNanos(durationInNs)).toNanos();
					return durationInNs;
				}
			});
			//.expireAfterAccess(10, TimeUnit.SECONDS);

		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.setCaffeine(caffeineConfig);
		caffeineCacheManager.setCacheNames(Arrays.asList("users", "groups"));
		return caffeineCacheManager;
	}

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySource()
	{
		return new PropertySourcesPlaceholderConfigurer();
	}

/*	@Bean(name = "simpleCacheManager")
	public CacheManager simpleCacheManager()

	{
		SimpleCacheManager cache = new SimpleCacheManager();
		cache.setCaches(Arrays.asList(new ConcurrentMapCache("users"), new ConcurrentMapCache("groups")));
		return cache;
	}*/
}
