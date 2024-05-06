package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.util.ArrayList;
import java.util.HashMap;

import com.ags.pms.Helper;
import com.ags.pms.io.JsonHandler;
import com.ags.pms.services.PasswordHandler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)

public class User implements Identifiable {
    
    protected int id;
    protected String name;
    protected String dob;
    protected String email;
    protected String username;
    protected String password;
    
    private PasswordHandler pwHandler = new PasswordHandler("9Vs+DfEF1+3tF8fCKLp9BQ==", "JoprQnQRq95s/Nuz");
    
    public User() {
    }

    public User(String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        this.username = username;
        this.password = password;
    }

    public User(int id, String name, String dob, String email, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void initializeUser(int id, String name, String dob, String email, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    void setUsername(String username) {
        this.username = username;
    }

    void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }

    public void encryptPassword() {
         try {
            this.password = pwHandler.encryptPassword(this.password);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException | InvalidAlgorithmParameterException e) {
            Helper.printErr(Helper.getStackTraceString(e));
        }
    }
    
    public void decryptPassword() {
        try {
            this.password = pwHandler.decryptPassword(this.password);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException | InvalidAlgorithmParameterException e) {
            Helper.printErr(Helper.getStackTraceString(e));                
        }
    }

    public <T extends User> boolean login() {
        JsonHandler handler = new JsonHandler();

        @SuppressWarnings("unchecked")
        Class<T> userClass = (Class<T>)getClass();
        String classSimpleName = userClass.getSimpleName();
        ArrayList<T> dataArrayList = handler.readJson(classSimpleName);

        HashMap<String, String> users = new HashMap<>();

        for (User obj : dataArrayList) {
            try {
                users.put(obj.getUsername(), pwHandler.decryptPassword(obj.getPassword()));
            } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                    | BadPaddingException | InvalidAlgorithmParameterException ex) {
                Helper.printErr(Helper.getStackTraceString(ex));
            }
        }

        // PRINT ALL VALUES OF HASHSET
        // for (String name : users.keySet()) {
            // String key = name.toString();
            // String value = users.get(name).toString();
            // System.out.println(key + " " + value);
        // }

        if (validateUser(users)) return true;

        return false;
    }

    private boolean validateUser(HashMap<String, String> users) {
        if (users != null) {
            if (users.containsKey(this.username)) {
                if (users.get(this.username).equals(this.password)) {
                    return true;
                }
            }
        }
        return false;
    }

}
