package sabbat.location.infrastructure.unittest.command;

import identity.UserRef;
import infrastructure.util.Tuple2;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.internal.matchers.Equals;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.infrastructure.DtoToCommand.ActivityUpdateEventDtoConverter;
import sabbat.location.infrastructure.builder.ActivityUpdateEventDtoBuilder;
import sabbat.location.infrastructure.builder.TimeSeriesCoordinateBuilder;
import sabbat.location.infrastructure.client.dto.ActivityUpdateEventDto;
import sabbat.location.infrastructure.client.dto.TimeSeriesCoordinate;

import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 07.11.16.
 */
@RunWith(JUnit4.class)
public class ActivityUpdateCommandTests {
    @Test
    public void When_convert_dto_to_command_expect_identitytoken_correct()
    {
        String identityToken = "sometokenvallue";

        ActivityUpdateEventDto dto = new ActivityUpdateEventDtoBuilder()
                .WithIdentityToken(identityToken)
                .build();

        ActivityUpdateEventDtoConverter converter = new ActivityUpdateEventDtoConverter();
        UserRef userRef = createUserRef();
        ActivityUpdateCommand command = converter.convert(new Tuple2<>(userRef, dto));

        Assert.assertThat(command.getUser(), new IsEqual(userRef));
    }

    @Test
    public void When_convert_dto_to_command_expect_time_series_correct()
    {
        TimeSeriesCoordinate timeCoordinate0 = new TimeSeriesCoordinateBuilder().withLatitude(5.7).build();
        TimeSeriesCoordinate timeCoordinate1 = new TimeSeriesCoordinateBuilder().withLatitude(34.55).build();

        ActivityUpdateEventDto dto = new ActivityUpdateEventDtoBuilder()
                .withTimeSeries(timeCoordinate0)
                .withTimeSeries(timeCoordinate1)
                .build();

        ActivityUpdateEventDtoConverter converter = new ActivityUpdateEventDtoConverter();
        ActivityUpdateCommand command = converter.convert(new Tuple2<>(createUserRef(), dto));

        Assert.assertEquals(2, command.getCoordinates().size());
        Assert.assertThat(
                command.getCoordinates().stream().map(tc -> new TimeSeriesCoordinateBuilder()
                        .withLatitude(tc.getLatitude())
                        .build()).collect(Collectors.toList()),
                IsIterableContainingInAnyOrder.containsInAnyOrder(timeCoordinate0, timeCoordinate1));
    }

    private UserRef createUserRef() {
        return new UserRef("someid", "name", Arrays.asList());
    }
}
