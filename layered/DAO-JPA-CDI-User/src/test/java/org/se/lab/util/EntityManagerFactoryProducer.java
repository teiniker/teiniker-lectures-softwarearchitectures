package org.se.lab.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProducer
{
	public static final String TEST = "test";

	@Produces
	@ApplicationScoped
	public EntityManagerFactory create()
	{
		return Persistence.createEntityManagerFactory(TEST);
	}

	public void destroy(@Disposes EntityManagerFactory factory)
	{
		factory.close();
	}
}
