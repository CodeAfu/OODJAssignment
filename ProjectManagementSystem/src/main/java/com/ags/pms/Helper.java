package com.ags.pms;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.ags.pms.io.FileName;
import com.ags.pms.models.User;
import com.ags.pms.services.PasswordHandler;

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
        
        return hashMap.get(filename);
    }
    
    public static String getFilenameByClassName(String className) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Student", "Students.txt");
        hashMap.put("Lecturer", "Lecturers.txt");
        hashMap.put("ProjectManager", "ProjectManagers.txt");
        hashMap.put("Admin", "Admins.txt");
        
        return hashMap.get(className);
    }

}
