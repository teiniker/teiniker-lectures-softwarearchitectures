package org.se.lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class JSONTest
{
	/*
	 * Using the org.json library
	 */
	
	@Test
	public void testReadJSON()
	{
		String json = "{\"id\":3,\"username\":\"bart\",\"password\":\"**********\"}\n";
		JSONObject obj = new JSONObject(json);
		int id = obj.getInt("id");
		String username = obj.getString("username");
		String password = obj.getString("password");
		
		Assert.assertEquals(3, id);
		Assert.assertEquals("bart", username);
		Assert.assertEquals("**********", password);
	}

	
	/*
	 * Using the javax.json-api
	 */
	
	@Test
	public void testJsonArrayBuilder()
	{
		  // build JSON
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        jsonArrayBuilder.add(
        		Json.createObjectBuilder()
                	.add("id", 3)
                    .add("username", "bart")
                    .add("password", "**********")
            );
        JsonArray usersJson = jsonArrayBuilder.build();

        System.out.println(usersJson.toString());
	}

	
	@Test
	public void testJsonReader() throws FileNotFoundException
	{
		JsonReader jsonReader = 
				Json.createReader(new FileReader(new File("src/test/resources/json", "user.json")));
	    JsonObject jsonObject = jsonReader.readObject();
	    
	    int id = jsonObject.getInt("id");
		String username = jsonObject.getString("username");
		String password = jsonObject.getString("password");
	    
	    Assert.assertEquals(3, id);
	    Assert.assertEquals("bart", username);
		Assert.assertEquals("**********", password);
	}
}
