package com.ags.pms.models;

public class Admin implements AuthUser {
    private String username;
    private String password;

    public Admin() {

    }

    public void assignRoleToLecturer(Lecturer lecturer, Role role) {
        lecturer.setRole(role);
    }
    
    public String getRoleOfLecturer(Lecturer lecturer) {
        return lecturer.getRole().name();
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
    
    @Override
    public void login() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
