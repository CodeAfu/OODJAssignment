package com.ags.pms.services;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ags.pms.models.AuthUser;

public class PasswordEncryptor {

    private AES aes;

    public void initAES() throws NoSuchAlgorithmException {
        aes = new AES();
        aes.init();
    }
    
    public String encryptPassword(String password) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        return aes.encrypt(password);
    }
    
    public String decryptPassword(String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return aes.decrypt(password);
    }

}
