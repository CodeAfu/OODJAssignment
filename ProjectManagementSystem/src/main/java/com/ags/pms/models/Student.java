package com.ags.pms.models;

import java.util.Date;

public class Student extends User {
    private Project[] projects; 

    public Student() {
        super();
        projects = new Project[10];
    }

    public Student(int numOfProjects) {
        super();
        projects = new Project[numOfProjects];
    }
    
    public Student(int id, String name, String dob, String email, int numOfProjects) {
        super(id, name, dob, email);
        projects = new Project[numOfProjects];
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
