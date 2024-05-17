package com.ags.pms.models;

import java.lang.reflect.Array;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.text.html.Option;

import com.ags.pms.Helper;
import com.ags.pms.data.DataContext;

public class Student extends User {

    private ArrayList<Report> reports;
    private ArrayList<PresentationSlot> presentationSlots;
    private ArrayList<Project> projects;
    private ArrayList<String> modules;
    private ProjectManager supervisor;
    private ProjectManager secondMarker;

    public Student(int id, String name, String dob, String email, String username, String password,
            ArrayList<Report> reports, ArrayList<PresentationSlot> presentationSlots, ArrayList<Project> projects,
            ArrayList<String> modules, ProjectManager supervisor, ProjectManager secondMarker)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        this.reports = reports;
        this.presentationSlots = presentationSlots;
        this.projects = projects;
        this.modules = modules;
        this.supervisor = supervisor;
        this.secondMarker = secondMarker;
    }

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

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
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

    public ProjectManager getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(ProjectManager superviser) {
        this.supervisor = superviser;
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

        if (!superviser.isEmpty()) {
            this.supervisor = superviser.get();
        }
    }

    private Report createReport(Project project, String moodleLink, int totalMarks) {
        DataContext context = new DataContext();
        int id = context.fetchNextReportId();
        Report report = new Report(id, project, moodleLink, totalMarks);
        return report;
    }

    public void addReport(Report report) {
        reports.add(report);
    }

    public void addProject(Project project) {
        projects.add(project);
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

    private void requestPresentation(String module) {
        if (!modules.contains(module)) {
            Helper.printErr("Student is not enrolled in module: " + module);
            return;
        }
        DataContext context = new DataContext();
        Request request = new Request(context.fetchNextRequestId(), this, module);
        context.addRequest(request);
        context.writeAllDataAsync();
    }

    public ArrayList<Request> viewPresentationRequests() {
        DataContext context = new DataContext();
        ArrayList<Request> myRequests = context.getRequests().stream()
                                                .filter(r -> r.getUser().getId() == this.id && r.getRequestType() == RequestType.PRESENTATION)
                                                .collect(Collectors.toCollection(ArrayList::new));                                              
        return myRequests;
    }

    public ArrayList<Request> viewAllRequests() {
        DataContext context = new DataContext();
        ArrayList<Request> myRequests = context.getRequests().stream()
                                                .filter(r -> r.getUser().getId() == this.id && r.getRequestType() == RequestType.PRESENTATION)
                                                .collect(Collectors.toCollection(ArrayList::new));                                              
        return myRequests;
    }

    public String retrieveReportDetails(Report report) {
        if (!reports.contains(report)) {
            Helper.printErr("Report does not exist: " + report.getId());
            return null;
        }

        return report.toString();
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
