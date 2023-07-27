package org.se.lab;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PostTest
{
	private Post post;
	
	@Before
	public void setup()
	{
		post = new Post(1, "Eve hacked FB!");
	}

	@Test
	public void testConstructor()
	{
		Assert.assertEquals(1, post.getId());
		Assert.assertEquals("Eve hacked FB!", post.getText());
	}

	@Test
	public void testToString()
	{
		Assert.assertEquals("1,Eve hacked FB!", post.toString());
	}

}
