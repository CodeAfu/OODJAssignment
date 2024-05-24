# OODJ Assignment

## VERY USEFUL METHODS

```java
// I/O flow is simply:
// - FETCH all the data into an ArrayList<T> where T is a class. eg: Student, Admin
// - MODIFY the ArrayList<T> [custom logic]
// - SAVE the ArrayList<T> to the data file

// Import package
import com.ags.pms.data.*;

class Sample {
    public void sampleMethod() {
        ///-------------------------------------------------
        /// [FETCH]
        // Initialize context
        DataContext context = new DataContext();

        ///-------------------------------------------------
        /// [MODIFY]
        // Operations: Getters, Setters, Add, Remove, Update
        context.addStudent(newStudentObj);
        context.removeById(1004);
        context.updateAdminById(1002, a -> a.setName("TestName"));

        // To modify, you are free to change the following properties inside the 
        // DataContext class however you want:
        //
        // private ArrayList<Lecturer> lecturers = new ArrayList<>();
        // private ArrayList<Student> students = new ArrayList<>();
        // private ArrayList<Admin> admins = new ArrayList<>();
        // private ArrayList<ProjectManager> projectManagers = new ArrayList<>();
        // private ArrayList<Report> reports = new ArrayList<>();
        // private ArrayList<PresentationSlot> presentationSlots = new ArrayList<>();
        // private ArrayList<Request> requests = new ArrayList<>();
        // private ArrayList<Project> projects = new ArrayList<>();
        //
        // example:
        ArrayList<Student> students = context.getStudents();

        for (Student student : students) {
            if (student.getName() == "Bob")
            student.setName("Joey");
        }
        context.setStudents(students);
        // REFER ABOVE FOR ALL 'DataContext' PROPERTIES, CHECK IN THE CLASS FOR UPDATED LIST

        ///-------------------------------------------------
        /// [SAVE]
        // Write modified students ArrayList to file
        context.writeAllChangesAsync();
    }
}
```

## ID SCHEME
- Admin: 1xxx
- Lecturer / ProjectManager: 2xxx
- Student: 4xxx
- PresentationSlot: 5xxx
- Request: 6xxx
- Project: 7xxx
- Report: 8xxx

## TODO
- Build Models
    - Lecturer -> Ruhit ❗
    - ProjectManager -> Ibrahim ❗
    - Admin -> Ibrahim ❗
    - Student -> Ibrahim ❗
- Build GUI
    - Login -> Ruhit
    - Register -> Ruhit
    - MainMenuBody -> Ruhit
    - StudentMenu -> Rion
    - LecturerMenu -> Ruhit
    - AdminMenu -> Rion
- Build Project Dependencies
    - DataContext (Optional) -> Ibrahim ✅
    - JsonHandler - Ibrahim ✅
    - PasswordHandler -> Ibrahim ✅
- Documentation
    - Use Case Diagram -> Rion
    - Class Diagram -> Rion

### GUI
```
Note: 
- DO NOT ADD ID INPUT FIELDS FOR ANYTHING IN THE GUI (Create functions, eg: register user, create project, create student, etc),<br>
- BUT ALSO INCLUDE IDS FOR ALL COMPONENETS THAT DISPLAY DATA (View/Read Functions, eg: JTable listing reports, projects, even viewing single user data).<br>
- THE ID IS AUTO-GENERATED UPON CREATION (Use the constructor with no ID parameter if I missed a method and you want to contruct your own logic)
```
- Login
- Register
- Main Menus (Adaptive Menus based on user roles listed below)
- Student:
    - Dashboard
        - Upto your creative freedom
    - Reports
        - Display Report on JTable { s.getReports() }
        - Add Report - Opens Separate (very basic) JFrame to construct report, just add text fields for every field on Report model { ReportJFrame? }
        - Add a "Remove" button for report
    - Presentations
        - View this student's presentation requests [2 JTables]
            - 1 for seeing student requests { s.viewPresentationSlots() }
            - 1 for approved requests { s.getPresentationSlots() }
        - Make new Presentation Request 
            - Popup JFrame with Text fields for each parameter for method -> { s.requestPresentation() }
    - My Requests
        - Displays all pending requests AND completed requests (might deprecate tbh)
- Lecturer:
    - Dashboard
        - Include list of Students and assigned supervisees (same as View Supervisees lol)
        - Rest is upto ur creative freedom
    - View Supervisees
        - Get list of supervisees assigned to students 
            - Make sure to include Student ID and Supervisor ID { l.viewSupervisees(), l.viewSupervisees().forEach(s -> s.getSupervisee()) }
                - Note: Some students don't have a supervisor, expecting null error. Deal with it by indicating they dont have one (Add a string to JTable)
    - Presentation Requests
        - Get PENDING Student Presentation Requests (Table) { l.viewPendingPresentationRequests() }
        - Confirm presentations (Button, functions based on Table row selected) { l.assignPresentationSlot() }
    - View Second Marker slots
        - Get list of Students with no second marker assigned (JTable) { l.viewSecondMarkerSlots() }
        - Get this lecturer's Second Marker request (ONLY 1 REQUEST AT A TIME) (Some nice component idk) { l.viewSecondMarkerAcceptance() }
        - Apply for Second Marker Request (Button) { l.applyForSecondMarker() }
    - Evaluate report with feedback
        - Get list of all reports (JTable) { l.viewStudentReports() }
        - Give feedback to student Report (Button, JFrame with Report Details and editable feedback) { l.evaluateReport() }
- Project Manager Menus:
    - All Lecturer Methods
    - Assign AssessmentType to Student
        - Load all Student Data (Table) { pm.viewStudents() }
        - Set assesment type of Student (Radio button + button combination?) { pm.assignStudentAssessmentType() }
            - Use the AssessmentType Enum
    - Assign Supervisor and SecondMarker to Lecturer
        - Load all Lecturers (Table) { pm.viewLecturers() }
        - Assign role to Lecturer (Radio button + button combination?) { pm.assignRoleToLecturer }
            - Use the Role Enum
    - View Report Status
        - List Reports (Table) { pm.viewReportStatus() } <- Not sure the significance of this menu
- Admin:
    - Register Students/Lecturer
    
    - Update Menu (Register Student,  Assign Project Manager to Lecturer)


### Models
- Lecturer -> Ruhit
- Student -> Rion
- Admin -> Rion
- Project Manager -> Ruhit

### Functionalities (Adapt as deemed necessary)
#### User Based:
- Admin
    - Register Students AND Lecturers
    - Amend Students (update, delete)
    - Add, update, remove PM role from lecturer

- Project Manager
    - Allot student to assessment type (AssessmentType)
    - Assign supervisor and second markers for registered lecturers
    - View status of report by student/intake

- Lecturer
    - View Assigned supervisees (intake, assessment type, individual)
    - View presentation requests
    - View second marker acceptance / available slots
    - Confirm date of presentation & slot
    - Evaluate report with feedback
    - View supervisee list
    - Dashboard consisting details of assigned supervisees

- Student
    - Submit details of report submission (edit, delete)
        - Date, Assessment type, moodle link, etc
    - Request presentation date
    - Check result / status of submission

#### Menu Based:
- Register Menu
    - Required Fields (not in order): 
        - UserID
        - Password 
        - Email
        - FirstName
        - LastName
        - DoB

### A more defined prototype that is very high quality
![prototype](https://i.imgur.com/WVzjwj1.png)
### Sample reference for navbar and titlebar
![Sample](https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.ijraset.com%2Fimages%2Ftext_version_uploads%2Fimag%25201_4136.png&f=1&nofb=1&ipt=5f5bd7f69bd30eba2e928849c826361dad820541aafe46b8ea2f463229e94754&ipo=images)<br>
[Tutorial for this UI](https://youtube.com/playlist?list=PLjrrZBv_CFYQgCFsHTzfIqtypsWF2KBvJ&si=fGg8GQiqHL31PDAO)<br>
Ignore unnecessary icons
### Copy pasta
✅
❌
❗