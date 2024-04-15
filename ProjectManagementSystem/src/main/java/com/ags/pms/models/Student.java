package com.ags.pms.models;

import java.util.Date;

public class Student {
    private int id;
    private String name;
    private String dob;
    private Project[] projects; 

    public Student() {
        projects = new Project[10];
    }

    public Student(int numOfProjects) {
        projects = new Project[numOfProjects];
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Project[] getProjects() {
        return projects;
    }

    public void setProjects(Project[] projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        projects[projects.length - 1] = project;
    }

    public void submitProject() {
        
    }

    public void submitProject(Project project) {
        
    }
}
