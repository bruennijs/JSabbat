package integrationtest.account;

import account.IAccountService;
import account.User;
import com.okta.sdk.authc.credentials.ClientCredentials;
import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.ClientBuilder;
import com.okta.sdk.client.Clients;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.stream.Collectors;

/**
 * Created by bruenni on 15.06.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"testokta"})
@SpringApplicationConfiguration(classes = IntegrationTestConfig.class)
public class OktaAccountServiceTest {

	@Autowired
	@Qualifier("oktaAccountService")
	public IAccountService accountService;

	private static String userId = "00uau41fdzjgnUYSt0h7";

	@org.junit.Test
	public void getUserById_expect_properties_correct() throws Exception {

		// password Password2017

		User userById = accountService.getUserById(userId);

		Assert.assertThat(userById.getId(), IsEqual.equalTo("00uau41fdzjgnUYSt0h7"));
		Assert.assertThat(userById.getName(), IsEqual.equalTo("user@test.de"));
		Assert.assertThat(userById.getEmail(), IsEqual.equalTo("user@test.de"));

		//client.listUsers();
	}

	@org.junit.Test
	public void getUserById_expect_groups_are_correct() throws Exception {
		User userById = accountService.getUserById(userId);

		Assert.assertThat(userById.getGroupRefs().stream().map(gr -> gr.getId()).collect(Collectors.toList()),
			Matchers.hasItem(IsEqual.equalTo("00gau3v9ge9TFTTbq0h7")));
	}
}
