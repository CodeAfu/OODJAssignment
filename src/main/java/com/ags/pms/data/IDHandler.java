package com.ags.pms.data;

import java.util.Comparator;
import java.util.Optional;

import javax.xml.crypto.Data;

import com.ags.pms.models.Admin;
import com.ags.pms.models.Lecturer;
import com.ags.pms.models.Report;
import com.ags.pms.models.Student;

public class IDHandler {

    private DataContext context;

    private int nextStudentId;
    private int nextLecturerId;
    private int nextAdminId;
    private int nextReportId;

    // For Jackson's Databind package
    public IDHandler() { }

    public IDHandler(DataContext context) {
        this.context = context;
        populateMaxIds();
    }

    public int getNextStudentId() {
        return nextStudentId;
    }
    private void setNextStudentId(int nextStudentId) {
        this.nextStudentId = nextStudentId;
    }
    public int getNextLecturerId() {
        return nextLecturerId;
    }
    private void setNextLecturerId(int nextLecturerId) {
        this.nextLecturerId = nextLecturerId;
    }
    public int getNextAdminId() {
        return nextAdminId;
    }
    private void setNextAdminId(int nextAdminId) {
        this.nextAdminId = nextAdminId;
    }
    public int getNextReportId() {
        return nextReportId;
    }
    private void setNextReportId(int nextReportId) {
        this.nextReportId = nextReportId;
    }

    public int assignStudentId() {
        return nextStudentId++;
    }
    public int assignLecturerId() {
        return nextLecturerId++;
    }
    public int assignAdminId() {
        return nextAdminId++;
    }
    public int assignReportId() {
        return nextReportId++;
    }

    private void populateMaxIds() {
        if (!context.getAdmins().isEmpty()){
            this.setNextAdminId(context.getAdmins().stream().max(Comparator.comparingInt(Admin::getId)).isPresent() ? 
                context.getAdmins().stream().max(Comparator.comparingInt(Admin::getId)).get().getId() + 1 : 1000);
        } else { this.setNextAdminId(1000); }

        if (!context.getStudents().isEmpty()) {
            this.setNextStudentId(context.getStudents().stream().max(Comparator.comparingInt(Student::getId)).isPresent() ? 
                context.getStudents().stream().max(Comparator.comparingInt(Student::getId)).get().getId() + 1 : 4000);
        } else { this.setNextAdminId(4000); }

        if (!context.getLecturers().isEmpty()) {
            this.setNextLecturerId(context.getLecturers().stream().max(Comparator.comparingInt(Lecturer::getId)).isPresent() ? 
                context.getLecturers().stream().max(Comparator.comparingInt(Lecturer::getId)).get().getId() + 1 : 2000);
        } else { this.setNextAdminId(2000); }
        
        if (!context.getReports().isEmpty()) {
            this.setNextReportId(context.getReports().stream().max(Comparator.comparingInt(Report::getId)).isPresent() ? 
                context.getReports().stream().max(Comparator.comparingInt(Report::getId)).get().getId() + 1 : 8000);
        } else { this.setNextAdminId(8000); }

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
