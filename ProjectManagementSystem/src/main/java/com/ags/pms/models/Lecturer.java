package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ags.pms.io.FileName;
import com.ags.pms.io.JsonHandler;

public class Lecturer extends User {
    
    protected boolean isProjectManager;

    public Lecturer() {
        super();
        isProjectManager = false;    
    }
    
    public Lecturer(int id, String name, String dob, String email, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
        isProjectManager = false;
    }

    public Lecturer(String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(username, password);
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
        JsonHandler handler = new JsonHandler();
        ArrayList<Lecturer> lecturers = handler.readJson(FileName.LECTURERS);
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
