package com.ags.pms.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.PasswordAuthentication;
import java.util.Scanner;

import com.ags.pms.Helper;
import com.ags.pms.models.*;
import com.ags.pms.services.PasswordHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHandler implements JsonIO {

    private String path = "ProjectManagementSystem/src/main/java/com/ags/pms/data/";

    public String readFile() {
        return "";
    }

    public void initFile(String filename) {
        try {
            File file = new File(path + filename);
            if (file.createNewFile()) {
                System.out.println("File Created: " + file.getName());   
            } else {
                System.out.println("File already exists");
            }
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }
    }

    public void writeFile(String filename, String contents) {
        try {
            FileWriter writer = new FileWriter(path + filename);
            writer.write(contents);
            writer.close();
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }
    }

    public String readFile(String filename) {
        String output = "";
        try {
            FileReader file = new FileReader(path + filename);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                output += reader.nextLine();
            }
            reader.close();
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }

        return output;
    }

    public void writeJson() {
        try { 
            ObjectMapper mapper = new ObjectMapper();
            PasswordHandler pwHandler = new PasswordHandler();

            // Strings below should not be shared, create config file to hide info
            pwHandler.initFromStrings("9Vs+DfEF1+3tF8fCKLp9BQ==", "JoprQnQRq95s/Nuz");
            String password = pwHandler.encryptPassword("SamplePassword");
            Student student = new Student(1, "John Doe", "10/02/2024", "johndoe@email.com", "user", password);

            String jsonData = mapper.writeValueAsString(student);

            writeFile("Students.txt", jsonData);

        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }

    }

    @Override
    public void read(JsonReadable read) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public void write(JsonWritable write) {

    }
}
