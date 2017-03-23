package org.se.lab.presentation;

import org.se.lab.service.PersonService;

public class ViewHelperFactory
{

	public PersonViewHelper createPersonViewHelper(PersonService service)
	{
		PersonViewHelper helper = new PersonViewHelper();
		helper.setService(service);
		return helper;
	}
}
