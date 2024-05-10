package com.ags.pms;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ags.pms.models.*;
import com.ags.pms.services.*;
import com.fasterxml.jackson.databind.introspect.ConcreteBeanPropertyBase;
import com.formdev.flatlaf.json.Json;

import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.implementation.bytecode.constant.ClassConstant;

import com.ags.pms.data.DataContext;
import com.ags.pms.data.SeedData;
import com.ags.pms.forms.Login;
import com.ags.pms.io.FileName;
import com.ags.pms.io.JsonHandler;

public class ProjectManagementSystem {

    private static ArrayList<Student> studentsFromJson;
    private static ArrayList<Lecturer> lecturersFromJson;
    private static ArrayList<Admin> adminsFromJson;
    private static ArrayList<ProjectManager> projectManagersFromJson;

    public static void main(String[] args) {
        try {
            // app();
            consoleTests();
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }
}
    
    private static void app() {
        new Login().setVisible(true);
    }
    
    private static void consoleTests() throws Exception {
        // testLogin();
        // testFileHandlerAsyncOperations();
        // testFileHandler();
        // testAES();
        // generateNewAESKey();
        smallTests();
        // dataContextTest();
    }


    @SuppressWarnings("unused")
    private static void smallTests() throws Exception {
        // DataContext context = new DataContext();
        Admin admin1 = new Admin(1001, "Jay", "20/12/1999", "jay@admin.com", "admin", "VerySecureRight");
        admin1.allotProjectManager(2001, RequestType.SUPERVISOR);
        admin1.removeProjectManager(2001);
        System.out.println("Test");
    }
    
    @SuppressWarnings("unused")
    private static void dataContextTest() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        try {
            // SeedData.executeWithContext();
            SeedData.init();
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException | InvalidAlgorithmParameterException e) {
            Helper.printErr(Helper.getStackTraceString(e));
        }

        DataContext context = new DataContext();
        
        Student student1 = new Student(4001, "John Doe", "10/02/2024", "johndoe@email.com", "johnUser", "TestPass", new ArrayList<Report>());
        ProjectManager projectManager1 = new ProjectManager(context.fetchNextLecturerId(), "Michael Myers", "17/04/1972", "michael@email.com", "michael_myers", "asdfpass", Role.SECONDMARKER, new ArrayList<Student>());
        ProjectManager projectManager2 = new ProjectManager(context.fetchNextLecturerId(), "Hehe My Man", "17/04/1972", "michael@email.com", "michael_myers", "asdfpass", Role.SECONDMARKER, new ArrayList<Student>());
        PresentationSlot slot = new PresentationSlot(context.fetchNextPresentationSlotId());
        Request request = new Request(context.fetchNextRequestId(), student1, "Computer Science");
        Project project = new Project(context.fetchNextProjectId(), "Computer Science", AssessmentType.CP1, "You must do this project!!!!!!!!!!!!!!!");

        // context.addPresentationSlot(slot);
        // context.addRequest(request);
        // context.addProject(project);

        // context.writeAllDataAsync();
        
        // context.addProjectManager(projectManager1);
        // context.addProjectManager(projectManager2);
        
        // context.writeAllDataAsync();
        // context.print();
        System.out.println("--------------------------");

        ArrayList<Lecturer> lecturers = context.getLecturers();
        lecturers.forEach(l -> System.out.println(l.getUsername()));

        Student student = context.getStudentByID(4020);
        Student student2 = context.getStudent(s -> s.getId() == 4001);
        Lecturer lecturer = context.getLecturer(l -> l.getId() == 2001);
    }
















    
    @SuppressWarnings("unused")
    private static void testLogin() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        ProjectManager manager = new ProjectManager("somelecturerPM", "123qweasdzxc");
        System.out.println(manager.login());
    }

    @SuppressWarnings("unused")
    private static void testFileHandlerAsyncOperations() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        JsonHandler handler = new JsonHandler();
        PasswordHandler pwHandler = new PasswordHandler("9Vs+DfEF1+3tF8fCKLp9BQ==", "JoprQnQRq95s/Nuz");

        SeedData.init();
        
        handler.readJsonAsync(FileName.STUDENTS)
            .thenAccept(studentsFromJsonList -> {
                studentsFromJson = new ArrayList<>();
                for (Object user : studentsFromJsonList) {
                    if (user instanceof Student) {
                        studentsFromJson.add((Student)user);
                    } else {
                        System.out.println(user + " was not added (JsonRead)");
                    }
                }
            })
            .thenRun(() -> {
                System.out.println("Json Read");
                studentsFromJson.forEach(u -> System.out.println(u.getUsername() + ": " + u.getPassword() + " | " + u.getClass().getSimpleName()));
            })
            .exceptionally(ex -> {
                ex.printStackTrace();
                return null;
            });

        handler.readJsonAsync("Admin")
            .thenAccept(adminsFromJsonList -> {
                adminsFromJson = new ArrayList<>();
                for (Object user : adminsFromJsonList) {
                    if (user instanceof Admin) {
                        adminsFromJson.add((Admin)user);
                    } else {
                        System.out.println(user + " was not added (JsonRead)");
                    }
                }
            })
            .thenRun(() -> {
                System.out.println("Json Read");
                adminsFromJson.forEach(u -> System.out.println(u.getUsername() + ": " + u.getPassword() + " | " + u.getClass().getSimpleName()));
            })
            .exceptionally(ex -> {
                ex.printStackTrace();
                return null;
            });

        // Thread sleep required to run the async method I guess
        // since the main thread dies before the async threads execute?
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    private static void testFileHandler() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        JsonHandler handler = new JsonHandler();
        PasswordHandler pwHandler = new PasswordHandler("9Vs+DfEF1+3tF8fCKLp9BQ==", "JoprQnQRq95s/Nuz");

        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Lecturer> lecturers = new ArrayList<>();
        ArrayList<Admin> admins = new ArrayList<>();
        ArrayList<ProjectManager> projectManagers = new ArrayList<>();

        Student student1 = new Student(4001, "John Doe", "10/02/2024", "johndoe@email.com", "johnUser", "TestPass", new ArrayList<Report>());
        Student student2 = new Student(4002, "John Kumar", "09/03/2024", "johnkumar@email.com", "john_kumar", "GoodStuff", new ArrayList<Report>());
        students.add(student1);
        students.add(student2);

        Lecturer lecturer1 = new Lecturer(2001, "Joshua", "11/01/1980", "joshua@lecturer.com", "josh_lecturer", "verySecurePasswordMate");
        Lecturer lecturer2 = new Lecturer(2002, "Amardeep", "11/01/1980", "amardeep@lecturer.com", "somelecturer", "123qweasdzxc");
        lecturer1.setProjectManager(true);
        lecturers.add(lecturer1);
        lecturers.add(lecturer2);
        
        Admin admin1 = new Admin("admin", "OkayDude");
        Admin admin2 = new Admin("heh_this_user_has_changed", "test2");
        admins.add(admin1);
        admins.add(admin2);
        
        ProjectManager projectManager1 = new ProjectManager(2001, "JoshuaPM", "11/01/1980", "joshuaPM@lecturer.com", "josh_lecturerPM", "verySecurePasswordMate", Role.SUPERVISOR, new ArrayList<Student>());
        ProjectManager projectManager2 = new ProjectManager(2002, "AmardeepPM", "11/01/1980", "amardeepPM@lecturer.com", "somelecturerPM", "123qweasdzxc", Role.SECONDMARKER, new ArrayList<Student>());
        projectManagers.add(projectManager1);
        projectManagers.add(projectManager2);

        handler.writeJson(students);
        handler.writeJson(lecturers);
        handler.writeJson(admins);
        handler.writeJson(projectManagers);

        // READ
        ArrayList<Student> studentsFromJson = handler.readJson(FileName.STUDENTS);
        ArrayList<Admin> adminsFromJson = handler.readJson(FileName.ADMINS);
        ArrayList<Lecturer> lecturersFromJson = handler.readJson(FileName.LECTURERS);
        ArrayList<ProjectManager> projectManagersFromJson = handler.readJson(FileName.PROJECTMANAGERS);
        
        studentsFromJson.forEach(obj -> System.out.println(obj.getUsername() + ": " + obj.getPassword()));
        adminsFromJson.forEach(obj -> System.out.println(obj.getUsername() + ": " + obj.getPassword()));
        lecturersFromJson.forEach(obj -> System.out.println(obj.getUsername() + ": " + obj.getPassword()));
        projectManagersFromJson.forEach(obj -> System.out.println(obj.getUsername() + ": " + obj.getPassword()));
    }

    @SuppressWarnings("unused")
    private static void testAES() throws Exception {
        PasswordHandler handler = new PasswordHandler();

        // password.init();
        handler.initFromStrings("9Vs+DfEF1+3tF8fCKLp9BQ==", "JoprQnQRq95s/Nuz");

        String encryptedPassword = handler.encryptPassword("TestPasswordDamn");
        String decryptedPassword = handler.decryptPassword("yQC5XJSuqubdkMm319tlVm4rzj06iqYyaWo12SaDbA0=");

        Helper.printErr("Encrypted: " + encryptedPassword);
        Helper.printErr("Decrypted: " + decryptedPassword);
        Helper.printErr("Secret Key: " + handler.exportKeys()[0]);
        Helper.printErr("IV: " + handler.exportKeys()[1]);
    }

    @SuppressWarnings("unused")
    private static void generateNewAESKey() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        PasswordHandler handler = new PasswordHandler();
        handler.init();
        handler.encryptPassword(".");

        Helper.printErr("Secret Key: " + handler.exportKeys()[0]);
        Helper.printErr("IV: " + handler.exportKeys()[1]);
    }

}