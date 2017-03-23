package org.se.lab.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class CDITestBase
{
	public void txBegin(EntityManager em)
	{
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	}

	
	public void txCommit(EntityManager em)
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

	
	public void txRollback(EntityManager em)
	{
		EntityTransaction tx = em.getTransaction();
		tx.rollback();
	}
}