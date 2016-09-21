package sabbat.location.infrastructure.integrationtest.persistence.activity;

import com.google.common.collect.Lists;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityCoordinate;
import sabbat.location.core.domain.model.ActivityCoordinatePrimaryKey;
import sabbat.location.core.domain.model.Coordinate;
import sabbat.location.core.persistence.activity.IActivityRepository;
import sabbat.location.infrastructure.integrationtest.CassandraTestConfig;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.*;

/**
 * Created by bruenni on 20.09.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { CassandraTestConfig.class })
public class CassandraActivityRepositoryTest {

    @Autowired
    @Qualifier("cassandraActivityRepository")
    public IActivityRepository ActivityRepository;

    @Test
    public void When_insert_expect_returned_entity_equals_inserted_title()
    {
        Activity activity = new ActivityBuilder().build();

        Activity insertedActivity = ActivityRepository.save(activity);

        Assert.assertEquals(insertedActivity.getKey(), activity.getKey());
        Assert.assertEquals(insertedActivity.getTitle(), activity.getTitle());
        Assert.assertEquals(insertedActivity.getStarted(), activity.getStarted());
        Assert.assertNull(insertedActivity.getFinished());
    }

    @Test
    public void When_insert_expect_get_by_id_returns_expected_activity()
    {
        Activity activity = new ActivityBuilder().build();

        Activity insertedActivity = ActivityRepository.save(activity);

        Activity getEntity = ActivityRepository.findOne(insertedActivity.getKey());

        Assert.assertEquals(insertedActivity, getEntity);
    }

    @Test
    public void When_update_activity_with_finished_value_after_insert_expect_get_by_id_returns_activity_with_changed_finished_property()
    {
        Date finished = Date.from(LocalDateTime.of(1981, 1, 12, 0, 0, 0).toInstant(ZoneOffset.UTC));

        Activity activity = new ActivityBuilder().build();

        Activity insertedActivity = ActivityRepository.save(activity);

        insertedActivity.setFinished(finished);

        Activity updatedActivity = ActivityRepository.save(activity);

        Activity getEntity = ActivityRepository.findOne(insertedActivity.getKey());

        Assert.assertEquals(finished, getEntity.getFinished());
        Assert.assertEquals(finished, updatedActivity.getFinished());
    }

    @Test
    public void When_insert_activity_repoitory_expect_select_can_read_them_gain()
    {
        Date now = new Date();

        Activity activity = new ActivityBuilder().build();
        ActivityCoordinatePrimaryKey activityCoordinatePrimaryKey1 = new ActivityCoordinatePrimaryKeyBuilder().fromActivityKey(activity.getKey());
        ActivityCoordinatePrimaryKey activityCoordinatePrimaryKey2 = new ActivityCoordinatePrimaryKeyBuilder().fromActivityKey(activity.getKey());

        ActivityCoordinate activityCoordinate1 = new ActivityCoordinateBuilder()
                .withPrimaryKey(activityCoordinatePrimaryKey1)
                .build();

        ActivityCoordinate activityCoordinate2 = new ActivityCoordinateBuilder()
                .withPrimaryKey(activityCoordinatePrimaryKey2)
                .build();

        Activity insertedActivity = ActivityRepository.save(activity);

        List<ActivityCoordinate> insertedActivityCoordinates = Lists.newArrayList(ActivityRepository.insertCoordinate(Arrays.asList(activityCoordinate1, activityCoordinate2)));

        List<ActivityCoordinate> readActivityCoordinates = Lists.newArrayList(ActivityRepository.findActivityCoordinates(activity));

        Assert.assertEquals(insertedActivityCoordinates.size(), readActivityCoordinates.size());

        Assert.assertThat(insertedActivityCoordinates, Is.is(readActivityCoordinates));
    }
}
