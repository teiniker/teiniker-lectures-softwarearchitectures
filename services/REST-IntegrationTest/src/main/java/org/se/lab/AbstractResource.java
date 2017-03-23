package org.se.lab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;


public abstract class AbstractResource
{
	public static final String PROPERTIES_FILE_LOCATION = "/server.properties";
	public static final String XML_SCHEMA_URL = "http://www.w3.org/2001/XMLSchema";
	public static final String XSD_FILE_LOCATION = "/org/se/lab/Server.xsd";
	
	private final Logger LOG = Logger.getLogger(AbstractResource.class);
	
	protected String loadFromPropertyFile(String propertiesLocation, String key) 
    {
    	Properties properties = new Properties();
    	InputStream propertiesStream = AbstractResource.class.getResourceAsStream(propertiesLocation);
    	if(propertiesStream == null)
    	{
    		throw new RestServiceException("Properties file does not exist!");
    	}

    	try
    	{
	    	properties.load(propertiesStream);
	    	return properties.getProperty(key);
    	}
    	catch(IOException e)
    	{
    		throw new RestServiceException("Can't read property file!", e);
    	}
    }
	
	
	protected InputStream getXmlFileStream(String filename)
	{		
		String directory = loadFromPropertyFile(PROPERTIES_FILE_LOCATION, "xml.directory");
		try
		{
			if(directory != null)
			{
				LOG.info("read XML file: " + filename + " from directory: " + directory);
				return new FileInputStream(new File(directory, filename));
			}
			else
			{
				LOG.info("read XML file: " + filename + " from CLASSPATH");
				return AbstractResource.class.getResourceAsStream(filename);
			}
		}
		catch (FileNotFoundException e)
		{
			throw new RestServiceException("Can't access xml file: " + new File(directory, filename).getAbsolutePath(), e);
		}
	}
	
	
	protected void validateXmlFile(String filename) 
	{
		LOG.debug("validate XML file: " + filename);

		try
		{			
		    SchemaFactory schemaFactory=SchemaFactory.newInstance(XML_SCHEMA_URL);
			Schema schema = schemaFactory.newSchema(this.getClass().getResource(XSD_FILE_LOCATION ));	
		    Validator validator = schema.newValidator();
		    validator.validate(new StreamSource(getXmlFileStream(filename)));
		}
		catch(IOException e)
		{
			throw new RestServiceException("Invalid XML file: " + filename, e);
		}
		catch(SAXException e)
		{
			throw new RestServiceException("Invalid XML file: " + filename, e);
		}
	}
	
	
	protected String readXmlFile(String filename)
    {
		LOG.debug("read XML file: " + filename);
		
//		validateXmlFile(filename);
		
		BufferedReader reader = null;		
		try
		{
			reader = new BufferedReader(new InputStreamReader(getXmlFileStream(filename)));
        
			StringBuilder xml = new StringBuilder();
			String line;
	        while ((line = reader.readLine()) != null) 
	        {
	        	xml.append(line).append('\n');
	        }
	        return xml.toString();
        }
        catch (IOException e)
        {
        	LOG.error("Can't read XML file!", e);
        	return "";
        }
		finally
		{
            try
            {
            	if(reader != null)
                reader.close();
            }
            catch (IOException e)
            {
            	LOG.error("Can't close BufferedReader!", e);
            }
		}
    }
}
