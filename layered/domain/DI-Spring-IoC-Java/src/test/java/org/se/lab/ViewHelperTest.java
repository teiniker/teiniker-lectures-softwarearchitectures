package org.se.lab;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.se.lab.presentation.PersonViewHelper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ViewHelperTest
{
	private AnnotationConfigApplicationContext context;

	private static final String HTML = """
		<TABLE border="1">
		  <TR>
		    <TD>3</TD>
		    <TD>Stefan</TD>
		    <TD>Zweig</TD>
		  </TR>
		  <TR>
		    <TD>7</TD>
		    <TD>Wolf</TD>
		    <TD>Haas</TD>
		  </TR>
		  <TR>
		    <TD>11</TD>
		    <TD>Hermann</TD>
		    <TD>Hesse</TD>
		  </TR>
		</TABLE>""";
 
    
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
        assertEquals(HTML, table);

		context.close();
    }
}
