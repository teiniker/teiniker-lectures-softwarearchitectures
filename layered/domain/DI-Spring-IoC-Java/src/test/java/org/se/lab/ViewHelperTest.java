package org.se.lab;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.se.lab.presentation.PersonViewHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ViewHelperTest
{
	private AnnotationConfigApplicationContext context;

	private static final String html = 
    	"<TABLE border=\"1\">\n"+
    	"  <TR>\n" +
    	"    <TD>3</TD>\n" +
    	"    <TD>Stefan</TD>\n" +
    	"    <TD>Zweig</TD>\n" +
    	"  </TR>\n" +
    	"  <TR>\n" +
    	"    <TD>7</TD>\n" +
    	"    <TD>Wolf</TD>\n" +
    	"    <TD>Haas</TD>\n" +
    	"  </TR>\n" +
    	"  <TR>\n" +
    	"    <TD>11</TD>\n" +
    	"    <TD>Hermann</TD>\n" +
    	"    <TD>Hesse</TD>\n" +
    	"  </TR>\n" +
    	"</TABLE>";
 
    
    @Test
    public void testViewHelper()
    {
		// Load the configuration class
		context = new AnnotationConfigApplicationContext(AppConfiguration.class);

		// Get the beans
		PersonViewHelper helper = context.getBean(PersonViewHelper.class);

        helper.setPerson("7", "Wolf", "Haas");
        helper.setPerson("3", "Stefan", "Zweig");
        helper.setPerson("11", "Hermann", "Hesse");
        
        String table = helper.getPersonTable();
        System.out.println(table);
        assertEquals(html, table);
    }
}
