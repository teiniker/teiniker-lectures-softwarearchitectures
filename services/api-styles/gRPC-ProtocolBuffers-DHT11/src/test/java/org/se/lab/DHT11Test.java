package org.se.lab;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.apache.commons.codec.binary.Hex;

public class DHT11Test
{
	private DHT11 sensor;

	@Before
	public void setup()
	{
		sensor = DHT11.newBuilder()
					.setTemperature(2370)
					.setHumidity(670)
					.build();
	}

	@Test
	public void testDHT11()
	{
		Assert.assertEquals(2370, sensor.getTemperature());
		Assert.assertEquals(670, sensor.getHumidity());
	}

	@Test
	public void testDHT11Serialization() throws InvalidProtocolBufferException
	{
		// Setup

		// Exercise
		byte[] bytes = sensor.toByteArray();
		String hex = Hex.encodeHexString(bytes);
		// Verify
		String expected = "08c212109e05";
		Assert.assertEquals(expected, hex);
	}
}
