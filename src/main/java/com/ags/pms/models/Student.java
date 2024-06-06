package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ags.pms.Helper;
import com.ags.pms.data.DataContext;


public class Student extends User {

    private ArrayList<Integer> reportIds = new ArrayList<>();
    private ArrayList<Integer> presentationSlotIds = new ArrayList<>();
    private ArrayList<Integer> projectIds = new ArrayList<>();
    private ArrayList<String> modules = new ArrayList<>();
    private AssessmentType assessmentType;
    private int supervisorId;
    private int secondMarkerId;

    public Student(int id, String name, String dob, String email, String username, String password,
            ArrayList<Integer> reportIds, ArrayList<Integer> presentationSlotIds, ArrayList<Integer> projectsIds,
            ArrayList<String> modules, int supervisorId, int secondMarkerId)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        this.reportIds = reportIds;
        this.presentationSlotIds = presentationSlotIds;
        this.projectIds = projectsIds;
        this.modules = modules;
        this.supervisorId = supervisorId;
        this.secondMarkerId = secondMarkerId;
    }

    public Student(int id, String name, String dob, String email, String username, String password,
            ArrayList<Integer> reportIds, ArrayList<Integer> presentationSlotIds, ArrayList<Integer> projectsIds,
            ArrayList<String> modules)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        this.reportIds = reportIds;
        this.presentationSlotIds = presentationSlotIds;
        this.projectIds = projectsIds;
        this.modules = modules;
    }

    public Student() {
        super();
    }

    public Student(int id, String name, String dob, String email, String username, String password)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        this.reportIds = new ArrayList<>();
        this.projectIds = new ArrayList<>();
        this.presentationSlotIds = new ArrayList<>();
        this.modules = new ArrayList<>();
    }

    public Student(int id, String name, String dob, String email, String username, String password,
            ArrayList<Integer> reportIds, ArrayList<Integer> projectIds) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        this.reportIds = reportIds;
        this.projectIds = projectIds;
        this.presentationSlotIds = new ArrayList<>();
        this.modules = new ArrayList<>();
    }

    public ArrayList<Integer> getReportIds() {
        return reportIds;
    }

    public void setReportIds(ArrayList<Integer> reportIds) {
        this.reportIds = reportIds;
    }

    public ArrayList<Integer> getPresentationSlotIds() {
        return presentationSlotIds;
    }

    public void setPresentationSlotIds(ArrayList<Integer> presentationSlotIds) {
        this.presentationSlotIds = presentationSlotIds;
    }

    public void addPresentationSlotId(int presentationId) {
        presentationSlotIds.add(presentationId);
    }

    public ArrayList<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(ArrayList<Integer> projectIds) {
        this.projectIds = projectIds;
    }

    public void addProjectId(int projectId) {
        this.projectIds.add(projectId);
    }

    public ArrayList<String> getModules() {
        return modules;
    }

    public void setModules(ArrayList<String> modules) {
        this.modules = modules;
    }

    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }

    public int getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }

    public int getSecondMarkerId() {
        return secondMarkerId;
    }

    public void setSecondMarkerId(int secondMarkerId) {
        this.secondMarkerId = secondMarkerId;
    }

    public User fetchSupervisor() {
        if (supervisorId == 0) {
            Helper.printErr("No Supervisor fetched");
            return null;
        }
        DataContext context = new DataContext();
        User supervisor = context.getById(supervisorId);
        return supervisor;
    }
    
    public User fetchSecondMarker() {
        if (secondMarkerId == 0) {
            Helper.printErr("No Supervisor fetched");
            return null;
        }
        DataContext context = new DataContext();
        User secondMarker = context.getById(secondMarkerId);
        return secondMarker;
    }

    public ArrayList<Project> fetchProjects() {
        if (projectIds.isEmpty()) {
            return new ArrayList<>();
        }
        DataContext context = new DataContext();
        ArrayList<Project> projects = new ArrayList<>();
        projectIds.forEach(id -> projects.add(context.getById(id)));
        return projects;
    }

    public void createReport(int studentId, int projectId, String moodleLink, String contents) {
        DataContext context = new DataContext();
        Project project = context.getById(projectId);

        Report report = new Report(context.fetchNextReportId(), projectId, studentId, contents, 
                                   new Date(), moodleLink, project.getTotalMark());
                                   
        reportIds.add(report.getId());
        context.addReport(report);
        context.writeAllDataAsync();
    }

    public void addReport(int reportId) {
        reportIds.add(reportId);
    }

    public void submitReport(Report report) {
        DataContext context = new DataContext();
        context.addReport(report);
        context.writeAllDataAsync();
    }

    private void requestPresentation(String module) {
        if (!modules.contains(module)) {
            // throw new IllegalArgumentException("Student is not enrolled in module: " +
            // module);
            Helper.printErr("Student is not enrolled in module: " + module);
            return;
        }

        DataContext context = new DataContext();
        Request request = new Request(context.fetchNextRequestId(), this.id, module);
        context.addRequest(request);
        context.writeAllDataAsync();
    }

    public ArrayList<Request> viewPendingPresentationRequests() {
        DataContext context = new DataContext();
        ArrayList<Request> myRequests = context.getRequests().stream()
                .filter(r -> r.getStudentId() == this.id && r.getRequestType() == RequestType.PRESENTATION && r.isApproved() == false)
                .collect(Collectors.toCollection(ArrayList::new));
        return myRequests;
    }

    public ArrayList<Request> viewApprovedPresentationRequests() {
        DataContext context = new DataContext();
        ArrayList<Request> myRequests = context.getRequests().stream()
                .filter(r -> r.getStudentId() == this.id && r.getRequestType() == RequestType.PRESENTATION && r.isApproved() == true)
                .collect(Collectors.toCollection(ArrayList::new));
        return myRequests;
    }

    public ArrayList<Request> viewAllPresentationRequests() {
        DataContext context = new DataContext();
        ArrayList<Request> myRequests = context.getRequests().stream()
                .filter(r -> r.getLecturerId() == this.id && r.getRequestType() == RequestType.PRESENTATION)
                .collect(Collectors.toCollection(ArrayList::new));
        return myRequests;
    }

    public Report retrieveReportDetails(int reportId) {
        if (!reportIds.contains(reportId)) {
            Helper.printErr("Report does not exist: " + reportId);
            return null;
        }
        DataContext context = new DataContext();
        Report report = context.getById(reportId);

        return report;
    }

    public String retrievePresentationRequestDetails() {
        StringBuilder details = new StringBuilder();
        DataContext context = new DataContext();
        for (int slotId : presentationSlotIds) {
            PresentationSlot slot = context.getById(slotId);
            Student student = context.getById(slot.getStudentId());
            details.append(slot.getId())
                   .append(", ")
                   .append(student.getName())
                   .append(", ")
                   .append(slot.getPresentationDate().toString())
                   .append("\n");
        }
        return details.toString();
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public ArrayList<Report> fetchReports() {
        DataContext context = new DataContext();
        ArrayList<Report> reports = new ArrayList<>();

        for (int id : reportIds) {
            reports.add(context.getById(id));
        }
        return reports;   
    }

    public void removeReport(int reportId) {
        boolean exists = false;

        for (int id : reportIds) {
            if (id == reportId) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            Helper.printErr("No Report matched for this student: " + reportId);
            return;
        }
        
        DataContext context = new DataContext();
        reportIds.removeIf(id -> id == reportId);
        context.removeById(reportId);
    }

    public ArrayList<PresentationSlot> fetchAvailablePresentationSlots() {
        DataContext context = new DataContext();
        ArrayList<PresentationSlot> presentationSlots 
                = context.getPresentationSlots().stream()
                                                .filter(ps -> ps.isAvailable() == true)
                                                .collect(Collectors.toCollection(ArrayList::new));
        return presentationSlots;
    }

    public void sendPresentationRequest(int psId, String module) {
        DataContext context = new DataContext();

        PresentationSlot slot = context.getById(psId);
        Request request = new Request(context.fetchNextRequestId(), slot.getLecturerId(), this.id, slot.getId(),
                                      RequestType.PRESENTATION, module);
        context.addRequest(request);

        context.writeAllDataAsync();
    }

    public ArrayList<PresentationSlot> fetchMyPresentationSlots() {
        DataContext context = new DataContext();
        ArrayList<PresentationSlot> slots = new ArrayList<>();

        presentationSlotIds.forEach(id -> slots.add(context.getById(id)));

        return slots;
    }
}