package com.ags.pms.models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ags.pms.io.Jsonable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Lecturer extends User {
    
    private boolean isProjectManager;

    public Lecturer() {
        super();
        isProjectManager = false;    
    }
    
    public Lecturer(int id, String name, String dob, String email, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(id, name, dob, email, username, password);
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

    @Override
    public Jsonable jsonToObject(String json) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Lecturer.class);
    }

    @Override
    public void objectToJson(Jsonable obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'objectToJson'");
    }
}
