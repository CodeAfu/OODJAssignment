package com.ags.pms.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ags.pms.Helper;

public class Report {

    private int id;
    private Student student;
    private String module;
    private AssessmentType assessmentType;
    private boolean submitted;
    private Date dateSubmitted;
    private String moodleLink;
    private int studentMark;
    private int totalMark;

    public Report() {
        updateDateSubmitted();
    }

    public Report(int id, Student student, String module, AssessmentType assessmentType,
            String moodleLink, int studentMark, int totalMark) {
        this.id = id;
        this.student = student;
        this.module = module;
        this.assessmentType = assessmentType;
        this.moodleLink = moodleLink;
        this.studentMark = studentMark;
        this.totalMark = totalMark;
        updateDateSubmitted();
    }

    private void updateDateSubmitted() {
        if (!isSubmitted()) return; 
        dateSubmitted = new Date();
        System.out.println("Report submitted: " + Helper.getDateFormat().format(dateSubmitted));
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
    public AssessmentType getAssessmentType() {
        return assessmentType;
    }
    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }
    public boolean isSubmitted() {
        return submitted;
    }
    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
        updateDateSubmitted();
    }
    public Date getDateSubmitted() {
        return dateSubmitted;
    }
    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }
    public String getMoodleLink() {
        return moodleLink;
    }
    public void setMoodleLink(String moodleLink) {
        this.moodleLink = moodleLink;
    }
    public int getStudentMark() {
        return studentMark;
    }
    public void setStudentMark(int studentMark) {
        this.studentMark = studentMark;
    }
    public int getTotalMark() {
        return totalMark;
    }
    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }
}
