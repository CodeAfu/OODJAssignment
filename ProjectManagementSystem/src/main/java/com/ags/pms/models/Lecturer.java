package com.ags.pms.models;

public class Lecturer extends User {
    
    private boolean isProjectManager;

    public Lecturer() {
        super();
        isProjectManager = false;    
    }
    
    public Lecturer(int id, String name, String dob, String email) {
        super(id, name, dob, email);
        isProjectManager = false;
    }
    
    public boolean isProjectManager() {
        return isProjectManager;
    }

    public void setProjectManager(boolean isProjectManager) {
        this.isProjectManager = isProjectManager;
    }

    public void toggleProjectManager(boolean isProjectManager) {

        if (isProjectManager) {
            isProjectManager = false;
        } else {
            isProjectManager = true;
        }

        System.out.println("Project Manager: " + isProjectManager);
    }

    public void viewSupervisees() {

    }

    public void viewPresentationRequests() {

    }

    public void viewAvailableSlots() {

    }

    public void viewSecondMarkerAcceptance() {

    }

    public void setPresentationSlot() {

    }

    public void viewReport() {

    }



}
