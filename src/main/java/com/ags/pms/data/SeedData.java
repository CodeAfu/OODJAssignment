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

import java.time.LocalDateTime;

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

        Project project1 = new Project(7000, "Computer Science", AssessmentType.CP1, "Programming 101", 100);
        Project project2 = new Project(7001, "Business", AssessmentType.CP2, "Financial Management 101", 100);
        Project project3 = new Project(7002, "Physics", AssessmentType.CP1, "Quantum Mechanics", 100);
        Project project4 = new Project(7003, "Biology", AssessmentType.CP2, "Evolutionary Biology", 100);
        Project project5 = new Project(7004, "Chemistry", AssessmentType.CP1, "Organic Chemistry", 100);
        Project project6 = new Project(7005, "Maths", AssessmentType.CP2, "Calculus", 100);
        Project project7 = new Project(7006, "History", AssessmentType.CP1, "Ancient Civilisations", 100);
        Project project8 = new Project(7007, "English", AssessmentType.CP2, "Litrature Analysis", 100);
        Project project9 = new Project(7008, "Art", AssessmentType.CP1, "Art Appreciation", 100);
        Project project10 = new Project(7009, "Music", AssessmentType.CP2, "Music Composition", 100);
        Project project11 = new Project(7010, "Software Engineering", AssessmentType.RMCP, "Software Project", 100);
        Report report1 = new Report(8000, project1.getId(), 4002, true, LocalDateTime.of(2024, 5, 27, 10, 30), "https://sample.moodle.com/sample-link/1", 0, 100);
        Report report2 = new Report(8001, project2.getId(), 4002, true, LocalDateTime.of(2024, 5, 30, 13, 0), "https://sample.moodle.com/sample-link/2", 30, 100);
        ProjectManager projectManager1 = new ProjectManager(2005, "Magnus Palacios", "11/01/1980", "magp@lecturer.com", "magPM", "verySecurePassword", Role.SUPERVISOR, new ArrayList<Integer>(Arrays.asList(4001, 4002)));
        ProjectManager projectManager2 = new ProjectManager(2006, "Diego Hampton", "11/01/1980", "diegohPM@lecturer.com", "pm", "pm", Role.SECONDMARKER, new ArrayList<Integer>(Arrays.asList(4003)));
        ProjectManager projectManager3 = new ProjectManager(2008, "Sophia Johnson", "25/06/1970", "sophia@email.com", "sophia_j", "ProjectMan321", Role.SUPERVISOR, new ArrayList<Integer>(Arrays.asList(4004)));
        ProjectManager projectManager4 = new ProjectManager(2007, "Michael Wilson", "17/04/1972", "michael@email.com", "michael_w", "Wilson123", Role.SECONDMARKER, new ArrayList<Integer>());
        ProjectManager projectManager5 = new ProjectManager(2014, "David Davis", "15/07/1988", "david@projectmanager.com", "davidPM", "ProjectManager123", Role.SUPERVISOR, new ArrayList<Integer>());
        ProjectManager projectManager6 = new ProjectManager(2015, "Emily Evans", "12/09/1992", "emily@projectmanager.com", "emilyPM", "ProjectManager123", Role.SUPERVISOR, new ArrayList<Integer>());
        ProjectManager projectManager7 = new ProjectManager(2016, "Frank Fischer", "18/01/1985", "frank@projectmanager.com", "frankPM", "ProjectManager123", Role.SUPERVISOR, new ArrayList<Integer>());
        ProjectManager projectManager8 = new ProjectManager(2017, "Alice Adams", "12/03/1982", "alice@projectmanager.com", "alicePM", "ProjectManager123", Role.SUPERVISOR, new ArrayList<Integer>());
        ProjectManager projectManager9 = new ProjectManager(2018, "Bob Brown", "01/04/1990", "bob@projectmanager.com", "bobPM", "ProjectManager123", Role.SUPERVISOR, new ArrayList<Integer>());
        ProjectManager projectManager10 = new ProjectManager(2019, "Charlie Clarke", "25/05/1995", "charlie@projectmanager.com", "charliePM", "ProjectManager123", Role.SUPERVISOR, new ArrayList<Integer>());
        Lecturer lecturer1 = new Lecturer(2001, "Joshua Samuel", "01/01/1980", "joshua.lecturer@gmail.com", "lc", "lc", Role.NONE);
        Lecturer lecturer2 = new Lecturer(2002, "Mohamed Wafiq", "03/04/1980", "wafiq@gmail.com", "wafiq_lecturer", "password123", Role.NONE);
        Lecturer lecturer3 = new Lecturer(2003, "Sophie Williams", "07/03/1975", "sophie.williams@gmail.com", "sophie_lecturer", "password1", Role.NONE);
        Lecturer lecturer4 = new Lecturer(2004, "Muhammad Huzaifah Bin Ismail", "20/09/1980", "huzaifah@gmail.com", "huzaifah_lecturer", "pass123", Role.NONE);
        Lecturer lecturer5 = new Lecturer(2009, "David Brown", "09/09/1978", "david.brown@gmail.com", "dbrown_lecturer", "secure123", Role.NONE);
        Lecturer lecturer6 = new Lecturer(2010, "Andrew Cote", "01/01/1980", "andrew.cote@gmail.com", "acote_lecturer", "password123", Role.NONE);
        Lecturer lecturer7 = new Lecturer(2011, "Aarav Lee", "03/04/1980", "aarav.lee@gmail.com", "alee_lecturer", "pass123", Role.NONE);
        Lecturer lecturer8 = new Lecturer(2012, "Jameson Cox", "07/03/1980", "jameson.cox@gmail.com", "jcox_lecturer", "secure123", Role.NONE);
        Lecturer lecturer9 = new Lecturer(2013, "Kayden Small", "09/09/1980", "kayden.small@gmail.com", "ksmall_lecturer", "pass123", Role.NONE);
        Student student1 = new Student(4001, "John Doe", "10/02/2024", "johndoe@email.com", "johnUser", "whereIsMyPassword", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student2 = new Student(4002, "John Kumar", "09/03/2024", "johnkumar@email.com", "std", "std", new ArrayList<>(Arrays.asList(8000, 8001)), new ArrayList<>(), new ArrayList<>(Arrays.asList(7000, 7001)), new ArrayList<>(Arrays.asList("Computer Science", "Business")));
        Student student3 = new Student(4003, "Emma Smith", "05/08/2023", "emma@email.com", "emma_smith", "P@ssw0rd", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student4 = new Student(4004, "Michael Johnson", "12/11/2023", "michael@email.com", "michael_j", "secure123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Arts", "Biology")));
        Student student5 = new Student(4005, "Michael Myers", "12/11/1994", "michaelmyers@email.com", "michaelmee_j", "secukek23", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student6 = new Student(4006, "Jessica Martin", "15/01/2001", "jessica@email.com", "jessica_m", "secure123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Biology", "Software Engineering")));
        Student student7 = new Student(4007, "Alexander Thompson", "01/05/2001", "alexander@email.com", "alex_t", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student8 = new Student(4008, "Olivia Brown", "05/01/2001", "olivia@email.com", "olivia_b", "secure123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "History")));
        Student student9 = new Student(4009, "James Wilson", "01/08/2001", "james@email.com", "james_w", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Software", "English")));
        Student student10 = new Student(4010, "Isabella Davis", "01/01/2002", "isabella@email.com", "isabella_d", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student11 = new Student(4011, "Daniel Johnson", "01/05/2002", "daniel@email.com", "daniel_j", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student12 = new Student(4012, "Sophia Thompson", "01/01/2003", "sophia@email.com", "sophia_t", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student13 = new Student(4013, "Mason Davis", "01/05/2003", "mason@email.com", "mason_d", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student14 = new Student(4014, "Ava Brown", "01/01/2004", "ava@email.com", "ava_b", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student15 = new Student(4015, "Logan Wilson", "01/05/2004", "logan@email.com", "logan_w", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student16 = new Student(4016, "Benjamin Davis", "01/01/2005", "benjamin@email.com", "benjamin_d", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student17 = new Student(4017, "Liam Johnson", "01/05/2005", "liam@email.com", "liam_j", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student18 = new Student(4018, "Ethan Thompson", "01/01/2006", "ethan@email.com", "ethan_t", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student19 = new Student(4019, "Michael Davis", "01/05/2006", "michael@email.com", "michael_d", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student20 = new Student(4020, "Julia Brown", "01/01/2007", "julia@email.com", "julia_b", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student21 = new Student(4021, "Ethan Wilson", "01/05/2007", "ethan@email.com", "ethan_w", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student22 = new Student(4022, "Sarah Davis", "01/01/2008", "sarah@email.com", "sarah_d", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student23 = new Student(4023, "Daniel Johnson", "01/05/2008", "daniel@email.com", "daniel_j", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student24 = new Student(4024, "Mia Thompson", "01/01/2009", "mia@email.com", "mia_t", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Music", "Business", "Computer Science")));
        Student student25 = new Student(4025, "Ethan Davis", "01/05/2009", "ethan@email.com", "ethan_d", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student26 = new Student(4026, "Isabella Brown", "01/01/2010", "isabella@email.com", "isabella_b", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student27 = new Student(4027, "Oliver Wilson", "01/05/2010", "oliver@email.com", "oliver_w", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student28 = new Student(4028, "Charlotte Davis", "01/01/2011", "charlotte@email.com", "charlotte_d", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student29 = new Student(4029, "Jack Johnson", "01/05/2011", "jack@email.com", "jack_j", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Student student30 = new Student(4030, "Ava Thompson", "01/01/2012", "ava@email.com", "ava_t", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science", "Software Engineering")));
        Admin admin1 = new Admin(1001, "Jay", "20/12/1999", "jay@admin.com", "admin", "admin");
        Admin admin2 = new Admin(1002, "JayZee", "20/10/1999", "jayzee@admin.com", "systemkek", "huuuhe123");
        Admin admin3 = new Admin(1003, "Emily Davis", "15/03/1990", "emily@email.com", "emily_d", "admin321");
        PresentationSlot presentationSlot1 = new PresentationSlot(5000, 4001, LocalDateTime.of(2024, 5, 30, 10, 30), false);
        PresentationSlot presentationSlot2 = new PresentationSlot(5001, 4002, LocalDateTime.of(2024, 6, 13, 12, 30), false);
        PresentationSlot presentationSlot3 = new PresentationSlot(5002, 2001, LocalDateTime.of(2024, 5, 27, 15, 30));
        PresentationSlot presentationSlot4 = new PresentationSlot(5003, 2002, LocalDateTime.of(2024, 6, 1, 8, 0));
        PresentationSlot presentationSlot5 = new PresentationSlot(5004, 2002, LocalDateTime.of(2024, 5, 1, 8, 0));
        PresentationSlot presentationSlot6 = new PresentationSlot(5005, 2003, LocalDateTime.of(2024, 6, 5, 9, 30));
        PresentationSlot presentationSlot7 = new PresentationSlot(5006, 2001, LocalDateTime.of(2024, 6, 6, 10, 0));
        PresentationSlot presentationSlot8 = new PresentationSlot(5007, 2002, LocalDateTime.of(2024, 6, 7, 11, 30));
        PresentationSlot presentationSlot9 = new PresentationSlot(5008, 2003, LocalDateTime.of(2024, 6, 8, 16, 0));
        PresentationSlot presentationSlot10 = new PresentationSlot(5009, 2001, LocalDateTime.of(2024, 6, 9, 12, 0));
        PresentationSlot presentationSlot11 = new PresentationSlot(5010, 2002, LocalDateTime.of(2024, 6, 10, 13, 30));
        PresentationSlot presentationSlot12 = new PresentationSlot(5011, 2003, LocalDateTime.of(2024, 6, 11, 14, 0));
        PresentationSlot presentationSlot13 = new PresentationSlot(5012, 2001, LocalDateTime.of(2024, 6, 12, 15, 30));
        PresentationSlot presentationSlot14 = new PresentationSlot(5013, 2002, LocalDateTime.of(2024, 6, 13, 16, 0));
        PresentationSlot presentationSlot15 = new PresentationSlot(5014, 2003, LocalDateTime.of(2024, 6, 14, 17, 0));
        PresentationSlot presentationSlot16 = new PresentationSlot(5015, 2001, LocalDateTime.of(2024, 6, 15, 18, 30));
        PresentationSlot presentationSlot17 = new PresentationSlot(5016, 2002, LocalDateTime.of(2024, 6, 16, 19, 0));
        PresentationSlot presentationSlot18 = new PresentationSlot(5017, 2003, LocalDateTime.of(2024, 6, 17, 20, 0));
        PresentationSlot presentationSlot19 = new PresentationSlot(5018, 2001, LocalDateTime.of(2024, 6, 18, 21, 30));
        PresentationSlot presentationSlot20 = new PresentationSlot(5019, 2002, LocalDateTime.of(2024, 6, 19, 22, 0));
        PresentationSlot presentationSlot21 = new PresentationSlot(5020, 2003, LocalDateTime.of(2024, 6, 20, 23, 0));
        Request request1 = new Request(6000, 2003, 4001, 5001, RequestType.PRESENTATION, "Computer Science");
        Request request2 = new Request(6001, 2004, 4002, 5002, RequestType.PRESENTATION, "Computer Science");
        Request request3 = new Request(6002, 2001, 4003, RequestType.SUPERVISOR);
        Request request4 = new Request(6003, 2002, 4002, RequestType.SECONDMARKER, "Computer Science");
        Request request5 = new Request(6004, 2003, 4001, RequestType.SECONDMARKER, "Business");
        Request request6 = new Request(6005, 2004, 4004, RequestType.SECONDMARKER, "Chemistry");
        Request request7 = new Request(6006, 2002, 4001, 5001, RequestType.PRESENTATION, "Computer Science");
        Request request8 = new Request(6007, 2001, 4002, 5002, RequestType.PRESENTATION, "Object Oriented Programming");
        Request request9 = new Request(6008, 2004, 4002, 5003, RequestType.PRESENTATION, "Data Analysis");
        Request request10 = new Request(6009, 2003, 4002, 5004, RequestType.PRESENTATION, "Socket Programming");
        Request request11 = new Request(6010, 2001, 4003, RequestType.SECONDMARKER, "Biology");
        Request request12 = new Request(6011, 2002, 4004, RequestType.SECONDMARKER, "Art");
        Request request13 = new Request(6012, 2003, 4001, 5005, RequestType.PRESENTATION, "Computer Science");
        Request request14 = new Request(6013, 2006, 4002, 5006, RequestType.PRESENTATION, "Object Oriented Programming");
        Request request15 = new Request(6014, 2002, 4003, 5007, RequestType.PRESENTATION, "Cybersecurity");
        Request request16 = new Request(6015, 2002, 4004, 5008, RequestType.PRESENTATION, "Data Analysis");
        Request request17 = new Request(6016, 2009, 4001, 5009, RequestType.PRESENTATION, "Socket Programming");
        Request request18 = new Request(6017, 2005, 4002, 5010, RequestType.PRESENTATION, "Databases");
        Request request19 = new Request(6018, 2007, 4002, RequestType.SUPERVISOR);
        Request request20 = new Request(6019, 2008, 4003, RequestType.SECONDMARKER, "Art");

        student5.setSupervisorId(2005);
        student2.setSupervisorId(2005);

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        students.add(student6);
        students.add(student7);
        students.add(student8);
        students.add(student9);
        students.add(student10);
        students.add(student11);
        students.add(student12);
        students.add(student13);
        students.add(student14);
        students.add(student15);
        students.add(student16);
        students.add(student17);
        students.add(student18);
        students.add(student19);
        students.add(student20);
        students.add(student21);
        students.add(student22);
        students.add(student23);
        students.add(student24);
        students.add(student25);
        students.add(student26);
        students.add(student27);
        students.add(student28);
        students.add(student29);
        students.add(student30);

        lecturers.add(lecturer1);
        lecturers.add(lecturer2);
        lecturers.add(lecturer3);
        lecturers.add(lecturer4);
        lecturers.add(lecturer5);
        lecturers.add(lecturer6);
        lecturers.add(lecturer7);
        lecturers.add(lecturer8);
        lecturers.add(lecturer9);

        admins.add(admin1);
        admins.add(admin2);
        admins.add(admin3);

        projectManagers.add(projectManager1);
        projectManagers.add(projectManager2);
        projectManagers.add(projectManager3);
        projectManagers.add(projectManager4);
        projectManagers.add(projectManager5);
        projectManagers.add(projectManager6);
        projectManagers.add(projectManager7);
        projectManagers.add(projectManager8);
        projectManagers.add(projectManager9);
        projectManagers.add(projectManager10);

        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        projects.add(project5);
        projects.add(project6);
        projects.add(project7);
        projects.add(project8);
        projects.add(project9);
        projects.add(project10);
        projects.add(project11);

        reports.add(report1);
        reports.add(report2);

        requests.add(request1);
        requests.add(request2);
        requests.add(request3);
        requests.add(request4);
        requests.add(request5);
        requests.add(request6);
        requests.add(request7);
        requests.add(request8);
        requests.add(request9);
        requests.add(request10);
        requests.add(request11);
        requests.add(request12);
        requests.add(request13);
        requests.add(request14);
        requests.add(request15);
        requests.add(request16);
        requests.add(request17);
        requests.add(request18);
        requests.add(request19);
        requests.add(request20);

        presentationSlots.add(presentationSlot1);
        presentationSlots.add(presentationSlot2);
        presentationSlots.add(presentationSlot3);
        presentationSlots.add(presentationSlot4);
        presentationSlots.add(presentationSlot5);
        presentationSlots.add(presentationSlot6);
        presentationSlots.add(presentationSlot7);
        presentationSlots.add(presentationSlot8);
        presentationSlots.add(presentationSlot9);
        presentationSlots.add(presentationSlot10);
        presentationSlots.add(presentationSlot11);
        presentationSlots.add(presentationSlot12);
        presentationSlots.add(presentationSlot13);
        presentationSlots.add(presentationSlot14);
        presentationSlots.add(presentationSlot15);
        presentationSlots.add(presentationSlot16);
        presentationSlots.add(presentationSlot17);
        presentationSlots.add(presentationSlot18);
        presentationSlots.add(presentationSlot19);
        presentationSlots.add(presentationSlot20);
        presentationSlots.add(presentationSlot21);
        
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

        Project project1 = new Project(7000, "Computer Science", AssessmentType.CP1, "Programming 101", 100);
        Project project2 = new Project(7001, "Business", AssessmentType.CP2, "Financial Management 101", 100);
        Project project3 = new Project(7002, "Physics", AssessmentType.CP1, "Quantum Mechanics", 100);
        Project project4 = new Project(7003, "Biology", AssessmentType.CP2, "Evolutionary Biology", 100);
        Project project5 = new Project(7004, "Chemistry", AssessmentType.CP1, "Organic Chemistry", 100);
        Project project6 = new Project(7005, "Maths", AssessmentType.CP2, "Calculus", 100);
        Project project7 = new Project(7006, "History", AssessmentType.CP1, "Ancient Civilisations", 100);
        Project project8 = new Project(7007, "English", AssessmentType.CP2, "Litrature Analysis", 100);
        Project project9 = new Project(7008, "Art", AssessmentType.CP1, "Art Appreciation", 100);
        Project project10 = new Project(7009, "Music", AssessmentType.CP2, "Music Composition", 100);
        Report report1 = new Report(8000, project1.getId(), 4002, true, LocalDateTime.of(2024, 5, 27, 10, 30), "https://sample.moodle.com/sample-link/1", 0, 100);
        Report report2 = new Report(8001, project2.getId(), 4002, true, LocalDateTime.of(2024, 5, 30, 13, 0), "https://sample.moodle.com/sample-link/2", 30, 100);
        ProjectManager projectManager1 = new ProjectManager(2005, "JoshuaPM", "11/01/1980", "joshuaPM@lecturer.com", "josh_lecturerPM", "verySecurePasswordMate", Role.SUPERVISOR, new ArrayList<Integer>(Arrays.asList(4001, 4002)));
        ProjectManager projectManager2 = new ProjectManager(2006, "AmardeepPM", "11/01/1980", "amardeepPM@lecturer.com", "pm", "pm", Role.SECONDMARKER, new ArrayList<Integer>(Arrays.asList(4003)));
        ProjectManager projectManager3 = new ProjectManager(2008, "Sophia Johnson", "25/06/1970", "sophia@email.com", "sophia_j", "ProjectMan321", Role.SUPERVISOR, new ArrayList<Integer>(Arrays.asList(4004)));
        ProjectManager projectManager4 = new ProjectManager(2007, "Michael Wilson", "17/04/1972", "michael@email.com", "michael_w", "Wilson123", Role.SECONDMARKER, new ArrayList<Integer>());
        ProjectManager projectManager5 = new ProjectManager(2014, "David Davis", "15/07/1988", "david@projectmanager.com", "davidPM", "ProjectManager123", Role.SUPERVISOR, new ArrayList<Integer>());
        ProjectManager projectManager6 = new ProjectManager(2015, "Emily Evans", "12/09/1992", "emily@projectmanager.com", "emilyPM", "ProjectManager123", Role.SUPERVISOR, new ArrayList<Integer>());
        ProjectManager projectManager7 = new ProjectManager(2016, "Frank Fischer", "18/01/1985", "frank@projectmanager.com", "frankPM", "ProjectManager123", Role.SUPERVISOR, new ArrayList<Integer>());
        ProjectManager projectManager8 = new ProjectManager(2017, "Alice Adams", "12/03/1982", "alice@projectmanager.com", "alicePM", "ProjectManager123", Role.SUPERVISOR, new ArrayList<Integer>());
        ProjectManager projectManager9 = new ProjectManager(2018, "Bob Brown", "01/04/1990", "bob@projectmanager.com", "bobPM", "ProjectManager123", Role.SUPERVISOR, new ArrayList<Integer>());
        ProjectManager projectManager10 = new ProjectManager(2019, "Charlie Clarke", "25/05/1995", "charlie@projectmanager.com", "charliePM", "ProjectManager123", Role.SUPERVISOR, new ArrayList<Integer>());
        Lecturer lecturer1 = new Lecturer(2001, "Joshua Samuel", "01/01/1980", "joshua.lecturer@gmail.com", "lecturer42", "securepassword", Role.NONE);
        Lecturer lecturer2 = new Lecturer(2002, "Mohamed Wafiq", "03/04/1980", "wafiq@gmail.com", "wafiq_lecturer", "password123", Role.NONE);
        Lecturer lecturer3 = new Lecturer(2003, "Sophie Williams", "07/03/1975", "sophie.williams@gmail.com", "sophie_lecturer", "password1", Role.NONE);
        Lecturer lecturer4 = new Lecturer(2004, "Muhammad Huzaifah Bin Ismail", "20/09/1980", "huzaifah@gmail.com", "huzaifah_lecturer", "pass123", Role.NONE);
        Lecturer lecturer5 = new Lecturer(2009, "David Brown", "09/09/1978", "david.brown@gmail.com", "dbrown_lecturer", "secure123", Role.NONE);
        Lecturer lecturer6 = new Lecturer(2010, "Andrew Cote", "01/01/1980", "andrew.cote@gmail.com", "acote_lecturer", "password123", Role.NONE);
        Lecturer lecturer7 = new Lecturer(2011, "Aarav Lee", "03/04/1980", "aarav.lee@gmail.com", "alee_lecturer", "pass123", Role.NONE);
        Lecturer lecturer8 = new Lecturer(2012, "Jameson Cox", "07/03/1980", "jameson.cox@gmail.com", "jcox_lecturer", "secure123", Role.NONE);
        Lecturer lecturer9 = new Lecturer(2013, "Kayden Small", "09/09/1980", "kayden.small@gmail.com", "ksmall_lecturer", "pass123", Role.NONE);
        Student student1 = new Student(4001, "John Doe", "10/02/2024", "johndoe@email.com", "johnUser", "whereIsMyPassword", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList("Computer Science")));
        Student student2 = new Student(4002, "John Kumar", "09/03/2024", "johnkumar@email.com", "std", "std", new ArrayList<>(Arrays.asList(8000, 8001)), new ArrayList<>(), new ArrayList<>(Arrays.asList(7000, 7001)), new ArrayList<>(Arrays.asList("Computer Science")));
        Student student3 = new Student(4003, "Emma Smith", "05/08/2023", "emma@email.com", "emma_smith", "P@ssw0rd", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student4 = new Student(4004, "Michael Johnson", "12/11/2023", "michael@email.com", "michael_j", "secure123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student5 = new Student(4005, "Michael Myers", "12/11/1994", "michaelmyers@email.com", "michaelmee_j", "secukek23", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student6 = new Student(4006, "Jessica Martin", "15/01/2001", "jessica@email.com", "jessica_m", "secure123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student7 = new Student(4007, "Alexander Thompson", "01/05/2001", "alexander@email.com", "alex_t", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student8 = new Student(4008, "Olivia Brown", "05/01/2001", "olivia@email.com", "olivia_b", "secure123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student9 = new Student(4009, "James Wilson", "01/08/2001", "james@email.com", "james_w", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student10 = new Student(4010, "Isabella Davis", "01/01/2002", "isabella@email.com", "isabella_d", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student11 = new Student(4011, "Daniel Johnson", "01/05/2002", "daniel@email.com", "daniel_j", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student12 = new Student(4012, "Sophia Thompson", "01/01/2003", "sophia@email.com", "sophia_t", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student13 = new Student(4013, "Mason Davis", "01/05/2003", "mason@email.com", "mason_d", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student14 = new Student(4014, "Ava Brown", "01/01/2004", "ava@email.com", "ava_b", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student15 = new Student(4015, "Logan Wilson", "01/05/2004", "logan@email.com", "logan_w", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student16 = new Student(4016, "Benjamin Davis", "01/01/2005", "benjamin@email.com", "benjamin_d", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student17 = new Student(4017, "Liam Johnson", "01/05/2005", "liam@email.com", "liam_j", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student18 = new Student(4018, "Ethan Thompson", "01/01/2006", "ethan@email.com", "ethan_t", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student19 = new Student(4019, "Michael Davis", "01/05/2006", "michael@email.com", "michael_d", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student20 = new Student(4020, "Julia Brown", "01/01/2007", "julia@email.com", "julia_b", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student21 = new Student(4021, "Ethan Wilson", "01/05/2007", "ethan@email.com", "ethan_w", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student22 = new Student(4022, "Sarah Davis", "01/01/2008", "sarah@email.com", "sarah_d", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student23 = new Student(4023, "Daniel Johnson", "01/05/2008", "daniel@email.com", "daniel_j", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student24 = new Student(4024, "Mia Thompson", "01/01/2009", "mia@email.com", "mia_t", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student25 = new Student(4025, "Ethan Davis", "01/05/2009", "ethan@email.com", "ethan_d", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student26 = new Student(4026, "Isabella Brown", "01/01/2010", "isabella@email.com", "isabella_b", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student27 = new Student(4027, "Oliver Wilson", "01/05/2010", "oliver@email.com", "oliver_w", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student28 = new Student(4028, "Charlotte Davis", "01/01/2011", "charlotte@email.com", "charlotte_d", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student29 = new Student(4029, "Jack Johnson", "01/05/2011", "jack@email.com", "jack_j", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student30 = new Student(4030, "Ava Thompson", "01/01/2012", "ava@email.com", "ava_t", "pass123", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Admin admin1 = new Admin(1001, "Jay", "20/12/1999", "jay@admin.com", "admin", "admin");
        Admin admin2 = new Admin(1002, "JayZee", "20/10/1999", "jayzee@admin.com", "systemkek", "huuuhe123");
        Admin admin3 = new Admin(1003, "Emily Davis", "15/03/1990", "emily@email.com", "emily_d", "admin321");
        PresentationSlot presentationSlot1 = new PresentationSlot(5000, 4001, LocalDateTime.of(2024, 5, 30, 10, 30), false);
        PresentationSlot presentationSlot2 = new PresentationSlot(5001, 4002, LocalDateTime.of(2024, 6, 13, 12, 30), false);
        PresentationSlot presentationSlot3 = new PresentationSlot(5002, 2001, LocalDateTime.of(2024, 5, 27, 15, 30));
        PresentationSlot presentationSlot4 = new PresentationSlot(5003, 2002, LocalDateTime.of(2024, 6, 1, 8, 0));
        PresentationSlot presentationSlot5 = new PresentationSlot(5004, 2002, LocalDateTime.of(2024, 5, 1, 8, 0));
        PresentationSlot presentationSlot6 = new PresentationSlot(5005, 2003, LocalDateTime.of(2024, 6, 5, 9, 30));
        PresentationSlot presentationSlot7 = new PresentationSlot(5006, 2001, LocalDateTime.of(2024, 6, 6, 10, 0));
        PresentationSlot presentationSlot8 = new PresentationSlot(5007, 2002, LocalDateTime.of(2024, 6, 7, 11, 30));
        PresentationSlot presentationSlot9 = new PresentationSlot(5008, 2003, LocalDateTime.of(2024, 6, 8, 16, 0));
        PresentationSlot presentationSlot10 = new PresentationSlot(5009, 2001, LocalDateTime.of(2024, 6, 9, 12, 0));
        PresentationSlot presentationSlot11 = new PresentationSlot(5010, 2002, LocalDateTime.of(2024, 6, 10, 13, 30));
        PresentationSlot presentationSlot12 = new PresentationSlot(5011, 2003, LocalDateTime.of(2024, 6, 11, 14, 0));
        PresentationSlot presentationSlot13 = new PresentationSlot(5012, 2001, LocalDateTime.of(2024, 6, 12, 15, 30));
        PresentationSlot presentationSlot14 = new PresentationSlot(5013, 2002, LocalDateTime.of(2024, 6, 13, 16, 0));
        PresentationSlot presentationSlot15 = new PresentationSlot(5014, 2003, LocalDateTime.of(2024, 6, 14, 17, 0));
        PresentationSlot presentationSlot16 = new PresentationSlot(5015, 2001, LocalDateTime.of(2024, 6, 15, 18, 30));
        PresentationSlot presentationSlot17 = new PresentationSlot(5016, 2002, LocalDateTime.of(2024, 6, 16, 19, 0));
        PresentationSlot presentationSlot18 = new PresentationSlot(5017, 2003, LocalDateTime.of(2024, 6, 17, 20, 0));
        PresentationSlot presentationSlot19 = new PresentationSlot(5018, 2001, LocalDateTime.of(2024, 6, 18, 21, 30));
        PresentationSlot presentationSlot20 = new PresentationSlot(5019, 2002, LocalDateTime.of(2024, 6, 19, 22, 0));
        PresentationSlot presentationSlot21 = new PresentationSlot(5020, 2003, LocalDateTime.of(2024, 6, 20, 23, 0));
        Request request1 = new Request(6000, 2003, 4001, 5001, RequestType.PRESENTATION, "Computer Science");
        Request request2 = new Request(6001, 2004, 4002, 5002, RequestType.PRESENTATION, "Computer Science");
        Request request3 = new Request(6002, 2001, 4003, RequestType.SUPERVISOR);
        Request request4 = new Request(6003, 2002, 4002, RequestType.SECONDMARKER);
        Request request5 = new Request(6004, 2003, 4001, RequestType.SECONDMARKER);
        Request request6 = new Request(6005, 2004, 4004, RequestType.SECONDMARKER);
        Request request7 = new Request(6006, 2002, 4001, 5001, RequestType.PRESENTATION, "Computer Science");
        Request request8 = new Request(6007, 2001, 4002, 5002, RequestType.PRESENTATION, "Object Oriented Programming");
        Request request9 = new Request(6008, 2004, 4002, 5003, RequestType.PRESENTATION, "Data Analysis");
        Request request10 = new Request(6009, 2003, 4002, 5004, RequestType.PRESENTATION, "Socket Programming");
        Request request11 = new Request(6010, 2001, 4003, RequestType.SECONDMARKER);
        Request request12 = new Request(6011, 2002, 4004, RequestType.SECONDMARKER);
        Request request13 = new Request(6012, 2003, 4001, 5005, RequestType.PRESENTATION, "Computer Science");
        Request request14 = new Request(6013, 2006, 4002, 5006, RequestType.PRESENTATION, "Object Oriented Programming");
        Request request15 = new Request(6014, 2002, 4003, 5007, RequestType.PRESENTATION, "Cybersecurity");
        Request request16 = new Request(6015, 2002, 4004, 5008, RequestType.PRESENTATION, "Data Analysis");
        Request request17 = new Request(6016, 2009, 4001, 5009, RequestType.PRESENTATION, "Socket Programming");
        Request request18 = new Request(6017, 2005, 4002, 5010, RequestType.PRESENTATION, "Databases");
        Request request19 = new Request(6018, 2007, 4002, RequestType.SUPERVISOR);
        Request request20 = new Request(6019, 2008, 4003, RequestType.SECONDMARKER);

        student5.setSupervisorId(2005);
        student2.setSupervisorId(2005);

        context.addStudent(student1);
        context.addStudent(student2);
        context.addStudent(student3);
        context.addStudent(student4);
        context.addStudent(student5);

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
        context.addRequest(request5);
        context.addRequest(request6);
        context.addRequest(request7);
        context.addRequest(request8);
        context.addRequest(request9);
        context.addRequest(request10);

        context.addPresentationSlot(presentationSlot1);
        context.addPresentationSlot(presentationSlot2);
        context.addPresentationSlot(presentationSlot3);
        context.addPresentationSlot(presentationSlot4);
        context.addPresentationSlot(presentationSlot5);
        
        context.writeAllDataAsync();
    }
}
