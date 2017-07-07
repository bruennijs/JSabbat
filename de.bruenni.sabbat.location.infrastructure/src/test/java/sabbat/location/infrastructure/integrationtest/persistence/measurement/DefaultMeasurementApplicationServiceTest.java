package sabbat.location.infrastructure.integrationtest.persistence.measurement;

import identity.UserRef;
import infrastructure.util.IterableUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sabbat.location.core.application.service.MaesurementApplicationService;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.application.service.command.TimeCoordinate;
import sabbat.location.core.domain.model.coordinate.UserCoordinate;
import sabbat.location.core.persistence.coordinate.UserCoordinateRepository;
import sabbat.location.infrastructure.integrationtest.CassandraIntegrationTest;
import sabbat.location.infrastructure.integrationtest.IntegrationTestConfig;
import test.matcher.LambdaMatcher;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 07.07.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"dev"})
@SpringBootTest(classes = { CassandraIntegrationTest.class })
public class DefaultMeasurementApplicationServiceTest {

    private static Logger logger = LoggerFactory.getLogger(DefaultMeasurementApplicationServiceTest.class);

    @Autowired
    @Qualifier("defaultMeasurementApplicationService")
    public MaesurementApplicationService service;

    @Autowired
    @Qualifier("cassandraUserCoordinateRepository")
    public UserCoordinateRepository repository;

    @Test
    public void when_insert_expect_findActivityCoordinates_finds_all_inserted_ones() throws Exception {

        UserRef userRef = new UserRef("userid1", "bruenni", Arrays.asList());

        Instant now = Instant.now(Clock.systemUTC());

        List<TimeCoordinate> timeCoordinates = Arrays.asList(
                new TimeCoordinate(23.11, 34.22, Date.from(now)),
                new TimeCoordinate(22.11, 34.23, Date.from(now.plus(20, ChronoUnit.SECONDS))));

        ActivityUpdateCommand command = new ActivityUpdateCommand(userRef, "activityid1", timeCoordinates, Optional.empty(), Optional.empty());

        Iterable<UserCoordinate> insertedCoordinates = service.insert(command);

        Iterable<UserCoordinate> actualCoordinates = repository.findActivityCoordinates(userRef.getId(), command.getActivityId());

        //Iterable<UserCoordinate> actualCoordinates = repository.findAll(IterableUtils.stream(insertedCoordinates).map(uc -> uc.getKey()).collect(Collectors.toList()));

        // asserts
        Assert.assertThat(actualCoordinates,
                Matchers.contains(timeCoordinates.stream().map(tc -> new UserCoordinateMatcher(userRef.getId(), command.getActivityId(), tc)).collect(Collectors.toList())));
    }

    private class UserCoordinateMatcher extends TypeSafeDiagnosingMatcher<UserCoordinate>
    {
        private String userRefId;
        private String activityId;
        private TimeCoordinate timeCoordinate;

        public UserCoordinateMatcher(TimeCoordinate tc) {
            timeCoordinate = tc;
        }

        public UserCoordinateMatcher(String userRefId, String activityId, TimeCoordinate tc) {
            this.userRefId = userRefId;
            this.activityId = activityId;

            timeCoordinate = tc;
        }

        @Override
        protected boolean matchesSafely(UserCoordinate item, Description mismatchDescription) {

            logger.debug("[tc=" + this.timeCoordinate.toString() + ", uc=" + item.toString() + "]" + "lbd=" + BigDecimal.valueOf(timeCoordinate.getLongitude()));

            BigDecimal bd1 = BigDecimal.valueOf(timeCoordinate.getLongitude());
            BigDecimal bd2 = BigDecimal.valueOf(timeCoordinate.getLatitude());

            return item.getKey().getActivityid() == activityId
                    && item.getKey().getUserId() == userRefId
                    && item.getKey().getCaptured() == timeCoordinate.getTimestamp()
                    && item.getLongitude() == BigDecimal.valueOf(timeCoordinate.getLongitude())
                    && item.getLatitude() == BigDecimal.valueOf(timeCoordinate.getLatitude());
        }

        @Override
        public void describeTo(Description description) {

        }
    }
}
