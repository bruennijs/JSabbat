package sabbat.apigateway.location.unittest;

import infrastructure.util.IterableUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import sabbat.apigateway.location.builder.LocationApiDtoConverterBuilder;
import sabbat.apigateway.location.controller.converter.LocationApiDtoConverter;
import sabbat.location.api.dto.ActivityUpdateEventDto;
import sabbat.location.api.dto.TimeSeriesCoordinate;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by bruenni on 02.10.16.
 */
@RunWith(Parameterized.class)
public class LocationApiDtoConverterTest {

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]
                {
                        //// format: latitude longitude height timestamp(utc)
                        { "53.15081779 8.22885028 6.044 1475422138",  Arrays.asList(new TimeSeriesCoordinate(new Date(1475422138000l), 53.15081779d, 8.22885028d)) },
                        { "53.15081779 8.22885028    6.044 1475422138",  Arrays.asList(new TimeSeriesCoordinate(new Date(1475422138000l), 53.15081779d, 8.22885028d)) },
                        { "89.779 166.2212 100.4 1475422138",  Arrays.asList(new TimeSeriesCoordinate(new Date(1475422138000l), 89.779d, 166.2212d)) },
                        { "87.779 155.2212 100.4 1475422159 99.9 111.2212 2560.4 1475422177",  Arrays.asList(new TimeSeriesCoordinate(Date.from(Instant.ofEpochSecond(1475422159)), 87.779d, 155.2212d),
                                                                                                             new TimeSeriesCoordinate(Date.from(Instant.ofEpochSecond(1475422177)), 99.9d, 111.2212d)) }
                });
    }

    @Parameterized.Parameter(0)
    public String actualPoints;

    @Parameterized.Parameter(1)
    public List<TimeSeriesCoordinate> expectedTimeSeriesList;

    @Test
    public void when_create_activityupdateeventdto_expect_points_parsed_correctly() throws Exception {
        LocationApiDtoConverter sut = new LocationApiDtoConverterBuilder().build();

        ActivityUpdateEventDto dto = sut.transformUpdateEvent("", actualPoints);

        List<TimeSeriesCoordinate> actualTimeSeriesList = IterableUtils.toList(dto.getTimeSeries());
        Assert.assertEquals(expectedTimeSeriesList.size(), actualTimeSeriesList.size());

        Assert.assertTrue("parsed timeseries list not as expected" + dto, actualTimeSeriesList.stream().allMatch(actual -> expectedTimeSeriesList.contains(actual)));
    }
}
