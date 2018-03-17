package web;

import data.model.Article;
import data.model.Order;
import data.model.OrderLine;
import service.ArticleService;
import service.OrderService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class OrderDataBean 
	implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private OrderService orderService;

	@Inject
	private ArticleService articleService;

	/*
	 * Properties
	 */

	private List<Order> orderList;
	public List<Order> getOrderList()
	{
		return orderList;
	}
	public void setOrderList(List<Order> orderList)
	{
		this.orderList = orderList;
	}

	private String customer;
	public String getCustomer()
	{
		return customer;
	}
	public void setCustomer(String customer)
	{
		this.customer = customer;
	}

	private int amount;
	public int getAmount()
	{
		return amount;
	}
	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	private long artId;
	public long getArtId()
	{
		return artId;
	}
	public void setArtId(long artId)
	{
		this.artId = artId;
	}

	/*
	 * Actions
	 */

	public String showOrderListAction()
	{
		orderList = orderService.findAll();
		return "";
	}

	public String addOrderAction()
	{
		Order order = new Order();
		order.setCustomer(this.customer);
		orderService.add(order);
		return showOrderListAction();
	}

	public String removeOrderAction(Order order)
	{
		if (order != null)
		{
			orderService.delete(order);
		}
		return showOrderListAction();
	}

	public String addOrderLineAction(Order order)
	{
		OrderLine orderLine = new OrderLine();
		orderLine.setAmount(this.amount);
		Article article = articleService.findById(this.artId);
		orderLine.setArticle(article);
		orderService.addOrderLine(order, orderLine);
		return "";
	}

	public String removeOrderLineAction(OrderLine orderLine)
	{
		orderService.removeOrderLine(orderLine);
		return showOrderListAction();
	}
}
