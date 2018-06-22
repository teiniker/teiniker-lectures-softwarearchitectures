package org.se.lab;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XPathTest
{
    @Test
    public void testEvaluateToString() 
        throws XPathExpressionException
    {
        XPath xpath = XPathFactory.newInstance().newXPath();
        InputSource src = new InputSource("src/test/resources/xml/order.xml");
        
        Assert.assertEquals("FHJ-20151020-007", xpath.evaluate("/order/name", src));
        Assert.assertEquals("100", xpath.evaluate("/order/@id", src));
        
        
        NodeList nodes = (NodeList) xpath.evaluate("//product",src, XPathConstants.NODESET);
        
        Assert.assertEquals(3, nodes.getLength());
        for(int i = 0; i< nodes.getLength(); i++)
        {
            Product p = toProduct((Element)nodes.item(i));
            System.out.println(p.getId() + "," + p.getDescription() + "," + p.getPrice());
        }
    }
    
    private Product toProduct(Element element)
    {
        String id = element.getAttribute("id");
        
        NodeList children = element.getChildNodes();            
        String description = children.item(1).getTextContent();
        String price = children.item(3).getTextContent();
        
        Product p = new Product();
        p.setId(Long.parseLong(id));
        p.setDescription(description);
        p.setPrice(Long.parseLong(price));
        
        return p;
    }
}
