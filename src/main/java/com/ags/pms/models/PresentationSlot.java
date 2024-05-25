package com.ags.pms.models;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.text.DateFormat;

import com.ags.pms.Helper;
import com.ags.pms.data.DataContext;

// CREATED BY PROJECTMANAGER - Free Slots for students to apply
public class PresentationSlot implements Identifiable {

    private int id;
    private int studentId;
    private int lecturerId;
    private String module;
    private Date presentationDate;
    private boolean isAvailable;
    
    public PresentationSlot() {
        this.isAvailable = true;
    }
    
    public PresentationSlot(int id, int lecturerId, LocalDateTime presentationDate) {
        this.isAvailable = true;
        this.id = id;
        this.lecturerId = lecturerId;
        this.presentationDate = Helper.convertToDate(presentationDate);
    }
    
    public PresentationSlot(int id, int studentId, LocalDateTime presentationDate, boolean isAvailable) {
        this.id = id;
        this.studentId = studentId;
        this.presentationDate = Helper.convertToDate(presentationDate);
        this.isAvailable = isAvailable;
    }

    public PresentationSlot(int studentId, LocalDateTime presentationDate, String module) {
        DataContext context = new DataContext();
        this.id = context.fetchNextPresentationSlotId();
        this.isAvailable = false;
        this.studentId = studentId;
        this.presentationDate = Helper.convertToDate(presentationDate);
        this.module = module;
    }

    public Student fetchStudent() {
        DataContext context = new DataContext();
        return context.getById(studentId);
    }

    public Lecturer fetchLecturer() {
        DataContext context = new DataContext();
        return context.getById(lecturerId);
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

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
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

    @Override
    public String toString() {
        DateFormat formatter = Helper.getDateFormat();
        return id + ": " + formatter.format(presentationDate);
    }
}
