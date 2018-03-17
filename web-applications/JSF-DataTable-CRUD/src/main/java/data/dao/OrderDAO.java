package data.dao;

import data.model.Order;

import java.util.List;

public interface OrderDAO
{

	Order insert(Order order);

	Order update(Order order);

	void delete(Order order);

	Order findById(long id);

	List<Order> findAll();
}
