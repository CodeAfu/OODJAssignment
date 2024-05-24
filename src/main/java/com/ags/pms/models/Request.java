package com.ags.pms.models;

import com.ags.pms.data.DataContext;

public class Request implements Identifiable {

    private int id;
    private int lecturerId;
    private int studentId; // For SECONDMARKER and SUPERVISOR
    private int presentationSlotId;
    private RequestType requestType;
    private String module;
    private boolean isApproved;
    private boolean isCompletedRequest;

    private Student student;
    private Lecturer lecturer;
    private PresentationSlot presentationSlot;

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

    public Request(int id, int lecturerId, int studentId, int presentationSlotId, RequestType requestType, String module) {
        this.id = id;
        this.lecturerId = lecturerId;
        this.studentId = studentId;
        this.presentationSlotId = presentationSlotId;
        this.requestType = requestType;
        this.module = module;
    }
    
    public Request(int id, int lecturerId, int studentId, RequestType requestType) {
        this.id = id;
        this.lecturerId = lecturerId;
        this.studentId = studentId;
        this.requestType = requestType;
    }

    public void fetchForeignKeyVariables() {
        DataContext context = new DataContext();
        if (lecturerId != 0) {
            lecturer = context.getById(lecturerId);
        }
        if (studentId != 0) {
            student = context.getById(studentId);
        }
        if (presentationSlotId != 0) {
            presentationSlot = context.getById(presentationSlotId);
        }
    }

    public Lecturer fetchLecturer() {
        DataContext context = new DataContext();
        Lecturer lecturer = context.getById(lecturerId);
        return lecturer;
    }

    public Student fetchStudent() {
        DataContext context = new DataContext();
        Student student = context.getById(studentId);
        return student;
    }
    public PresentationSlot fetchPresentationSlot() {
        DataContext context = new DataContext();
        PresentationSlot presentationSlot = context.getById(presentationSlotId);
        return presentationSlot;
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

    public int getPresentationSlotId() {
        return presentationSlotId;
    }

    public void setPresentationSlotId(int presentationSlotId) {
        this.presentationSlotId = presentationSlotId;
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
    
    public boolean isCompletedRequest() {
        return isCompletedRequest;
    }

    public void setCompletedRequest(boolean isCompletedRequest) {
        this.isCompletedRequest = isCompletedRequest;
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

    @Override
    public String toString() {
        fetchForeignKeyVariables();
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id);
        
        // Check if student is not null
        if (student != null) {
            sb.append(", Student: ").append(fetchStudent().getName());
        } else {
            sb.append(", Student: null");
        }
        
        // Check if lecturer is not null
        if (lecturer != null) {
            sb.append(", Lecturer: ").append(fetchLecturer().getName());
        } else {
            sb.append(", Lecturer: null");
        }
    
        // Check if presentationSlot is not null
        if (presentationSlot != null) {
            sb.append(", PSlot: ").append(fetchPresentationSlot().getPresentationDate());
        } else {
            sb.append(", PSlot: null");
        }
        
        return sb.toString();
    }
}
