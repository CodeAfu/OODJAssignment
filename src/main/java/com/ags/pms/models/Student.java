package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Student extends User {

    private ArrayList<Report> reports;
    private AssessmentType assessmentType;

    // Debug
    public Student() {
        super();
        reports = new ArrayList<Report>();
    }
    
    public Student(int id, String name, String dob, String email, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        reports = new ArrayList<Report>();
    }
    
    public Student(int id, String name, String dob, String email, String username, String password, ArrayList<Report> projects, AssessmentType assessmentType) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        this.reports = projects;
        this.assessmentType = assessmentType;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> projects) {
        this.reports = projects;
    }

    public void addProject(Report project) {
        reports.add(project);
    }

    public void submitProject() {
        
    }

    public void submitProject(Report project) {
        
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
