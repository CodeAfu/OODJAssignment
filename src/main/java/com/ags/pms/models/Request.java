package com.ags.pms.models;

import com.fasterxml.jackson.databind.Module.SetupContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ags.pms.data.DataContext;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Request implements Identifiable {

    private int id;
    private int requesterId;
    private RequestType requestType;
    private int studentId; // For SECONDMARKER and SUPERVISOR
    private String module;
    private boolean isApproved;
    
    public Request() {
    }
    
    public Request(int userId, RequestType requestType, String module, boolean isApproved) {
        DataContext context = new DataContext();
        this.id = context.fetchNextRequestId();
        this.requesterId = userId;
        this.requestType = requestType;
        this.module = module;
        this.isApproved = isApproved;
    }

    public Request(int userId, RequestType requestType, int studentId, boolean isApproved) {
        DataContext context = new DataContext();
        this.id = context.fetchNextRequestId();
        this.requesterId = userId;
        this.requestType = requestType;
        this.studentId = studentId;
        this.isApproved = isApproved;
    }

    public Request(int id, int userId, RequestType requestType, int studentId, boolean isApproved) {
        this.id = id;
        this.requesterId = userId;
        this.requestType = requestType;
        this.studentId = studentId;
        this.isApproved = isApproved;
    }
    
    public Request(int userId, RequestType requestType, boolean isApproved) {
        DataContext context = new DataContext();
        this.id = context.fetchNextRequestId();
        this.requesterId = userId;
        this.requestType = requestType;
        this.isApproved = isApproved;
    }
    

    public Request(int id, int userId, RequestType requestType, String module, boolean isApproved) {
        this.id = id;
        this.requesterId = userId;
        this.requestType = requestType;
        this.module = module;
        this.isApproved = isApproved;
    }

    public Request(int id, int userId, RequestType requestType, boolean isApproved) {
        this.id = id;
        this.requesterId = userId;
        this.requestType = requestType;
        this.isApproved = isApproved;
    }

    // BAD CONSTRUCTOR
    public Request(int id, int userId, String module) {
        this.id = id;
        this.requesterId = userId;
        this.module = module;
        this.isApproved = false;
    }   

    @Override
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(int userId) {
        this.requesterId = userId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        if ((this.requestType == RequestType.PRESENTATION || this.requestType == null) && studentId != 0) {
            throw new IllegalArgumentException("Student can only be assigned for Supervisor or SecondMarker Requests.");
        }
        this.studentId = studentId;
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

    public User viewUser() {
        DataContext context = new DataContext();
        User user = context.getById(requesterId);

        if (user instanceof Student) {
            return (Student) user;
        } else if (user instanceof Lecturer) {
            return (Lecturer) user;
        } else if (user instanceof ProjectManager) {
            return (ProjectManager) user;
        } else if (user instanceof Admin) {
            return (Admin) user;
        }

        throw new IllegalArgumentException("User Type is not quite right." + user.getClass().getSimpleName());
    }

}
