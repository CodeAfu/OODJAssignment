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

public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        super(username, password);
    }

    // Requires overloads for other Student Constructors
    public void registerStudent(int id, String name, String dob, String email, String username, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Student student = new Student(id, name, dob, email, username, password);
    
        // Save student to the database
    }

    public void registerStudent(Student student) {
        
    }
    
    @Override
    public Jsonable jsonToObject(String json) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Admin.class);
    }

    @Override
    public void objectToJson(Jsonable obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'objectToJson'");
    }
    
}
