package org.se.lab;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


@Entity
@Table(name=Person.TABLE_NAME)

@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DISCRIMINATOR", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("PERSON")

//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)

//@Inheritance(strategy=InheritanceType.JOINED)
public class Person
{
	public static final String TABLE_NAME = "TEST_PERSON";
	
	/*
	 * Constructor
	 */
	public Person(String firstName, String lastName)
	{
		setFirstName(firstName);
		setLastName(lastName);
	}
	
	protected Person()
	{
		this("","");
	}
	
	
	/*
	 * Property: id
	 */
	@Id
	@GeneratedValue
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
	 * Property: firstName
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
	 * Property: lastName
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
	 * Object methods
	 */
	
	@Override
	public String toString()
	{
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + "]";
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
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
