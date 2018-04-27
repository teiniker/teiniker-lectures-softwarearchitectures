package org.se.lab.business;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

final class PasswordEncoder // package private
{
    /*
     * Don't create an instance of this class.
     */
    private PasswordEncoder() {}
    
    
    public static String toHashValue(String s)
    {
        if(s == null)
            throw new IllegalArgumentException();
        try
        {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            byte[] defaultBytes = s.getBytes("UTF-8");
            algorithm.update(defaultBytes);
            byte[] bytes = algorithm.digest();
            return Hex.encodeHexString(bytes);
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            throw new IllegalStateException("Unable to calculate hash value!", e);
        }
    }
}
