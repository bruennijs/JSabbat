package unittest.dto;

import infrastructure.parser.JsonDtoParser;
import infrastructure.parser.ParserException;
import infrastructure.parser.SerializingException;
import org.junit.Assert;
import org.junit.Test;
import sabbat.location.api.dto.ActivityCreatedResponseDto;

/**
 * Created by bruenni on 01.10.16.
 */
public class ActivityCreatedResponseDtoTest {
    @Test
    public void When_serialize_expect_string_as_expected() throws SerializingException {
        ActivityCreatedResponseDto dto = new ActivityCreatedResponseDto("someid");
        JsonDtoParser jsonDtoParser = new JsonDtoParser();
        String dtoString = jsonDtoParser.serialize(dto);

        Assert.assertEquals("{\"id\":\"someid\"}", dtoString);
    }

    @Test
    public void When_serialize_and_parse_expect_same_object() throws SerializingException, ParserException {
        ActivityCreatedResponseDto dto = new ActivityCreatedResponseDto("someid");
        JsonDtoParser jsonDtoParser = new JsonDtoParser();
        ActivityCreatedResponseDto dtoParsed = jsonDtoParser.parse(jsonDtoParser.serialize(dto), ActivityCreatedResponseDto.class);

        Assert.assertEquals(dto.getId(), dtoParsed.getId());
    }
}
