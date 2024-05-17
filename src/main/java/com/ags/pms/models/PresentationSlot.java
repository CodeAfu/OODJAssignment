package com.ags.pms.models;

import java.util.Date;

import com.ags.pms.data.DataContext;

// CREATED BY PROJECTMANAGER - Free Slots for students to apply
public class PresentationSlot implements Identifiable {

    private int id;
    private int studentId;
    private String module;
    private Date presentationDate;
    private boolean isAvailable;

    public PresentationSlot() {
        this.isAvailable = true;
    }

    public PresentationSlot(int id, int studentId, Date presentationDate, boolean isAvailable) {
        this.id = id;
        this.studentId = studentId;
        this.presentationDate = presentationDate;
        this.isAvailable = isAvailable;
    }

    public PresentationSlot(int id) {
        this.isAvailable = true;
        this.id = id;
    }

    public PresentationSlot(int studentId, Date presentationDate, String module) {
        DataContext context = new DataContext();
        this.id = context.fetchNextPresentationSlotId();
        this.isAvailable = false;
        this.module = module;
        this.studentId = studentId;
        this.presentationDate = presentationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Date getPresentationDate() {
        return presentationDate;
    }

    public void setPresentationDate(Date presentationDate) {
        this.presentationDate = presentationDate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }


}
