package org.se.lab;

import org.jboss.logging.Logger;

public class ConverterImpl 
	implements Converter
{
	private final Logger LOG = Logger.getLogger(ConverterImpl.class);
		
	@Override
	public String convertIntegerToHexString(int value)
	{
		LOG.info("convertIntegerToHexString(" + value + ")");		
		return String.format("%04x", value);
	}
	
	@Override
	public String convertIntegerToBinaryString(int value)
	{
		LOG.info("convertIntegerToBinaryString(" + value + ")");
		return String.format("%16s", Integer.toBinaryString(value)).replace(" ", "0");
	}
}
