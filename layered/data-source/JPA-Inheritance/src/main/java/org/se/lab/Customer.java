package org.se.lab;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name=Customer.TABLE_NAME)
@DiscriminatorValue("CUSTOMER")
public class Customer
	extends Person
{
	public static final String TABLE_NAME = "TEST_CUSTOMER";
	
	/*
	 * Constructor
	 */
	public Customer(String firstName, String lastName, String street, String city, String state)
	{
		super(firstName,lastName);
		setStreet(street);
		setCity(city);
		setState(state);
	}
	
	protected Customer()
	{
		this("","","","","");
	}
	
	
	/*
	 * Property: street
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
	 * Property: city
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
	 * Property: state
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

		
	/*
	 * Object methods
	 */
	
	@Override
	public String toString()
	{
		return "Customer [street=" + street + ", city=" + city + ", state="
				+ state + "]";
	}	
}
