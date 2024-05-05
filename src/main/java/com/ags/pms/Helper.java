package com.ags.pms;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.ags.pms.data.IDHandler;
import com.ags.pms.io.FileName;
import com.ags.pms.io.JsonHandler;
import com.ags.pms.models.Admin;
import com.ags.pms.models.Lecturer;
import com.ags.pms.models.ProjectManager;
import com.ags.pms.models.Report;
import com.ags.pms.models.Student;
import com.fasterxml.jackson.annotation.JsonAlias;

public class Helper {
    
    public static void printErr(String text) {
        System.err.print("\u001B[31m");
        System.err.println(text);
        System.err.print("\u001B[0m");
    }

    public static String getStackTraceString(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

    public static String getFilenameByEnum(FileName filename) {
        HashMap<FileName, String> hashMap = new HashMap<>();
        hashMap.put(FileName.STUDENTS, "Students.txt");
        hashMap.put(FileName.LECTURERS, "Lecturers.txt");
        hashMap.put(FileName.PROJECTMANAGERS, "ProjectManagers.txt");
        hashMap.put(FileName.ADMINS, "Admins.txt");
        hashMap.put(FileName.REPORTS, "Reports.txt");
        hashMap.put(FileName.IDHANDLER, "IDs.txt");
        
        return hashMap.get(filename);
    }
    
    public static String getFilenameByClassName(String className) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Student", "Students.txt");
        hashMap.put("Lecturer", "Lecturers.txt");
        hashMap.put("ProjectManager", "ProjectManagers.txt");
        hashMap.put("Admin", "Admins.txt");
        hashMap.put("Report", "Reports.txt");
        hashMap.put("IDHandler", "IDs.txt");
        
        return hashMap.get(className);
    }

    public static Class<?> getClassTypeByFilename(FileName filename) {
        HashMap<FileName, Class<?>> hashMap = new HashMap<>();
        hashMap.put(FileName.STUDENTS, Student.class);
        hashMap.put(FileName.LECTURERS, Lecturer.class);
        hashMap.put(FileName.ADMINS, Admin.class);
        hashMap.put(FileName.PROJECTMANAGERS, ProjectManager.class);
        hashMap.put(FileName.REPORTS, Report.class);
        hashMap.put(FileName.IDHANDLER, IDHandler.class);

        return hashMap.get(filename);
    }

    public static Class<?> getClassTypeByClassName(String className) {
        HashMap<String, Class<?>> hashMap = new HashMap<>();
        hashMap.put("Student", Student.class);
        hashMap.put("Lecturer", Lecturer.class);
        hashMap.put("Admin", Admin.class);
        hashMap.put("ProjectManager", ProjectManager.class);
        hashMap.put("IDHandler", IDHandler.class);
        
        return hashMap.get(className);
    }

    public static DateFormat getDateFormat() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    // public static Class<?> getClassByID(int id) {
    //     String idToString = String.valueOf(id);
    //     char firstCharacter = idToString.charAt(0);

    //     if (firstCharacter == '2') {
    //         if (isProjectManager(id)) {
    //             return ProjectManager.class;
    //         }
    //         return Lecturer.class;
    //     }

    //     HashMap<Character, Class<?>> hashMap = new HashMap<>();
    //     hashMap.put('4', Student.class);
    //     hashMap.put('1', Admin.class);
        
    //     return hashMap.get(firstCharacter);
    // }

    // private static boolean isProjectManager(int id) {
    //     JsonHandler handler = new JsonHandler();

    // }


}
