package org.se.lab;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = CreditCard.TABLE_NAME)
public class CreditCard
{
	public static final String TABLE_NAME = "TEST_CREDIT_CARD";
	
	/*
	 * Constructors
	 */
	public CreditCard(String number)
	{
		setNumber(number);
	}
	
	protected CreditCard() {}
	
	
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
	 * Property: number:String
	 */
	@Column(name="CARD_NUMBER")
	private String number;
	public String getNumber()
	{
		return number;
	}
	public void setNumber(String number)
	{
		this.number = number;
	}
	

	/*
	 * Relationship: one-to-one bidirectional
	 * CreditCard -[1]--[1]- Customer
	 */
	@OneToOne(mappedBy="creditCard")
	private Customer customer;
	public Customer getCustomer()
	{
		return customer;
	}
	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}
	
}
