package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import data.dao.OrderDAO;
import data.dao.OrderLineDAO;
import data.model.Order;
import data.model.OrderLine;

@Stateless
public class OrderService
{
	private final Logger LOG = Logger.getLogger(OrderService.class);
	
	@Inject
	private OrderDAO orderDAO;

	@Inject
	private OrderLineDAO orderLineDAO;

	private PriceVisitor priceVisitor = new PriceVisitorImpl();

	public void add(Order order)
	{
		LOG.debug("add: " + order);
		
		try
		{
			orderDAO.insert(order);
		} 
		catch (Exception e)
		{
			LOG.error("Can't add Order " + order, e);
			throw new ServiceException("Can't add order: " + order);
		}
	}

	public List<Order> findAll()
	{
		try
		{
			List<Order> orderList = orderDAO.findAll();
			LOG.debug("findAll: " + orderList);	
			return orderList;
		} 
		catch (Exception e)
		{
			LOG.error("Can't find Orders", e);
			throw new ServiceException("Can't find all orders");
		}
	}

	public void delete(Order order)
	{
		LOG.debug("delete: " + order);
		try
		{
			orderDAO.delete(order);
		} 
		catch (Exception e)
		{
			LOG.error("Can't delete Order " + order, e);
			throw new ServiceException("Can't remove order: " + order);
		}
	}

	public void update(Order order)
	{
		LOG.debug("update: " + order);
		try
		{
			order.setPrice(priceVisitor.visitEntity(order));
			orderDAO.update(order);
		} 
		catch (Exception e)
		{
			LOG.error("Can't delete Order " + order, e);
			throw new ServiceException("Can't update order: " + order);
		}
	}

	public void addOrderLine(Order order, OrderLine orderLine)
	{
		LOG.debug("addOrderLine: " + order + "," + orderLine);
		try
		{
			orderLine.setPrice(priceVisitor.visitEntity(orderLine));
			List<OrderLine> orderLines = order.getOrderLines();
			orderLines.add(orderLine);
			update(order);
			orderLine.setOrder(order);
			orderLineDAO.insert(orderLine);
		} 
		catch (Exception e)
		{
			LOG.error("Can't add OrderLine " + order + "," + orderLine, e);
			throw new ServiceException("Can't add orderline: " + orderLine + " for order: " + order);
		}
	}

	public void removeOrderLine(OrderLine orderLine)
	{
		LOG.debug("removeOrderLine: " + orderLine);

		try
		{
			orderLineDAO.delete(orderLine);
			Order order = orderLine.getOrder();
			order.getOrderLines().remove(orderLine);
			update(order);
		} 
		catch (Exception e)
		{
			LOG.error("Can't remove OrderLine " + orderLine, e);
			throw new ServiceException("Can't remove orderline: " + orderLine);
		}
	}
}
