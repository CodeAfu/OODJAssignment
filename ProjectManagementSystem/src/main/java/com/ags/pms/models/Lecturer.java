package com.ags.pms.models;

public class Lecturer extends User {
    private Role role;
    
    public Lecturer(Role role) {
        super();
        this.role = role;
    }
    
    public Lecturer(int id, String name, String dob, String email, Role role) {
        super(id, name, dob, email);
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
