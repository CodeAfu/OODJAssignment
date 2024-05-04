package com.ags.pms.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.management.openmbean.ArrayType;

import java.util.Optional;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.NoSuchElementException;

import com.ags.pms.Helper;
import com.ags.pms.io.FileName;
import com.ags.pms.io.JsonHandler;
import com.ags.pms.models.Admin;
import com.ags.pms.models.Lecturer;
import com.ags.pms.models.ProjectManager;
import com.ags.pms.models.Report;
import com.ags.pms.models.Student;
import com.ags.pms.models.User;
import com.fasterxml.jackson.core.TSFBuilder;


public class DataContext {

    private JsonHandler handler;

    private ArrayList<Lecturer> lecturers = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<ProjectManager> projectManagers = new ArrayList<>();
    private ArrayList<Report> reports = new ArrayList<>();

    private IDTracker idTracker = new IDTracker();
    
    private CompletableFuture<Void> allFutures;

    public DataContext() {
        handler = new JsonHandler();
        populateAllDataAsync();
        handler.readJson(FileName.REPORTS);
        getMaxIds();
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

    // public <T extends User> T getByID(int id) {
    //     @SuppressWarnings("unchecked")
    //     Optional<T> foundUser = (Optional<T>) Stream.of(
    //             get(students.stream(), s -> s.getId() == id),
    //             get(lecturers.stream(), l -> l.getId() == id),
    //             get(admins.stream(), a -> a.getId() == id),
    //             get(projectManagers.stream(), pm -> pm.getId() == id)
    //     ).flatMap(Optional::stream)
    //     .findFirst();

    //     return foundUser.orElse(null);
    // }

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

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addAdmin(Admin admin) {
        admins.add(admin);
    }

    public void addLecturer(Lecturer lecturer) {
        lecturers.add(lecturer);
    }

    public void addProjectManager(ProjectManager projectManager) {
        projectManagers.add(projectManager);
    }

    public void addReport(Report report) {
        reports.add(report);
    }

    private void populateAllDataAsync() {
        allFutures = CompletableFuture.allOf(
            populateStudentsAsync(),
            populateAdminsAsync(),
            populateLecturersAsync(),
            populateProjectManagersAsync(),
            populateReportsAsync()
        );
        
        allFutures.join();
    }

    public void writeAllDataAsync() {
        allFutures = CompletableFuture.allOf(
            saveAdminsAsync(),
            saveStudentsAsync(),
            saveLecturersAsync(),
            saveProjectManagersAsync(),
            saveReportsAsync()
        ).thenRun(() -> System.out.println("Json Written"))
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> saveStudentsAsync() {
        return CompletableFuture.runAsync(() -> {
            handler.writeJson(students);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> saveLecturersAsync() {
        return CompletableFuture.runAsync(() -> {
            handler.writeJson(lecturers);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });

    }

    public CompletableFuture<Void> saveProjectManagersAsync() {
        return CompletableFuture.runAsync(() -> {
            handler.writeJson(projectManagers);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> saveAdminsAsync() {
        return CompletableFuture.runAsync(() -> {
            handler.writeJson(admins);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> saveReportsAsync() {
        return CompletableFuture.runAsync(() -> {
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

    public CompletableFuture<Void> sort() {
        return CompletableFuture.runAsync(() -> {

        });
    }

    // private void compare() {
    //     Collections.sort( new Comparator<User>() {
    //         @Override
    //         public int compare(User user1, User user2) {
    //             return Integer.compare(user1.getId(), user2.getId());
    //         }
    //     });
    // }

    public void isValidUser(String username, String password) {
        
    }

    private void getMaxIds() {
        if (!admins.isEmpty()){
            idTracker.setAdminId(admins.stream().max(Comparator.comparingInt(Admin::getId)).isPresent() ? 
                admins.stream().max(Comparator.comparingInt(Admin::getId)).get().getId() + 1 : 1000);
        }

        if (!students.isEmpty()) {
            idTracker.setStudentId(students.stream().max(Comparator.comparingInt(Student::getId)).isPresent() ? 
                students.stream().max(Comparator.comparingInt(Student::getId)).get().getId() + 1 : 4000);
        }

        if (!lecturers.isEmpty()) {
            idTracker.setLecturerId(lecturers.stream().max(Comparator.comparingInt(Lecturer::getId)).isPresent() ? 
                lecturers.stream().max(Comparator.comparingInt(Lecturer::getId)).get().getId() + 1 : 2000);
        }

        if (!projectManagers.isEmpty()) {
            idTracker.setProjectManagerId(projectManagers.stream().max(Comparator.comparingInt(ProjectManager::getId)).isPresent() ? 
                projectManagers.stream().max(Comparator.comparingInt(ProjectManager::getId)).get().getId() + 1 : 2000);
        }

        if (!reports.isEmpty()) {
            idTracker.setReportId(reports.stream().max(Comparator.comparingInt(Report::getId)).isPresent() ? 
                reports.stream().max(Comparator.comparingInt(Report::getId)).get().getId() + 1 : 8000);
        }
    }
}
