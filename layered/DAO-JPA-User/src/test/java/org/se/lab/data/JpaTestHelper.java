package org.se.lab.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class JpaTestHelper 
{
	/*
	 * Property: persistenceUnitName
	 */
	private String persistenceUnitName;
	public String getPersistenceUnitName()
	{
		return persistenceUnitName;
	}
	public void setPersistenceUnitName(String persistenceUnitName)
	{
		if(persistenceUnitName == null || persistenceUnitName.length() == 0)
			throw new IllegalArgumentException("Illegal parameter persistenceUnitName = " + persistenceUnitName);
		this.persistenceUnitName = persistenceUnitName;
	}


	/*
	 * Get an instance of the EntityManagerFactory.
	 */
	protected EntityManagerFactory getEnityManagerFactory()
	{
		if(persistenceUnitName == null)
			throw new IllegalStateException("PersistenceUnitName must be set!");
		return Persistence.createEntityManagerFactory(persistenceUnitName);
	}

	
	/*
	 * Manage an EntityManager.
	 */
	
	private EntityManager em;
	public EntityManager getEntityManager()
	{
		if(em == null)
		{
			em = getEnityManagerFactory().createEntityManager();
		}
		return em;			
	}
	public EntityManager getEntityManager(String persistenceUnitName)
	{
		setPersistenceUnitName(persistenceUnitName);
		return getEntityManager();			
	}

	
	public void closeEntityManager()
	{
		if(em != null)
			em.close();
	}
	
	
	/*
	 * Handle Transactions 
	 */
	
	protected void txBegin()
	{
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	}
   
	protected void txCommit()
	{
		EntityTransaction tx = em.getTransaction();
		if(tx.getRollbackOnly())
		{
			tx.rollback();
		}
		else
		{
			tx.commit();
		}
	}
    
	protected void txRollback()
	{
		EntityTransaction tx = em.getTransaction();
		tx.rollback();
	}
}
