# OODJ Assignment

## TODO
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
        - View Second marker slots
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
- Lecturer
- Student
- Admin
- Project Manager

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
