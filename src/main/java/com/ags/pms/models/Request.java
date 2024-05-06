package com.ags.pms.models;

public class Request implements Identifiable {

    private int id;
    private User user;
    private RequestType requestType;
    private String module;
    private boolean isApproved;

    public Request() {
    }

    public Request(int id, User user, RequestType requestType, String module) {
        this.id = id;
        this.user = user;
        this.requestType = requestType;
        this.module = module;
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
    
    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

}
