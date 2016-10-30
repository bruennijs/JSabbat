package identity.implementation;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.ApplicationList;
import com.stormpath.sdk.application.Applications;
import com.stormpath.sdk.authc.*;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.impl.authc.DefaultBasicAuthenticationOptions;
import com.stormpath.sdk.impl.cache.DefaultCacheManagerBuilder;
import com.stormpath.sdk.impl.tenant.DefaultTenantOptions;
import com.stormpath.sdk.resource.ResourceException;
import identity.GroupRef;
import identity.IAuthenticationService;
import identity.UserJwtBuilder;
import identity.UserRef;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.identity.ITokenAuthentication;
import infrastructure.identity.Jwt;
import infrastructure.identity.Token;
import infrastructure.util.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by bruenni on 16.10.16.
 */
public class StormpathAuthenticationService implements IAuthenticationService {

    private static Logger logger = LoggerFactory.getLogger(StormpathAuthenticationService.class);

    @Value("${stormpath.application.name}")
    public String ApplicationName;

    @Value("${sabbat.shared.jwt.ttl}")
    public long JwtTtl = 3600;

    @Value("${stormpath.cache.ttl}")
    public long CacheTtl = 3600;


    private Client client;
    private ITokenAuthentication tokenAuthentication;

    /**
     * Constructor
     */
    public StormpathAuthenticationService(ITokenAuthentication tokenAuthentication)
    {
        this.tokenAuthentication = tokenAuthentication;
        Initialize();
    }

    private void Initialize() {

        //HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient();

        //CacheManager cacheManager = new HazelcastCacheManager(hazelcastInstance);

        this.client = Clients.builder()
                //.setApiKey(ApiKeys.builder().build())
                //.setCacheManager(cacheManager)
                .setCacheManager(new DefaultCacheManagerBuilder().withDefaultTimeToLive(CacheTtl, TimeUnit.SECONDS).build())
                .build();
    }

    @Override
    public UserRef verify(Token token) throws AuthenticationFailedException {

        if (logger.isDebugEnabled())
            logger.debug(String.format("Verifying token [%1s]", token.getValue()));

        Jwt jwt = this.tokenAuthentication.verify(token);

        String userName = new UserJwtBuilder().fromJwt(jwt).getUserName();
        List<String> groupNames = new UserJwtBuilder().fromJwt(jwt).getGroupNames();

        return new UserRef(userName, userName, groupNames.stream().map(GroupRef::new).collect(Collectors.toList()));
    }

    @Override
    public Token authenticate(String userName, String password) throws AuthenticationFailedException {

        if (logger.isDebugEnabled())
            logger.debug(String.format("Authenticating username [%1s]", userName));

        Application application = getApplication();

        AuthenticationRequest request = UsernamePasswordRequests.builder()
                .setUsernameOrEmail(userName)
                .setPassword(password)
                .withResponseOptions(new DefaultBasicAuthenticationOptions().withAccount())
                .build();

        try
        {
            AuthenticationResult result = application.authenticateAccount(request);

            Account account = result.getAccount();
            List<String> groupMemberNames = getGroupMemberNames(account);

            Jwt jwt = CreateJwt(userName, groupMemberNames);

            return this.tokenAuthentication.create(jwt);
        }
        catch (ResourceException resourceException)
        {
            logger.error("Authentication failed on user [" + userName + "]", resourceException);
            throw new AuthenticationFailedException("Authentication failed on user [" + userName + "]", resourceException);
        }

        //Account account = getAccount(userName);

        //new BasicAuthenticator().authenticate(new DefaultUsernamePasswordRequest(userName, password.toCharArray()));
        //new OAuthPasswordRequestAuthenticatorFactory().forApplication(null).authenticate()

        //accountOpt.get().getGroupMemberships().forEach(gms -> gms.getGroup().)

        //return Token.valueOf();
    }

    private Jwt CreateJwt(String userName, List<String> groupMemberNames) {
        Instant iat = Instant.now(Clock.systemUTC());
        Instant exp = iat.plus(JwtTtl, ChronoUnit.SECONDS);

        return new UserJwtBuilder().withData(userName, groupMemberNames, iat, exp).build();
    }

    private List<String> getGroupMemberNames(Account account) {
        return IterableUtils.stream(account.getGroups()).map(group -> group.getName()).collect(Collectors.toList());
    }

    private Application getApplication() {
        ApplicationList applications = this.client.getApplications(Applications.where(Applications.name().eqIgnoreCase(ApplicationName)));
        return applications.single();
    }

    private Account getAccount(String userName) throws Exception {
        HashMap<String, Object> retrieveMap = new HashMap<>();
        retrieveMap.put("expand", "groups");

        AccountList accounts = this.client.getCurrentTenant(new DefaultTenantOptions().withAccounts()).getAccounts(retrieveMap);
        Stream<Account> accountStream = IterableUtils.stream(accounts);
        Optional<Account> accountOpt = accountStream.filter(account -> account.getUsername().equals(userName)).findFirst();
        if (!accountOpt.isPresent())
            throw new Exception("Account rearding");

        return accountOpt.get();
    }
}
