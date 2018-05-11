package org.se.lab;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Remote;
import javax.ejb.Singleton;

import org.jboss.logging.Logger;

import java.util.concurrent.atomic.AtomicInteger;

@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Singleton
@Remote(IntegerCounter.class)
public class IntegerCounterEJB 
	implements IntegerCounter
{
	private final Logger LOG = Logger.getLogger(IntegerCounterEJB.class);
	private AtomicInteger count = new AtomicInteger(0);

	/*
     * Life Cycle Methods
     */

	public IntegerCounterEJB()
	{
		LOG.info("IntegerCounterEJB() " + this);
	}
	
	@PostConstruct
    public void init()
    {
		LOG.info("IntegerCounterEJB.init() " + this);
    }
    
    @PreDestroy
    public void destroy()
    {
    	LOG.info("IntegerCounterEJB.destroy() " + this);
    }

    
	/*
	 * Bean Methods
	 */
    
	@Override
	public void increment()
	{
		LOG.info("IntegerCounterEJB.increment()");

		count.incrementAndGet();
	}

	@Override
	public void decrement()
	{
		LOG.info("IntegerCounterEJB.decrement()");
		count.decrementAndGet();
	}

	@Override
	public int getValue()
	{
		LOG.info("IntegerCounterEJB.getValue()");
		return count.get();
	}
}