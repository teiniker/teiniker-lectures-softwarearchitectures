package data.dao;

import data.model.Order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class OrderDAOImpl implements OrderDAO
{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Order insert(Order order)
	{
		entityManager.persist(order);
		return order;
	}

	@Override
	public Order update(Order order)
	{
		return entityManager.merge(order);
	}

	@Override
	public void delete(Order order)
	{
		Order o = findById(order.getId());
		entityManager.remove(o);
	}

	@Override
	public Order findById(long id)
	{
		return entityManager.find(Order.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findAll()
	{
		final String hql = "SELECT a FROM " + Order.class.getName() + " AS a";
		return entityManager.createQuery(hql).getResultList();
	}
}
