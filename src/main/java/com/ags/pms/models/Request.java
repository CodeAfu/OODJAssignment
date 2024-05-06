package com.ags.pms.models;

public abstract class Request implements Identifiable {

    private int id;
    private User user;
    
    @Override
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
