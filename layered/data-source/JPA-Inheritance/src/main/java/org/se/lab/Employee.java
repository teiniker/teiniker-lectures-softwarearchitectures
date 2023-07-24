package org.se.lab;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name=Employee.TABLE_NAME)
@DiscriminatorValue("EMPLOYEE")
public class Employee
	extends Person
{
	public static final String TABLE_NAME = "TEST_EMPLOYEE";

	/*
	 * Constructor
	 */
	public Employee(String firstName, String lastName, int employeeId)
	{
		super(firstName, lastName);
		setEmployeeId(employeeId);
	}
	
	protected Employee()
	{
		this("","",0);
	}
	
	
	/*
	 * Property: employeeId
	 */
	private int employeeId;
	public int getEmployeeId()
	{
		return employeeId;
	}
	public void setEmployeeId(int employeeId)
	{
		this.employeeId = employeeId;
	}
	
	
	/*
	 * Object methods
	 */
	
	@Override
	public String toString()
	{
		return "Employee ["
				+ ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName()
				+ "employeeId=" + employeeId + ", getId()=" + getId()
				+ "]";
	}
}
