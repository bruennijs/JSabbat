package identity.implementation;

import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.ApplicationList;
import com.stormpath.sdk.application.Applications;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.impl.cache.DefaultCacheManagerBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.TimeUnit;

/**
 * Created by bruenni on 30.10.16.
 */
public class StormpathFactory {

    @Value("${stormpath.cache.ttl}")
    public long CacheTtl = 3600;
    private Client client;

    /**
     * Connects to stormpath server
     */
    public void connect()
    {
        if (this.client == null) {
            this.client = Clients.builder()
                    //.setApiKey(ApiKeys.builder().build())
                    //.setCacheManager(cacheManager)
                    .setCacheManager(new DefaultCacheManagerBuilder().withDefaultTimeToLive(CacheTtl, TimeUnit.SECONDS).build())
                    .build();
        }
    }

    /**
     * Creates stormpath application
     * @param applicationName
     * @return
     */
    public Application getApplication(String applicationName)
    {
        ApplicationList applications = this.client.getApplications(Applications.where(Applications.name().eqIgnoreCase(applicationName)));
        return applications.single();
    }
}
