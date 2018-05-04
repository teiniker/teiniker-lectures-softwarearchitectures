package org.se.lab;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.logging.Logger;


@Stateless
@Remote(IntegerConverter.class)
public class IntegerConverterEJB 
	implements IntegerConverter
{	
	private final Logger LOG = Logger.getLogger(IntegerConverterEJB.class);

	@Inject
	private Converter converter;

	
	/*
     * Life Cycle Methods
     */

	public IntegerConverterEJB()
	{
		LOG.info("IntegerConverterEJB() " + this);
	}
	
	@PostConstruct
    public void init()
    {
		LOG.info("init() " + this);
    }
    
    @PreDestroy
    public void destroy()
    {
    	LOG.info("destroy() " + this);
    }

    
	/*
	 * Bean Methods
	 */
    
	@Override
	public String toHex(int value)
	{
		LOG.info("IntegerConverterEJB.toHex(" + value + ")");
		
		return converter.convertIntegerToHexString(value);
	}

	@Override
	public String toBin(int value)
	{
		LOG.info("toBin(" + value + ")");
		delay(1000);
		return converter.convertIntegerToBinaryString(value);
	}
	
	
	/*
	 * Helper methods
	 */
	
	private void delay(long ms)
	{
		try 
		{
			Thread.sleep(ms);
		} 
		catch (InterruptedException e) 
		{
			// interrupted sleep does not matter
		}
	}
}