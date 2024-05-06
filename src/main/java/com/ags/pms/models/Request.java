package com.ags.pms.models;

import com.fasterxml.jackson.databind.Module.SetupContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Request implements Identifiable {

    private int id;
    private User user;
    private RequestType requestType;
    private String module;
    private boolean isApproved;

    public Request() {
    }

    public Request(int id, User user, String module) {
        if (user instanceof Student) {
            this.requestType = RequestType.PRESENTATION;
            this.user = (Student) user;
        } else if (user instanceof ProjectManager) {
            this.requestType = RequestType.SECONDMARKER;
            this.user = (ProjectManager) user;
            this.module = module;
        } else if (user instanceof Lecturer) {
            this.requestType = RequestType.SECONDMARKER;
            this.user = (Lecturer) user;
        }

        this.id = id;
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
