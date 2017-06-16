package org.se.lab.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

import org.apache.log4j.Logger;
import org.se.lab.client.UserDTO;
import org.se.lab.client.UserResourceEJBService;
import org.se.lab.client.UserService;

@Path("/v1/articles")
@Stateless
public class GatewayResourceEJB
{
	private final Logger LOG = Logger.getLogger(GatewayResourceEJB.class);
	
	private UserService service;
			
	public GatewayResourceEJB()
	{
		LOG.debug(GatewayResourceEJB.class.getName() + " created");
	
		UserResourceEJBService ws = new UserResourceEJBService();
		service = ws.getUserServicePort();	
	}
	
	
	@GET
	@Produces("application/json")
	public List<ArticleDTO> findAll()
	{
		LOG.debug("findAll()");
		List<ArticleDTO> response = null;
		try
		{
			Client client = ClientBuilder.newClient();
			response = client	// don't hardcode URLs !!!
						.target("http://localhost:8080").path("REST-EJB-ArticleService/v1/articles")
						.request().accept("application/xml").get(new GenericType<List<ArticleDTO>>() {});
		
			LOG.debug(response);
		
			// How to access the SOAP Service
			List<UserDTO> users = service.findAll(0, 0); 
			for(UserDTO user : users)
			{
				LOG.debug(user.getId() + ":" + user.getUsername());
			}
			client.close();
		}
		catch(Exception e)
		{
			LOG.error("Can't read articles!", e);
		}
		return response;
	}
	
	
	@GET
	@Path("{id}")
	@Produces("application/json")
	public ArticleDTO findById(@PathParam("id") int id) 
	{
		LOG.debug("findById(" + id + ")");
		ArticleDTO response = null;
		try
		{
			Client client = ClientBuilder.newClient();
			response = client
						.target("http://localhost:8080").path("REST-EJB-ArticleService/v1/articles/" + id)
						.request().accept("application/xml").get(ArticleDTO.class);
		
			LOG.debug(response);
			client.close();
		}
		catch(Exception e)
		{
			LOG.error("Can't read articles!", e);
		}
		
		return response;
	}
}
