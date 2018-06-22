package org.se.lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class OrderValidationTest
{
	private JAXBContext context;

	@Before
	public void setup() throws JAXBException
	{
		context = JAXBContext.newInstance("org.se.lab");
	}
		
	@Test
	public void testReadAndValidateOrder() throws SAXException, JAXBException, FileNotFoundException
	{
	    SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema xsd = schemaFactory.newSchema(new File("src/main/resources/xsd", "order.xsd"));
        
		Source src = new StreamSource(new FileReader(new File("src/test/resources/xml", "order.xml")));
		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(xsd);  // validate XML against XSD
		JAXBElement<Order> element = unmarshaller.unmarshal(src, Order.class);
		Order order = element.getValue();

		Assert.assertEquals("FHJ-20151020-007", order.getName());
	}
}
