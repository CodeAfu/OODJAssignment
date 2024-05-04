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
    private ArrayList<PresentationSlot> presentationSlots;



    // Debug
    public Student() {
        super();
        reports = new ArrayList<Report>();
    }
    
    public Student(int id, String name, String dob, String email, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        reports = new ArrayList<Report>();
    }
    
    public Student(int id, String name, String dob, String email, String username, String password, ArrayList<Report> reports) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        this.reports = reports;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }

    public void addReport(Report report) {
        reports.add(report);
    }

    public void submitReport() {
        
    }

    public void submitReport(Report report) {
        
    }
    
    public ArrayList<PresentationSlot> getPresentationSlots() {
        return presentationSlots;
    }

    public void setPresentationSlots(ArrayList<PresentationSlot> presentationSlots) {
        this.presentationSlots = presentationSlots;
    }

    public void assignPresentationSlot(PresentationSlot slot) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPresentationSlot'");
    }

    public String retrieveReportDetails() {
        return "";
    }

    public ProjectManager retrieveSecondMarker() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSecondMarker'");
    }

    public String retrievePresentationSlot() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPresentationSlot'");
    }

    public static String retrievePresentationRequestDetails() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrievePresentationRequestDetails'");
    }

}
