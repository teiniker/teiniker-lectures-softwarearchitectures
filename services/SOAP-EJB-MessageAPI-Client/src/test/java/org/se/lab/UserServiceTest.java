package org.se.lab;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.client.Order;
import org.se.lab.client.OrderLine;
import org.se.lab.client.OrderResourceEJBService;
import org.se.lab.client.OrderService;
import org.se.lab.client.Product;

public class UserServiceTest 
{
	private OrderService service;
	
	@Before
	public void init()
	{
		OrderResourceEJBService ws = new OrderResourceEJBService();
		service = ws.getOrderServicePort();	
	}
	
	@After
	public void destroy()
	{
	}
	
	
	@Test
	public void testProcessOrder()
	{
        Order order = new Order();
        order.setName("FHJ-20151020-007");
        
        OrderLine l1 = new OrderLine();
        l1.setQuantity(2);
        Product p1 = new Product();
        p1.setDescription("Effective Java");
        p1.setPrice(3336);
        l1.setProduct(p1);
        order.getLines().add(l1);

        OrderLine l2 = new OrderLine();
        l2.setQuantity(2);
        Product p2 = new Product();
        p2.setDescription("Design Patterns");
        p2.setPrice(5280);
        l2.setProduct(p2);
        order.getLines().add(l2);

	    service.process(order);
	}
}
