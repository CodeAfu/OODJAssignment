# OODJ Assignment

## ID SCHEME
- Admin: 1xxx
- Lecturer: 2xxx
- Student: 4xxx
- PresentationSlot: 5xxx
- Request: 6xxx
- Project: 7xxx
- Report: 8xxx

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

        // Call readJson() method and store data in an ArrayList<T> 
        // USING:
        ArrayList<Student> students = handler.readJson(FileName.STUDENTS); 
        // OR
        ArrayList<Student> students = handler.readJson("Student");

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
    - MainMenuBody -> Ibrahim
    - StudentMenu -> Ibrahim
    - LecturerMenu -> Ruhit
    - AdminMenu -> Rion
- Build Project Dependencies
    - DataContext (Optional) -> Ibrahim
    - JsonHandler - Ibrahim ✅
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