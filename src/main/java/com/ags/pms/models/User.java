package com.ags.pms.models;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ags.pms.Helper;
import com.ags.pms.data.DataContext;
import com.ags.pms.services.PasswordHandler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class User implements Identifiable {
    
    protected int id;
    protected String name;
    protected String dob;
    protected String email;
    protected String username;
    protected String password;

    private PasswordHandler pwHandler = new PasswordHandler("9Vs+DfEF1+3tF8fCKLp9BQ==", "JoprQnQRq95s/Nuz");
    // private PasswordHandler pwHandler = new PasswordHandler();
    
    public User() {
    }

    public User(String name, String dob, String email, String username, String password) {
        DataContext context = new DataContext();
        if (this instanceof Student) {
            this.id = context.fetchNextStudentId();
        } else if (this instanceof Admin) {
            this.id = context.fetchNextAdminId();
        } else if (this instanceof Lecturer || this instanceof ProjectManager) {
            this.id = context.fetchNextLecturerId();
        }
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.username = username;
        this.password = password;
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
            if (!pwHandler.isSetup()) {
                pwHandler.initFromConfigLoader();                
            }
            this.password = pwHandler.encryptPassword(this.password);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException 
                | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | IOException e) {
                    Helper.printErr(Helper.getStackTraceString(e));
                }
            }
    
    public void decryptPassword() {
        try {
            if (!pwHandler.isSetup()) {
                pwHandler.initFromConfigLoader();                
            }
            this.password = pwHandler.decryptPassword(this.password);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException 
                | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | IOException e) {
            Helper.printErr(Helper.getStackTraceString(e));                
        }
    }
}
