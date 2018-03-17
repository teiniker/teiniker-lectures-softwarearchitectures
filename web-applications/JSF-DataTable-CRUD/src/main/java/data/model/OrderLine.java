package data.model;

import javax.persistence.*;

@Entity(name = "orderline")
@Table
public class OrderLine extends OrderElement
{
	private static final long serialVersionUID = 1L;

	@OneToOne
	private Article article;
	public Article getArticle()
	{
		return article;
	}
	public void setArticle(Article article)
	{
		this.article = article;
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

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;
	public Order getOrder()
	{
		return order;
	}

	public void setOrder(Order order)
	{
		this.order = order;
	}
}
