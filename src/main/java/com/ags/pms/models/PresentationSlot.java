package com.ags.pms.models;

import java.util.Date;

import com.ags.pms.data.DataContext;

// CREATED BY PROJECTMANAGER - Free Slots for students to apply
public class PresentationSlot implements Identifiable {

    private int id;
    private Student student;
    private String module;
    private Date presentationDate;
    private boolean isApproved;

    public PresentationSlot() {
    }

    public PresentationSlot(Student student, Date presentationDate) {
        this.student = student;
        this.presentationDate = presentationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }


}
