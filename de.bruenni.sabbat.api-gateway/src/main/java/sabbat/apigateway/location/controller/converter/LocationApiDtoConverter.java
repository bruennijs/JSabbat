package sabbat.apigateway.location.controller.converter;

import com.sun.javafx.binding.StringFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabbat.apigateway.location.controller.dto.ActivityCreatedResponse;
import sabbat.apigateway.location.controller.dto.ActivityStoppedResponse;
import sabbat.apigateway.location.controller.dto.MapMyTracksResponse;
import sabbat.location.api.dto.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by bruenni on 24.07.16.
 */
public class LocationApiDtoConverter {

    Logger log = LoggerFactory.getLogger(LocationApiDtoConverter.class);

    /**
     * Points format: '<latitide> <longitude> <altitude> <timestamp>'
     * Example: '53.15081779 8.22885028 6.044 1475422138'
     * @param id
     * @param points
     * @return
     * @throws Exception
     */
    public ActivityUpdateEventDto transformUpdateEvent(String id, String points) throws Exception {

        LinkedList<TimeSeriesCoordinate> list = new LinkedList<>();

        String[] pointTokens = SplitTokens(points);

        parseTimeSeriesBlocksRecursive(pointTokens, list);

        return new ActivityUpdateEventDto(id, "", list);
    }

    private void parseTimeSeriesBlocksRecursive(String[] tokens, LinkedList<TimeSeriesCoordinate> list) throws Exception {

        if (tokens.length >= 4) {
            TimeSeriesCoordinate timeSeriesCoordinate = ParseTimeSeries(tokens);

            list.add(timeSeriesCoordinate);

            String[] tokensLeft = Arrays.copyOfRange(tokens, 4, tokens.length);

            parseTimeSeriesBlocksRecursive(tokensLeft, list);
        }
    }

    private Date ParseTimestamp(String[] pointTokens) {
        Instant instant = Instant.ofEpochSecond(Long.valueOf(pointTokens[3]));
        return Date.from(instant);
    }

    /**
     * , LocationApiDtoConverter dtoConverter
     * @param tokens
     */
    private TimeSeriesCoordinate ParseTimeSeries(String[] tokens) {

        Date timestamp = ParseTimestamp(tokens);

        // for each block parse block
        return new TimeSeriesCoordinate(timestamp, Double.valueOf(tokens[0]).doubleValue(), Double.valueOf(tokens[1]).doubleValue());
    }

    private String[] SplitTokens(String points) throws Exception {

        //String[] tokensByEqualSign = points.split("\\=");

        if (points == null || points.isEmpty())
        {
            throw new Exception("Points may not be null or empty");
        }

        String[] tokens = points.split("\\ +");

        if (tokens.length < 4)
        {
            throw new Exception(StringFormatter.format("Points does not have 4 tokens seperated by space [%1s]", points).getValue());
        }

        return tokens;
    }

    /**
     * Transformatation of DTO to apigateway DTO
     * @param dto
     * @return
     */
    public MapMyTracksResponse transformResponse(IActivityResponseDto dto) throws Exception {
        if (dto instanceof ActivityCreatedResponseDto) {
            ActivityCreatedResponseDto locationResponseDto = (ActivityCreatedResponseDto) dto;

            log.debug("Transform [" + locationResponseDto + "]");

            return new ActivityCreatedResponse(Long.parseLong(locationResponseDto.getId()));
        }
        else if (dto instanceof ActivityStoppedResponseDto)
        {
            return new ActivityStoppedResponse();
        }

        throw new Exception(StringFormatter.format("IActivityResponseDto impl not supported [type=%1s]", dto.getClass().toString()).getValue());
    }
}
