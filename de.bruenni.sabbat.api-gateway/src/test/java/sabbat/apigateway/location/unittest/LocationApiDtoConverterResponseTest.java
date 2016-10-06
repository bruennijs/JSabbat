package sabbat.apigateway.location.unittest;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sabbat.apigateway.location.builder.LocationApiDtoConverterBuilder;
import sabbat.apigateway.location.controller.converter.LocationApiDtoConverter;
import sabbat.apigateway.location.controller.dto.ActivityCreatedResponse;
import sabbat.apigateway.location.controller.dto.MapMyTracksResponse;
import sabbat.apigateway.location.systemtest.SystemTestConfig;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;
import sabbat.location.infrastructure.client.dto.ActivityUpdateEventDto;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * Created by bruenni on 02.10.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { UnitTestConfig.class })
public class LocationApiDtoConverterResponseTest {

    @Test
    public void when_transform_activitycreatedresponsedto_expect_mapmytracksdto_contains_type_and_id() throws Exception {
        LocationApiDtoConverter sut = new LocationApiDtoConverterBuilder().build();

        long time = new Date().getTime();
        ActivityCreatedResponseDto dto = new ActivityCreatedResponseDto(String.format("%1d", time));

        ActivityCreatedResponse activityCreatedResponse = (ActivityCreatedResponse) sut.transformResponse(dto);

        Assert.assertEquals(time, activityCreatedResponse.activityId);
        Assert.assertEquals("activity_started", activityCreatedResponse.type);
    }
}
