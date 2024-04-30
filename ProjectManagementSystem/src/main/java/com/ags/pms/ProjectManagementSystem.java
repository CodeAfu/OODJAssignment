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
        // app();
        try {
            consoleTests();
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }
    }
    
    private static void app() {
        new Login().setVisible(true);
    }
    
    private static void consoleTests() throws Exception {
        // smallerTests();
        // testLogin();
        // testFileHandlerAsyncOperations();
        // testFileHandler();
        // testAES();
        // generateNewAESKey();
        dataContextTest();
    }

    @SuppressWarnings("unused")
    private static void smallerTests() throws Exception {
    }
    
    @SuppressWarnings("unused")
    private static void dataContextTest() {
        DataContext context = new DataContext();
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

        SeedData.execute();
        
        handler.readJsonAsync(FileName.STUDENTS)
            .thenAccept(studentsFromJsonList -> {
                studentsFromJson = new ArrayList<>();
                for (User user : studentsFromJsonList) {
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
                for (User user : adminsFromJsonList) {
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

        Student student1 = new Student(4001, "John Doe", "10/02/2024", "johndoe@email.com", "johnUser", "TestPass", new ArrayList<Project>(), AssessmentType.FYP);
        Student student2 = new Student(4002, "John Kumar", "09/03/2024", "johnkumar@email.com", "john_kumar", "GoodStuff", new ArrayList<Project>(), AssessmentType.INVESTIGATIONREPORTS);
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
        
        ProjectManager projectManager1 = new ProjectManager(2001, "JoshuaPM", "11/01/1980", "joshuaPM@lecturer.com", Role.SUPERVISOR, "josh_lecturerPM", "verySecurePasswordMate");
        ProjectManager projectManager2 = new ProjectManager(2002, "AmardeepPM", "11/01/1980", "amardeepPM@lecturer.com", Role.SECONDMARKER, "somelecturerPM", "123qweasdzxc");
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