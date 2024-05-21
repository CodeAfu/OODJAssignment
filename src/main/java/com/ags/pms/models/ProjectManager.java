package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ags.pms.data.DataContext;

public class ProjectManager extends Lecturer {

    private ArrayList<Integer> superviseeIds = new ArrayList<>();

    public ProjectManager() {
        super();
    }
    
    public ProjectManager(int id, String name, String dob, String email, String username, String password, Role role, ArrayList<Integer> superviseeIds) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password, role);
        this.isProjectManager = true;
        this.superviseeIds = superviseeIds;
    }

    public ProjectManager(String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(username, password);
        this.isProjectManager = true;
    }

    public ArrayList<Integer> getSuperviseeIds() {
        return superviseeIds;
    }

    public void setSuperviseeIds(ArrayList<Integer> supervisees) {
        this.superviseeIds = supervisees;
    }

    public void addSuperviseeId(int superviseeId) {
        this.superviseeIds.add(superviseeId);
    }

    public void createProject(Project project) {
        DataContext context = new DataContext();
        context.addProject(project);
        context.writeAllDataAsync();
    }
    
    public void assignProject(int studentId, Project project) {
        DataContext context = new DataContext();
        ArrayList<Student> students = context.getStudents();

        students.forEach(student -> {
            if (student.getId() == id) {
                student.addProject(project.getId());
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

    public void assignStudentAssessmentType(int studentId, AssessmentType assessmentType) {
        DataContext context = new DataContext();
        context.updateStudentById(studentId, s -> s.setAssessmentType(assessmentType));
        context.writeAllDataAsync();
    }

    public void assignRoleToLecturer(int id, Role role) {
        DataContext context = new DataContext();
        User user = context.getById(id);

        if (user instanceof Lecturer) {
            context.updateLecturerById(id, lec -> lec.setRole(role));
        } else if (user instanceof ProjectManager) {
            context.updateProjectManagerById(id, pm -> pm.setRole(role));
        }

        context.writeAllDataAsync();
    }

    public ArrayList<Lecturer> viewLecturers() {
        DataContext context = new DataContext();
        return context.getLecturers();
    }

    public ArrayList<Report> viewReportStatus() {
        DataContext context = new DataContext();
        return context.getReports();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
