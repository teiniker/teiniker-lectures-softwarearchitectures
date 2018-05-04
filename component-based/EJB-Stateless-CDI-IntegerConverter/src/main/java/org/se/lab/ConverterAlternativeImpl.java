package org.se.lab;

import javax.enterprise.inject.Alternative;

import org.jboss.logging.Logger;


@Alternative
public class ConverterAlternativeImpl 
	implements Converter
{
	private final Logger LOG = Logger.getLogger(ConverterAlternativeImpl.class);
		
	@Override
	public String convertIntegerToHexString(int value)
	{
		LOG.info("onvertIntegerToHexString(" + value + ")");		
		return String.format("%04x", value);
	}
	
	@Override
	public String convertIntegerToBinaryString(int value)
	{
		LOG.info("convertIntegerToBinaryString(" + value + ")");
		return String.format("%16s", Integer.toBinaryString(value)).replace(" ", "0");
	}
}
