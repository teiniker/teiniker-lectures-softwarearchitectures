package org.se.lab;

import java.io.Serializable;

public class Article implements Serializable
{
	private static final long serialVersionUID = 1L;

	public Article(String description, long price)
	{		
		setId(id);
		setDescription(description);
		setPrice(price);
	}
	
	private int id;
	public int getId()
	{
		return this.id;
	}
	public void setId(int id)
	{
		if(id < 0)
			throw new IllegalArgumentException("Invalid parameter id: " + id);
		this.id = id;
	}

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
		return getId() + "," + getDescription() + "," + getPrice();
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
		Article other = (Article) obj;
		if (id != other.id)
			return false;
		return true;
	}
}