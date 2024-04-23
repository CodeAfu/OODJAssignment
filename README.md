# OODJ Assignment

## VERY USEFUL METHODS

```java
// Import this package to read and write Json Data files
import com.ags.pms.io.*;

// I/O flow is simply:
// - Fetch all the data into an ArrayList<T> where T is a class. eg: Student, Admin [readJson()]
// - Modify the ArrayList<T> [custom logic]
// - Save the ArrayList<T> to the data file [writeJson()]

// 2 Core commands:
// - writeJson(): Takes ArrayList<T> as parameter, write as json to relavant file. [1 overload]
// - readJson(): Takes FileName OR classType as parameter, returns ArrayList<T>. [2 overloads] 

// Example Usage:
class Sample {
    public void sampleMethod() {

        // init JsonHandler
        JsonHandler handler = new JsonHandler();  

        // Read 
        ArrayList<Student> students = handler.handler.readJson(FileName.STUDENTS); 
        // OR
        ArrayList<Student> students = handler.handler.readJson("Student");

        // Modify the contents of students (just an example, idk if this works)
        for (Student student : students) {
            if (student.getName() == "Kurwa") {
                student.setName("Helvette");
            }
        }

        // Write modified students ArrayList to file
        handler.writeJson(students);
    }
}

```

## TODO
- Build Models
    - Lecturer -> Ruhit
    - ProjectManager -> Ruhit
    - Admin -> Rion
    - Student -> Rion
- Build GUI
    - Login -> Ruhit
    - Register -> Ruhit
    - MainMenuBody -> NA
    - StudentMenu -> NA
    - LecturerMenu -> NA
    - AdminMenu -> NA
- Build Project Dependencies
    - FileHandler -> Ibrahim
    - PasswordHandler -> Ibrahim ✅
    

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

### Definitely High Fidelity Prototype
![prototype](https://i.imgur.com/ICzJID8.png)

### Copy pasta
✅
❌
❗