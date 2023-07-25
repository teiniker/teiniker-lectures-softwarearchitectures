package org.se.lab;

import org.se.lab.data.PersonDAO;
import org.se.lab.data.PersonDAOInMemoryImpl;
import org.se.lab.data.PersonDAOLogger;
import org.se.lab.presentation.PersonViewHelper;
import org.se.lab.service.PersonService;
import org.se.lab.service.PersonServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration
{
    @Bean
    public PersonDAO personDAO()
    {
        return new PersonDAOInMemoryImpl();
    }

    @Bean
    public PersonDAOLogger personDAOlogger(PersonDAO personDAO)
    {
        PersonDAOLogger personDAOLogger = new PersonDAOLogger();
        personDAOLogger.setDao(personDAO);
        return personDAOLogger;
    }

    @Bean
    public PersonService personService(PersonDAOLogger personDAOLogger)
    {
        PersonServiceImpl personService = new PersonServiceImpl();
        personService.setDao(personDAOLogger);
        return personService;
    }

    @Bean
    public PersonViewHelper personHelper(PersonService personService)
    {
        PersonViewHelper personViewHelper = new PersonViewHelper();
        personViewHelper.setService(personService);
        return personViewHelper;
    }
}
