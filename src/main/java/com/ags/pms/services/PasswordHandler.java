package com.ags.pms.services;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ags.pms.ConfigLoader;

public class PasswordHandler {

    private AES aes = new AES();
    private ConfigLoader configLoader = new ConfigLoader();

    public PasswordHandler() {
    }

    public PasswordHandler(String secretKey, String IV) {
        initFromStrings(secretKey, IV);
    }

    public void init() throws NoSuchAlgorithmException {
        aes.init();
    }
    
    public void initFromStrings(String secretKey, String IV) {
        aes.setKey(secretKey);
        aes.setIV(IV);
    }

    public void initFromConfigLoader() throws IOException {
        configLoader.init();
        aes.setKey(configLoader.getSecretKey());
        aes.setIV(configLoader.getIV());
    }
    
    public String encryptPassword(String password) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return aes.encrypt(password);
    }
    
    public String decryptPassword(String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return aes.decrypt(password);
    }
    
    public String[] exportKeys() {
        return aes.exportKeys();
    }

    public boolean isSetup() {
        return aes.isSetup();
    }
    
}
