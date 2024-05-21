package com.ags.pms.models;

import com.ags.pms.data.DataContext;

public class Request implements Identifiable {

    private int id;
    private int lecturerId;
    private int studentId; // For SECONDMARKER and SUPERVISOR
    private RequestType requestType;
    private String module;
    private boolean isApproved;

    public Request(int id, int studentId, String module) {
        this.id = id;
        this.studentId = studentId;
        this.module = module;
    }

    public Request() {
    }

    public Request(int id, int lecturerId, int studentId, RequestType requestType, String module) {
        this.id = id;
        this.lecturerId = lecturerId;
        this.studentId = studentId;
        this.requestType = requestType;
        this.module = module;
    }

    public Request(int id, int lecturerId, int studentId, RequestType requestType, String module, boolean isApproved) {
        this.id = id;
        this.lecturerId = lecturerId;
        this.studentId = studentId;
        this.requestType = requestType;
        this.module = module;
        this.isApproved = isApproved;
    }

    public Request(int id, int lecturerId, int studentId, RequestType requestType, boolean isApproved) {
        this.id = id;
        this.lecturerId = lecturerId;
        this.studentId = studentId;
        this.requestType = requestType;
        this.isApproved = isApproved;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int userId) {
        this.lecturerId = userId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        // if ((this.requestType == RequestType.PRESENTATION || this.requestType == null) && studentId != 0) {
            // throw new IllegalArgumentException("Student can only be assigned for Supervisor or SecondMarker Requests.");
        // }
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
        User user = context.getById(lecturerId);

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
