package com.ags.pms.models;

public class ProjectManager extends Lecturer {

    private Role role;
    
    public ProjectManager(int id, String name, String dob, String email, Role role) {
        super(id, name, dob, email, role);
        this.role = role;
    }
    
    public void createProject() {
        
    }
    
    public void assignProject() {
        
    }
    
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
