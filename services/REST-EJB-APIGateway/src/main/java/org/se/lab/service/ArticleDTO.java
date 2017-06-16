package org.se.lab.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="article")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArticleDTO
{
	/*
	 * A default constructor is needed to make the JAXB marshaler happy.
	 */
	protected ArticleDTO()
	{
		this(0, "", 0L);
	}
	
	public ArticleDTO(int id, String description, long price)
	{
		setId(id);
		setDescription(description);
		setPrice(price);
	}
	
		
	
	/*
	 * Property: id:int
	 */
	@XmlAttribute
	private int id;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	
	/*
	 * Property: description:String
	 */
	@XmlElement
	private String description;	
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		if(description == null)
			throw new IllegalArgumentException("Invalid parameter description!");
		this.description = description;
	}
	
	
	/*
	 * Property: price:String
	 */
	@XmlAttribute
	private long price;
	public long getPrice()
	{
		return price;
	}
	public void setPrice(long price)
	{
		if(price < 0)
			throw new IllegalArgumentException("Invalid parameter price: " + price);
		this.price = price;
	}	
	
	
	/*
	 * Object methods
	 */
	
	@Override
	public String toString()
	{
		return "User [id=" + getId() + ", description=" + getDescription() + ", price=" + getPrice() + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticleDTO other = (ArticleDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}	
}
