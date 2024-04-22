package com.ags.pms.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.ags.pms.Helper;
import com.ags.pms.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHandler {

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

    public void writeData(String filename, String contents) {
        try {
            FileWriter writer = new FileWriter(path + filename);
            writer.write(contents);
            writer.close();
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }
    }

    public String readData(String filename) {
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

    @SuppressWarnings("hiding")
    public <Jsonable> void writeJson(ArrayList<Jsonable> objTs) {
        if (objTs.isEmpty()) {
            throw new NullPointerException("writeJson() objTs is null.");
        }

        try { 
            ObjectMapper mapper = new ObjectMapper();
            Jsonable firstObj = objTs.get(0); 
            String className = firstObj.getClass().getSimpleName();
            String jsonData;
            
            switch (className) {
                case "Student":
                    @SuppressWarnings("unchecked") 
                    ArrayList<Student> students = new ArrayList<>((ArrayList<Student>) objTs);
                    jsonData = mapper.writeValueAsString(students);
                    writeData(Helper.getFilenameByClassName(className), jsonData);
                    break;
                case "Lecturer":
                    @SuppressWarnings("unchecked") 
                    ArrayList<Lecturer> lecturers = new ArrayList<>((ArrayList<Lecturer>) objTs);
                    jsonData = mapper.writeValueAsString(lecturers);
                    writeData(Helper.getFilenameByClassName(className), jsonData);
                    break;
                case "ProjectManager":
                    @SuppressWarnings("unchecked") 
                    ArrayList<ProjectManager> projectManagers = new ArrayList<>((ArrayList<ProjectManager>) objTs);
                    jsonData = mapper.writeValueAsString(projectManagers);
                    writeData(Helper.getFilenameByClassName(className), jsonData);
                    break;
                case "Admin":
                    @SuppressWarnings("unchecked") 
                    ArrayList<Admin> admins = new ArrayList<>((ArrayList<Admin>) objTs);
                    jsonData = mapper.writeValueAsString(admins);
                    writeData(Helper.getFilenameByClassName(className), jsonData);
                    break;
            }
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }

    }
}
