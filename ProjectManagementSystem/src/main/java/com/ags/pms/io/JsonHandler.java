package com.ags.pms.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import com.ags.pms.Helper;
import com.ags.pms.models.*;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonHandler {

    private String path = "ProjectManagementSystem/src/main/java/com/ags/pms/data/";

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

    public <T extends User> void writeJson(ArrayList<T> objTs) {
        if (objTs.isEmpty()) {
            throw new NullPointerException("writeJson() objTs is null.");
        }

        try { 
            ObjectMapper mapper = new ObjectMapper();
            T firstObj = objTs.get(0); 
            String className = firstObj.getClass().getSimpleName();
            String jsonData;
            objTs.forEach(obj -> obj.encryptPassword());
            
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

    public <T extends User> ArrayList<T> readJson(FileName filename) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<T> jsonableList = new ArrayList<>();
        String json = readData(Helper.getFilenameByEnum(filename));
        try { 
            JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, Helper.getClassTypeByFilename(filename));
            jsonableList = mapper.readValue(json, type);
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }
        return jsonableList;
    }

    public <T extends User> ArrayList<T> readJson(String className) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<T> jsonableList = new ArrayList<>();
        String json = readData(Helper.getFilenameByClassName(className));
        try { 
            JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, Helper.getClassTypeByClassName(className));
            jsonableList = mapper.readValue(json, type);
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }
        return jsonableList;
    }

    public CompletableFuture<Void> writeDataAsync(String filename, String contents) {
        return CompletableFuture.runAsync(() -> {
            writeData(filename, contents);
        });
    }

    public CompletableFuture<String> readDataAsync(String filename) {
        return CompletableFuture.supplyAsync(() -> {
            return readData(filename);
        });
    }

    public <T extends User> CompletableFuture<Void> writeJsonAsync(ArrayList<T> objTs) {
        return CompletableFuture.runAsync(() -> {
            writeJson(objTs);
        });
    }

    public <T extends User> CompletableFuture<ArrayList<T>> readJsonAsync(FileName filename) {
        return CompletableFuture.supplyAsync(() -> {
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<T> jsonableList = new ArrayList<>();
            String json = readDataAsync(Helper.getFilenameByEnum(filename)).join(); // Wait for readDataAsync to complete
            
            try {
                JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, Helper.getClassTypeByFilename(filename));
                jsonableList = mapper.readValue(json, type);
            } catch (Exception ex) {
                Helper.printErr(Helper.getStackTraceString(ex));
            }
            return jsonableList;
        });
    }

    public <T extends User> CompletableFuture<ArrayList<T>> readJsonAsync(String className) {
        return CompletableFuture.supplyAsync(() -> {
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<T> jsonableList = new ArrayList<>();
            String json = readDataAsync(Helper.getFilenameByClassName(className)).join(); // Wait for readDataAsync to complete
    
            try {
                JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, Helper.getClassTypeByClassName(className));
                jsonableList = mapper.readValue(json, type);
            } catch (Exception ex) {
                Helper.printErr(Helper.getStackTraceString(ex));
            }
            return jsonableList;
        });
    }
}
