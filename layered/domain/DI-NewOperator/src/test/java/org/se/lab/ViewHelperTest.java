package org.se.lab;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.se.lab.presentation.PersonViewHelper;

public class ViewHelperTest
{   
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
    public void testPersonViewHelper()
    {
        PersonViewHelper helper = new PersonViewHelper(); 
        
        helper.setPerson("7", "Wolf", "Haas");
        helper.setPerson("3", "Stefan", "Zweig");
        helper.setPerson("11", "Hermann", "Hesse");
        
        String table = helper.getPersonTable();
        System.out.println(table);
        assertEquals(HTML, table);
    }
}
