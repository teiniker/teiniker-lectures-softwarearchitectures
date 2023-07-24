package org.se.lab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = Reservation.TABLE_NAME)
public class Reservation
{
	protected static final String TABLE_NAME = "TEST_RESERVATION";
	
	/*
	 * Constructors
	 */
	public Reservation(Date dateReserved, long amountPaid)
	{
		setDateReserved(dateReserved);
		setAmountPaid(amountPaid);
	}
	
	protected Reservation() {}
	
	
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
	 * Property: amountPaid:long
	 */
	@Column(name="AMOUNT_PAID")
	private long amountPaid;
	public long getAmountPaid()
	{
		return amountPaid;
	}
	public void setAmountPaid(long amountPaid)
	{
		this.amountPaid = amountPaid;
	}
	
	
	/*
	 * Property: dateReserved:Date
	 */
	@Column(name="DATE_RESERVED")
	private Date dateReserved;
	public Date getDateReserved()
	{
		return dateReserved;
	}
	public void setDateReserved(Date dateReserved)
	{
		this.dateReserved = dateReserved;
	}
	
	
	/*
	 * Relationship: many-to-many bidirectional
	 * Reservation -[*]---[*]- Customer 
	 */
	@ManyToMany // owning side
	@JoinTable(name="TEST_RESERVATION_CUSTOMER", 
		joinColumns={@JoinColumn(name="RESERVATION_ID")},
		inverseJoinColumns={@JoinColumn(name="CUSTOMER_ID")}
	)				
	private List<Customer> customers = new ArrayList<Customer>();
	public List<Customer> getCustomers()
	{
		return customers;
	}
	public void setCustomers(List<Customer> customers)
	{
		this.customers = customers;
	}
	
}
