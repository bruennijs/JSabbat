package sabbat.location.infrastructure.integrationtest.persistence.activity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.persistence.activity.IActivityRepository;
import sabbat.location.infrastructure.integrationtest.CassandraTestConfig;

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
    public void When_insert_expect_get_by_id_returns_expected_activity()
    {
        Activity activity = new ActivityBuilder().build();

        Activity insertedActivity = ActivityRepository.save(activity);

        Assert.assertEquals(insertedActivity.getTitle(), activity.getTitle());
    }
}
