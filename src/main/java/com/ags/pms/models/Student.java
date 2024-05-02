package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Student extends User {

    private ArrayList<Project> projects;
    private AssessmentType assessmentType;

    // Debug
    public Student() {
        super();
        projects = new ArrayList<Project>();
    }
    
    public Student(int id, String name, String dob, String email, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        projects = new ArrayList<Project>();
    }
    
    public Student(int id, String name, String dob, String email, String username, String password, ArrayList<Project> projects, AssessmentType assessmentType) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        this.projects = projects;
        this.assessmentType = assessmentType;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public void submitProject() {
        
    }

    public void submitProject(Project project) {
        
    }

    public void editStudent(Student student) {
        
    }
    
    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }

}
