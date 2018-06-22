package org.se.lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class OrderTest
{
	private JAXBContext context;

	@Before
	public void setup() throws JAXBException
	{
		context = JAXBContext.newInstance(Order.class);
	}
		
	@Test
	public void testReadOrder() throws SAXException, JAXBException, FileNotFoundException
	{
		Source src = new StreamSource(new FileReader(new File("src/test/resources/xml", "order.xml")));
		Unmarshaller unmarshaller = context.createUnmarshaller();
		JAXBElement<Order> element = unmarshaller.unmarshal(src, Order.class);
		Order order = element.getValue();

		Assert.assertEquals("FHJ-20151020-007", order.getName());
	}
	
	@Test
	public void testWriteOrder() throws JAXBException, IOException
	{
	    Order order = new Order(100, "FHJ-20151020-007");
	    order.getLines().add(new OrderLine(101, 2, new Product(102, "Effective Java", 3336)));
	    order.getLines().add(new OrderLine(101, 2, new Product(103, "Design Patterns", 5280)));
	    
        JAXBContext context = JAXBContext.newInstance(Order.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        Writer out = new StringWriter();
        marshaller.marshal(order, out);
        out.close();            
        String xml = out.toString();

        System.out.println(xml);
	}	
}
