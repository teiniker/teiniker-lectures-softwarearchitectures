package org.se.lab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

public class UserServiceConnectorImpl implements UserServiceConnector
{
    private final Logger LOG = Logger.getLogger(UserServiceConnectorImpl.class);
    private static final String WEB_APP_NAME = "/REST-EJB-UserService/v1";

    protected final Proxy PROXY;
    protected final String HOST;
    protected final String PORT;

    public UserServiceConnectorImpl()
    {
        LOG.info("UserServiceConnectorImpl()");

        Properties properties = new Properties();
        try
        {
            properties.load(this.getClass().getResourceAsStream("/rest.properties"));
            HOST = properties.getProperty("rest.host");
            PORT = properties.getProperty("rest.port");
            LOG.info("Connect to " + HOST + ":" + PORT);

            String proxyAddress = properties.getProperty("proxy.address");
            String proxyPort = properties.getProperty("proxy.port");
            if (proxyAddress != null && proxyPort != null)
            {
                System.out.println("Use proxy " + proxyAddress + ":" + proxyPort);
                int port = Integer.parseInt(proxyPort);
                PROXY = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyAddress, port));
            }
            else
            {
                PROXY = Proxy.NO_PROXY;
            }
        }
        catch (IOException e)
        {
            throw new IllegalStateException("Can't setup remote connection!", e);
        }
    }


    @Override
    public void insert(User user)
    {
        LOG.info("insert(" + user + ")");

        try
        {
            URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setRequestProperty("Accept", "application/xml");
            OutputStreamWriter w = new OutputStreamWriter(connection.getOutputStream());
            w.write(convertUser2Xml(user));
            w.flush();
            w.close();

            // Response
            int httpResponseCode = connection.getResponseCode();
            if (httpResponseCode != 200 && httpResponseCode != 201)
                throw new ServiceException("Insert error: " + httpResponseCode);
        }
        catch (IOException e)
        {
            throw new ServiceException("Can't forward request to the service!", e);
        }
    }


    @Override
    public void update(User user)
    {
        LOG.info("update(" + user + ")");

        try
        {
            // Request
            URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users/1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setRequestProperty("Accept", "application/xml");

            OutputStreamWriter w = new OutputStreamWriter(connection.getOutputStream());
            w.write(convertUser2Xml(user));
            w.flush();
            w.close();

            // Response
            int httpResponseCode = connection.getResponseCode();
            if (httpResponseCode != 200 && httpResponseCode != 204)
                throw new ServiceException("Update error: " + httpResponseCode);
        }
        catch (IOException e)
        {
            throw new ServiceException("Can't forward request to the service!", e);
        }
    }


    @Override
    public void delete(int id)
    {
        LOG.info("delete(" + id + ")");
        try
        {
            // Request
            URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
            connection.setRequestMethod("DELETE");

            // Response
            int httpResponseCode = connection.getResponseCode();
            if (httpResponseCode != 200 && httpResponseCode != 204)
                throw new ServiceException("Delete error: " + httpResponseCode);
        }
        catch (IOException e)
        {
            throw new ServiceException("Can't forward request to the service!", e);
        }
    }


    @Override
    public User findById(int id)
    {
        LOG.info("findById(" + id + ")");

        try
        {
            // Request
            URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");

            // Response
            int httpResponseCode = connection.getResponseCode();
            if (httpResponseCode != 200)
                return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer content = new StringBuffer();
            while ((line = reader.readLine()) != null)
            {
                content.append(line).append("\n");
            }
            String xml = content.toString();
            User user = convertXml2User(xml);
            return user;
        }
        catch (IOException e)
        {
            throw new ServiceException("Can't forward request to the service!", e);
        }
    }


    @Override
    public List<User> findAll()
    {
        try
        {
            // Request
            URL url = new URL("http://" + HOST + ":" + PORT + WEB_APP_NAME + "/users");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(PROXY);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");

            // Response
            int httpResponseCode = connection.getResponseCode();
            if (httpResponseCode != 200)
                return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer content = new StringBuffer();
            while ((line = reader.readLine()) != null)
            {
                content.append(line).append("\n");
            }
            String xml = content.toString();

            // TODO: unmarshalling List<User>
            return Collections.emptyList();
        }
        catch (IOException e)
        {
            throw new ServiceException("Can't forward request to the service!", e);
        }
    }

    
    /*
     * JAXB Serialization
     */
    protected User convertXml2User(String xml)
    {
        try
        {
            Source src = new StreamSource(new StringReader(xml));
            JAXBContext context;
            context = JAXBContext.newInstance(User.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            JAXBElement<User> element = unmarshaller.unmarshal(src, User.class);
            User user = element.getValue();
            return user;
        }
        catch (JAXBException e)
        {
            throw new ServiceException("Can't convert XML to User object!", e);
        }
    }

    protected String convertUser2Xml(User user)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(User.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Writer out = new StringWriter();
            marshaller.marshal(user, out);
            out.close();
            String xml = out.toString();
            return xml;
        }
        catch (JAXBException | IOException e)
        {
            throw new ServiceException("Can't convert User object to XML!", e);
        }
    }
}
