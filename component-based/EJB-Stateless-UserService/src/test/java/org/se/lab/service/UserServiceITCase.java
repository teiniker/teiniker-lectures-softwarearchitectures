package org.se.lab.service;

import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jboss.logging.Logger;
import org.junit.*;

public class UserServiceITCase
{    
	private final Logger LOG = Logger.getLogger(UserServiceITCase.class);
	private static final JdbcTestHelper JDBC_HELPER = new JdbcTestHelper("src/test/resources/jdbc.properties");

	private UserService service;

	@BeforeClass
    public static void init()
    {
        JDBC_HELPER.executeSqlScript("src/test/resources/sql/createUserTable.sql");
        JDBC_HELPER.executeSqlScript("src/test/resources/sql/insertUserTable.sql");
    }

    @AfterClass
    public static void destroy()
    {
        JDBC_HELPER.executeSqlScript("src/test/resources/sql/dropUserTable.sql");
    }

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
    public void setup() throws NamingException  
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
    	for(UserDTO user : users)
    	{
    		System.out.println(user);
    	}    	
    }

    @Test
    public void testAddUser()
    {
        service.addUser("homer", "ksShjd5123");
    }

}
