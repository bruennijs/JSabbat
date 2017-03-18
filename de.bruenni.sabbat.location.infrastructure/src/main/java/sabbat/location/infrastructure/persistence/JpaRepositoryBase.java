package sabbat.location.infrastructure.persistence;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by bruenni on 18.03.17.
 */
public abstract class JpaRepositoryBase {

	private EntityManagerFactory entityManagerFactory;

	private ThreadLocal<EntityManager> entityManager = new ThreadLocal();

	public JpaRepositoryBase() {
	}

	private EntityManagerFactory getEMF() {
		if (entityManagerFactory == null)
		{
			entityManagerFactory = Persistence.createEntityManagerFactory(getPersistenceUnit());
		}

		return entityManagerFactory;
	}

	/**
	 * Gets the persistence unit to load EntityManagerFactiry from.
	 * @return
	 */
	protected abstract String getPersistenceUnit();

	protected EntityManager getEntityManager()
	{
		if(entityManager.get() == null) {
			EntityManager entityManager = getEMF().createEntityManager();
			this.entityManager.set(entityManager);
		}

		return entityManager.get();
	}
}
