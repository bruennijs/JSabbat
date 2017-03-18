package sabbat.location.infrastructure.persistence.activity;

import infrastructure.util.IterableUtils;
import infrastructure.util.StreamUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Value;
import sabbat.location.core.builder.ActivityBuilder;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityCoordinate;
import sabbat.location.core.domain.model.ActivityPrimaryKey;
import sabbat.location.core.persistence.activity.IActivityRepository;
import sabbat.location.infrastructure.persistence.TransactionScope;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by bruenni on 16.03.17.
 */
public class JpaActivityRepository implements IActivityRepository {

	@Value(value = "${jpa.persistence-unit}")
	public String persistenceUnit;

	//@PersistenceUnit(unitName ="activity")
	private EntityManagerFactory entityManagerFactory;

	public JpaActivityRepository() {
	}

	@Override
	public Iterable<Activity> findByUserIds(String[] userIds) throws Exception {

		return new TransactionScope(getEMF().createEntityManager()).run(em ->
		{
			Stream<Activity> activityStream = em
				.createQuery("SELECT * FROM loc.activity as a WHERE a.userid IN {:array}")
				.setParameter("array", userIds)
				.getResultList()
				.stream();

			return activityStream.collect(Collectors.toList());
		});
	}

	private EntityManagerFactory getEMF() {
		if (entityManagerFactory == null)
		{
			entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
		}

		return entityManagerFactory;
	}

	@Override
	public Iterable<ActivityCoordinate> insertCoordinate(List<ActivityCoordinate> list) {
		return null;
	}

	@Override
	public Iterable<ActivityCoordinate> findActivityCoordinates(Activity activity) {
		return null;
	}

	@Override
	public <S extends Activity> S save(S entity) {
		return new TransactionScope(getEMF().createEntityManager()).run(em ->
		{
			if (entity.getId() == 0l)
			{
				return em.merge(entity);
			}
			else {
				em.persist(entity);
			}
			return entity;
		});
	}

	@Override
	public <S extends Activity> Iterable<S> save(Iterable<S> entities) {
		return null;
	}

	@Override
	public Activity findOne(Long activityPrimaryKey) {
		return null;
	}

	@Override
	public boolean exists(Long activityPrimaryKey) {
		return false;
	}

	@Override
	public Iterable<Activity> findAll() {
		return null;
	}

	@Override
	public Iterable<Activity> findAll(Iterable<Long> activityPrimaryKeys) {
		return null;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public void delete(Long activityPrimaryKey) {

	}

	@Override
	public void delete(Activity entity) {

	}

	@Override
	public void delete(Iterable<? extends Activity> entities) {

	}

	@Override
	public void deleteAll() {

	}
}
