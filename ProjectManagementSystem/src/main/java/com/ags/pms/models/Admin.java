package com.ags.pms.models;

public class Admin implements AuthUser {

    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

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
    
    @Override
    public void login() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    // Needs change
    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
