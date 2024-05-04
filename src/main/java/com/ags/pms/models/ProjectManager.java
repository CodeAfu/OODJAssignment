package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.datatype.DatatypeConfigurationException;

import com.ags.pms.data.DataContext;

public class ProjectManager extends Lecturer {

    private Role role;
    
    public ProjectManager() {
        super();
    }
    
    public ProjectManager(int id, String name, String dob, String email, Role role, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        this.isProjectManager = true;
        this.role = role;
    }

    public ProjectManager(String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(username, password);
    }
    
    public void createProject() {
        DataContext context = new DataContext();
        Report report = new Report();

        context.addReport(report);
        context.saveReportsAsync();
    }
    
    public void assignProject(String moduleName, Report report) {
        DataContext context = new DataContext();
        ArrayList<Student> students = context.getStudents();

        students.forEach(student -> {
            if (student.getModules().contains(moduleName)) {
                student.addReport(report);
            }
        });

        context.saveStudentsAsync();
    }
    
}
