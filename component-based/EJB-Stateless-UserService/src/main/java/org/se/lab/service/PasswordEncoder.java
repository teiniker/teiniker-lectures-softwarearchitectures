package org.se.lab.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface PasswordEncoder
{
    byte[] convertToMD5Bytes(String s)
            throws NoSuchAlgorithmException, UnsupportedEncodingException;

    String convertToHexString(byte[] bytes);

    public String convertToMD5String(String s);

}
