package com.ags.pms.data;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import com.ags.pms.io.FileName;
import com.ags.pms.io.JsonHandler;
import com.ags.pms.models.Admin;
import com.ags.pms.models.Lecturer;
import com.ags.pms.models.ProjectManager;
import com.ags.pms.models.Student;

public class DataContext {

    private JsonHandler handler;

    private ArrayList<Lecturer> lecturers;
    private ArrayList<Student> students;
    private ArrayList<Admin> admins;
    private ArrayList<ProjectManager> projectManagers;

    private CompletableFuture<Void> allFutures;

    public DataContext() {
        handler = new JsonHandler();
        populateAllDataAsync();
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

    private void populateAllDataAsync() {
        allFutures = CompletableFuture.allOf(
            populateStudentsAsync(),
            populateAdminsAsync(),
            populateLecturersAsync(),
            populateProjectManagersAsync()
        );
        
        allFutures.join();
    }

    public void writeAllDataAsync() {
        allFutures = CompletableFuture.allOf(
            saveAdminsAsync(),
            saveStudentsAsync(),
            saveLecturersAsync(),
            saveProjectManagersAsync()
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
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });

    }
}
