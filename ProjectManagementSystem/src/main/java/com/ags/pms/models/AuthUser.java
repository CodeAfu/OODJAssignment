package com.ags.pms.models;

public interface AuthUser {
    
    public <T extends User> boolean login();

}
