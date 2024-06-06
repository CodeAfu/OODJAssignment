package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.crypto.Data;

import com.ags.pms.Helper;
import com.ags.pms.data.DataContext;

public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(int id, String name, String dob, String email, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
    }

    public Admin(String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(username, password);
    }


    public ArrayList<User> fetchAllUsers() {
        DataContext context = new DataContext();
        ArrayList<User> users = new ArrayList<>();

        context.getStudents().forEach(s -> users.add(s));
        context.getProjectManagers().forEach(pm -> users.add(pm));
        context.getLecturers().forEach(l -> users.add(l));

        Collections.sort(users, Comparator.comparingInt(User::getId));

        return users;
    }

    public void registerStudent(int id, String name, String dob, String email, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        DataContext context = new DataContext();
        Student student = new Student(id, name, dob, email, username, password);
        context.addStudent(student);
        context.writeAllDataAsync();
    }

    public void registerStudent(Student student) {
        DataContext context = new DataContext();
        context.addStudent(student);
        context.writeAllDataAsync();
    }
    
    public void registerLecturer(Lecturer lecturer) {
        DataContext context = new DataContext();
        context.addLecturer(lecturer);
        context.writeAllDataAsync();        
    }

    public void editLecturer(int id, Lecturer edittedLecturer) {
        DataContext context = new DataContext();
        ArrayList<Lecturer> lecturers = context.getLecturers();

        for (Lecturer l : lecturers) {
            if (l.getId() == id) {
                l.setName(edittedLecturer.getName());
                l.setDob(edittedLecturer.getDob());
                l.setEmail(edittedLecturer.getDob());
                l.setUsername(edittedLecturer.getUsername());
                l.setPassword(edittedLecturer.getPassword());
                l.setProjectManager(edittedLecturer.isProjectManager());
                break;
            }
        }
        context.writeAllDataAsync();
    }

    public void editStudent(int id, Student edittedStudent) {
        DataContext context = new DataContext();
        ArrayList<Student> students = context.getStudents();

        for (Student s : students) {
            if (s.getId() == id) {
                s.setName(edittedStudent.getName());
                s.setDob(edittedStudent.getDob());
                s.setEmail(edittedStudent.getDob());
                s.setUsername(edittedStudent.getUsername());
                s.setPassword(edittedStudent.getPassword());
                s.setReportIds(edittedStudent.getReportIds());
                s.setPresentationSlotIds(edittedStudent.getPresentationSlotIds());
                s.setModules(edittedStudent.getModules());
                s.setSupervisorId(edittedStudent.getSupervisorId());
                s.setSecondMarkerId(edittedStudent.getSecondMarkerId());
                break;
            }
        }
        context.writeAllDataAsync();
    }
    
    public void deleteLecturer(int id) {
        DataContext context = new DataContext();
        ArrayList<Lecturer> lecturers = context.getLecturers();
        
        for (Iterator<Lecturer> iterator = lecturers.iterator(); iterator.hasNext();) {
            Lecturer lecturer = iterator.next();
            if (lecturer.getId() == id) {
                iterator.remove();
                break;
            }
        }
        context.writeAllDataAsync();
    }

    public void deleteStudent(int id) {
        DataContext context = new DataContext();
        ArrayList<Student> students = context.getStudents();
        
        for (Iterator<Student> iterator = students.iterator(); iterator.hasNext();) {
            Student student = iterator.next();
            if (student.getId() == id) {
                iterator.remove();
                break;
            }
        }
        context.writeAllDataAsync();
    }

    public void promoteLecturer(int lecturerId, RequestType requestType) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        DataContext context = new DataContext();
        Lecturer lecturer = context.getLecturerByID(lecturerId);

        if (lecturer == null) {
            Helper.printErr("Lecturer not found for ID: " + lecturerId);
            return;
        }
        
        lecturer.setProjectManager(true);
        
        ProjectManager projectManager = new ProjectManager(lecturer.getId(), 
            lecturer.getName(), lecturer.getDob(), lecturer.getEmail(), lecturer.getUsername(), lecturer.getPassword(),
            Helper.getRoleFromRequestType(requestType), new ArrayList<Integer>());
        
        context.removeById(lecturer.getId());
        context.addProjectManager(projectManager);
        
        context.writeAllDataAsync();
    }   
    
    public void removeProjectManager(int projectManagerId) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        DataContext context = new DataContext();
        ProjectManager projectManager = context.getProjectManagerByID(projectManagerId);
        
        if (projectManager == null) {
            Helper.printErr("ProjectManager not found for ID: " + projectManagerId);
            return;
        }
        
        projectManager.setProjectManager(false);
        
        Lecturer lecturer = new Lecturer(projectManager.getId(),
            projectManager.getName(), projectManager.getDob(), projectManager.getEmail(), 
            projectManager.getUsername(), projectManager.getPassword(), Role.NONE);
            
        context.removeById(projectManager.getId());
        context.addLecturer(lecturer);
        
        context.writeAllDataAsync();
    }

    public void demoteProjectManager(int pmId) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        DataContext context = new DataContext();
        ProjectManager pm = context.getProjectManagerByID(pmId);
        
        if (pm == null) {
            Helper.printErr("Lecturer not found for ID: " + pmId);
            return;
        }
        
        pm.setProjectManager(false);
        
        Lecturer lecturer = new Lecturer(pm.getId(),
            pm.getName(), pm.getDob(), pm.getEmail(), 
            pm.getUsername(), pm.getPassword(), Role.NONE);
            
        context.removeById(pm.getId());
        context.addLecturer(lecturer);
        
        context.writeAllDataAsync();
    }

    public void registerUser(String name, String dob, String email, String username, String password, String role) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        DataContext context = new DataContext();

        if (role.equals("Student")) {
            Student student = new Student(context.fetchNextStudentId(), name, dob, email, username, password);
            context.addStudent(student);
        }
        else if (role.equals("Lecturer")) {
            Lecturer lecturer = new Lecturer(context.fetchNextLecturerId(), name, dob, email, username, password, Role.NONE);
            context.addLecturer(lecturer);
        }
        else if (role.equals("Project Manager")) {
            ProjectManager pm = new ProjectManager(context.fetchNextLecturerId(), name, dob, email, username, password, Role.NONE, new ArrayList<>());
            context.addProjectManager(pm);
        }
        
        context.writeAllDataAsync();
    }

    public User fetchUser(int userId) {
        DataContext context = new DataContext();
        return context.getById(userId);
    }

    public void updateUser(int id, String name, String dob, String email, String username, String password) {
        DataContext context = new DataContext();
        context.populateUserCollection();

        context.updateUserById(id, u -> {
            u.setName(name);
            u.setDob(dob);
            u.setEmail(email);
            u.setUsername(username);
            u.setPassword(password);
        });

        context.writeAllDataAsync();
    }

    public void deleteUser(int userId) {
        DataContext context = new DataContext();
        context.removeById(userId);
        context.writeAllDataAsync();
    }
}
