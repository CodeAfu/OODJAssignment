package com.ags.pms.models;

public class Admin extends User {

    public Admin() {

    }

    // Requires overloads for other Student Constructors
    public void registerStudent(int id, String name, String dob, String email) {
        Student student = new Student(id, name, dob, email);
    
        // Save student to the database
    }

    public void registerStudent(int id, String name, String dob, String email, int numOfProjects) {
        Student student = new Student(id, name, dob, email, numOfProjects);
    
        // Save student to the database
    }
    
}
