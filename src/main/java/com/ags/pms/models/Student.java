package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.text.html.Option;

import com.ags.pms.data.DataContext;

public class Student extends User {

    private ArrayList<Report> reports;
    private ArrayList<PresentationSlot> presentationSlots;
    private ArrayList<String> modules;
    private ProjectManager superviser;
    private ProjectManager secondMarker;

    // Debug
    public Student() {
        super();
        reports = new ArrayList<Report>();
        presentationSlots = new ArrayList<>();
        modules = new ArrayList<>();
    }
    
    public Student(int id, String name, String dob, String email, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        reports = new ArrayList<>();
        presentationSlots = new ArrayList<>();
        modules = new ArrayList<>();
    }
    
    public Student(int id, String name, String dob, String email, String username, String password, ArrayList<Report> reports) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        this.reports = reports;
        presentationSlots = new ArrayList<>();
        modules = new ArrayList<>();
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }

    public ArrayList<String> getModules() {
        return modules;
    }
    
    public void setModules(ArrayList<String> modules) {
        this.modules = modules;
    }
    
    public ArrayList<PresentationSlot> getPresentationSlots() {
        return presentationSlots;
    }

    public ProjectManager getSuperviser() {
        return superviser;
    }

    public void setSuperviser(ProjectManager superviser) {
        this.superviser = superviser;
    }

    public ProjectManager getSecondMarker() {
        return secondMarker;
    }

    public void setSecondMarker(ProjectManager secondMarker) {
        this.secondMarker = secondMarker;
    }

    private void fetchSupervisor() {
        DataContext context = new DataContext();

        Optional<ProjectManager> superviser = context.getProjectManagers().stream()
            .filter(pm -> pm.getRole() == Role.SUPERVISOR)
            .filter(sp -> {
                ArrayList<Student> supervisees = sp.getSupervisees();
                return supervisees != null && supervisees.contains(this);
            })
            .findFirst();
    }

    private Report createReport(String module, AssessmentType assessmentType, String moodleLink, int totalMarks) {
        DataContext context = new DataContext();
        int id = context.fetchNextReportId();
        Report report = new Report(id, this, module, assessmentType, moodleLink, totalMarks);
        return report;
    }

    public void addReport(Report report) {
        reports.add(report);
    }

    public void submitReport(Report report) {
        DataContext context = new DataContext();
        context.addReport(report);
        context.writeAllDataAsync();
    }

    public void setPresentationSlots(ArrayList<PresentationSlot> presentationSlots) {
        this.presentationSlots = presentationSlots;
    }

    public void addPresentationSlot(PresentationSlot slot) {
        this.presentationSlots.add(slot);
    }

    public String retrieveReportDetails() {
        StringBuilder stringBuilder = new StringBuilder();
        reports.forEach(r -> {
            stringBuilder.append(r.retrieveReportDetails());
        });
        return stringBuilder.toString();
    }

    public String retrievePresentationRequestDetails() {
        StringBuilder details = new StringBuilder();
        for (PresentationSlot slot : presentationSlots) {
            details.append(slot.getId())
                   .append(", ")
                   .append(slot.getStudent().getName())
                   .append(", ")
                   .append(slot.getPresentationDate().toString())
                   .append("\n");
        }
        return details.toString();
    }
}
