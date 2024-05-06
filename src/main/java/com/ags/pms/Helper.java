package com.ags.pms;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.ags.pms.data.DataContext;
import com.ags.pms.data.IDHandler;
import com.ags.pms.io.FileName;
import com.ags.pms.io.JsonHandler;
import com.ags.pms.models.Admin;
import com.ags.pms.models.Lecturer;
import com.ags.pms.models.PresentationSlot;
import com.ags.pms.models.Project;
import com.ags.pms.models.ProjectManager;
import com.ags.pms.models.Report;
import com.ags.pms.models.Request;
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
        hashMap.put(FileName.REQUESTS, "Requests.txt");
        hashMap.put(FileName.PRESENTATIONSLOTS, "PresentationSlot.txt");
        hashMap.put(FileName.PROJECTS, "Projects.txt");
        
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
        hashMap.put("Request", "Requests.txt");
        hashMap.put("PresentationSlot", "PresentationSlots.txt");
        hashMap.put("Project", "Projects.txt");
        
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
        hashMap.put(FileName.REQUESTS, Request.class);
        hashMap.put(FileName.PRESENTATIONSLOTS, PresentationSlot.class);
        hashMap.put(FileName.PROJECTS, Project.class);

        return hashMap.get(filename);
    }

    public static Class<?> getClassTypeByClassName(String className) {
        HashMap<String, Class<?>> hashMap = new HashMap<>();
        hashMap.put("Student", Student.class);
        hashMap.put("Lecturer", Lecturer.class);
        hashMap.put("Admin", Admin.class);
        hashMap.put("ProjectManager", ProjectManager.class);
        hashMap.put("IDHandler", IDHandler.class);
        hashMap.put("Request", Request.class);
        hashMap.put("PresentationSlot", PresentationSlot.class);
        hashMap.put("Project", Project.class);
        
        return hashMap.get(className);
    }

    public static DateFormat getDateFormat() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    // public static Class<?> getClassByID(int id) {
    //     String idToString = String.valueOf(id);
    //     char firstCharacter = idToString.charAt(0);
        
    //     if (firstCharacter == '2') {
    //         return assessLecturerOrPM(id);
    //     }

    //     HashMap<Character, Class<?>> hashMap = new HashMap<>();
    //     hashMap.put('1', Admin.class);
    //     hashMap.put('4', Student.class);

    //     Class<?> result = hashMap.get(firstCharacter);
    //     if (result == null) {
    //         throw new IllegalArgumentException("No PM found (Helper)");
    //     }
    //     return result;        
    // }
    

    // private static Class<?> assessLecturerOrPM(int id) {
    //     DataContext context = new DataContext();
    //     List<Integer> lecturerIds = context.getLecturers().stream()
    //                                         .map(l -> l.getId())
    //                                         .collect(Collectors.toList());
    //     List<Integer> projectManagerIds = context.getProjectManagers().stream()
    //                                         .map(l -> l.getId())
    //                                         .collect(Collectors.toList());

    //     if (lecturerIds.contains(id)) {
    //         return Lecturer.class;
    //     } else if (projectManagerIds.contains(id)) {
    //         return ProjectManager.class;
    //     } else {
    //         throw new IllegalArgumentException("No PM found (Helper)");
    //         // return null;
    //     }
    // }

}
