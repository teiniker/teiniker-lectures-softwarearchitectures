package org.se.lab.business;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User
{
    /*
     * Constructors
     */

    public User() {}

    /*
     * Reconstitutes a User from persistent storage where the password
     * is already stored as a hash, bypassing setPassword() hashing.
     */
    public static User restore(int id, String firstname, String lastname,
            String username, String hashedPassword)
    {
        User u = new User();
        u.id = id;
        u.setFirstname(firstname);
        u.setLastname(lastname);
        u.setUsername(username);
        u.password = hashedPassword;
        return u;
    }


    /*
     * Property: id:int
     */
    private int id;
    public final int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }


    /*
     * Property: firstname:String
     */
    private String firstname;
    public final String getFirstname()
    {
        return firstname;
    }
    public final void setFirstname(final String firstname)
    {
        if(firstname == null)
            throw new IllegalArgumentException();
        this.firstname = firstname;
    }


    /*
     * Property: lastname:String
     */
    private String lastname;
    public final String getLastname()
    {
        return lastname;
    }
    public final void setLastname(final String lastname)
    {
        if(lastname == null)
            throw new IllegalArgumentException();
        this.lastname = lastname;
    }


    /*
     * Property: username:String
     */
    private String username;
    public final String getUsername()
    {
        return username;
    }
    public final void setUsername(final String username)
    {
        if(username == null)
            throw new IllegalArgumentException();
        this.username = username;
    }


    /*
     * Property: password:String
     */
    private String password;
    public final String getPassword()
    {
        return password;
    }
    public final void setPassword(final String plainPassword)
    {
        if (plainPassword == null)
            throw new IllegalArgumentException();
        this.password = toHashValue(plainPassword);
    }

    private static String toHashValue(String s)
    {
        try
        {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            byte[] bytes = algorithm.digest(s.getBytes("UTF-8"));
            return Hex.encodeHexString(bytes);
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            throw new IllegalStateException("Unable to calculate hash value!", e);
        }
    }


    /*
     * Object methods
     */

    @Override
    public String toString()
    {
        return getId() + "," + getFirstname() + "," + getLastname()
            + "," + getUsername();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
