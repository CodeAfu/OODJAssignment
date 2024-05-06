package com.ags.pms.models;

public abstract class Request implements Identifiable {

    private int id;
    private User user;
    private RequestType requestType;
    private String module;
    private boolean isApproved;

    public Request() {
    }

    public Request(int id, User user) {
        this.id = id;
        this.user = user;
    }   
    
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
