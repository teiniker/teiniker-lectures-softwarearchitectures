package org.se.lab.presentation;

import java.util.List;

import org.se.lab.data.Person;
import org.se.lab.service.PersonServiceImpl;

public class PersonViewHelper
{        
    public void setPerson(String id, String firstName, String lastName)
    {
        // TODO: validate parameters
        Person p = new Person(Long.parseLong(id), firstName, lastName);
        PersonServiceImpl service = new PersonServiceImpl();
        service.save(p);
    }
    
    public String getPersonTable()
    {
    	PersonServiceImpl service = new PersonServiceImpl();
        List<Person> list = service.load();
        StringBuilder html = new StringBuilder();
        html.append("<TABLE border=\"1\">").append("\n");
        for(Person p : list)
        {
            html.append(getPersonRow(p));
        }        
        html.append("</TABLE>");
        return html.toString();
    }
    
    /*
     * Helper operations
     */
    
    protected String getPersonRow(Person p)
    {
        StringBuilder html = new StringBuilder();
        html.append("  <TR>").append("\n");
        html.append("    <TD>").append(p.getId()).append("</TD>").append("\n");
        html.append("    <TD>").append(p.getFirstName()).append("</TD>").append("\n");
        html.append("    <TD>").append(p.getLastName()).append("</TD>").append("\n");
        html.append("  </TR>").append("\n");
        return html.toString();
    }
    
    // ...
}
