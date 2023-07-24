package org.se.lab;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = Phone.TABLE_NAME)
public class Phone
{
	public static final String TABLE_NAME = "TEST_PHONE";
	
	/*
	 * Constructors
	 */
	public Phone(String number)
	{
		setNumber(number);
	}
	
	protected Phone() {}
	
	
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
	@Column(name="PHONE_NUMBER")
	private String number;
	public String getNumber()
	{
		return number;
	}
	public void setNumber(String number)
	{
		this.number = number;
	}
}

