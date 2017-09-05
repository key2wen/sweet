
package com.wenwen.sweet.util;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.NamingException;

import org.springframework.beans.factory.FactoryBean;

public final class EncryptDBPasswordFactory implements FactoryBean<String> {
    private String password;

    public EncryptDBPasswordFactory() {
    }

    private String encode(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] kbytes = "strong".getBytes();
        SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(1, key);
        byte[] encoding = cipher.doFinal(secret.getBytes());
        BigInteger n = new BigInteger(encoding);
        return n.toString(16);
    }

    private char[] decode(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] kbytes = "strong".getBytes();
        SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");
        BigInteger n = new BigInteger(secret, 16);
        byte[] encoding = n.toByteArray();
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(2, key);
        byte[] decode = cipher.doFinal(encoding);
        return (new String(decode)).toCharArray();
    }

    public String getObject() throws Exception {
        return this.password != null ? String.valueOf(this.decode(this.password)) : null;
    }

    public Class<String> getObjectType() {
        return String.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void main(String[] args) throws NamingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        EncryptDBPasswordFactory encrypt = new EncryptDBPasswordFactory();
        String secret = "Hm654Wenwen123";
        String secText = encrypt.encode(secret);
        System.out.println(secText);
        System.out.println(encrypt.decode("-aa252cb6b0cfeb7ef903ae8377c1f37"));
    }
}
