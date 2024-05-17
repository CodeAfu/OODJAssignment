package com.ags.pms.data;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.print.PrintServiceLookup;

import com.ags.pms.io.JsonHandler;
import com.ags.pms.models.Admin;
import com.ags.pms.models.AssessmentType;
import com.ags.pms.models.Lecturer;
import com.ags.pms.models.PresentationSlot;
import com.ags.pms.models.Project;
import com.ags.pms.models.Report;
import com.ags.pms.models.Request;
import com.ags.pms.models.RequestType;
import com.ags.pms.models.ProjectManager;
import com.ags.pms.models.Role;
import com.ags.pms.models.Student;

// Seed all data here incase of complete data loss lol
public class SeedData {

    private ArrayList<Student> students;
    private ArrayList<Lecturer> lecturers;
    private ArrayList<Admin> admins;
    private ArrayList<ProjectManager> projectManagers;

    private static CompletableFuture<Void> futures;

    public static void init() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        JsonHandler handler = new JsonHandler();

        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Lecturer> lecturers = new ArrayList<>();
        ArrayList<Admin> admins = new ArrayList<>();
        ArrayList<ProjectManager> projectManagers = new ArrayList<>();
        ArrayList<Project> projects = new ArrayList<>();
        ArrayList<PresentationSlot> presentationSlots = new ArrayList<>();
        ArrayList<Report> reports = new ArrayList<>();
        ArrayList<Request> requests = new ArrayList<>();

        Project project1 = new Project(7000, "Computer Science", AssessmentType.CP1, "Do this project!!!!!!");
        Project project2 = new Project(7001, "Cybersecurity", AssessmentType.CP2, "Do this project!!!!!!");
        Report report1 = new Report(8000, project1.getId(), false, null, "https://sample.moodle.com/sample-link/1", -1, 100);
        Report report2 = new Report(8001, project2.getId(), true, new Date(), "https://sample.moodle.com/sample-link/2", 30, 100);
        Student student1 = new Student(4001, "John Doe", "10/02/2024", "johndoe@email.com", "johnUser", "TestPass", new ArrayList<Integer>());
        Student student2 = new Student(4002, "John Kumar", "09/03/2024", "johnkumar@email.com", "john_kumar", "GoodStuff", new ArrayList<Integer>(Arrays.asList(8000, 8001)));
        Student student3 = new Student(4003, "Emma Smith", "05/08/2023", "emma@email.com", "emma_smith", "P@ssw0rd", new ArrayList<Integer>());
        Student student4 = new Student(4004, "Michael Johnson", "12/11/2023", "michael@email.com", "michael_j", "secure123", new ArrayList<Integer>());
        Lecturer lecturer1 = new Lecturer(2001, "Joshua", "11/01/1980", "joshua@lecturer.com", "josh_lecturer", "verySecurePasswordMate", Role.NONE);
        Lecturer lecturer2 = new Lecturer(2002, "Amardeep", "11/01/1980", "amardeep@lecturer.com", "somelecturer", "123qweasdzxc", Role.NONE);
        Lecturer lecturer3 = new Lecturer(2003, "Sophie Williams", "03/07/1975", "sophie@email.com", "sophie_will", "Passw0rd", Role.NONE);
        Lecturer lecturer4 = new Lecturer(2004, "David Brown", "20/09/1978", "david@email.com", "david_brown", "MyP@ssw0rd", Role.NONE);
        Admin admin1 = new Admin(1001, "Jay", "20/12/1999", "jay@admin.com", "admin", "VerySecureRight");
        Admin admin2 = new Admin(1002, "JayZee", "20/10/1999", "jayzee@admin.com", "systemkek", "huuuhe123");
        Admin admin3 = new Admin(1003, "Emily Davis", "15/03/1990", "emily@email.com", "emily_d", "admin321");
        ProjectManager projectManager1 = new ProjectManager(2005, "JoshuaPM", "11/01/1980", "joshuaPM@lecturer.com", "josh_lecturerPM", "verySecurePasswordMate", Role.SUPERVISOR, new ArrayList<Integer>(Arrays.asList(4001, 4002)));
        ProjectManager projectManager2 = new ProjectManager(2006, "AmardeepPM", "11/01/1980", "amardeepPM@lecturer.com", "somelecturerPM", "123qweasdzxc", Role.SECONDMARKER, new ArrayList<Integer>(Arrays.asList(4003)));
        ProjectManager projectManager3 = new ProjectManager(2008, "Sophia Johnson", "25/06/1970", "sophia@email.com", "sophia_j", "ProjectMan321", Role.SUPERVISOR, new ArrayList<Integer>(Arrays.asList(4004)));
        ProjectManager projectManager4 = new ProjectManager(2007, "Michael Wilson", "17/04/1972", "michael@email.com", "michael_w", "Wilson123", Role.SECONDMARKER, new ArrayList<Integer>());
        Request request1 = new Request(6000, 4001, RequestType.PRESENTATION, "Computer Science", false);
        Request request2 = new Request(6001, 4002, RequestType.PRESENTATION, "Computer Science", true);
        Request request3 = new Request(6002, 2001, RequestType.SUPERVISOR, false);
        Request request4 = new Request(6003, 2002, RequestType.SECONDMARKER, true);
        PresentationSlot presentationSlot1 = new PresentationSlot(5000, 4001, new Date(), false);
        PresentationSlot presentationSlot2 = new PresentationSlot(5001);
        PresentationSlot presentationSlot3 = new PresentationSlot(5002);

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        
        lecturers.add(lecturer1);
        lecturers.add(lecturer2);
        lecturers.add(lecturer3);
        lecturers.add(lecturer4);
        
        admins.add(admin1);
        admins.add(admin2);
        admins.add(admin3); 
        
        projectManagers.add(projectManager1);
        projectManagers.add(projectManager2);
        projectManagers.add(projectManager3);
        projectManagers.add(projectManager4);

        projects.add(project1);
        projects.add(project2);
        
        reports.add(report1);
        reports.add(report2);

        requests.add(request1);
        requests.add(request2);
        requests.add(request3);
        requests.add(request4);

        presentationSlots.add(presentationSlot1);
        presentationSlots.add(presentationSlot2);
        presentationSlots.add(presentationSlot3);
        

        futures = CompletableFuture.allOf(
            handler.writeJsonAsync(students),
            handler.writeJsonAsync(lecturers),
            handler.writeJsonAsync(admins),
            handler.writeJsonAsync(projectManagers),
            handler.writeJsonAsync(projects),
            handler.writeJsonAsync(reports),
            handler.writeJsonAsync(requests),
            handler.writeJsonAsync(presentationSlots)
        ).thenRun(() -> System.out.println("Json Written"));
        futures.join();
        
    }

    public static void executeWithContext() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        DataContext context = new DataContext();

        Project project1 = new Project(7000, "Computer Science", AssessmentType.CP1, "Do this project!!!!!!");
        Project project2 = new Project(7001, "Cybersecurity", AssessmentType.CP2, "Do this project!!!!!!");
        Report report1 = new Report(8000, project1.getId(), false, null, "https://sample.moodle.com/sample-link/1", -1, 100);
        Report report2 = new Report(8001, project2.getId(), true, new Date(), "https://sample.moodle.com/sample-link/2", 30, 100);
        Student student1 = new Student(4001, "John Doe", "10/02/2024", "johndoe@email.com", "johnUser", "TestPass", new ArrayList<Integer>());
        Student student2 = new Student(4002, "John Kumar", "09/03/2024", "johnkumar@email.com", "john_kumar", "GoodStuff", new ArrayList<Integer>(Arrays.asList(8000, 8001)));
        Student student3 = new Student(4003, "Emma Smith", "05/08/2023", "emma@email.com", "emma_smith", "P@ssw0rd", new ArrayList<Integer>());
        Student student4 = new Student(4004, "Michael Johnson", "12/11/2023", "michael@email.com", "michael_j", "secure123", new ArrayList<Integer>());
        Lecturer lecturer1 = new Lecturer(2001, "Joshua", "11/01/1980", "joshua@lecturer.com", "josh_lecturer", "verySecurePasswordMate", Role.NONE);
        Lecturer lecturer2 = new Lecturer(2002, "Amardeep", "11/01/1980", "amardeep@lecturer.com", "somelecturer", "123qweasdzxc", Role.NONE);
        Lecturer lecturer3 = new Lecturer(2003, "Sophie Williams", "03/07/1975", "sophie@email.com", "sophie_will", "Passw0rd", Role.NONE);
        Lecturer lecturer4 = new Lecturer(2004, "David Brown", "20/09/1978", "david@email.com", "david_brown", "MyP@ssw0rd", Role.NONE);
        Admin admin1 = new Admin(1001, "Jay", "20/12/1999", "jay@admin.com", "admin", "VerySecureRight");
        Admin admin2 = new Admin(1002, "JayZee", "20/10/1999", "jayzee@admin.com", "systemkek", "huuuhe123");
        Admin admin3 = new Admin(1003, "Emily Davis", "15/03/1990", "emily@email.com", "emily_d", "admin321");
        ProjectManager projectManager1 = new ProjectManager(2005, "JoshuaPM", "11/01/1980", "joshuaPM@lecturer.com", "josh_lecturerPM", "verySecurePasswordMate", Role.SUPERVISOR, new ArrayList<Integer>(Arrays.asList(4001, 4002)));
        ProjectManager projectManager2 = new ProjectManager(2006, "AmardeepPM", "11/01/1980", "amardeepPM@lecturer.com", "somelecturerPM", "123qweasdzxc", Role.SECONDMARKER, new ArrayList<Integer>(Arrays.asList(4003)));
        ProjectManager projectManager3 = new ProjectManager(2008, "Sophia Johnson", "25/06/1970", "sophia@email.com", "sophia_j", "ProjectMan321", Role.SUPERVISOR, new ArrayList<Integer>(Arrays.asList(4004)));
        ProjectManager projectManager4 = new ProjectManager(2007, "Michael Wilson", "17/04/1972", "michael@email.com", "michael_w", "Wilson123", Role.SECONDMARKER, new ArrayList<Integer>());
        Request request1 = new Request(6000, 4001, RequestType.PRESENTATION, "Computer Science", false);
        Request request2 = new Request(6001, 4002, RequestType.PRESENTATION, "Computer Science", true);
        Request request3 = new Request(6002, 2001, RequestType.SUPERVISOR, false);
        Request request4 = new Request(6003, 2002, RequestType.SECONDMARKER, true);
        PresentationSlot presentationSlot1 = new PresentationSlot(5000, 4001, new Date(), false);
        PresentationSlot presentationSlot2 = new PresentationSlot(5001);
        PresentationSlot presentationSlot3 = new PresentationSlot(5002);


        context.addStudent(student1);
        context.addStudent(student2);
        context.addStudent(student3);
        context.addStudent(student4);

        context.addLecturer(lecturer1);
        context.addLecturer(lecturer2);
        context.addLecturer(lecturer3);
        context.addLecturer(lecturer4);
    
        context.addAdmin(admin1);
        context.addAdmin(admin2);
        context.addAdmin(admin3);

        context.addProjectManager(projectManager1);
        context.addProjectManager(projectManager2);
        context.addProjectManager(projectManager3);
        context.addProjectManager(projectManager4);
        
        context.addProject(project1);
        context.addProject(project2);

        context.addReport(report1);
        context.addReport(report2);

        context.addRequest(request1);
        context.addRequest(request2);
        context.addRequest(request3);
        context.addRequest(request4);

        context.addPresentationSlot(presentationSlot1);
        context.addPresentationSlot(presentationSlot2);
        context.addPresentationSlot(presentationSlot3);
        
        context.writeAllDataAsync();
    }
}
