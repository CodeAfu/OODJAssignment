package com.ags.pms.models;

public class Student extends User {

    private Project[] projects;
    private AssessmentType assessmentType;

    // Debug
    public Student() {
        super();
        projects = new Project[10];
    }

    // Probably delete
    public Student(int numOfProjects) {
        super();
        projects = new Project[numOfProjects];
    }
    
    public Student(int id, String name, String dob, String email, String username, String password, int numOfProjects) {
        super(id, name, dob, email, username, password);
        projects = new Project[numOfProjects];
    }
    
    public Student(int id, String name, String dob, String email, String username, String password) {
        super(id, name, dob, email, username, password);
        projects = new Project[10];
    }

    public Project[] getProjects() {
        return projects;
    }

    public void setProjects(Project[] projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        projects[projects.length - 1] = project;
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
}
