package org.se.lab;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = Customer.TABLE_NAME)
public class Customer
{
	public static final String TABLE_NAME = "TEST_CUSTOMER";
	
	/*
	 * Constructors 
	 */
	
	public Customer(String firstName, String lastName)
	{
		setFirstName(firstName);
		setLastName(lastName);
	}
	
	protected Customer() {}
	
	
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
	 * Property: firstName:String
	 */
	private String firstName;
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	
	/*
	 * Property: lastName:String
	 */
	private String lastName;
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	

	/*
	 * Relationship: one-to-one unidirectional 
	 * Customer ---[1]-> Address
	 */
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="ADDRESS_ID")
	private Address address;
	public Address getAddress()
	{
		return address;
	}
	public void setAddress(Address address)
	{
		this.address = address;
	}
	
	
	/*
	 * Relationship: one-to-one bidirectional 
	 * Customer -[1]--[1]- CreditCard
	 */
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="CREDIT_CARD_ID")
	private CreditCard creditCard;
	public CreditCard getCreditCard()
	{
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard)
	{
		this.creditCard = creditCard;
		creditCard.setCustomer(this);
	}
	
	/*
	 * Relationship: one-to-many unidirectional 
	 * Customer ---[*]-> Phone
	 */
	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(name="CUSTOMER_ID")
	private List<Phone> phoneNumbers = new ArrayList<Phone>();
	public List<Phone> getPhoneNumbers()
	{
		return phoneNumbers;
	}
	public void setPhoneNumbers(List<Phone> phoneNumbers)
	{
		this.phoneNumbers = phoneNumbers;
	}
	
	
	/*
	 * Relationship: many-to-many bidirectional
	 * Customer -[*]---[*]- Reservation
	 */
	@ManyToMany(mappedBy="customers")
	private List<Reservation> reservations = new ArrayList<Reservation>();
	public List<Reservation> getReservations()
	{
		return reservations;
	}
	public void setReservations(List<Reservation> reservations)
	{
		this.reservations = reservations;
	}
	
}
