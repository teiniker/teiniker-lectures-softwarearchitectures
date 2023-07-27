package org.se.lab;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BlogTest
{
	private Blog blog;
	private PostTableStub stub;
	
	@Before
	public void setup()
	{
		stub = new PostTableStub();
		blog = new Blog(stub);

		blog.post("First SWD Blog entry.");
		blog.post("In the 3rd semester SWD we learn cool things about software testing!!");
		blog.post("Also in the 3rd semester we hear about software design.");
	}
	
	@After
	public void teardown()
	{
		IntegerSequence.setValue(1); // reset sequence
	}

	@Test
	public void testGetPosts()
	{
		List<Post> posts = blog.getPosts();	
		Assert.assertEquals(3, posts.size());
	}

	@Test
	public void testFilter()
	{
		List<Post> swdPosts = blog.filter("SWD");
		Assert.assertEquals(2, swdPosts.size());
	}
}
