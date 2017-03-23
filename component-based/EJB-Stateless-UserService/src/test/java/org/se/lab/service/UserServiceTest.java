package org.se.lab.service;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest
{    
	private final Logger LOG = Logger.getLogger(UserServiceTest.class);
	
	private static final JdbcTestHelper JDBC_HELPER = new JdbcTestHelper();
	
	@Before
	public void init()
	{
		JDBC_HELPER.executeSqlScript("sql/createUserTable.sql");
		JDBC_HELPER.executeSqlScript("sql/insertUserTable.sql");
	}
	
	@After
	public void destroy()
	{
		JDBC_HELPER.executeSqlScript("sql/dropUserTable.sql");		
	}
	
	private UserService service;
	
	// The JNDI lookup name for a stateless session bean has the syntax of:
	// ejb:<appName>/<moduleName>/<distinctName>/<beanName>!<viewClassName>
	//
	// <appName> The application name is the name of the EAR that the EJB is deployed in
	// (without the .ear). If the EJB JAR is not deployed in an EAR then this is
	// blank. The app name can also be specified in the EAR's application.xml
	//
	// <moduleName> By the default the module name is the name of the EJB JAR file (without the
	// .jar suffix). The module name might be overridden in the ejb-jar.xml
	//
	// <distinctName> : WildFly allows each deployment to have an (optional) distinct name.
	// This example does not use this so leave it blank.
	//
	// <beanName> : The name of the session been to be invoked.
	//
	// <viewClassName>: The fully qualified classname of the remote interface. Must include
	// the whole package name.
	
	// "ejb:/wildfly-ejb-remote-server-side/CalculatorBean!" + RemoteCalculator.class.getName()
	// java:global/EJB-Calculator-RemoteTest/CalculatorTestBean!org.se.lab.Calculator
	//  ejb:/EJB-Calculator-RemoteTest/CalculatorTestBean!org.se.lab.Calculator
	
    @Before
    public void setUp() throws NamingException  
    {
    	final Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);
        
        final String jndiName = "ejb:" + "" 
        		+ "/" + "EJB-Stateless-UserService" 
        		+ "/" + ""
        		+ "/" + "UserServiceEJB" 
        		+ "!" + UserService.class.getName();
   
        LOG.info("JNDI Name = " + jndiName);
        service =  (UserService) context.lookup(jndiName);
    }
    
    
    @Test
    public void testFindAllUsers()
    {
    	List<UserDTO> users = service.findAllUsers();
    	service.findAllUsers();
    	service.findAllUsers();
    	service.findAllUsers();

    	LOG.info(users);
    	
    	Assert.assertEquals(4, users.size());
    }
    
    @Test
    public void testAddUser()
    {
    	service.addUser("maggie", "maggie");
    	
    	List<UserDTO> users = service.findAllUsers();
    	LOG.info(users);
    	Assert.assertEquals(5, users.size());
    }
}
