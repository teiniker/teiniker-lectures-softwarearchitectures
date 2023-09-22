package org.se.lab;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable
{
    private static final long serialVersionUID = 1L;

    User(long id, String username, String password)
    {
        setId(id);
        setUsername(username);
        setPassword(password);
    }

    User()
    {
    }


    private long id;
    public long getId()
    {
        return this.id;
    }
    public void setId(long id)
    {
        this.id = id;
    }

    private String username;
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }

    private String password;
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    /*
     * Object methods
     */

    @java.lang.Override
    public java.lang.String toString()
    {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    public boolean equals(Object object)
    {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        User user = (User) object;
        if (id != user.id) return false;
        return true;
    }

    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }
}