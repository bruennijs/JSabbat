package sabbat.location.infrastructure.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TransactionScope {
	private EntityManager entityManager;

	public TransactionScope(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public <U> U run(java.util.function.Function<EntityManager, U> callback)
	{
		EntityTransaction trx = this.entityManager.getTransaction();
		try {
			trx.begin();

			U result = callback.apply(entityManager);

			trx.commit();

			return result;
		}
		catch (Exception e)
		{
			trx.rollback();
			throw e;
		}
		finally
		{
			this.entityManager.close();
		}

	}
}
