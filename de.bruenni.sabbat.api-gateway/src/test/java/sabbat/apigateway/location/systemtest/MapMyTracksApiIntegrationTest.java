package sabbat.apigateway.location.systemtest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.GreaterThan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sabbat.apigateway.location.controller.dto.ActivityCreatedResponse;
import sabbat.apigateway.location.integrationtest.IntegrationTestConfig;
import test.matcher.LambdaMatcher;

import java.util.UUID;

/**
 * Created by bruenni on 14.07.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { SystemTestConfig.class})
public class MapMyTracksApiIntegrationTest {

    Logger logger = LoggerFactory.getLogger(MapMyTracksApiIntegrationTest.class);

    @Value("${apigateway.mapmytracksapi.url}")
    public String ApiUrl;

    @Test
    public void When_start_activity_expect_returns_200_OK()
    {
        ResponseEntity<ActivityCreatedResponse> response = new MapMyTracksApiClient(ApiUrl).startActivity("MapMyTracksApiIntegrationTest title");

        logger.debug(response.toString());

        Assert.assertEquals(200, response.getStatusCode().value());
        Assert.assertThat(response.getBody().activityId, new LambdaMatcher<>(id -> !id.isEmpty(), "is empty"));
        Assert.assertEquals("activity_started", response.getBody().type);
    }
}
