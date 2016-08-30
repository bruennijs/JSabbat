package sabbat.location.infrastructure.client.parser;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

/**
 * Created by bruenni on 28.08.16.
 */
public interface IDtoParser {
    <T> String serialize(T object) throws JsonProcessingException;

    <T> T parse(String dto, Class<T> type) throws IOException;
}
