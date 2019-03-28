package org.se.lab;

import org.jboss.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class MultiThreadedClient
        extends AbstractEJBTest
        implements Runnable
{
    private final Logger LOG = Logger.getLogger(MultiThreadedClient.class);

    public static void main(String... args)
    {
        for(int i=0; i<10; i++)
        {
            Thread t = new Thread(new MultiThreadedClient());
            t.start();
        }
    }

    @Override
    public void run()
    {
        System.out.println("Thread " + Thread.currentThread().getId());

        IntegerConverter converter = lookupEJB();
        String hex = converter.toBin(0xffd2);
        System.out.println(hex);
    }
}
