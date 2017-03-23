package org.se.lab.util;

import org.apache.log4j.Logger;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/*
 * To inject always the same EntityManager instance for testing - I created a singleton
 * TODO: merge with EntityManagerFactoryProducer
 */

public class EntityManagerProducer
{
	private final Logger LOG = Logger.getLogger(EntityManagerProducer.class);
	
	private static EntityManager em = null; 
	
	@Inject
	private EntityManagerFactory emf;

	@Produces
	public EntityManager create()
	{
		LOG.debug("create()");
		if(em == null)
		{
			em = emf.createEntityManager();
		}
		return em;
	}

	public void destroy(@Disposes EntityManager em)
	{
		em.close();
		LOG.debug(String.format("%s Entity manager was closed", em));
	}
}
