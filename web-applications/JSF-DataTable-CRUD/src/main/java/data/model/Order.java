package data.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "`order`")
@Table
public class Order extends OrderElement
{
	private static final long serialVersionUID = 1L;

	private String customer;
	public String getCustomer()
	{
		return customer;
	}
	public void setCustomer(String customer)
	{
		this.customer = customer;
	}

	@OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "order")
	private List<OrderLine> orderLines;
	public List<OrderLine> getOrderLines()
	{
		return orderLines;
	}
	public void setOrderLines(List<OrderLine> orderLines)
	{
		this.orderLines = orderLines;
	}
}
