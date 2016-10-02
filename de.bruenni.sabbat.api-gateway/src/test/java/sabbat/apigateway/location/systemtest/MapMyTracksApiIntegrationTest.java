package sabbat.apigateway.location.systemtest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sabbat.apigateway.location.controller.dto.ActivityCreatedResponse;
import sabbat.apigateway.location.integrationtest.IntegrationTestConfig;

import java.util.UUID;

/**
 * Created by bruenni on 14.07.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { SystemTestConfig.class})
public class MapMyTracksApiIntegrationTest {

    @Value("${apigateway.mapmytracksapi.url}")
    public String ApiUrl;

    @Test
    public void When_start_activity_expect_returns_200_OK()
    {
        ResponseEntity<ActivityCreatedResponse> response = new MapMyTracksApiClient(ApiUrl).startActivity("MapMyTracksApiIntegrationTest title");

        Assert.assertEquals(200, response.getStatusCode().value());
        UUID.fromString(response.getBody().activityId);
        Assert.assertEquals("activity_started", response.getBody().type);
    }
}
