package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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
                s.setReports(edittedStudent.getReports());
                s.setPresentationSlots(edittedStudent.getPresentationSlots());
                s.setModules(edittedStudent.getModules());
                s.setSupervisor(edittedStudent.getSupervisor());
                s.setSecondMarker(edittedStudent.getSecondMarker());
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

    public void allotProjectManager(int lecturerId, RequestType requestType) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        DataContext context = new DataContext();
        Lecturer lecturer = context.getLecturerByID(lecturerId);

        if (lecturer == null) {
            Helper.printErr("Lecturer not found for ID: " + lecturerId);
            return;
        }
        
        lecturer.setProjectManager(true);
        
        ProjectManager projectManager = new ProjectManager(lecturer.getId(), 
            lecturer.getName(), lecturer.getDob(), lecturer.getEmail(), lecturer.getUsername(), lecturer.getPassword(),
            Helper.getRoleFromRequestType(requestType), new ArrayList<Student>());
        
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
}
