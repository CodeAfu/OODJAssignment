package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;

import com.ags.pms.data.DataContext;
import com.ags.pms.io.FileName;
import com.ags.pms.io.JsonHandler;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;

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
            supervisees.put(s.getId(), s.getSupervisor().getId());
        }
        return supervisees;
    }

    public ArrayList<Request> viewPresentationRequests() {
        DataContext context = new DataContext();
        ArrayList<Request> presentationRequests = (ArrayList<Request>) context.getRequests().stream()
                                                            .filter(r -> r.getRequestType() == RequestType.PRESENTATION)
                                                            .collect(Collectors.toList());
        return presentationRequests;
    }

    public ArrayList<PresentationSlot> viewAvailableSlots() {
        DataContext context = new DataContext();
        ArrayList<PresentationSlot> availableSlots = (ArrayList<PresentationSlot>) context.getPresentationSlots().stream()
                                                                .filter(r -> r.isAvailable() == true)
                                                                .collect(Collectors.toList());
        return availableSlots;
    }

    public void viewSecondMarkerAcceptance() {
        DataContext context = new DataContext();



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