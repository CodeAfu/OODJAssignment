package com.ags.pms.models;

public class Admin extends User {

    public Admin() {

    }

    // Requires overloads for other Student Constructors
    public void registerStudent(int id, String name, String dob, String email, String username, String password) {
        Student student = new Student(id, name, dob, email, username, password);
    
        // Save student to the database
    }

    public void registerStudent(Student student) {
        
    }
    
}
