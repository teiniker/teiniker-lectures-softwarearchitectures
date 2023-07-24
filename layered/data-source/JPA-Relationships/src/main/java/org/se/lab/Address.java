package org.se.lab;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = Address.TABLE_NAME)
public class Address
{
	public static final String TABLE_NAME = "TEST_ADDRESS";
	
	/*
	 * Constructors
	 */
	public Address(String street, String city, String state)
	{
		setStreet(street);
		setCity(city);
		setState(state);		
	}
	
	protected Address() {}
	
	
	/*
	 * Property: id:int
	 */
	@Id
	@GeneratedValue
	private int id;
	public int getId()
	{
		return id;
	}

	
	/*
	 * Property: street:String 
	 */
	private String street;
	public String getStreet()
	{
		return street;
	}
	public void setStreet(String street)
	{
		this.street = street;
	}

	
	/*
	 * Property: city:String
	 */
	private String city;
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	
	
	/*
	 * Property: state:String
	 */
	private String state;
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
}
