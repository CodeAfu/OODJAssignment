package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ags.pms.data.DataContext;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

public class ProjectManager extends Lecturer {

    private Role role;
    private ArrayList<Student> supervisees = new ArrayList<>();

    public ProjectManager() {
        super();
    }

    public ProjectManager(int id, String name, String dob, String email, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
    }
    
    public ProjectManager(int id, String name, String dob, String email, Role role, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        this.isProjectManager = true;
        this.role = role;
    }

    public ProjectManager(String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(username, password);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ArrayList<Student> getSupervisees() {
        return supervisees;
    }

    public void setSupervisees(ArrayList<Student> supervisees) {
        this.supervisees = supervisees;
    }

    public void addSupervisee(Student student) {
        supervisees.add(student);
    }
    
    public void createProject(String module, String details) {
        DataContext context = new DataContext();
        Project project = new Project(context.fetchNextProjectId(), module, details);
        context.addProject(project);
        context.writeAllDataAsync();
    }
    
    public void assignProject(int studentId, Project project) {
        DataContext context = new DataContext();
        ArrayList<Student> students = context.getStudents();

        students.forEach(student -> {
            if (student.getId() == id) {
                student.addProject(project);
                return;
            }
        });
        context.saveStudentsAsync();
    }

    public ArrayList<Report> viewReports() {
        DataContext context = new DataContext();
        ArrayList<Report> reports = context.getReports();
        return reports;
    }

    // Assign Supervisor and Marker
}
