package sabbat.location.infrastructure.persistence.activity;

import org.springframework.beans.factory.annotation.Value;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityCoordinate;
import sabbat.location.core.domain.model.ActivityRelation;
import sabbat.location.core.persistence.activity.IActivityRepository;
import sabbat.location.infrastructure.persistence.JpaRepositoryBase;
import sabbat.location.infrastructure.persistence.TransactionScope;

import javax.persistence.*;
import java.util.List;

/**
 * Created by bruenni on 16.03.17.
 */
public class JpaActivityRepository extends JpaRepositoryBase implements IActivityRepository {

	@Value(value = "${jpa.persistence-unit.activity}")
	public String persistenceUnit;

	public JpaActivityRepository() {
	}

	@Override
	protected String getPersistenceUnit() {
		return persistenceUnit;
	}

	@Override
	public Iterable<Activity> findByUserIds(Iterable<String> userIds) throws Exception {

		return new TransactionScope(getEntityManager()).run(em ->
			em.createQuery("SELECT a FROM Activity as a WHERE a.userId IN :array", Activity.class)
				.setParameter("array", userIds)
				.setLockMode(LockModeType.PESSIMISTIC_READ)
				.getResultList());
	}

	@Override
	public Iterable<Activity> findActiveActivitiesByUserIds(List<String> userIds) {

		return new TransactionScope(getEntityManager()).run(em -> em.createQuery(
			"SELECT a FROM Activity as a " +
					"JOIN      a.domainEvents as  de ON TYPE(de)  = ActivityStartedEvent " +
					"LEFT JOIN a.domainEvents as de2 ON TYPE(de2) = ActivityStoppedEvent AND de.createdOn < de2.createdOn " +
					"WHERE a.userId IN :userIds AND de2.id IS NULL", Activity.class)
			.setParameter("userIds", userIds)
			.getResultList());
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
	public ActivityRelation save(ActivityRelation entity) {
		return new TransactionScope(getEntityManager()).run(em ->
		{
			if (!em.contains(entity)) {
				// when not managed and part of the current persistence context
				return em.merge(entity);
			}

			//// ...else transaction is comitted with only the possibly in memory changed managed entity
			return entity;
		});
	}

	@Override
	public void refresh(Activity torefresh) {
		getEntityManager().refresh(torefresh);
	}

	@Override
	public void truncate() {
		new TransactionScope(getEntityManager()).run(em ->
			em.createNativeQuery("truncate table loc.activity cascade")
				//.setLockMode(LockModeType.PESSIMISTIC_WRITE)
				.executeUpdate()
		);
	}

	@Override
	public Activity findByUuid(String uuid) {
		return new TransactionScope(getEntityManager()).run(em ->
			em.createQuery("SELECT a from Activity as a where uuid = :uuid", Activity.class)
			.setParameter("uuid", uuid)).getSingleResult();
	}

	@Override
	public <S extends Activity> S save(S entity) {
		return new TransactionScope(getEntityManager()).run(em ->
		{
			if (!em.contains(entity)) {
				// when not managed and part of the current persistence context
				return em.merge(entity);
			}

			//// ...else transaction is comitted with only the possibly in memory changed managed entity
			return entity;
		});
	}

	@Override
	public <S extends Activity> Iterable<S> save(Iterable<S> entities) {
		return null;
	}

	@Override
	public Activity findOne(Long activityPrimaryKey) {
		return new TransactionScope(getEntityManager()).run(em ->
		{
			Activity activity = em.find(Activity.class, activityPrimaryKey);
			//em.refresh(activity);
			return activity;
		});
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
