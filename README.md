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
        context.updateById(1004, a -> a.setName("TestName"));

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
- Login
- Register
- Main Menu (Adaptive Menus based on user role)
    - Student:
        - Dashboard
        - View Report
        - Request Presentation
        - Results
    - Lecturer:
        - Dashboard
        - View Supervisees
        - View Presentation Requests
        - View Second Marker slots
        - Confirm presentations
        - Evaluate report with feedback
        - Project Manager Menus:
            - Assign AssessmentType to Student
            - Assign Supervisor and SecondMarker to Lecturer
            - View Report Status
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