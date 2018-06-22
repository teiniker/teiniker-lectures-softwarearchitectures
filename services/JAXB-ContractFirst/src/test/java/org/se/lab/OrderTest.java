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
		context = JAXBContext.newInstance("org.se.lab");
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
	    Order order = new Order();
	    order.setId(100);
	    order.setName("FHJ-20151020-007");
	    
	    OrderLine l1 = new OrderLine();
	    l1.setId(101);
	    l1.setQuantity(2);
	    Product p1 = new Product();
	    p1.setId(102);
	    p1.setDescription("Effective Java");
	    p1.setPrice(3336);
	    l1.setProduct(p1);
	    order.getLines().add(l1);

        OrderLine l2 = new OrderLine();
        l2.setId(103);
        l2.setQuantity(2);
        Product p2 = new Product();
        p2.setId(104);
        p2.setDescription("Design Patterns");
        p2.setPrice(5280);
        l2.setProduct(p2);
        order.getLines().add(l2);
	    	    
        ObjectFactory factory = new ObjectFactory();
        JAXBElement<Order> element = factory.createOrder(order);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        Writer out = new StringWriter();
        marshaller.marshal(element, out);
        out.close();
        String xml = out.toString();

        System.out.println(xml);
	}	
}
