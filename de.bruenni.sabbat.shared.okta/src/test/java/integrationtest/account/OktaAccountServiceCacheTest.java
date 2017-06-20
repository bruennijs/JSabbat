package integrationtest.account;

import account.IAccountService;
import account.User;
import identity.GroupRef;
import identity.UserRef;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rx.Subscription;

import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 15.06.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"testokta"})
@SpringApplicationConfiguration(classes = IntegrationTestConfig.class)
public class OktaAccountServiceCacheTest {

	Logger log = LoggerFactory.getLogger(OktaAccountServiceCacheTest.class);

	@Autowired
	@Qualifier("oktaAccountService")
	public IAccountService accountService;

	private static String userId = "00uau41fdzjgnUYSt0h7";
	private String GROUP_ID = "00gau3v9ge9TFTTbq0h7";

	@org.junit.Test
	public void getUserById_expect_properties_correct() throws Exception {

		// password Password2017
		Long list = rx.Observable
			.interval(2, TimeUnit.SECONDS)
			.take(60)
			.doOnEach(l -> {
				log.debug(String.format("%1s [%2s]", l.toString(), accountService.getUserById(userId).getEmail()));
			})
			.toBlocking().last();

		//client.listUsers();
	}
}
