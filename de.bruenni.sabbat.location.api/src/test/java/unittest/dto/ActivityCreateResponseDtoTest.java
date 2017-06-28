package unittest.dto;

import builder.ActivityCreateRequestDtoBuilder;
import infrastructure.parser.JsonDtoParser;
import infrastructure.parser.ParserException;
import infrastructure.parser.SerializingException;
import org.junit.Assert;
import org.junit.Test;
import sabbat.location.api.dto.ActivityCreateRequestDto;

/**
 * Created by bruenni on 01.10.16.
 */
public class ActivityCreateResponseDtoTest {
    @Test
    public void When_serialize_and_parse_expect_same_object() throws SerializingException, ParserException {
        ActivityCreateRequestDto dto = new ActivityCreateRequestDtoBuilder().build();
        JsonDtoParser jsonDtoParser = new JsonDtoParser();
        ActivityCreateRequestDto dtoParsed = jsonDtoParser.parse(jsonDtoParser.serialize(dto), ActivityCreateRequestDto.class);

        Assert.assertEquals(dto, dtoParsed);
    }
}
