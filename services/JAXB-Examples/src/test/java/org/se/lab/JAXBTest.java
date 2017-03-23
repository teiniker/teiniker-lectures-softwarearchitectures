package org.se.lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class JAXBTest
{
	private JAXBContext context;

	@Before
	public void setup() throws JAXBException
	{
		context = JAXBContext.newInstance("org.se.lab");
	}
	
	
	@Test
	public void testReadSession() throws SAXException, JAXBException, FileNotFoundException
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema xsd = schemaFactory.newSchema(new File("src/main/resources/xsd", "session.xsd"));
		
		Source src = new StreamSource(new FileReader(new File("src/test/resources/xml", "session.xml")));
		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(xsd);  // validate XML against XSD
		JAXBElement<SessionRootType> element = unmarshaller.unmarshal(src, SessionRootType.class);
		SessionRootType root = element.getValue();

		Assert.assertEquals(2, root.getSessions().size());
		
		SessionType s1 = root.getSessions().get(0);
		Assert.assertEquals("one", s1.getId());

		SessionType s2 = root.getSessions().get(1);
		Assert.assertEquals("two", s2.getId());
	}
	
	@Test
	public void testWriteSession() throws JAXBException
	{
		ObjectFactory factory = new ObjectFactory();
		SessionRootType root = factory.createSessionRootType();
		
		SessionType s1 = factory.createSessionType();
		s1.setId("one");
		s1.setValid(true);
		
		SessionType s2 = factory.createSessionType();
		s2.setId("two");
		s2.setValid(false);
		
		root.getSessions().add(s1);
		root.getSessions().add(s2);
		
		JAXBElement<SessionRootType> element = factory.createSessionRoot(root);

		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(element, System.out);
	}
	
	@Test
	public void testReadItem() throws SAXException, JAXBException, FileNotFoundException
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema xsd = schemaFactory.newSchema(new File("src/main/resources/xsd", "session.xsd"));
		
		Source src = new StreamSource(new FileReader(new File("src/test/resources/xml", "item.xml")));
		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(xsd);  // validate XML against XSD
		JAXBElement<Item> element = unmarshaller.unmarshal(src, Item.class);
		Item root = element.getValue();

		Assert.assertEquals("Widget", root.getDescription());		
		Assert.assertEquals(new BigDecimal("500.0"), root.getPrice());
		Assert.assertEquals(new BigInteger("1"), root.getQuantity());		
	}
	
}
