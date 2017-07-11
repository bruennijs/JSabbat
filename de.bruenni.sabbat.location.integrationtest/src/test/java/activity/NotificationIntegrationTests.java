package activity;

import account.User;
import identity.UserJwtBuilder;
import infrastructure.identity.Jwt;
import infrastructure.identity.Token;
import notification.NotificationContent;
import notification.NotificationMessage;
import notification.TextNotificationContent;
import notification.UserNotificationService;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rx.Observable;
import rx.observables.BlockingObservable;
import sabbat.location.core.application.service.ActivityApplicationService;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.domain.model.Activity;
import test.matcher.LambdaMatcher;

import javax.xml.soap.Text;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


/**
 * Created by bruenni on 01.06.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"test"})
@SpringBootTest(classes = NotificationIntegrationTestConfiguration.class)
public class NotificationIntegrationTests {
	@Autowired
	@Qualifier("emailUserNotificationService")
	public UserNotificationService notificationService;

    @Value("${sabbat.location.integrationtest.userid}")
	private static String userId = "00uau41fdzjgnUYSt0h7";

	@Autowired
	public ApplicationContext ctx;

	@Test
	public void when_start_activity_expect_spring_context_fires_event_to_eventlistener_of_GroupActivityApplicationService() throws Exception {

        User user = createUser();

        NotificationMessage<TextNotificationContent> message = new NotificationMessage<>(user, "this is the subject", new TextNotificationContent("text content of the email [NotificationIntegrationTests]"));

        Observable<NotificationMessage<? extends NotificationContent>> result = notificationService.notify(message);

        //Observable.interval(1, TimeUnit.SECONDS)

        BlockingObservable<NotificationMessage<? extends NotificationContent>> resultBlocking = result.take(1)
            .timeout(10, TimeUnit.SECONDS)
            .toBlocking();

        NotificationMessage<? extends NotificationContent> responseMessage = resultBlocking.single();

        Assert.assertThat(responseMessage, LambdaMatcher.<NotificationMessage<? extends NotificationContent>>isMatching(msg -> msg.getId().equals(responseMessage.getId())));
    }

    private User createUser() {
        return new User(userId, "name", "oliver.bruentje@gmx.de", Arrays.asList());
    }
}
