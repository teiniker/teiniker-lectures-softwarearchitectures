package org.se.lab.presentation;

import java.util.List;

import org.se.lab.data.Person;
import org.se.lab.service.PersonService;

public class PersonViewHelper
{        
    /* 
     * Setter injection 
     * Association: ---[1]-> service:PersonService
     */
	private PersonService service;
    public void setService(PersonService service)
    {
        if(service == null)
            throw new NullPointerException();
        this.service = service;
    }
    
    
    public void setPerson(String id, String firstName, String lastName)
    {
        // TODO: validate parameters
    	Person p = new Person(Long.parseLong(id), firstName, lastName);
        service.save(p);
    }
    
    public String getPersonTable()
    {
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
     * Helper methods
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
