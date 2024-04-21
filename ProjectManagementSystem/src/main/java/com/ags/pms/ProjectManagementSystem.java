/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ags.pms;

import com.ags.pms.models.*;
import com.ags.pms.services.*;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ags.pms.forms.Login;
import com.ags.pms.io.JsonHandler;

public class ProjectManagementSystem {

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
        testFileHandler();
        // testAES();
    }


    private static void testFileHandler() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        JsonHandler handler = new JsonHandler();
        // handler.writeFile("Users.txt", "Test Write");
        
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Lecturer> lecturers = new ArrayList<>();

        Student student1 = new Student(4001, "John Doe", "10/02/2024", "johndoe@email.com", "johnUser", "TestPass", new ArrayList<Project>(), AssessmentType.FYP);
        Student student2 = new Student(4002, "John Kumar", "09/03/2024", "johnkumar@email.com", "john_kumar", "GoodStuff", new ArrayList<Project>(), AssessmentType.INVESTIGATIONREPORTS);
        students.add(student1);
        students.add(student2);

        Lecturer lecturer1 = new Lecturer(2001, "Joshua", "11/01/1980", "joshua@lecturer.com", "josh_lecturer", "verySecurePasswordMate");
        Lecturer lecturer2 = new Lecturer(2002, "Amardeep", "11/01/1980", "amardeep@lecturer.com", "somelecturer", "123qweasdzxc");
        lecturer1.setProjectManager(true);

        lecturers.add(lecturer1);
        lecturers.add(lecturer2);

        handler.writeJson(students);
        handler.writeJson(lecturers);
        String text = handler.readData("Students.txt");
        System.out.println(text);
    }

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

}