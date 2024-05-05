package com.ags.pms.models;

import java.util.ArrayList;
import java.util.Date;

import com.ags.pms.data.DataContext;

public class PresentationRequest {
    
    private int id;
    private Student student;
    private Date presentationDate;
    private boolean isApproved;

    public PresentationRequest() {
    }

    public PresentationRequest(Student student, Date presentationDate) {
        DataContext context = new DataContext();
        
        this.student = student;
        this.presentationDate = presentationDate;
    }
}