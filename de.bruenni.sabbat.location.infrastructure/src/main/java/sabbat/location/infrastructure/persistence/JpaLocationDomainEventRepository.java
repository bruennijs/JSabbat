package sabbat.location.infrastructure.persistence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import sabbat.location.core.domain.model.DomainEvent;

/**
 * Created by bruenni on 18.03.17.
 */
public class JpaLocationDomainEventRepository extends JpaRepositoryBase implements CrudRepository<DomainEvent, Long> {

	@Value(value = "${jpa.persistence-unit.activity}")
	public String persistenceUnit;

	@Override
	public <S extends DomainEvent> S save(S entity) {
/*		return new TransactionScope(getEntityManager()).run(em ->
		{
			em.
		});*/
		return null;
	}

	@Override
	public <S extends DomainEvent> Iterable<S> save(Iterable<S> entities) {
		return null;
	}

	@Override
	public DomainEvent findOne(Long aLong) {
		return null;
	}

	@Override
	public boolean exists(Long aLong) {
		return false;
	}

	@Override
	public Iterable<DomainEvent> findAll() {
		return null;
	}

	@Override
	public Iterable<DomainEvent> findAll(Iterable<Long> longs) {
		return null;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public void delete(Long aLong) {

	}

	@Override
	public void delete(DomainEvent entity) {

	}

	@Override
	public void delete(Iterable<? extends DomainEvent> entities) {

	}

	@Override
	public void deleteAll() {

	}

	@Override
	protected String getPersistenceUnit() {
		return persistenceUnit;
	}
}
