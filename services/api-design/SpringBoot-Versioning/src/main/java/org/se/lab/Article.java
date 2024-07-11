package org.se.lab;

import java.io.Serializable;

public class Article implements Serializable
{
	private static final long serialVersionUID = 1L;

	public Article(long id, String description, long price)
	{		
		setId(id);
		setDescription(description);
		setPrice(price);
	}

	private long id;
	public long getId()
	{
		return this.id;
	}
	public void setId(long id)
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
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Article article = (Article) o;
		return id == article.id;
	}

	@Override
	public int hashCode()
	{
		return (int) (id ^ (id >>> 32));
	}
}