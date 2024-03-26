package com.ags.models;

public class ProjectManager {
    private int id;



    public void createProject() {

    }
    
    public void assignProject() {

    }

    public void assignLecturerRole(Lecturer lecturer, Role role) {
        lecturer.setRole(role);
    }
}
