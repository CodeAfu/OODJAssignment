package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.ArrayList;
import java.util.HashMap;

import com.ags.pms.Helper;
import com.ags.pms.data.DataContext;

public class Lecturer extends User {
    
    protected boolean isProjectManager;
    protected Role role;

    public Lecturer() {
        super();
        isProjectManager = false;
    }
    
    public Lecturer(int id, String name, String dob, String email, String username, String password, Role role) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void toggleProjectManager() {
        isProjectManager = !isProjectManager;
        System.out.println("Project Manager: " + isProjectManager);
    }

    public ArrayList<Student> viewSupervisees() {
        DataContext context = new DataContext();
        ArrayList<Student> students = context.getStudents();
        return students;
    }

    public ArrayList<Request> viewPendingPresentationRequests() {
        DataContext context = new DataContext();
        ArrayList<Request> presentationRequests = (ArrayList<Request>) context.getRequests().stream()
                                                            .filter(r -> r.getRequestType() == RequestType.PRESENTATION && r.isApproved() == false)
                                                            .collect(Collectors.toList());
        return presentationRequests;
    }

    public ArrayList<PresentationSlot> viewAvailableSlots() {
        DataContext context = new DataContext();
        ArrayList<PresentationSlot> availableSlots = context.getPresentationSlots().stream()
                                                            .filter(r -> r.isAvailable() == true)
                                                            .collect(Collectors.toCollection(ArrayList::new));
        return availableSlots;
    }

    public ArrayList<Student> viewSecondMarkerSlots() {
        DataContext context = new DataContext();
        ArrayList<Student> availableStudents = context.getStudents().stream()
                                                    .filter(s -> s.getSecondMarkerId() == 0)
                                                    .collect(Collectors.toCollection(ArrayList::new));
        return availableStudents;
    }

    public Request viewSecondMarkerAcceptance() {
        DataContext context = new DataContext();
        Request request = context.getRequest(r -> r.getUserId() == this.id);

        if (request == null) {
            throw new NullPointerException("No Request found for User ID: " + this.id);
            // Helper.printErr("No Request found for User ID: " + this.id);
            // return null;
        }
    
        if (request.getRequestType() != RequestType.SECONDMARKER) {
            throw new IllegalArgumentException("Invalid RequestType: " + request.getRequestType());
            // Helper.printErr("Invalid RequestType: " + request.getRequestType());
            // return null;
        }

        return request;
    }

    public void assignPresentationSlot(int requestId, int studentId, int presentationSlotId) {
        DataContext context = new DataContext();
        PresentationSlot slot = context.getById(presentationSlotId);

        if (!slot.isAvailable()) {
            throw new IllegalArgumentException("Presentation slot is not available: " + slot.getId());
        }

        context.updatePresentationSlotById(presentationSlotId, ps -> ps.setAvailable(false));
        context.updateStudentById(studentId, s -> s.addPresentationSlotId(slot.getId()));
        context.updateRequestById(requestId, r -> r.setApproved(true));
        
        context.writeAllDataAsync();
    }

    public ArrayList<Report> viewReport(Student student) {
        ArrayList<Report> reports = new ArrayList<>();
        DataContext context = new DataContext();

        for (int reportId : student.getReportIds()) {
            reports.add(context.getById(reportId));
        }
        return reports;
    }

    public void evaluateReport(int studentId) {
        DataContext context = new DataContext();
    }
}