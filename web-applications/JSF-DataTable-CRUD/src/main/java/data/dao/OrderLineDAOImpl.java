package data.dao;

import data.model.OrderLine;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class OrderLineDAOImpl implements OrderLineDAO
{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public OrderLine insert(OrderLine orderLine)
	{
		entityManager.persist(orderLine);
		return orderLine;
	}

	@Override
	public OrderLine update(OrderLine orderLine)
	{
		return entityManager.merge(orderLine);
	}

	@Override
	public void delete(OrderLine orderLine)
	{
		OrderLine ol = findById(orderLine.getId());
		entityManager.remove(ol);
	}

	@Override
	public OrderLine findById(long id)
	{
		return entityManager.find(OrderLine.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderLine> findAll()
	{
		final String hql = "SELECT a FROM " + OrderLine.class.getName() + " AS a";
		return entityManager.createQuery(hql).getResultList();
	}
}
