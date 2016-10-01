package sabbat.location.infrastructure.unittest.dto;

import infrastructure.parser.JsonDtoParser;
import org.junit.Assert;
import org.junit.Test;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;

import java.io.IOException;

/**
 * Created by bruenni on 01.10.16.
 */
public class ActivityCreateResponseDtoTest {
    @Test
    public void When_serialize_and_parse_expect_same_object() throws IOException {
        ActivityCreateRequestDto dto = new ActivityCreateRequestDto("someid", "title text");
        JsonDtoParser jsonDtoParser = new JsonDtoParser();
        ActivityCreateRequestDto dtoParsed = jsonDtoParser.parse(jsonDtoParser.serialize(dto), ActivityCreateRequestDto.class);

        Assert.assertEquals(dto, dtoParsed);
    }

}
