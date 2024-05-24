package com.ags.pms.models;

import com.ags.pms.data.DataContext;

public class Project implements Identifiable {

    private int id;
    private String module;
    private AssessmentType assessmentType;
    private String details;
    private int totalMark;

    public Project() {
    }

    public Project(String module, AssessmentType assessmentType, String details) {
        DataContext context = new DataContext();
        this.id = context.fetchNextProjectId();
        this.module = module;
        this.assessmentType = assessmentType;
        this.details = details;
    }

    public Project(int id, String module, AssessmentType assessmentType, String details, int totalMark) {
        this.id = id;
        this.module = module;
        this.assessmentType = assessmentType;
        this.details = details;
        this.totalMark = totalMark;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getModule() {
        return module;
    }
    public void setModule(String module) {
        this.module = module;
    }
    public int getTotalMark() {
        return totalMark;
    }
    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }
    public AssessmentType getAssessmentType() {
        return assessmentType;
    }
    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }
}
