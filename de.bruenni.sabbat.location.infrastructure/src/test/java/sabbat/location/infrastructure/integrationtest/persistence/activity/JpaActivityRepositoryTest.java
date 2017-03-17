package sabbat.location.infrastructure.integrationtest.persistence.activity;

import org.hamcrest.core.IsNot;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sabbat.location.core.builder.ActivityBuilder;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.persistence.activity.IActivityRepository;
import sabbat.location.infrastructure.integrationtest.IntegrationTestConfig;
import sabbat.location.infrastructure.persistence.activity.JpaActivityRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by bruenni on 17.03.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = { "test" })
@SpringApplicationConfiguration(classes = { IntegrationTestConfig.class })
public class JpaActivityRepositoryTest {

	@Autowired
	@Qualifier("activityRepository")
	public IActivityRepository activityRepository;

	@org.junit.Before
	public void setup()
	{
	}

	@Test
	public void store_and_query_activity_by_id()
	{
		Activity activity = new ActivityBuilder().build();
		Activity saved = activityRepository.save(activity);

		Assert.assertThat(saved.getId(), IsNot.not(0l));

		Activity readEntity = activityRepository.findOne(saved.getId());

		Assert.assertThat(readEntity, new Equals(saved));
	}

/*	private JpaActivityRepository CreateRepository() {
		return new JpaActivityRepository();
	}*/
}
