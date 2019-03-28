package org.se.lab;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public abstract class AbstractEJBTest
{
    public IntegerConverter lookupEJB()
    {
        try
        {
            final Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);

            final String jndiName = "ejb:" + ""
                    + "/" + "EJB-Stateless-IntegerConverter"
                    + "/" + ""
                    + "/" + "IntegerConverterEJB"
                    + "!" + IntegerConverter.class.getName();

            System.out.println("JNDI Name = " + jndiName);
            IntegerConverter counter =  (IntegerConverter) context.lookup(jndiName);
            return counter;
        }
        catch(NamingException e)
        {
            throw new IllegalStateException("Can't find a component reference!", e);
        }
    }

}
