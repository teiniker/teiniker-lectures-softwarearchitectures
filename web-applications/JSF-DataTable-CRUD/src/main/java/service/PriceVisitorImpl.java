package service;

import data.model.Article;
import data.model.Order;
import data.model.OrderElement;
import data.model.OrderLine;

public class PriceVisitorImpl 
	implements PriceVisitor
{

	@Override
	public double visitEntity(OrderElement orderElement)
	{

		if (orderElement instanceof Article)
		{
			return orderElement.getPrice();
		}

		if (orderElement instanceof OrderLine)
		{
			OrderLine orderLine = (OrderLine) orderElement;
			return orderLine.getArticle().getPrice() * orderLine.getAmount();
		}

		if (orderElement instanceof Order)
		{

			Order order = (Order) orderElement;

			double price = 0.0;

			for (OrderLine ol : order.getOrderLines())
			{
				price += ol.getPrice();
			}
			return price;
		}
		return 0;
	}
}
