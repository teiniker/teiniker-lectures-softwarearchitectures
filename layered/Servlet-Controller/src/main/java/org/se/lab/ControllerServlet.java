package org.se.lab;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet
{
	private final Logger LOG = LoggerFactory.getLogger(ControllerServlet.class);

	private static final long serialVersionUID = 1L;

	public ControllerServlet()
	{
		super();
	}
	
	// Don't do this in real Web applications!!
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		doPost(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException
	{
		LOG.debug("GET " + request.getRemoteHost());
		
		// Handle request
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String usergroup = request.getParameter("usergroup");
		String action = request.getParameter("action");

		// Create content
		String content = "Request Parameters: " + action + "," + username + "," + password 	+ "," + usergroup;
		LOG.debug(content);

		// Generate response
		response.setContentType("text/plain");
		response.setBufferSize(1024);
		PrintWriter out = response.getWriter();
		out.println(content);
		out.close();
	}
}
