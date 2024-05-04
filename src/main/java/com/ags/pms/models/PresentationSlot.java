package com.ags.pms.models;

import java.util.Date;

import com.ags.pms.data.DataContext;

public class PresentationSlot {

    private int id;
    private Student student;
    private Date presentationDate;

    public PresentationSlot() {
    }

    public PresentationSlot(Student student, Date presentationDate) {
        DataContext context = new DataContext();
        
        this.student = student;
        this.presentationDate = presentationDate;
    }

    public PresentationSlot getPresentationSlot() {
        return this;
    }
}
