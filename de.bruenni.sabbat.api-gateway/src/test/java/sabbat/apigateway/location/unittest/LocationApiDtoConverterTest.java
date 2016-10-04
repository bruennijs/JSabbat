package sabbat.apigateway.location.unittest;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.data.geo.Point;
import sabbat.apigateway.location.builder.LocationApiDtoConverterBuilder;
import sabbat.apigateway.location.controller.converter.LocationApiDtoConverter;
import sabbat.location.infrastructure.client.dto.ActivityUpdateEventDto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by bruenni on 02.10.16.
 */
@RunWith(Parameterized.class)
public class LocationApiDtoConverterTest {

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]
                {
                        { "53.15081779 8.22885028 6.044 1475422138",  new Pair(new Date(1475422138000l), new Point(8.22885028d, 53.15081779d)) },
                        { "89.779 166.2212 100.4 1475422138",  new Pair(new Date(1475422138000l), new Point(166.2212d, 89.779d)) },
                        { "87.779 155.2212 100.4 1475422159 99.9 111.2212 2560.4 1475422177",  new Pair(new Date(1475422159000l), new Point(155.2212d, 87.779d)) }
                });
    }

    @Parameterized.Parameter(0)
    public String actualPoints;

    @Parameterized.Parameter(1)
    public Pair<Date, Point> expectedTuple;

    @Test
    public void when_create_activityupdateeventdto_expect_points_parsed_correctly() throws Exception {
        LocationApiDtoConverter sut = new LocationApiDtoConverterBuilder().build();

        ActivityUpdateEventDto dto = sut.transformUpdateEvent("", actualPoints);

        Assert.assertEquals(expectedTuple.getValue(), dto.getCoordinate());
    }

    @Test()
    public void when_create_activityupdateeventdto_expect_date_parsed() throws Exception {
        LocationApiDtoConverter sut = new LocationApiDtoConverterBuilder().build();

        ActivityUpdateEventDto dto = sut.transformUpdateEvent("", actualPoints);

        Assert.assertEquals(expectedTuple.getKey(), dto.getTimestamp());
    }
    
    @Test
    public void time()
    {
        System.out.println(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(1475563957000l)).toString());
        System.out.println(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(1475563987000l)).toString());
        System.out.println(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(1475609546000l)).toString());

    }
}
