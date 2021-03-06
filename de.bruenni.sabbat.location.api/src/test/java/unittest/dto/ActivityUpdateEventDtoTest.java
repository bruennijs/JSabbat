package unittest.dto;


import builder.ActivityUpdateEventDtoBuilder;
import infrastructure.parser.JsonDtoParser;
import infrastructure.util.IterableUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabbat.location.api.dto.ActivityUpdateEventDto;
import sabbat.location.api.dto.TimeSeriesCoordinate;

import java.time.Instant;
import java.util.Date;

/**
 * Created by bruenni on 08.10.16.
 */
@RunWith(JUnit4.class)
public class ActivityUpdateEventDtoTest {

    Logger logger = LoggerFactory.getLogger(ActivityUpdateEventDtoTest.class);

    @Test()
    public void when_create_activityupdateeventdto_expect_date_parsed() throws Exception {
        //String iso8601TimeStamp = "2016-05-31T23:59:59Z";

        Instant instantTimeStamp = Instant.ofEpochSecond(1475923152);

        Date timeStamp = Date.from(instantTimeStamp);

        ActivityUpdateEventDto dto = new ActivityUpdateEventDtoBuilder()
                .withTimeSeries(new TimeSeriesCoordinate(timeStamp, 0.0, 0.0))
                .build();

        JsonDtoParser parser = new JsonDtoParser();
        String dtoString = parser.serialize(dto);

        logger.debug(dtoString);

        ActivityUpdateEventDto parsedDto = parser.parse(dtoString, ActivityUpdateEventDto.class);

        Assert.assertEquals(dto, parsedDto);

        Date serializedDtoTimestamp = IterableUtils.stream(parsedDto.getTimeSeries()).findFirst().get().getTimestamp();
        Assert.assertEquals(instantTimeStamp, serializedDtoTimestamp.toInstant());

        //// expect string containing the ISO8601 Z format
/*        ZonedDateTime zdtUtc = ZonedDateTimeExtensions.ofUtcInstant(instantTimeStamp);
        String zdtUtcIsoString = ZonedDateTimeExtensions.toIso9601ZeroString(zdtUtc);

        Assert.assertTrue(dtoString.contains(zdtUtcIsoString));*/
    }
}
