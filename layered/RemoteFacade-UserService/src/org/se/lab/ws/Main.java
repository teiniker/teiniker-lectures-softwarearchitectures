/*
 * $ wsgen -verbose -cp ./build -d ./build -s ./src-gen at.fhj.endpoint.HelloWorld
 * $ java -cp ./build at.fhj.endpoint.Main
 * 
 * => http://localhost:8030/service/HelloWorld?wsdl
 */

package org.se.lab.ws;

import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;
import org.se.lab.business.ServiceFactory;
import org.se.lab.business.UserService;

public class Main
{
    public static void main(String[] args) 
    {
    	final Logger logger = Logger.getLogger(Main.class);

    	ServiceFactory factory = new ServiceFactory();
    	UserService service = factory.createUserService();
    	
    	
    	logger.debug("Start Web Service");
        
        // The address must be a URL scheme compatible with the endpoint's binding.
        final String address = "http://localhost:8030/v1/UserService";
        
        // An implementor object must be an instance of a class annotated with the
        // @WebService annotation.
        // An implementor should be written so as to support multiple threads because
        // an endpoint will be typically invoked to serve concurrent requests.
        UserServiceRemoteFacade implementor = new UserServiceRemoteFacade(service);
        
        // Create an endpoint with the specified implementor object.
        Endpoint endpoint = Endpoint.create(implementor);
        
        // Publish this endpoint at the given address.
        endpoint.publish(address);

        logger.debug("Web Service is running...");
    }
}