package com.ags.pms.models;


// ?????????
public interface AuthUser {
    
    public void login();

    public String getUsername();
    public void setUsername(String username);
    public String getPassword(); // Encrypt
    public void setPassword(String password);


}
