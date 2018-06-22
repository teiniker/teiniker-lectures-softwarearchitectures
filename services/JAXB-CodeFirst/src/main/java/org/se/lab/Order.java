package org.se.lab;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order
	extends Entity
{
	/*
	 * Constructor
	 */
    protected Order()
    {      
    }
    
	public Order(long id, String name)
	{
		setId(id);
		setName(name);
	}
	
	
	/*
	 * Property: name:String
	 */
	@XmlElement
	private String name;
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		if(name == null)
			throw new IllegalArgumentException();
		this.name = name;
	}
	
	/*
	 * Association: ---[*]-> OrderLines
	 */
	@XmlElementWrapper(name = "lines")
	@XmlElement(name = "line")
	private List<OrderLine> lines = new ArrayList<>();
    public List<OrderLine> getLines()
    {
        return lines;
    }
    public void setLines(List<OrderLine> lines)
    {
        if(lines == null)
            throw new IllegalArgumentException();
        this.lines = lines;
    }
	
}
