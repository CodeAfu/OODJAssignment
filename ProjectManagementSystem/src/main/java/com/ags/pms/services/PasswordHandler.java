package com.ags.pms.services;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ags.pms.models.AuthUser;

public class PasswordHandler {

    private String encryptedPassword;
    private AES aes;
    
    public void init() throws NoSuchAlgorithmException {
        aes = new AES();
        aes.init();
    }
    
    public void initFromStrings(String secretKey, String IV) {
        aes = new AES();
        aes.setKey(secretKey);
        aes.setIV(IV);
    }
    
    public String encryptPassword(String password) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return aes.encrypt(password);
    }
    
    public String decryptPassword(String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return aes.decrypt(password);
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
    
    public String[] exportKeys() {
        return aes.exportKeys();
    }
    
}
