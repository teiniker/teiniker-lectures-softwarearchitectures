package org.se.lab;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/v1/users")
public interface UserResource
{
	@POST
	@Consumes("application/xml")
	@Produces("application/xml")
	public Response insert(UserDTO user);

	
	@PUT
	@Path("{id}")
	@Consumes("application/xml")
	public void update(@PathParam("id") int id, UserDTO user);


	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") int id);
	
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<UserDTO> findAll(@QueryParam("index") int index, @QueryParam("size") int size);
	
	
	@GET
	@Path("{id}")
	@Produces({"application/xml", "application/json"})
	public UserDTO findById(@PathParam("id") int id);
}
