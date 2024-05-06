package com.ags.pms.data;

import java.util.Comparator;
import java.util.Optional;

import com.ags.pms.io.JsonHandler;
import com.ags.pms.models.Admin;
import com.ags.pms.models.Lecturer;
import com.ags.pms.models.PresentationRequest;
import com.ags.pms.models.PresentationSlot;
import com.ags.pms.models.Project;
import com.ags.pms.models.Report;
import com.ags.pms.models.Student;

public class IDHandler {

    private DataContext context;

    private int nextAdminId;
    private int nextLecturerId;
    private int nextStudentId;
    private int nextPresentationSlotId;
    private int nextRequestId;
    private int nextProjectId;
    private int nextReportId;

    // For Jackson's Databind package
    public IDHandler() { }

    public IDHandler(DataContext context) {
        this.context = context;
        populateMaxIds();
    }

    public void initFromFile() {
        JsonHandler handler = new JsonHandler();
        IDHandler idHandler = handler.getIds();

        this.nextAdminId = idHandler.getNextAdminId();
        this.nextLecturerId = idHandler.getNextLecturerId();
        this.nextStudentId = idHandler.getNextStudentId();
        this.nextPresentationSlotId = idHandler.getNextPresentationSlotId();
        this.nextRequestId = idHandler.getNextRequestId();
        this.nextProjectId = idHandler.getNextProjectId();
        this.nextReportId = idHandler.getNextReportId();
    }

    public int getNextStudentId() {
        return nextStudentId;
    }
    void setNextStudentId(int nextStudentId) {
        this.nextStudentId = nextStudentId;
    }
    public int getNextLecturerId() {
        return nextLecturerId;
    }
    void setNextLecturerId(int nextLecturerId) {
        this.nextLecturerId = nextLecturerId;
    }
    public int getNextAdminId() {
        return nextAdminId;
    }
    void setNextAdminId(int nextAdminId) {
        this.nextAdminId = nextAdminId;
    }
    public int getNextReportId() {
        return nextReportId;
    }
    void setNextReportId(int nextReportId) {
        this.nextReportId = nextReportId;
    }
    public int getNextPresentationSlotId() {
        return nextPresentationSlotId;
    }
    public void setNextPresentationSlotId(int nextPresentationSlotId) {
        this.nextPresentationSlotId = nextPresentationSlotId;
    }
    public int getNextRequestId() {
        return nextRequestId;
    }
    public void setNextRequestId(int nextRequestId) {
        this.nextRequestId = nextRequestId;
    }
    public int getNextProjectId() {
        return nextProjectId;
    }
    public void setNextProjectId(int nextProjectId) {
        this.nextProjectId = nextProjectId;
    }

    public int assignAdminId() {
        return nextAdminId++;
    }
    public int assignLecturerId() {
        return nextLecturerId++;
    }
    public int assignStudentId() {
        return nextStudentId++;
    }
    public int assignPresentationSlotId() {
        return nextPresentationSlotId++;
    }
    public int assignRequestid() {
        return nextRequestId++;
    }
    public int assingProjectId() {
        return nextProjectId++;
    }
    public int assignReportId() {
        return nextReportId++;
    }

    void populateMaxIds() {
        if (!context.getAdmins().isEmpty()){
            this.setNextAdminId(context.getAdmins().stream().max(Comparator.comparingInt(Admin::getId)).isPresent() ? 
                context.getAdmins().stream().max(Comparator.comparingInt(Admin::getId)).get().getId() + 1 : 1000);
        } else { this.setNextAdminId(1000); }

        if (!context.getLecturers().isEmpty()) {
            this.setNextLecturerId(context.getLecturers().stream().max(Comparator.comparingInt(Lecturer::getId)).isPresent() ? 
                context.getLecturers().stream().max(Comparator.comparingInt(Lecturer::getId)).get().getId() + 1 : 2000);
        } else { this.setNextLecturerId(2000); }

        if (!context.getStudents().isEmpty()) {
            this.setNextStudentId(context.getStudents().stream().max(Comparator.comparingInt(Student::getId)).isPresent() ? 
                context.getStudents().stream().max(Comparator.comparingInt(Student::getId)).get().getId() + 1 : 4000);
        } else { this.setNextStudentId(4000); }
        
        if (!context.getPresentationSlots().isEmpty()) {
            this.setNextPresentationSlotId(context.getPresentationSlots().stream().max(Comparator.comparingInt(PresentationSlot::getId)).isPresent() ? 
                context.getPresentationSlots().stream().max(Comparator.comparingInt(PresentationSlot::getId)).get().getId() + 1 : 5000);
        } else { this.setNextPresentationSlotId(5000); }

        if (!context.getPresentationRequests().isEmpty()) {
            this.setNextRequestId(context.getPresentationRequests().stream().max(Comparator.comparingInt(PresentationRequest::getId)).isPresent() ? 
                context.getPresentationRequests().stream().max(Comparator.comparingInt(PresentationRequest::getId)).get().getId() + 1 : 6000);
        } else { this.setNextRequestId(6000); }

        if (!context.getProjects().isEmpty()) {
            this.setNextProjectId(context.getProjects().stream().max(Comparator.comparingInt(Project::getId)).isPresent() ? 
                context.getProjects().stream().max(Comparator.comparingInt(Project::getId)).get().getId() + 1 : 7000);
        } else { this.setNextProjectId(7000); }
        
        if (!context.getReports().isEmpty()) {
            this.setNextReportId(context.getReports().stream().max(Comparator.comparingInt(Report::getId)).isPresent() ? 
                context.getReports().stream().max(Comparator.comparingInt(Report::getId)).get().getId() + 1 : 8000);
        } else { this.setNextReportId(8000); }

        if (!context.getProjectManagers().isEmpty()) {
            Optional<Integer> maxProjectManagerId = context.getProjectManagers().stream()
                                                                .map(pm -> pm.getId())
                                                                .max(Comparator.naturalOrder());
            if (maxProjectManagerId.isPresent() 
                && maxProjectManagerId.get() > this.getNextLecturerId()) {
                this.setNextLecturerId(maxProjectManagerId.get() + 1);
            }
        }
    }
}
