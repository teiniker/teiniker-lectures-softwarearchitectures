package data.dao;

import data.model.OrderLine;

import java.util.List;

public interface OrderLineDAO
{

	OrderLine insert(OrderLine orderLine);

	OrderLine update(OrderLine orderLine);

	void delete(OrderLine orderLine);

	OrderLine findById(long id);

	List<OrderLine> findAll();
}
