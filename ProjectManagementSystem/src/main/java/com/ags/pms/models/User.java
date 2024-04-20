package com.ags.pms.models;

public abstract class User implements AuthUser {
    
    protected int id;
    protected String name;
    protected String dob;
    protected String email;
    protected String username;
    protected String password;

    public User() {
    }
    
    public User(int id, String name, String dob, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void initializeUser(int id, String name, String dob, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
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

    public void setAccount(String username, String Password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void login(String username, String password) {
        
    }

}
