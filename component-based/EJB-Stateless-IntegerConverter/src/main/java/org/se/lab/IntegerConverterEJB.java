package org.se.lab;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;


@Stateless
@Remote(IntegerConverter.class)
public class IntegerConverterEJB 
	implements IntegerConverter
{	
	private final Logger LOG = Logger.getLogger(IntegerConverterEJB.class);

	/*
     * Life Cycle Methods
     */

	public IntegerConverterEJB()
	{
		LOG.info("IntegerConverterEJB.IntegerConverterEJB() " + this);
	}
	
	@PostConstruct
    public void init()
    {
		LOG.info("IntegerConverterEJB.init() " + this);
    }
    
    @PreDestroy
    public void destroy()
    {
    	LOG.info("IntegerConverterEJB.destroy() " + this);
    }

    
	/*
	 * Bean Methods
	 */
    
	@Override
	public String toHex(int value)
	{
		LOG.info("IntegerConverterEJB.toHex(" + value + ")");
		
		return String.format("%04x", value);
	}

	@Override
	public String toBin(int value)
	{
		LOG.info("IntegerConverterEJB.toBin(" + value + ")");
		delay(1000);
		return String.format("%16s", Integer.toBinaryString(value)).replace(" ", "0");
	}
	
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