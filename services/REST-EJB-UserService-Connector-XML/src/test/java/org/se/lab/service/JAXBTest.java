package org.se.lab.service;

import java.io.IOException;
import java.io.StringReader;
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
import org.junit.Test;
import org.se.lab.User;


public class JAXBTest
{
    @Test
    public void testSerialization() throws JAXBException, IOException
    {        
        User user = new User(7, "homer", "ijD8qepbRnIsva0kx0cKRCcYysg=");
        
        JAXBContext context = JAXBContext.newInstance(User.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        Writer out = new StringWriter();
        marshaller.marshal(user, out);
        out.close();            
        String xml = out.toString();
    
        String expected = 
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<user id=\"7\">\n" +
                "    <username>homer</username>\n" +
                "    <password>ijD8qepbRnIsva0kx0cKRCcYysg=</password>\n" +
                "</user>\n";        
        Assert.assertEquals(expected, xml);
    }
           
    
    @Test
    public void testDeserialization() throws JAXBException
    {
        String xml = 
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<user id=\"7\">" +
                    "<username>homer</username>" +
                    "<password>ijD8qepbRnIsva0kx0cKRCcYysg=</password>" +
                "</user>";
        
        Source src = new StreamSource(new StringReader(xml));
        JAXBContext context = JAXBContext.newInstance(User.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        JAXBElement<User> element = unmarshaller.unmarshal(src, User.class);        
        User user = element.getValue();
        
        String expected = "User [id=7, username=homer, password=ijD8qepbRnIsva0kx0cKRCcYysg=]";
        Assert.assertEquals(expected, user.toString());
    }
}
