package org.se.lab;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.se.lab.util.CDITestBase;
import org.se.lab.util.JdbcTestHelper;
import org.se.lab.util.WeldJUnit4Runner;


@RunWith(WeldJUnit4Runner.class)
public class CDITest extends CDITestBase
{
	private final Logger LOG = Logger.getLogger(CDITest.class);
	private static final JdbcTestHelper JDBC_HELPER = new JdbcTestHelper();

	@Inject
	UserDAO dao;

	@Inject
	EntityManager em;

	@BeforeClass
	public static void init()
	{
		JDBC_HELPER.executeSqlScript("sql/createUserTable.sql");
	}

	@AfterClass
	public static void destroy()
	{
		JDBC_HELPER.executeSqlScript("sql/dropUserTable.sql");
	}

	@Before
	public void setup()
	{
		LOG.debug("em = " + em);
		txBegin(em);
		dao.insert(new User("homer"));
		dao.insert(new User("marge"));
		dao.insert(new User("bart"));
		dao.insert(new User("lisa"));
		dao.insert(new User("maggie"));
	}

	@After
	public void teardown()
	{
		txRollback(em);
	}

	@Test
	public void testFindAll() throws Exception
	{
		List<User> users = dao.findAll();

		Assert.assertNotNull(users);
		Assert.assertEquals(5, users.size());
	}
}
