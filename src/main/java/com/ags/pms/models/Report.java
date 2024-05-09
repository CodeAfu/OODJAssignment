package com.ags.pms.models;

import java.util.Date;

import com.ags.pms.Helper;
import com.ags.pms.data.DataContext;
import com.ags.pms.data.IDHandler;

public class Report implements Identifiable {

    private int id;
    private Project project;
    private boolean submitted;
    private Date dateSubmitted;
    private String moodleLink;
    private int studentMark;
    private int totalMark;


    public Report() {
    }

    public Report(Project project, boolean submitted, Date dateSubmitted, String moodleLink,
            int studentMark, int totalMark) {
        DataContext context = new DataContext();
        this.id = context.fetchNextReportId();
        this.project = project;
        this.submitted = submitted;
        this.dateSubmitted = dateSubmitted;
        this.moodleLink = moodleLink;
        this.studentMark = studentMark;
        this.totalMark = totalMark;
    }

    public Report(int id, Project project, boolean submitted, Date dateSubmitted, String moodleLink,
            int studentMark, int totalMark) {
        this.id = id;
        this.project = project;
        this.submitted = submitted;
        this.dateSubmitted = dateSubmitted;
        this.moodleLink = moodleLink;
        this.studentMark = studentMark;
        this.totalMark = totalMark;
    }


    public Report(int id, Project project, String moodleLink, int studentMark, int totalMark) {
        this.id = id;
        this.project = project;
        this.moodleLink = moodleLink;
        this.studentMark = studentMark;
        this.totalMark = totalMark;
    }

    public Report(int id, Project project,
            int studentMark, int totalMark) {
        this.id = id;
        this.project = project;
        this.moodleLink = "https://sample.moodle.com/test-report";
        this.studentMark = studentMark;
        this.totalMark = totalMark;
    }

    public Report(int id, Project project,
            String moodleLink, int totalMark) {
        this.id = id;
        this.project = project;
        this.moodleLink = moodleLink;
        this.totalMark = totalMark;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
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
