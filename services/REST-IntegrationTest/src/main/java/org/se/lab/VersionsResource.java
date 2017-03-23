package org.se.lab;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

@Path("/versions")
public class VersionsResource
	extends AbstractResource
{
	private final Logger LOG = Logger.getLogger(VersionsResource.class);
	private final String XML_FILE_NAME = "/xml/versions.xml";
	
	@GET
	@Produces("application/xml")
	public Response findAll()
	{
		LOG.debug("GET /versions");
		try
		{
			return Response.ok().entity(readXmlFile(XML_FILE_NAME)).build();			
		}
		catch(Exception e)
		{
			LOG.error("Can't handle GET /versions request!", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}		
	}
}
