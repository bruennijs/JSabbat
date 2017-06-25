package sabbat.apigateway.location.unittest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sabbat.apigateway.location.builder.LocationApiDtoConverterBuilder;
import sabbat.apigateway.location.controller.converter.LocationApiDtoConverter;
import sabbat.apigateway.location.controller.dto.ActivityCreatedResponse;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;

import java.util.Date;

/**
 * Created by bruenni on 02.10.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { UnitTestConfig.class })
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
