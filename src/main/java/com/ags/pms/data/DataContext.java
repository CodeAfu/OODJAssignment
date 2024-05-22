package com.ags.pms.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.NoSuchElementException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.function.Consumer;


import com.ags.pms.Helper;
import com.ags.pms.io.FileName;
import com.ags.pms.io.JsonHandler;
import com.ags.pms.models.Admin;
import com.ags.pms.models.Identifiable;
import com.ags.pms.models.Lecturer;
import com.ags.pms.models.PresentationSlot;
import com.ags.pms.models.Project;
import com.ags.pms.models.ProjectManager;
import com.ags.pms.models.Report;
import com.ags.pms.models.Request;
import com.ags.pms.models.Student;
import com.ags.pms.models.User;

public class DataContext {

    private JsonHandler jsonHandler;

    private Map<String, List<? extends Identifiable>> collections = new HashMap<>();
    private Map<String, List<? extends User>> userCollections = new HashMap<>();

    private ArrayList<Lecturer> lecturers = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<ProjectManager> projectManagers = new ArrayList<>();
    private ArrayList<Report> reports = new ArrayList<>();
    private ArrayList<PresentationSlot> presentationSlots = new ArrayList<>();
    private ArrayList<Request> requests = new ArrayList<>();
    private ArrayList<Project> projects = new ArrayList<>();

    private IDHandler idHandler;
    
    private CompletableFuture<Void> allFutures;

    public DataContext() {
        jsonHandler = new JsonHandler();
        populateAllDataAsync();
        populateCollection();
        initIds();
    }

    private void populateCollection() {
        collections.put("lecturers", lecturers);
        collections.put("students", students);
        collections.put("admins", admins);
        collections.put("projectManagers", projectManagers);
        collections.put("reports", reports);
        collections.put("presentationSlots", presentationSlots);
        collections.put("requests", requests);
        collections.put("projects", projects);
    }
    
    public void populateUserCollection() {
        userCollections.put("lecturers", lecturers);
        userCollections.put("students", students);
        userCollections.put("admins", admins);
        userCollections.put("projectManagers", projectManagers);
    }

    // Only call on constructor
    private void initIds() {
        IDHandler savedIdHandler = jsonHandler.getIdsFromJson();
        idHandler = new IDHandler(this);

        idHandler.setNextAdminId(Math.max(savedIdHandler.getNextAdminId(), idHandler.getNextAdminId()));
        idHandler.setNextLecturerId(Math.max(savedIdHandler.getNextLecturerId(), idHandler.getNextLecturerId()));
        idHandler.setNextStudentId(Math.max(savedIdHandler.getNextStudentId(), idHandler.getNextStudentId()));
        idHandler.setNextPresentationSlotId(Math.max(savedIdHandler.getNextPresentationSlotId(), idHandler.getNextPresentationSlotId()));
        idHandler.setNextRequestId(Math.max(savedIdHandler.getNextRequestId(), idHandler.getNextRequestId()));
        idHandler.setNextProjectId(Math.max(savedIdHandler.getNextProjectId(), idHandler.getNextProjectId()));
        idHandler.setNextReportId(Math.max(savedIdHandler.getNextReportId(), idHandler.getNextReportId()));
    }

    // Call after all write operations
    private void updateIds() {
        idHandler = new IDHandler(this);
    }

    public int fetchNextStudentId() {
        return idHandler.assignStudentId();
    }

    public int fetchNextAdminId() {
        return idHandler.assignAdminId();
    }

    public int fetchNextLecturerId() {
        return idHandler.assignLecturerId();
    }

    public int fetchNextPresentationSlotId() {
        return idHandler.assignPresentationSlotId();
    }

    public int fetchNextRequestId() {
        return idHandler.assignRequestid();
    }

    public int fetchNextProjectId() {
        return idHandler.assignProjectId();
    }

    public int fetchNextReportId() {
        return idHandler.assignReportId();
    }

    public Student getStudentByID(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public Lecturer getLecturerByID(int id) {
        return lecturers.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public Admin getAdminByID(int id) {
        return admins.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public Report getReportByID(int id) {
        return reports.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
    }

    public ProjectManager getProjectManagerByID(int id) {
        return projectManagers.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public Student getStudent(Expression<Student> expression) {
        return students.stream()
                        .filter(student -> expression.action(student))
                        .findFirst()
                        .orElseThrow(() -> new NoSuchElementException("Student not found"));
    }

    public Lecturer getLecturer(Expression<Lecturer> expression) {
        return lecturers.stream()
                       .filter(lecturer -> expression.action(lecturer))
                       .findFirst()
                       .orElseThrow(() -> new NoSuchElementException("Lecturer not found"));
    }

    public Admin getAdmin(Expression<Admin> expression) {
        return admins.stream()
                       .filter(admin -> expression.action(admin))
                       .findFirst()
                       .orElseThrow(() -> new NoSuchElementException("Admin not found"));
    }

    public ProjectManager getProjectManager(Expression<ProjectManager> expression) {
        return projectManagers.stream()
                       .filter(projectManager -> expression.action(projectManager))
                       .findFirst()
                       .orElseThrow(() -> new NoSuchElementException("ProjectManager not found"));
    }

    public Request getRequest(Expression<Request> expression) {
        return requests.stream()
                       .filter(request -> expression.action(request))
                       .findFirst()
                       .orElse(null);
    }

    public void updateAdminById(int id, Consumer<Admin> updater) {
        Optional<Admin> obj = admins.stream()
                .filter(o -> o.getId() == id)
                .findFirst();
        obj.ifPresent(updater);
    }

    public void updateLecturerById(int id, Consumer<Lecturer> updater) {
        Optional<Lecturer> obj = lecturers.stream()
                .filter(o -> o.getId() == id)
                .findFirst();
        obj.ifPresent(updater);
    }

    public void updateProjectManagerById(int id, Consumer<ProjectManager> updater) {
        Optional<ProjectManager> obj = projectManagers.stream()
                .filter(o -> o.getId() == id)
                .findFirst();
        obj.ifPresent(updater);
    }

    public void updateStudentById(int id, Consumer<Student> updater) {
        Optional<Student> obj = students.stream()
                .filter(o -> o.getId() == id)
                .findFirst();
        obj.ifPresent(updater);
    }

    public void updatePresentationSlotById(int id, Consumer<PresentationSlot> updater) {
        Optional<PresentationSlot> obj = presentationSlots.stream()
                .filter(o -> o.getId() == id)
                .findFirst();
        obj.ifPresent(updater);
    }

    public void updateRequestById(int id, Consumer<Request> updater) {
        Optional<Request> obj = requests.stream()
                .filter(o -> o.getId() == id)
                .findFirst();
        obj.ifPresent(updater);
    }
    
    public void updateProjectById(int id, Consumer<Project> updater) {
        Optional<Project> obj = projects.stream()
                .filter(o -> o.getId() == id)
                .findFirst();
        obj.ifPresent(updater);
    }
    
    public void updateReportById(int id, Consumer<Report> updater) {
        Optional<Report> obj = reports.stream()
                .filter(o -> o.getId() == id)
                .findFirst();
        obj.ifPresent(updater);
    }

    // public <T extends Identifiable> T getById(ArrayList<T> objTs, int id) {
    //     return objTs.stream()
    //                 .filter(o -> o.getId() == id)
    //                 .findFirst()
    //                 .orElseThrow(() -> new NoSuchElementException("Object not found"));
    // }

    @SuppressWarnings("unchecked")
    public <T extends Identifiable> T getById(int id) {
        return collections.values().stream()
                .flatMap(Collection::stream)
                .filter(o -> o.getId() == id)
                .map(obj -> {
                    if (Helper.isIdentifiableInstance(obj.getClass())) {
                        return (T) obj;
                    } else {
                        throw new IllegalArgumentException("Unsupported class type");
                    }
                })
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Object not found: " + id));
    }

    @SuppressWarnings("unchecked")
    public <T extends User> T getValidUser(String username, String password) {
        return userCollections.values().stream()
                .flatMap(Collection::stream)
                .filter(o -> o.getUsername().equals(username) && o.getPassword().equals(password))
                .map(obj -> {
                    if (Helper.isUserInstance(obj.getClass())) {
                        return (T) obj;
                    } else {
                        throw new IllegalArgumentException("Unsupported class type");
                    }
                })
                .findFirst()
                .orElse(null);
    }

    public <T extends Identifiable> CompletableFuture<T> getByIdAsync(int id) {
        return CompletableFuture.supplyAsync(() -> {
            T result = getById(id);
            return result;
        })
        .exceptionallyAsync(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    // public <T extends Identifiable> T getObj(T objToFind) {
    //     return collections.values().stream()
    //             .flatMap(Collection::stream)
    //             .filter(o -> o == objToFind)
    //             .map(obj-> (T) obj)
    //             .findFirst()
    //             .orElseThrow(() -> new NoSuchElementException("Object not found"));
    // }

    // @SuppressWarnings("unchecked")
    // private <T> List<T> getListFromClass(Class<?> classType) {
    //     if (classType == Student.class) {
    //         return (List<T>) students;
    //     } else if (classType == Lecturer.class || classType == ProjectManager.class) {
    //         return (List<T>) lecturers;
    //     } else if (classType == Admin.class) {
    //         return (List<T>) admins;
    //     } else if (classType == Report.class) {
    //         return (List<T>) reports;
    //     } else if (classType == PresentationSlot.class) {
    //         return (List<T>) presentationSlots;
    //     } else if (classType == Request.class) {
    //         return (List<T>) requests;
    //     } else if (classType == Project.class) {
    //         return (List<T>) projects;
    //     } else {
    //         throw new IllegalArgumentException("Unsupported class type");
    //     }
    // }

    public <T extends Identifiable> T removeById(int id) {
        for (List<? extends Identifiable> list : collections.values()) {
            Optional<? extends Identifiable> optional = list.stream()
                    .filter(obj -> obj.getId() == id)
                    .findFirst();

            if (optional.isPresent()) {
                @SuppressWarnings("unchecked")
                T objToRemove = (T) optional.get();
                list.removeIf(obj -> obj.getId() == id);
                return objToRemove;
            }
        }
        Helper.printErr("Null value not found. ID: " + id);
        return null;
    }

    public Map<String, List<? extends User>> getUserCollections() {
        return userCollections;
    }

    public void setLecturers(ArrayList<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void setAdmins(ArrayList<Admin> admins) {
        this.admins = admins;
    }

    public void setProjectManagers(ArrayList<ProjectManager> projectManagers) {
        this.projectManagers = projectManagers;
    }

    public ArrayList<Lecturer> getLecturers() {
        return lecturers;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Admin> getAdmins() {
        return admins;
    }

    public ArrayList<ProjectManager> getProjectManagers() {
        return projectManagers;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }
    public ArrayList<PresentationSlot> getPresentationSlots() {
        return presentationSlots;
    }

    public void setPresentationSlots(ArrayList<PresentationSlot> presentationSlots) {
        this.presentationSlots = presentationSlots;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> presentationRequests) {
        this.requests = presentationRequests;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public IDHandler getIdHandler() {
        return idHandler;
    }

    private <T extends Identifiable> boolean checkDuplicateId(ArrayList<T> objTs, T obj) {
        for (T o : objTs) {
            if (o.getId() == obj.getId()) {
                Helper.printErr("Error: Duplicate object. ID=" + obj.getId());
                return true;
            }
        }
        return false;
    }

    public void addStudent(Student student) {
        if (checkDuplicateId(students, student)) return;
        students.add(student);
        updateIds();
    }

    public void addAdmin(Admin admin) {
        if (checkDuplicateId(admins, admin)) return;
        admins.add(admin);
        updateIds();
    }

    public void addLecturer(Lecturer lecturer) {
        if (checkDuplicateId(lecturers, lecturer)) return;
        lecturers.add(lecturer);
        updateIds();
    }

    public void addProjectManager(ProjectManager projectManager) {
        if (checkDuplicateId(projectManagers, projectManager)) return;
        projectManagers.add(projectManager);
        updateIds();
    }

    public void addPresentationSlot(PresentationSlot presentationSlot) {
        if (checkDuplicateId(presentationSlots, presentationSlot)) return;
        presentationSlots.add(presentationSlot);
        updateIds();
    }

    public void addRequest(Request request) {
        if (checkDuplicateId(requests, request)) return;
        requests.add(request);
        updateIds();
    }

    public void addProject(Project project) {
        if (checkDuplicateId(projects, project)) return;
        projects.add(project);
        updateIds();
    }

    public void addReport(Report report) {
        if (checkDuplicateId(reports, report)) return;
        reports.add(report);
        updateIds();
    }

    private void populateAllDataAsync() {
        allFutures = CompletableFuture.allOf(
            populateStudentsAsync(),
            populateAdminsAsync(),
            populateLecturersAsync(),
            populateProjectManagersAsync(),
            populateReportsAsync(),
            populatePresentationSlotsAsync(),
            populateRequestsAsync(),
            populateProjectsAsync()
        )
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
        allFutures.join();
    }

    public void writeAllDataAsync() {
        this.sortAll();
        allFutures = CompletableFuture.allOf(
            saveAdminsAsync(),
            saveStudentsAsync(),
            saveLecturersAsync(),
            saveProjectManagersAsync(),
            saveReportsAsync(),
            savePresentationSlotsAsync(),
            saveRequestsAsync(),
            saveProjectsAsync()
        ).thenRun(() -> System.out.println("Json Written"))
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
        allFutures.join();

        jsonHandler.updateIds(idHandler);
    }

    public CompletableFuture<Void> saveStudentsAsync() {
        return CompletableFuture.runAsync(() -> {
            sort(students);
            jsonHandler.writeJson(students);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> saveLecturersAsync() {
        return CompletableFuture.runAsync(() -> {
            sort(lecturers);
            jsonHandler.writeJson(lecturers);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });

    }

    public CompletableFuture<Void> saveProjectManagersAsync() {
        return CompletableFuture.runAsync(() -> {
            sort(projectManagers);
            jsonHandler.writeJson(projectManagers);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> saveAdminsAsync() {
        return CompletableFuture.runAsync(() -> {
            sort(admins);
            jsonHandler.writeJson(admins);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> saveReportsAsync() {
        return CompletableFuture.runAsync(() -> {
            sort(reports);
            jsonHandler.writeJson(reports);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> savePresentationSlotsAsync() {
        return CompletableFuture.runAsync(() -> {
            sort(presentationSlots);
            jsonHandler.writeJson(presentationSlots);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> saveRequestsAsync() {
        return CompletableFuture.runAsync(() -> {
            sort(requests);
            jsonHandler.writeJson(requests);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> saveProjectsAsync() {
        return CompletableFuture.runAsync(() -> {
            sort(projects);
            jsonHandler.writeJson(projects);
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> populateProjectManagersAsync() {
        return jsonHandler.readJsonAsync(FileName.PROJECTMANAGERS)
            .thenAccept(projectManagerList -> {
                projectManagers = new ArrayList<>();
                projectManagerList.forEach(pm -> projectManagers.add((ProjectManager) pm));
            })
            .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> populateStudentsAsync() {
        return jsonHandler.readJsonAsync(FileName.STUDENTS)
            .thenAccept(studentList -> {
                students = new ArrayList<>();
                studentList.forEach(s -> students.add((Student) s));
            })
            .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> populateLecturersAsync() {
        return jsonHandler.readJsonAsync(FileName.LECTURERS)
            .thenAccept(lecturerList -> {
                lecturers = new ArrayList<>();
                lecturerList.forEach(l -> lecturers.add((Lecturer) l));
            })
            .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> populateAdminsAsync() {
        return jsonHandler.readJsonAsync(FileName.ADMINS)
            .thenAccept(adminList -> {
                admins = new ArrayList<>();
                adminList.forEach(a -> admins.add((Admin) a));
            })
            .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> populateReportsAsync() {
        return jsonHandler.readJsonAsync(FileName.REPORTS)
            .thenAccept(reportList -> {
                reports = new ArrayList<>();
                reportList.forEach(r -> reports.add((Report) r));
            })
            .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public CompletableFuture<Void> populatePresentationSlotsAsync() {
        return jsonHandler.readJsonAsync(FileName.PRESENTATIONSLOTS)
            .thenAccept(psList -> {
                presentationSlots = new ArrayList<>();
                psList.forEach(ps -> presentationSlots.add((PresentationSlot) ps));
            })
            .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }
    public CompletableFuture<Void> populateRequestsAsync() {
        return jsonHandler.readJsonAsync(FileName.REQUESTS)
            .thenAccept(requestList -> {
                requests = new ArrayList<>();
                requestList.forEach(r -> requests.add((Request) r));
            })
            .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }
    
    public CompletableFuture<Void> populateProjectsAsync() {
        return jsonHandler.readJsonAsync(FileName.PROJECTS)
            .thenAccept(projectList -> {
                projects = new ArrayList<>();
                projectList.forEach(p -> projects.add((Project) p));
            })
            .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public void print() {
        allFutures.thenRun(() -> {
            System.out.print("STUDENTS: ");
            students.forEach(x -> System.out.print(x.getUsername() + " "));
            System.out.println();
    
            System.out.print("LECTURERS: ");
            lecturers.forEach(x -> System.out.print(x.getUsername() + " "));
            System.out.println();
    
            System.out.print("ADMINS: ");
            admins.forEach(x -> System.out.print(x.getUsername() + " "));
            System.out.println();
    
            System.out.print("PROJECTMANAGERS: ");
            projectManagers.forEach(x -> System.out.print(x.getUsername() + " "));
            System.out.println();
    
            System.out.print("REPORTS: ");
            reports.forEach(x -> System.out.print(x.getMoodleLink() + " "));
            System.out.println();
        })
        .exceptionally(ex -> { ex.printStackTrace(); return null; });
    }

    public void sortAll() {
        Collections.sort(students, Comparator.comparingInt(Student::getId));
        Collections.sort(lecturers, Comparator.comparingInt(Lecturer::getId));
        Collections.sort(projectManagers, Comparator.comparingInt(ProjectManager::getId));
        Collections.sort(admins, Comparator.comparingInt(Admin::getId));
        Collections.sort(reports, Comparator.comparingInt(Report::getId));
        Collections.sort(projects, Comparator.comparingInt(Project::getId));
        Collections.sort(presentationSlots, Comparator.comparingInt(PresentationSlot::getId));
        Collections.sort(requests, Comparator.comparingInt(Request::getId));
    }

    public <T extends Identifiable> void sort(ArrayList<T> objTs) {
        Collections.sort(objTs, Comparator.comparingInt(T::getId));
    }
    
}
