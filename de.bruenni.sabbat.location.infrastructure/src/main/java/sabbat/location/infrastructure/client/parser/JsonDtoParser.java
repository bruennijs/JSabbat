package sabbat.location.infrastructure.client.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by bruenni on 28.08.16.
 */
public class JsonDtoParser implements IDtoParser {
    private final ObjectMapper jsonMapper;

    public JsonDtoParser() {
        this.jsonMapper = new ObjectMapper();
    }

    /**
     * Serializes object to json.
     * @param object
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public <T> String serialize(T object) throws JsonProcessingException {
        return this.jsonMapper.writeValueAsString(object);
    }

    /**
     * Parses dto string to POJO instance.
     * @param dto
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    @Override
    public <T> T parse(String dto, Class<T> type) throws IOException {
        return (T)this.jsonMapper.readValue(dto, type);
    }
}
