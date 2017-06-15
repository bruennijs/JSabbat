package sabbat.apigateway.location.systemtest;

import infrastructure.util.Tuple2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mockito.internal.matchers.GreaterThan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.geo.Point;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sabbat.apigateway.Application;
import sabbat.apigateway.location.config.WebConfig;
import sabbat.apigateway.location.controller.dto.ActivityCreatedResponse;
import sabbat.apigateway.location.controller.dto.ActivityStoppedResponse;
import sabbat.apigateway.location.controller.dto.ActivityUpdatedResponse;
import sabbat.apigateway.location.integrationtest.IntegrationTestConfig;
import sabbat.apigateway.location.unittest.UnitTestConfig;
import test.matcher.LambdaMatcher;

import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * Created by bruenni on 14.07.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "test")
@SpringApplicationConfiguration(classes = { SystemTestConfig.class })
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
        Assert.assertThat(response.getBody().activityId, new LambdaMatcher<>(id -> id>0, "activity_id_not > 0"));
        Assert.assertEquals("activity_started", response.getBody().type);
    }

    @Test
    public void When_stop_activity_expect_returns_200_OK()
    {
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.setIso(DateTimeFormat.ISO.DATE_TIME);

        ResponseEntity<ActivityCreatedResponse> startResponse = new MapMyTracksApiClient(ApiUrl).startActivity(String.format("%1s", dateFormatter.print(new Date(), Locale.ROOT)));

        ResponseEntity<ActivityStoppedResponse> stopResponse = new MapMyTracksApiClient(ApiUrl).stopActivity(startResponse.getBody().activityId);

        logger.debug(stopResponse.toString());

        Assert.assertEquals(200, stopResponse.getStatusCode().value());
        Assert.assertEquals("activity_stopped", stopResponse.getBody().type);
    }

    @Test
    public void When_update_activity_expect_returns_200_OK()
    {
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.setTimeZone(TimeZone.getDefault());
        dateFormatter.setIso(DateTimeFormat.ISO.DATE_TIME);

        ResponseEntity<ActivityCreatedResponse> startResponse = new MapMyTracksApiClient(ApiUrl).startActivity(String.format("%1s", dateFormatter.print(new Date(), Locale.ROOT)));

        List<Tuple2<Point, Date>> tuples = Arrays.asList(new Tuple2<>(new Point(8.2, 54.5454), new Date()));

        for (int i=0; i<50; i++) {
            ResponseEntity<ActivityUpdatedResponse> response = new MapMyTracksApiClient(ApiUrl).updateActivity(startResponse.getBody().activityId, tuples);

            logger.debug(response.toString());

            Assert.assertEquals(200, response.getStatusCode().value());
            Assert.assertEquals("activity_updated", response.getBody().type);
        }
    }
}
