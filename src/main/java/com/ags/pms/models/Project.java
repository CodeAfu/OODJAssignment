package com.ags.pms.models;

public class Project implements Identifiable {

    private int id;
    private String module;
    private String details;

    public Project() {
    }

    public Project(int id, String module, String details) {
        this.id = id;
        this.module = module;
        this.details = details;    
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
}
