package org.se.lab;

import org.se.lab.data.DAOFactory;
import org.se.lab.data.PersonDAO;
import org.se.lab.presentation.PersonViewHelper;
import org.se.lab.presentation.ViewHelperFactory;
import org.se.lab.service.PersonService;
import org.se.lab.service.ServiceFactory;

public class Assembler
{
	public PersonViewHelper getPersonViewHelper()
	{
		DAOFactory daoFactory = new DAOFactory();
    	ServiceFactory serviceFactory = new ServiceFactory();
    	ViewHelperFactory viewHelperFactory = new ViewHelperFactory();
		
        PersonDAO dao = daoFactory.createPersonDAOWithLogger();
        PersonService service = serviceFactory.createPersonService(dao);
        PersonViewHelper viewHelper = viewHelperFactory.createPersonViewHelper(service);
              
        return viewHelper;
	}
}
