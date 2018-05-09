package org.se.lab;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;

import org.jboss.logging.Logger;

import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;


@Stateful
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
		LOG.info("IntegerCounterEJB.CounterBean() " + this);
	}
	
	@PostConstruct @TransactionAttribute(NOT_SUPPORTED)
    protected void init()
    {
		LOG.info("IntegerCounterEJB.init() " + this);
    }
    
    @PreDestroy @TransactionAttribute(NOT_SUPPORTED)
    protected void destroy()
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

    @Remove
    public void removeCounter()
    {
    	LOG.info("IntegerCounterEJB.removeCounter()");
    }
}