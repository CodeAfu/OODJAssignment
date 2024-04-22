package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ags.pms.io.Jsonable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Student extends User {

    private ArrayList<Project> projects;
    private AssessmentType assessmentType;

    // Debug
    public Student() {
        super();
        projects = new ArrayList<Project>();
    }
    
    public Student(int id, String name, String dob, String email, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        projects = new ArrayList<Project>();
    }
    
    public Student(int id, String name, String dob, String email, String username, String password, ArrayList<Project> projects, AssessmentType assessmentType) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        this.projects = projects;
        this.assessmentType = assessmentType;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public void submitProject() {
        
    }

    public void submitProject(Project project) {
        
    }

    public void editStudent(Student student) {
        
    }
    
    public AssessmentType getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }

    @Override
    public Jsonable jsonToObject(String json) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Student> typeReference = new TypeReference<Student>() { };
        return mapper.readValue(json, typeReference);
    }

    @Override
    public void objectToJson(Jsonable obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'objectToJson'");
    }
}
