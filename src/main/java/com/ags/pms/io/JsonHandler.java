package com.ags.pms.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

import com.ags.pms.Helper;
import com.ags.pms.data.IDHandler;
import com.ags.pms.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHandler {

    private String absolutePath = new File("").getAbsolutePath();
    private String path = "\\src\\main\\java\\resources\\db\\";

    public void initFile(String filename) {
        try {
            File file = new File(getPath(filename));
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

            FileWriter writer = new FileWriter(getPath(filename));
            writer.write(contents);
            writer.close();
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }
    }

    public String readData(String filename) {
        String output = "";
        try {
            FileReader file = new FileReader(getPath(filename));
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

    public void updateIds(IDHandler idHandler) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonData = mapper.writeValueAsString(idHandler);
            writeData(Helper.getFilenameByEnum(FileName.IDHANDLER), jsonData);
        } catch (JsonProcessingException e) {
            Helper.printErr(Helper.getStackTraceString(e));
        }
    }

    public IDHandler getIds() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = readData(Helper.getFilenameByEnum(FileName.IDHANDLER));
            return mapper.readValue(json, IDHandler.class);
        } catch (JsonProcessingException e) {
            Helper.printErr(Helper.getStackTraceString(e));
        }
        throw new NullPointerException("No IDs fetched (JsonHandler.getIds())");
    }

    public <T> void writeJson(ArrayList<T> objTs) {
        if (objTs.isEmpty()) {
            throw new NullPointerException("writeJson() objTs is null.");
        }

        try { 
            ObjectMapper mapper = new ObjectMapper();
            T firstObj = objTs.get(0); 
            String className = firstObj.getClass().getSimpleName();
            String jsonData;
            objTs.forEach(obj -> {
                if (obj instanceof User) {
                    ((User) obj).encryptPassword();
                }
            });
            
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
                case "Report":
                    @SuppressWarnings("unchecked") 
                    ArrayList<Report> reports = new ArrayList<>((ArrayList<Report>) objTs);
                    jsonData = mapper.writeValueAsString(reports);
                    writeData(Helper.getFilenameByClassName(className), jsonData);
                    break;
                case "IDHandler":
                    @SuppressWarnings("unchecked") 
                    ArrayList<IDHandler> idHandlers = new ArrayList<>((ArrayList<IDHandler>) objTs);
                    jsonData = mapper.writeValueAsString(idHandlers);
                    writeData(Helper.getFilenameByClassName(className), jsonData);
                    break;
                case "PresentationSlot":
                    @SuppressWarnings("unchecked") 
                    ArrayList<PresentationSlot> presentationSlots = new ArrayList<>((ArrayList<PresentationSlot>) objTs);
                    jsonData = mapper.writeValueAsString(presentationSlots);
                    writeData(Helper.getFilenameByEnum(FileName.PRESENTATIONSLOTS), jsonData);
                    break;
                case "Request":
                    @SuppressWarnings("unchecked") 
                    ArrayList<Request> requests = new ArrayList<>((ArrayList<Request>) objTs);
                    jsonData = mapper.writeValueAsString(requests);
                    writeData(Helper.getFilenameByEnum(FileName.REQUESTS), jsonData);
                    break;
                    break;
                case "Project":
                    @SuppressWarnings("unchecked") 
                    ArrayList<Project> projects = new ArrayList<>((ArrayList<Project>) objTs);
                    jsonData = mapper.writeValueAsString(projects);
                    writeData(Helper.getFilenameByEnum(FileName.PROJECTS), jsonData);
                    break;
            }
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }
    }

    public <T> ArrayList<T> readJson(FileName filename) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<T> jsonableList = new ArrayList<>();
        String json = readData(Helper.getFilenameByEnum(filename));
        try { 
            JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, Helper.getClassTypeByFilename(filename));
            jsonableList = mapper.readValue(json, type);
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }

        jsonableList.forEach(obj -> {
            if (obj instanceof User) {
                ((User) obj).decryptPassword();
            }
        });

        return jsonableList;
    }

    public <T> ArrayList<T> readJson(String className) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<T> jsonableList = new ArrayList<>();
        String json = readData(Helper.getFilenameByClassName(className));
        try { 
            JavaType type = mapper.getTypeFactory().constructParametricType(ArrayList.class, Helper.getClassTypeByClassName(className));
            jsonableList = mapper.readValue(json, type);
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }

        jsonableList.forEach(obj -> {
            if (obj instanceof User) {
                ((User) obj).decryptPassword();
            }
        });

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

    public <T> CompletableFuture<Void> writeJsonAsync(ArrayList<T> objTs) {
        return CompletableFuture.runAsync(() -> {
            writeJson(objTs);
        });
    }

    public <T> CompletableFuture<ArrayList<T>> readJsonAsync(FileName filename) {
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

            jsonableList.forEach(obj -> {
                if (obj instanceof User) {
                    ((User) obj).decryptPassword();
                }
            });

            return jsonableList;
        });
    }

    public <T> CompletableFuture<ArrayList<T>> readJsonAsync(String className) {
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

            jsonableList.forEach(obj -> {
                if (obj instanceof User) {
                    ((User) obj).decryptPassword();
                }
            });
            
            return jsonableList;
        });
    }

    private String getPath(String filename) {
        return this.absolutePath + this.path + filename;
    }




}
