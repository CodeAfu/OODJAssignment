package com.ags.pms.models;

import java.util.Date;

import com.ags.pms.data.DataContext;

public class PresentationSlot {

    private int id;
    private Student student;
    private Date presentationDate;
    private boolean isApproved;

    public PresentationSlot() {
    }

    public PresentationSlot(Student student, Date presentationDate) {
        DataContext context = new DataContext();
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
