package com.ags.pms;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ags.pms.models.*;
import com.ags.pms.services.*;
import com.ags.pms.data.DataContext;
import com.ags.pms.data.SeedData;
import com.ags.pms.forms.AdminForm;
import com.ags.pms.forms.LecturerForm;
import com.ags.pms.forms.Login;
import com.ags.pms.forms.ProjectManagerForm;
import com.ags.pms.forms.StudentForm;

public class ProjectManagementSystem {

    private static Login loginForm;
    private static User user;

    public static void main(String[] args) {
        try {
            app();
            // consoleTests();
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }
    }

    private static void app() throws InterruptedException {
        boolean running = true;

        while (running) {
            loginForm = new Login();
            loginForm.setVisible(true);
            Helper.joinForm(loginForm);

            user = loginForm.getUser();

            if (user != null) {
                if (user.getClass().getSimpleName().equals("Lecturer")) {
                    LecturerForm lecturerForm = new LecturerForm(user);
                    lecturerForm.setVisible(true);
                    Helper.joinForm(lecturerForm);

                } else if (user.getClass().getSimpleName().equals("Admin")) {
                    AdminForm adminForm = new AdminForm(user);
                    adminForm.setVisible(true);
                    Helper.joinForm(adminForm);
                    
                } else if (user.getClass().getSimpleName().equals("Student")) {
                    StudentForm studentForm = new StudentForm(user);
                    studentForm.setVisible(true);
                    Helper.joinForm(studentForm);

                } else if (user.getClass().getSimpleName().equals("ProjectManager")) {
                    ProjectManagerForm projectManagerForm = new ProjectManagerForm(user);
                    projectManagerForm.setVisible(true);
                    Helper.joinForm(projectManagerForm);
                }
            }
            user = null;
        }
    }

    private static void consoleTests() throws Exception {
        // generateNewAESKey();
        // smallTests();
        dataContextTest();
    }

    @SuppressWarnings("unused")
    private static void smallTests() throws Exception {
        DataContext context = new DataContext();
    }


    @SuppressWarnings("unused")
    private static void dataContextTest() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        try {
            // SeedData.executeWithContext();
            SeedData.init();
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException | InvalidAlgorithmParameterException e) {
            Helper.printErr(Helper.getStackTraceString(e));
        }

        DataContext context = new DataContext();

        Student student1 = new Student(4001, "John Doe", "10/02/2024", "johndoe@email.com", "johnUser", "TestPass",
                new ArrayList<Integer>(), new ArrayList<>());

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
    private static void generateNewAESKey() throws NoSuchAlgorithmException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        PasswordHandler handler = new PasswordHandler();
        handler.init();
        handler.encryptPassword(".");

        Helper.printErr("Secret Key: " + handler.exportKeys()[0]);
        Helper.printErr("IV: " + handler.exportKeys()[1]);
    }
}