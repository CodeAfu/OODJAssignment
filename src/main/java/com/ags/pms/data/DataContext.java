package com.ags.pms.data;

import java.util.ArrayList;
import java.util.Collections;

import java.util.Optional;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.NoSuchElementException;

import com.ags.pms.Helper;
import com.ags.pms.io.FileName;
import com.ags.pms.io.JsonHandler;
import com.ags.pms.models.Admin;
import com.ags.pms.models.Identifiable;
import com.ags.pms.models.Lecturer;
import com.ags.pms.models.PresentationRequest;
import com.ags.pms.models.PresentationSlot;
import com.ags.pms.models.Project;
import com.ags.pms.models.ProjectManager;
import com.ags.pms.models.Report;
import com.ags.pms.models.Student;


public class DataContext {

    private JsonHandler handler;

    private ArrayList<Lecturer> lecturers = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<ProjectManager> projectManagers = new ArrayList<>();
    private ArrayList<Report> reports = new ArrayList<>();
    private ArrayList<PresentationSlot> presentationSlots = new ArrayList<>();
    private ArrayList<PresentationRequest> presentationRequests = new ArrayList<>();
    private ArrayList<Project> projects = new ArrayList<>();

    private IDHandler idHandler;
    
    private CompletableFuture<Void> allFutures;

    public DataContext() {
        handler = new JsonHandler();
        populateAllDataAsync();
        initIds();
    }

    // Only call on constructor
    private void initIds() {
        IDHandler savedIdHandler = handler.getIds();
        idHandler = new IDHandler(this);

        idHandler.setNextAdminId(Math.max(savedIdHandler.getNextAdminId(), idHandler.getNextAdminId()));
        idHandler.setNextLecturerId(Math.max(savedIdHandler.getNextLecturerId(), idHandler.getNextLecturerId()));
        idHandler.setNextStudentId(Math.max(savedIdHandler.getNextStudentId(), idHandler.getNextStudentId()));
        idHandler.setNextPresentationSlotId(Math.max(savedIdHandler.getNextPresentationSlotId(), idHandler.getNextPresentationSlotId()));
        idHandler.setNextRequestId(Math.max(savedIdHandler.getNextRequestId(), idHandler.getNextRequestId()));
        idHandler.setNextProjectId(Math.max(savedIdHandler.getNextProjectId(), idHandler.getNextProjectId()));
        idHandler.setNextReportId(Math.max(savedIdHandler.getNextReportId(), idHandler.getNextReportId()));
    }

    // Call after all write operations
    private void updateIds() {
        idHandler = new IDHandler(this);
    }

    public int fetchNextStudentId() {
        return idHandler.assignStudentId();
    }

    public int fetchNextAdminId() {
        return idHandler.assignAdminId();
    }

    public int fetchNextLecturerId() {
        return idHandler.assignLecturerId();
    }

    public int fetchNextReportId() {
        return idHandler.assignReportId();
    }

    public Student getStudentByID(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public Lecturer getLecturerByID(int id) {
        return lecturers.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public Admin getAdminByID(int id) {
        return admins.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public ProjectManager getProjectManagerByID(int id) {
        return projectManagers.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public Student getStudent(Expression<Student> expression) {
        return students.stream()
                        .filter(student -> expression.action(student))
                        .findFirst()
                        .orElseThrow(() -> new NoSuchElementException("Student not found"));
    }

    public Lecturer getLecturer(Expression<Lecturer> expression) {
        return lecturers.stream()
                       .filter(lecturer -> expression.action(lecturer))
                       .findFirst()
                       .orElseThrow(() -> new NoSuchElementException("Lecturer not found"));
    }

    public Admin getAdmin(Expression<Admin> expression) {
        return admins.stream()
                       .filter(admin -> expression.action(admin))
                       .findFirst()
                       .orElseThrow(() -> new NoSuchElementException("Admin not found"));
    }

    public ProjectManager getProjectManager(Expression<ProjectManager> expression) {
        return projectManagers.stream()
                       .filter(projectManager -> expression.action(projectManager))
                       .findFirst()
                       .orElseThrow(() -> new NoSuchElementException("ProjectManager not found"));
    }

    public <T extends Identifiable> T getById(ArrayList<T> objTs, int id) {
        return objTs.stream()
                    .filter(o -> o.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Object not found"));
    }

    public void setLecturers(ArrayList<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void setAdmins(ArrayList<Admin> admins) {
        this.admins = admins;
    }

    public void setProjectManagers(ArrayList<ProjectManager> projectManagers) {
        this.projectManagers = projectManagers;
    }

    public ArrayList<Lecturer> getLecturers() {
        return lecturers;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Admin> getAdmins() {
        return admins;
    }

    public ArrayList<ProjectManager> getProjectManagers() {
        return projectManagers;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }
    public ArrayList<PresentationSlot> getPresentationSlots() {
        return presentationSlots;
    }

    public void setPresentationSlots(ArrayList<PresentationSlot> presentationSlots) {
        this.presentationSlots = presentationSlots;
    }

    public ArrayList<PresentationRequest> getPresentationRequests() {
        return presentationRequests;
    }

    public void setPresentationRequests(ArrayList<PresentationRequest> presentationRequests) {
        this.presentationRequests = presentationRequests;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public IDHandler getIdHandler() {
        return idHandler;
    }

    private <T extends Identifiable> boolean checkDuplicateId(ArrayList<T> objTs, T obj) {
        for (T o : objTs) {
            if (o.getId() == obj.getId()) {
                Helper.printErr("Error: Duplicate object. ID=" + obj.getId());
                return true;
            }
        }
        return false;
    }

    public void addStudent(Student student) {
        if (checkDuplicateId(students, student)) return;;
        students.add(student);
        updateIds();
    }

    public void addAdmin(Admin admin) {
        if (checkDuplicateId(admins, admin)) return;;
        admins.add(admin);
        updateIds();
    }

    public void addLecturer(Lecturer lecturer) {
        if (checkDuplicateId(lecturers, lecturer)) return;;
        lecturers.add(lecturer);
        updateIds();
    }

    public void addProjectManager(ProjectManager projectManager) {
        if (checkDuplicateId(projectManagers, projectManager)) return;;
        projectManagers.add(projectManager);
        updateIds();
    }

    public void addReport(Report report) {
        if (checkDuplicateId(reports, report)) return;;
        reports.add(report);
        updateIds();
    }

    private void populateAllDataAsync() {
        allFutures = CompletableFuture.allOf(
            populateStudentsAsync(),
            populateAdminsAsync(),
            populateLecturersAsync(),
            populateProjectManagersAsync(),
            populateReportsAsync()
        )
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
        allFutures.join();
    }

    public void writeAllDataAsync() {
        this.sortAll();
        allFutures = CompletableFuture.allOf(
            saveAdminsAsync(),
            saveStudentsAsync(),
            saveLecturersAsync(),
            saveProjectManagersAsync(),
            saveReportsAsync()
        ).thenRun(() -> System.out.println("Json Written"))
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
        allFutures.join();

        handler.updateIds(idHandler);
    }

    public CompletableFuture<Void> saveStudentsAsync() {
        return CompletableFuture.runAsync(() -> {
            sort(students);
            handler.writeJson(students);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> saveLecturersAsync() {
        return CompletableFuture.runAsync(() -> {
            sort(lecturers);
            handler.writeJson(lecturers);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });

    }

    public CompletableFuture<Void> saveProjectManagersAsync() {
        return CompletableFuture.runAsync(() -> {
            sort(projectManagers);
            handler.writeJson(projectManagers);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> saveAdminsAsync() {
        return CompletableFuture.runAsync(() -> {
            sort(admins);
            handler.writeJson(admins);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> saveReportsAsync() {
        return CompletableFuture.runAsync(() -> {
            sort(reports);
            handler.writeJson(reports);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> populateProjectManagersAsync() {
        return handler.readJsonAsync(FileName.PROJECTMANAGERS)
            .thenAccept(projectManagerList -> {
                projectManagers = new ArrayList<>();
                projectManagerList.forEach(pm -> projectManagers.add((ProjectManager) pm));
            })
            .thenRun(() -> {
                System.out.println("ProjectManagers populated");
            })
            .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> populateStudentsAsync() {
        return handler.readJsonAsync(FileName.STUDENTS)
            .thenAccept(studentList -> {
                students = new ArrayList<>();
                studentList.forEach(s -> students.add((Student) s));
            })
            .thenRun(() -> {
                System.out.println("Students populated");
            })
            .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> populateLecturersAsync() {
        return handler.readJsonAsync(FileName.LECTURERS)
            .thenAccept(lecturerList -> {
                lecturers = new ArrayList<>();
                lecturerList.forEach(l -> lecturers.add((Lecturer) l));
            })
            .thenRun(() -> {
                System.out.println("Lecturers populated");
            })
            .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> populateAdminsAsync() {
        return handler.readJsonAsync(FileName.ADMINS)
            .thenAccept(adminList -> {
                admins = new ArrayList<>();
                adminList.forEach(a -> admins.add((Admin) a));
            })
            .thenRun(() -> {
                System.out.println("Admins populated");
            })
            .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> populateReportsAsync() {
        return handler.readJsonAsync(FileName.REPORTS)
            .thenAccept(reportList -> {
                reports = new ArrayList<>();
                reportList.forEach(r -> reports.add((Report) r));
            })
            .thenRun(() -> {
                System.out.println("Reports populated");
            })
            .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public void print() {
        allFutures.thenRun(() -> {
            System.out.print("STUDENTS: ");
            students.forEach(x -> System.out.print(x.getUsername() + " "));
            System.out.println();
    
            System.out.print("LECTURERS: ");
            lecturers.forEach(x -> System.out.print(x.getUsername() + " "));
            System.out.println();
    
            System.out.print("ADMINS: ");
            admins.forEach(x -> System.out.print(x.getUsername() + " "));
            System.out.println();
    
            System.out.print("PROJECTMANAGERS: ");
            projectManagers.forEach(x -> System.out.print(x.getUsername() + " "));
            System.out.println();
    
            System.out.print("REPORTS: ");
            reports.forEach(x -> System.out.print(x.getMoodleLink() + " "));
            System.out.println();
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public void sortAll() {
        Collections.sort(students, Comparator.comparingInt(Student::getId));
        Collections.sort(lecturers, Comparator.comparingInt(Lecturer::getId));
        Collections.sort(projectManagers, Comparator.comparingInt(ProjectManager::getId));
        Collections.sort(admins, Comparator.comparingInt(Admin::getId));
        Collections.sort(reports, Comparator.comparingInt(Report::getId));
    }

    public <T extends Identifiable> void sort(ArrayList<T> objTs) {
        Collections.sort(objTs, Comparator.comparingInt(T::getId));
    }

    public void isValidUser(String username, String password) {
        
    }
}
