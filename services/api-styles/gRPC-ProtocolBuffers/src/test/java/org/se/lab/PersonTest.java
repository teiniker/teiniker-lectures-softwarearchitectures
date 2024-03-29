package org.se.lab;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PersonTest
{
	private Person person;

	@Before
	public void setup()
	{
		person = Person.newBuilder()
					.setId(7)
					.setName("homer")
					.setPassword("$2y$12$9gRSvDCPp9lC/JBBo7jCZe.mXhpOiWk4z.y04YJ2NXzUo7qsKbg.S")
					.build();
	}

	@Test
	public void testPerson()
	{
		Assert.assertEquals(7, person.getId());
		Assert.assertEquals("homer", person.getName());
		Assert.assertEquals("$2y$12$9gRSvDCPp9lC/JBBo7jCZe.mXhpOiWk4z.y04YJ2NXzUo7qsKbg.S", person.getPassword());
	}

	@Test
	public void testPersonToString()
	{
		// Exercise
		String s = person.toString();
		// Verify
		String expected =
				"id: 7\n" +
				"name: \"homer\"\n" +
				"password: \"$2y$12$9gRSvDCPp9lC/JBBo7jCZe.mXhpOiWk4z.y04YJ2NXzUo7qsKbg.S\"\n";
		Assert.assertEquals(expected, s);
	}

	@Test
	public void testPersonSerialization() throws InvalidProtocolBufferException
	{
		// Setup
		byte[] bytes = person.toByteArray();
		// Exercise
		Person clone = Person.parseFrom(bytes);
		// Verify
		String expected =
				"id: 7\n" +
				"name: \"homer\"\n" +
				"password: \"$2y$12$9gRSvDCPp9lC/JBBo7jCZe.mXhpOiWk4z.y04YJ2NXzUo7qsKbg.S\"\n";
		Assert.assertEquals(expected, clone.toString());
	}
}
