package org.se.lab;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

@Interceptor
@Monitored
public class MonitorInterceptor 
	implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final Logger LOG = Logger.getLogger(MonitorInterceptor.class);

	@Inject 
	private EntityManager em;
	
	@AroundInvoke
	public Object monitorMethod(InvocationContext ctx) throws Exception
	{
		long before = System.currentTimeMillis();
		
		Object obj = ctx.proceed();
		
		long after = System.currentTimeMillis();
		LOG.debug("Method " + ctx.getMethod().getName() + "() : duration = " + (after - before) + ") : em = " + em);
		return obj;
	}
}
