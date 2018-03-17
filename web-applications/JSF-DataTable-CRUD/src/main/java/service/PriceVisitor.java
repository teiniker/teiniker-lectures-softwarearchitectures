package service;

import data.model.OrderElement;

public interface PriceVisitor
{

	double visitEntity(OrderElement orderElement);
}
