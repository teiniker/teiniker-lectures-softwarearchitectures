package org.se.lab;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import org.jboss.logging.Logger;

@Decorator
public class ConverterDecorator
	implements Converter
{
	@Inject @Delegate
	private Converter impl;
	
	private final Logger LOG = Logger.getLogger(ConverterDecorator.class);
	
	@Override
	public String convertIntegerToHexString(int value)
	{
		LOG.info("convertIntegerToHexString(" + value + ")");		
		return impl.convertIntegerToHexString(value);
	}
	
	@Override
	public String convertIntegerToBinaryString(int value)
	{
		LOG.info("convertIntegerToBinaryString(" + value + ")");
		return impl.convertIntegerToBinaryString(value);
	}
}
