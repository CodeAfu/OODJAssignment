package com.ags.pms.models;

import java.util.Date;

import com.ags.pms.Helper;
import com.ags.pms.data.DataContext;
import com.ags.pms.data.IDHandler;

public class Report implements Identifiable {

    private int id;
    private int projectId;
    private int studentId;
    private boolean submitted;
    private Date dateSubmitted;
    private String moodleLink;
    private int studentMark;
    private int totalMark;
    private String feedback;

    public Report() {
    }

    public Report(int projectId, boolean submitted, Date dateSubmitted, String moodleLink,
            int studentMark, int totalMark) {
        DataContext context = new DataContext();
        this.id = context.fetchNextReportId();
        this.projectId = projectId;
        this.submitted = submitted;
        this.dateSubmitted = dateSubmitted;
        this.moodleLink = moodleLink;
        this.studentMark = studentMark;
        this.totalMark = totalMark;
    }

    public Report(int id, int projectId, boolean submitted, Date dateSubmitted, String moodleLink,
            int studentMark, int totalMark) {
        this.id = id;
        this.projectId = projectId;
        this.submitted = submitted;
        this.dateSubmitted = dateSubmitted;
        this.moodleLink = moodleLink;
        this.studentMark = studentMark;
        this.totalMark = totalMark;
    }


    public Report(int id, int projectId, String moodleLink, int studentMark, int totalMark) {
        this.id = id;
        this.projectId = projectId;
        this.moodleLink = moodleLink;
        this.studentMark = studentMark;
        this.totalMark = totalMark;
    }

    public Report(int id, int projectId, int studentMark, int totalMark) {
        this.id = id;
        this.projectId = projectId;
        this.moodleLink = "https://sample.moodle.com/test-report";
        this.studentMark = studentMark;
        this.totalMark = totalMark;
    }

    public Report(int id, int projectId, String moodleLink, int totalMark) {
        this.id = id;
        this.projectId = projectId;
        this.moodleLink = moodleLink;
        this.totalMark = totalMark;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getProjectId() {
        return projectId;
    }
    public void setProjectId(int project) {
        this.projectId = project;
    }
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
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
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String retrieveReportDetails() {
        String output = "";
        return output;
    }

    private void updateDateSubmitted() {
        if (!isSubmitted()) return; 
        dateSubmitted = new Date();
        // System.out.println(this.getId() + " Report submitted: " + Helper.getDateFormat().format(dateSubmitted));
    }
}
