package com.ags.pms.services;

import com.ags.pms.Helper;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;

public class AES {

    private SecretKey key;
    private int keySize = 128;
    private int T_LEN = 128;
    private byte[] IV;

    public void init() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(keySize);
        key = generator.generateKey();
    }

    public void initFromString(String secretKey, String IV) {
        key = new SecretKeySpec(decode(secretKey), "AES");
        this.IV = decode(IV);
    }

    // public String encryptFirst(String message) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    //     byte[] messageInBytes = message.getBytes();
    //     Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
    //     encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
    //     IV = encryptionCipher.getIV();
    //     byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
    //     return encode(encryptedBytes);
    // }

    public String encrypt(String message) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] messageInBytes = message.getBytes();
        Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");

        if (this.IV == null) {
            this.IV = encryptionCipher.getIV();
            encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        } else {
            GCMParameterSpec spec = new GCMParameterSpec(T_LEN, IV);
            encryptionCipher.init(Cipher.ENCRYPT_MODE, key, spec);
        }

        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
    }

    public String decrypt(String encryptedMessage) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] messageInBytes = decode(encryptedMessage);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, IV);
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
        return new String(decryptedBytes);
    }

    public String exportKeys() {
        String secretKey = encode(key .getEncoded());
        String IV = encode(this.IV);

        return new String(
            "Secret Key: " + secretKey
            + "\nIV: " + IV
        );
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public void setKey(String secretKey, String algoString) {
        this.key = new SecretKeySpec(decode(secretKey), algoString);
    }

    public void setIV(String IV) {
        this.IV = decode(IV);
    }
}
