package org.se.lab;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Singleton;

import org.apache.log4j.Logger;


@Singleton
@Remote(IntegerCounter.class)
public class IntegerCounterEJB 
	implements IntegerCounter
{
	private final Logger LOG = Logger.getLogger(IntegerCounterEJB.class);
	private int count = 0;

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
		this.count++;
	}

	@Override
	public void decrement()
	{
		LOG.info("IntegerCounterEJB.decrement()");
		this.count--;
	}

	@Override
	public int getValue()
	{
		LOG.info("IntegerCounterEJB.getValue()");
		return this.count;
	}
}