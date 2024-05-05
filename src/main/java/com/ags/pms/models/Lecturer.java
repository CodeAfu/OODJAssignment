package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.util.ArrayList;
import java.util.HashMap;

import com.ags.pms.data.DataContext;
import com.ags.pms.io.FileName;
import com.ags.pms.io.JsonHandler;

public class Lecturer extends User {
    
    protected boolean isProjectManager;

    public Lecturer() {
        super();
        isProjectManager = false;
    }
    
    public Lecturer(int id, String name, String dob, String email, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        isProjectManager = false;
    }

    public Lecturer(String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(username, password);
        isProjectManager = false;
    }
    
    public boolean isProjectManager() {
        return isProjectManager;
    }

    public void setProjectManager(boolean isProjectManager) {
        this.isProjectManager = isProjectManager;
    }

    public void toggleProjectManager() {
        isProjectManager = !isProjectManager;
        System.out.println("Project Manager: " + isProjectManager);
    }

    public HashMap<Integer, Integer> viewSupervisees() {
        DataContext context = new DataContext();
        ArrayList<Student> students = context.getStudents();
        HashMap<Integer, Integer> supervisees = new HashMap<>();

        for (Student s : students) {
            supervisees.put(s.getId(), s.getSuperviser().getId());
        }
        
        return supervisees;
    }

    public void viewPresentationRequests() {
        JsonHandler handler = new JsonHandler();
        ArrayList<Student> supervisees = handler.readJson(FileName.STUDENTS);
        System.out.println("Presentation Requests for " + this.getName() + ":");

        for (Student student : supervisees) {
            System.out.println("- " + student.retrievePresentationRequestDetails());
        }
    }

    public void viewAvailableSlots() {
        JsonHandler handler = new JsonHandler();
        ArrayList<Student> supervisees = handler.readJson(FileName.STUDENTS);
        System.out.println("Available slots for " + this.getName() + ":");

        for (Student student : supervisees) {
            if (student.getPresentationSlots() == null) {
                System.out.println("- " + student.getName() + ": Available");
            } else {
                System.out.println("- " + student.getName() + ": " + student.getPresentationSlots());
            }
        }
    }

    public void viewSecondMarkerAcceptance() {
        JsonHandler handler = new JsonHandler();
        ArrayList<Student> supervisees = handler.readJson(FileName.STUDENTS);
        System.out.println("Second Marker Acceptance for " + this.getName() + ":");

        for (Student student : supervisees) {
            if (student.getSecondMarker() != null) {
                System.out.println("- " + student.getName() + ": " + student.getSecondMarker().getName() + " accepted");
            } else {
                System.out.println("- " + student.getName() + ": Not accepted yet");
            }
        }
    }

    public void assignPresentationSlot(Student student, PresentationSlot slot) {
        student.addPresentationSlot(slot);
    }

    public ArrayList<Report> viewReport(Student student) {
        ArrayList<Report> reports = new ArrayList<>();
        for (Report report : student.getReports()) {
            reports.add(report);
        }
        return reports;
    }
}