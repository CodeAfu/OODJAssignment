package com.ags.pms;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.Date;

import com.ags.pms.data.DataContext;
import com.ags.pms.data.IDHandler;
import com.ags.pms.io.FileName;
import com.ags.pms.models.Admin;
import com.ags.pms.models.Identifiable;
import com.ags.pms.models.Lecturer;
import com.ags.pms.models.PresentationSlot;
import com.ags.pms.models.Project;
import com.ags.pms.models.ProjectManager;
import com.ags.pms.models.Report;
import com.ags.pms.models.Request;
import com.ags.pms.models.RequestType;
import com.ags.pms.models.Role;
import com.ags.pms.models.Student;
import com.ags.pms.models.User;
import com.formdev.flatlaf.json.ParseException;

import java.time.LocalDateTime;
import java.time.ZoneId;

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
        hashMap.put(FileName.PRESENTATIONSLOTS, "PresentationSlots.txt");
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
        return new SimpleDateFormat("dd/MM/yyyy hh:mm a");
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException | java.text.ParseException e) {
            Helper.printErr(Helper.getStackTraceString(e));
            return null;
        }
    }

    public static Class<?> getClassTypeFromId(int id) {
        if (id >= 1000 && id < 2000) {
            return Admin.class;
        } else if (id >= 2000 && id < 3000) {
            return assessLecturerOrPM(id);
        } else if (id >= 4000 && id < 5000) {
            return Student.class;
        } else if (id >= 5000 && id < 6000) {
            return PresentationSlot.class;
        } else if (id >= 6000 && id < 7000) {
            return Request.class;
        } else if (id >= 7000 && id < 8000) {
            return Project.class;
        } else if (id >= 8000 && id < 9000) {
            return Report.class;
        } else {
            throw new IllegalArgumentException("Invalid ID range");
        }
    }

    private static Class<?> assessLecturerOrPM(int id) {
        DataContext context = new DataContext();
        List<Integer> lecturerIds = context.getLecturers().stream()
                                            .map(l -> l.getId())
                                            .collect(Collectors.toList());
        List<Integer> projectManagerIds = context.getProjectManagers().stream()
                                            .map(l -> l.getId())
                                            .collect(Collectors.toList());

        if (lecturerIds.contains(id)) {
            return Lecturer.class;
        } else if (projectManagerIds.contains(id)) {
            return ProjectManager.class;
        } else {
            throw new IllegalArgumentException("No PM found (Helper)");
            // return null;
        }
    }

    public static boolean isIdentifiableInstance(Class<?> clazz) {
        return Identifiable.class.isAssignableFrom(clazz);
    }

    public static boolean isUserInstance(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @SuppressWarnings("incomplete-switch")
    public static Role getRoleFromRequestType(RequestType requestType) {
        switch (requestType){
            case RequestType.SECONDMARKER:
                return Role.SECONDMARKER;
            case RequestType.SUPERVISOR:
                return Role.SUPERVISOR;
        }
        return null;
    }

    public static User assertUserType(User user) {
        if (user instanceof Lecturer) {
            return (Lecturer) user;
        } else if (user instanceof Student) {
            return (Student) user;
        } else if (user instanceof Admin) {
            return (Admin) user;
        } else if (user instanceof ProjectManager) {
            return (ProjectManager) user;
        }
        Helper.printErr("Object was not properly casted to a specific user instance: " + user.getId());
        return user;
    }

    public static void joinForm(JFrame form) throws InterruptedException {
        final Object formLock = new Object();
        form.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                synchronized (formLock) {
                    formLock.notifyAll();
                }
            }
            
            @Override
            public void windowClosed(WindowEvent e) {
                synchronized (formLock) {
                    formLock.notifyAll();
                }
            }
        });

        synchronized (formLock) {
            while (form.isVisible()) {
                formLock.wait();
            }
        }
    }

    public static Date convertToDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static boolean isNumeric(String s) {
        char[] charArray = s.toCharArray();
        boolean isNegative = charArray.length > 0 && charArray[0] == '-';
        int startIndex = isNegative ? 1 : 0;

        for (int i = startIndex; i < charArray.length; i++) {
            if (!Character.isDigit(charArray[i])) {
                return false;
            }
        }
        return true;
    }
}
