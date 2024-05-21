package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ags.pms.Helper;
import com.ags.pms.data.DataContext;

public class Student extends User {

    private ArrayList<Integer> reportIds;
    private ArrayList<Integer> presentationSlotIds;
    private ArrayList<Integer> projectIds;
    private ArrayList<String> modules;
    private AssessmentType assessmentType;
    private int supervisorId;
    private int secondMarkerId;

    public Student(int id, String name, String dob, String email, String username, String password,
            ArrayList<Integer> reports, ArrayList<Integer> presentationSlots, ArrayList<Integer> projects,
            ArrayList<String> modules, int supervisorId, int secondMarkerId)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        this.reportIds = reports;
        this.presentationSlotIds = presentationSlots;
        this.projectIds = projects;
        this.modules = modules;
        this.supervisorId = supervisorId;
        this.secondMarkerId = secondMarkerId;
    }

    // Debug
    public Student() {
        super();
        reportIds = new ArrayList<Integer>();
        presentationSlotIds = new ArrayList<>();
        modules = new ArrayList<>();
    }

    public Student(int id, String name, String dob, String email, String username, String password)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        reportIds = new ArrayList<>();
        presentationSlotIds = new ArrayList<>();
        modules = new ArrayList<>();
    }

    public Student(int id, String name, String dob, String email, String username, String password,
            ArrayList<Integer> reports) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        this.reportIds = reports;
        presentationSlotIds = new ArrayList<>();
        modules = new ArrayList<>();
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

    public void addProject(int projectId) {
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

    private void fetchSupervisor() {
        DataContext context = new DataContext();

        Optional<ProjectManager> supervisor = context.getProjectManagers().stream()
                .filter(pm -> pm.getRole() == Role.SUPERVISOR)
                .filter(sp -> {
                    ArrayList<Integer> superviseeIds = sp.getSuperviseeIds();
                    return superviseeIds != null && superviseeIds.contains(this.getId());
                })
                .findFirst();

        if (!supervisor.isEmpty()) {
            this.supervisorId = supervisor.get().getId();
        }
    }

    private Report createReport(Project project, String moodleLink, int totalMarks) {
        DataContext context = new DataContext();
        int id = context.fetchNextReportId();
        Report report = new Report(id, project.getId(), moodleLink, totalMarks);
        return report;
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

    public ArrayList<Request> viewPresentationRequests() {
        DataContext context = new DataContext();
        ArrayList<Request> myRequests = context.getRequests().stream()
                .filter(r -> r.getLecturerId() == this.id && r.getRequestType() == RequestType.PRESENTATION)
                .collect(Collectors.toCollection(ArrayList::new));
        return myRequests;
    }

    public ArrayList<Request> viewAllRequests() {
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
}
