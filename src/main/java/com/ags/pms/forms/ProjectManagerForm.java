/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ags.pms.forms;

import com.ags.pms.Helper;
import com.ags.pms.data.DataContext;
import com.ags.pms.models.AssessmentType;
import com.ags.pms.models.Identifiable;
import com.ags.pms.models.Lecturer;
import com.ags.pms.models.ProjectManager;
import com.ags.pms.models.Report;
import com.ags.pms.models.Request;
import com.ags.pms.models.Role;
import com.ags.pms.models.Student;
import com.ags.pms.models.User;
import com.fasterxml.jackson.core.JsonPointer;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Dimension;
import java.awt.Window;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.util.stream.Collectors;
import javax.swing.ListSelectionModel;
import java.util.Arrays;


/**
 *
 * @author Genzoku
 */
public class ProjectManagerForm extends javax.swing.JFrame {

    ArrayList<Request> lecSecondMarkerRequests;
    private ProjectManager projectManager;
    private int selectedReportId;

    public ProjectManagerForm() {
    initComponents();

    try {
        this.projectManager = new ProjectManager(2008, "Sophia Johnson", "25/06/1970", "sophia@email.com", "sophia_j", "ProjectMan321", Role.SUPERVISOR, new ArrayList<Integer>(Arrays.asList(4004)));

    } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
            | BadPaddingException | InvalidAlgorithmParameterException e) {
        e.printStackTrace();
    }

        openDashboard();  
        populateData();
    }

    public ProjectManagerForm(User user) {
        initComponents();
        this.projectManager = (ProjectManager) user;

        openDashboard();
        populateData();
    }

    private void populateData() {
        jLabelUsername.setText(projectManager.getUsername());
        populatePresentationsRequestTable();
        populateSecondMarkerRequestTable();
        populateSecondMarkerAcceptence();
        populateSupervisees();
        populatePresentationRequestComboBox();
        populateReportsTable();
        populateFilterStudentComboBox();
        populateStudentAssessmentTable();
        populateAssessmentTypeComboBox();
        populateLecturerRoleTable();
        populateLecturerRoleComboBox();
        populateStudentAssignPMTable();

    }

    private void openDashboard() {
        jPanelDashboard.setVisible(true);
        jPanelViewPresentation.setVisible(false);
        jPanelMarkerRequests.setVisible(false);
        jPanelViewSupervisee.setVisible(false);
        jPanelReport.setVisible(false);
        jPanelAssessment.setVisible(false);
        jPanelLecturerRole.setVisible(false);
    }

    private void openAvailableSlots() {
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(false);
        jPanelMarkerRequests.setVisible(true);
        jPanelViewSupervisee.setVisible(false);
        jPanelReport.setVisible(false);
        jPanelAssessment.setVisible(false);
        jPanelLecturerRole.setVisible(false);
    }

    private void acceptSecondMarkerRequest(int reqId) {
        boolean isCompleted = false;

        int response = JOptionPane.showConfirmDialog(null, "Request fulfilled. Delete?", "Confirm", 
                                                     JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            isCompleted = true;
        }

        projectManager.acceptSecondMarkerRequest(reqId, isCompleted);
        populateSecondMarkerRequestTable();
        loadRequestFromSelectedItem();
    }

    private void rejectSecondMarkerRequest(int reqId) {
        boolean isCompleted = false;
        int response = JOptionPane.showConfirmDialog(null, "Request fulfilled. Delete?", "Confirm", 
                                                     JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            isCompleted = true;
        }

        projectManager.rejectSecondMarkerRequest(reqId, isCompleted);
        populateSecondMarkerRequestTable();
        loadRequestFromSelectedItem();
    }

    private void populateSecondMarkerRequestTable() {
        DefaultTableModel model = (DefaultTableModel) jTableSecondMarkerRequests.getModel();
        model.setRowCount(0);
        lecSecondMarkerRequests = projectManager.viewSecondMarkerRequests();

        for (int i = 0; i < lecSecondMarkerRequests.size(); i++) {

            Object rowData[] = new Object[4];
            rowData[0] = lecSecondMarkerRequests.get(i).getId();
            rowData[1] = lecSecondMarkerRequests.get(i).fetchStudent().getName();
            rowData[2] = lecSecondMarkerRequests.get(i).fetchLecturer().getName();
            rowData[3] = lecSecondMarkerRequests.get(i).getRequestType();
    
            model.addRow(rowData);
        }
    }

    private void populatePresentationRequestComboBox() {
        jComboBoxPresentations.removeAllItems();
        ArrayList<Request> presentationRequests = projectManager.viewPendingPresentationRequests();
        presentationRequests.forEach(p -> jComboBoxPresentations.addItem(p));
    }

    private void populateSupervisees() {
        DefaultTableModel model = (DefaultTableModel) jTableViewSupervisee.getModel();
        model.setRowCount(0);
        ArrayList<Map<String, Object>> students = projectManager.viewSupervisees();

        for (int i = 0; i < students.size(); i++) {

            Object rowData[] = new Object[4];
            rowData[0] = students.get(i).get("id");
            rowData[1] = students.get(i).get("name");
            rowData[2] = students.get(i).get("supervisorName");
            rowData[3] = students.get(i).get("secondMarkerName");
    
            model.addRow(rowData);

        }
        jTableViewSupervisee.setFocusable(false);
        jTableViewSupervisee.setRowSelectionAllowed(false);
        jTableViewSupervisee.setCellSelectionEnabled(false);

    }

    private void populateReportsTable() {
        DefaultTableModel model = (DefaultTableModel) jTableReport.getModel();
        model.setRowCount(0);
        ArrayList<Map<String, Object>> studentsWithReports = projectManager.viewAllStudentsWithReports();

        ArrayList<Report> reports = new ArrayList<>();

        studentsWithReports.forEach(s -> {
            if (!((ArrayList<Report>) s.get("reports")).isEmpty()) {
                ((ArrayList<Report>) s.get("reports")).forEach(r -> {
                    reports.add(r);
                });
            }
        });
        
        for (int i = 0; i < reports.size(); i++) {
            Object rowData[] = new Object[5];
            
            rowData[0] = reports.get(i).getId();
            rowData[1] = reports.get(i).getStudentId();
            rowData[2] = reports.get(i).getDateSubmitted();
            rowData[3] = reports.get(i).getTotalMark();
            rowData[4] = reports.get(i).getFeedback();
            
            model.addRow(rowData);
        }
        
    }
    
    private void populateReportsTable(int id) {
        DefaultTableModel model = (DefaultTableModel) jTableReport.getModel();
        model.setRowCount(0);
        ArrayList<Map<String, Object>> studentsWithReports = projectManager.viewAllStudentsWithReports();

        ArrayList<Report> reports = new ArrayList<>();

        studentsWithReports.forEach(s -> {
            if (!((ArrayList<Report>) s.get("reports")).isEmpty()) {
                ((ArrayList<Report>) s.get("reports")).forEach(r -> {
                    reports.add(r);
                });
            }
        });
        for (int i = 0; i < reports.size(); i++) {
            Object rowData[] = new Object[5];
            
            rowData[0] = reports.get(i).getId();
            rowData[1] = reports.get(i).getStudentId();
            rowData[2] = reports.get(i).getDateSubmitted();
            rowData[3] = reports.get(i).getTotalMark();
            rowData[4] = reports.get(i).getFeedback();

            model.addRow(rowData);
        }
    }


    private void populateFilterStudentComboBox() {
        jComboBoxFilterStudent.removeAllItems();
        ArrayList<Student> students = new ArrayList<>();
        projectManager.viewAllStudentsWithReports().forEach(s -> {
            students.add((Student) s.get("student"));
        }); 

        students.forEach(s -> jComboBoxFilterStudent.addItem(s));
    }


    private void populateSecondMarkerAcceptence() {
        Map<String, Object> result = projectManager.viewSecondMarkerAcceptance();

        if (result == null) {
            return;
        }

        Student student = (Student) result.get("student");
        Lecturer myLecturer = (Lecturer) result.get("lecturer");
        boolean isApproved = (boolean) result.get("approved");

        jLabelRequestLecturerId.setText(Integer.toString(myLecturer.getId()));
        jLabelRequestLecturerName.setText(myLecturer.getName());
        jLabelRequestStudentId.setText(Integer.toString(student.getId()));
        jLabelRequestStudentName.setText(student.getName());

        DefaultTableModel model = (DefaultTableModel) jTablePresentation.getModel();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
        }

    }

    private void populateStudentAssessmentTable() {
        DefaultTableModel model = (DefaultTableModel) jTableStudentAssessmentTypes.getModel();

        // Clear the table before populating it again to avoid duplicate entries
        model.setRowCount(0);

        ArrayList<Student> students = projectManager.viewStudents();
        
        for (int i = 0; i < students.size(); i++) {
            Object rowData[] = new Object[3]; // Moved declaration inside the loop
            
            rowData[0] = students.get(i).getId();
            rowData[1] = students.get(i).getName();
            rowData[2] = (students.get(i).getAssessmentType() != null) 
                                ? students.get(i).getAssessmentType().toString() : null;

            model.addRow(rowData);
        }
    }

    private void populateAssessmentTypeComboBox() {
        jComboBoxAssessmentType.removeAllItems();

        for (AssessmentType type : AssessmentType.values()) {
            jComboBoxAssessmentType.addItem(type);
        }
        
        jComboBoxAssessmentType.setSelectedIndex(-1);
    }

    private void assignStudentAssessmentType() {
        DefaultTableModel dtm = (DefaultTableModel) jTableStudentAssessmentTypes.getModel();
        int selectedRow = jTableStudentAssessmentTypes.getSelectedRow();
        AssessmentType selectedAssessmentType = (AssessmentType) jComboBoxAssessmentType.getSelectedItem();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please Select a Student");
            return;
        }

        if (selectedAssessmentType == null) {
            JOptionPane.showMessageDialog(null, "Please Select an Assessment Type");
            return;
        }
        
        int studentId = (int) dtm.getValueAt(selectedRow, 0);
        if (studentId == 0 || studentId == -1) {
            JOptionPane.showMessageDialog(null, "Error fetching student");
            return;
        }

        String currentAssessmentType = (String) dtm.getValueAt(selectedRow, 2);
        String studentName = (String) dtm.getValueAt(selectedRow, 1);

        if (currentAssessmentType != null ) {

            int response = JOptionPane.showConfirmDialog(null, studentName + " has Assessment Type " + currentAssessmentType + ". Replace?", 
                            "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                projectManager.assignStudentAssessmentType(studentId, selectedAssessmentType);
            }
        } else {
            projectManager.assignStudentAssessmentType(studentId, selectedAssessmentType);
        }

        JOptionPane.showMessageDialog(null, "Student Assessment Type Updated");

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) { }

        populateStudentAssessmentTable();
    }

    private void populateStudentAssignPMTable() {
        DefaultTableModel model = (DefaultTableModel) jTableStudentAssignPM.getModel();

        // Clear the table before populating it again to avoid duplicate entries
        model.setRowCount(0);

        ArrayList<Student> students = projectManager.viewStudents();
        
        for (int i = 0; i < students.size(); i++) {
            Object rowData[] = new Object[4]; // Moved declaration inside the loop
            
            Lecturer supervisor = (Lecturer) students.get(i).fetchSupervisor();
            Lecturer secondMarker = (Lecturer) students.get(i).fetchSecondMarker();

            rowData[0] = students.get(i).getId();
            rowData[1] = students.get(i).getName();
            rowData[2] = (supervisor != null) 
                            ? supervisor.getName() : null;
            rowData[3] = (secondMarker != null) 
                            ? secondMarker.getName() : null;

            model.addRow(rowData);
        }
    }

    private void populateLecturerRoleTable() {
        DefaultTableModel model = (DefaultTableModel) jTableLecturerRoles.getModel();

        // Clear the table before populating it again to avoid duplicate entries
        model.setRowCount(0);

        ArrayList<Lecturer> lecturers = projectManager.viewLecturersAndPMs();
        
        for (int i = 0; i < lecturers.size(); i++) {
            Object rowData[] = new Object[3]; // Moved declaration inside the loop
            
            rowData[0] = lecturers.get(i).getId();
            rowData[1] = lecturers.get(i).getName();
            rowData[2] = (lecturers.get(i).getRole() != null) 
                                ? lecturers.get(i).getRole().toString() : null;

            model.addRow(rowData);
        }
    }

    private void populateLecturerRoleComboBox() {
        jComboBoxLecturerRoles.removeAllItems();

        for (Role type : Role.values()) {
            jComboBoxLecturerRoles.addItem(type);
        }
        
        jComboBoxLecturerRoles.setSelectedIndex(-1);
    }

    private void assignLecturerRole() {
        DefaultTableModel dtmLecturer = (DefaultTableModel) jTableLecturerRoles.getModel();
        DefaultTableModel dtmStudent = (DefaultTableModel) jTableStudentAssignPM.getModel();

        int selectedRowLecturer = jTableLecturerRoles.getSelectedRow();
        int selectedRowStudent = jTableStudentAssignPM.getSelectedRow();

        Role selectedRole = (Role) jComboBoxLecturerRoles.getSelectedItem();
        
        if (selectedRowLecturer == -1) {
            JOptionPane.showMessageDialog(null, "Please Select a Lecturer");
            return;
        }
        
        if (selectedRowStudent == -1) {
            JOptionPane.showMessageDialog(null, "Please Select a Student");
            return;
        }

        if (selectedRole == null) {
            JOptionPane.showMessageDialog(null, "Please Select a Role");
            return;
        }
        
        int lecturerId = (int) dtmLecturer.getValueAt(selectedRowLecturer, 0);
        int studentId = (int) dtmStudent.getValueAt(selectedRowStudent, 0);

        // Doesnt do anything I think
        if (lecturerId == 0 || lecturerId == -1) {
            JOptionPane.showMessageDialog(null, "Error fetching Lecturer");
            return;
        }

        String lecturerName = (String) dtmLecturer.getValueAt(selectedRowLecturer, 1);
        String currentRole = (String) dtmLecturer.getValueAt(selectedRowLecturer, 2);

        if (currentRole != null ) {

            int response = JOptionPane.showConfirmDialog(null, lecturerName + " has Role " + currentRole + ". Replace?", 
                            "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                projectManager.assignRoleToLecturer(lecturerId, studentId, selectedRole);;
            }
        } else {
            projectManager.assignRoleToLecturer(lecturerId, studentId, selectedRole);;
        }

        JOptionPane.showMessageDialog(null, "Lecturer Role Updated");

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) { }

        populateLecturerRoleTable();
        populateStudentAssignPMTable();
    }

    private void populatePresentationsRequestTable() {
        DefaultTableModel model = (DefaultTableModel) jTablePresentation.getModel();

        // Clear the table before populating it again to avoid duplicate entries
        model.setRowCount(0);

        ArrayList<Request> presentations = projectManager.viewPendingPresentationRequests();
        
        for (int i = 0; i < presentations.size(); i++) {
            Object rowData[] = new Object[7]; // Moved declaration inside the loop
            
            rowData[0] = presentations.get(i).getId();
            rowData[1] = presentations.get(i).getStudentId();
            rowData[2] = presentations.get(i).viewUser().getName();
            rowData[3] = presentations.get(i).fetchPresentationSlot().getPresentationDate();
            rowData[4] = presentations.get(i).fetchPresentationSlot().getModule();
            rowData[5] = presentations.get(i).getModule();
            rowData[6] = presentations.get(i).isApproved();

            model.addRow(rowData);
        }
        
        jTablePresentation.setFocusable(false);
        jTablePresentation.setRowSelectionAllowed(false);
        jTablePresentation.setCellSelectionEnabled(false);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTitle = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelUsername = new javax.swing.JLabel();
        jPanelSide = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanelDragLeft = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        requestBtn = new javax.swing.JButton();
        dashboardBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        markerRequestTabBtn = new javax.swing.JButton();
        viewSuperviseeBtn = new javax.swing.JButton();
        viewReportBtn = new javax.swing.JButton();
        viewAssessmentBtn = new javax.swing.JButton();
        viewLecturerBtn = new javax.swing.JButton();
        jPanelContents = new javax.swing.JPanel();
        jPanelDashboard = new javax.swing.JPanel();
        jPanelViewPresentation = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePresentation = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButtonApprovePresentation = new javax.swing.JButton();
        jComboBoxPresentations = new javax.swing.JComboBox<>();
        jPanelMarkerRequests = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableSecondMarkerRequests = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabelRequest = new javax.swing.JLabel();
        jButtonAcceptSecondMarker = new javax.swing.JButton();
        jLabelRequest7 = new javax.swing.JLabel();
        jLabelRequest8 = new javax.swing.JLabel();
        jLabelRequest9 = new javax.swing.JLabel();
        jLabelRequestLecturerName = new javax.swing.JLabel();
        jLabelRequestStudentId = new javax.swing.JLabel();
        jLabelRequestStudentName = new javax.swing.JLabel();
        jLabelRequest10 = new javax.swing.JLabel();
        jLabelRequestLecturerId = new javax.swing.JLabel();
        jLabelRequest11 = new javax.swing.JLabel();
        jLabelRequest12 = new javax.swing.JLabel();
        jLabelRequest13 = new javax.swing.JLabel();
        jLabelRequestApproved = new javax.swing.JLabel();
        jLabelRequestType = new javax.swing.JLabel();
        jLabelRequestModule = new javax.swing.JLabel();
        jButtonRejectSecondMarker = new javax.swing.JButton();
        jPanelViewSupervisee = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableViewSupervisee = new javax.swing.JTable();
        jButtonSwitchToSMApply = new javax.swing.JButton();
        jPanelReport = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableReport = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabelTotalMarks = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabelContents = new javax.swing.JLabel();
        jLabelMarks = new javax.swing.JLabel();
        jLabelMoodle = new javax.swing.JLabel();
        jLabelProjectAssessmentType = new javax.swing.JLabel();
        jLabelSupervisor = new javax.swing.JLabel();
        jLabelProjectModule = new javax.swing.JLabel();
        jLabelStudentId = new javax.swing.JLabel();
        jLabelStudentName = new javax.swing.JLabel();
        jComboBoxFilterStudent = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextAreaFeedback = new javax.swing.JTextArea();
        jButtonResetFilters = new javax.swing.JButton();
        jButtonFeedback = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabelSelectedReport = new javax.swing.JLabel();
        jPanelAssessment = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableStudentAssessmentTypes = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabelRequest4 = new javax.swing.JLabel();
        jButtonAssignAssessment = new javax.swing.JButton();
        jComboBoxAssessmentType = new javax.swing.JComboBox<>();
        jLabelRequest2 = new javax.swing.JLabel();
        jPanelLecturerRole = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableLecturerRoles = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabelRequest5 = new javax.swing.JLabel();
        jButtonAssignLecturerRole = new javax.swing.JButton();
        jComboBoxLecturerRoles = new javax.swing.JComboBox<>();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableStudentAssignPM = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanelTitle.setBackground(new java.awt.Color(245, 246, 248));

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 2, 36)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(0, 0, 51));
        jLabelTitle.setText("Project Management System");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Welcome!");

        jLabelUsername.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanelTitleLayout = new javax.swing.GroupLayout(jPanelTitle);
        jPanelTitle.setLayout(jPanelTitleLayout);
        jPanelTitleLayout.setHorizontalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitleLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jLabelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
        jPanelTitleLayout.setVerticalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitleLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTitleLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelUsername))
                    .addComponent(jLabelTitle))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanelSide.setBackground(new java.awt.Color(83, 116, 240));
        jPanelSide.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanelSide.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 266, -1, -1));

        jPanelDragLeft.setBackground(new java.awt.Color(102, 102, 255));
        jPanelDragLeft.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanelDragLeft.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 8, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/Male User_3.png"))); // NOI18N
        jPanelDragLeft.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 50, -1));

        requestBtn.setBackground(new java.awt.Color(110, 139, 251));
        requestBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        requestBtn.setForeground(new java.awt.Color(0, 0, 0));
        requestBtn.setText("View Presentation Requests");
        requestBtn.setBorderPainted(false);
        requestBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestBtnActionPerformed(evt);
            }
        });
        jPanelDragLeft.add(requestBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 169, 237, 37));

        dashboardBtn.setBackground(new java.awt.Color(110, 139, 251));
        dashboardBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dashboardBtn.setForeground(new java.awt.Color(0, 0, 0));
        dashboardBtn.setText("Dashboard");
        dashboardBtn.setBorderPainted(false);
        dashboardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardBtnActionPerformed(evt);
            }
        });
        jPanelDragLeft.add(dashboardBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 237, 37));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Logout");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanelDragLeft.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Project Manager");
        jPanelDragLeft.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 150, -1));

        jPanelSide.add(jPanelDragLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        markerRequestTabBtn.setBackground(new java.awt.Color(110, 139, 251));
        markerRequestTabBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        markerRequestTabBtn.setForeground(new java.awt.Color(0, 0, 0));
        markerRequestTabBtn.setText("Second Marker Requests");
        markerRequestTabBtn.setBorderPainted(false);
        markerRequestTabBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                markerRequestTabBtnMouseClicked(evt);
            }
        });
        jPanelSide.add(markerRequestTabBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 223, 240, 37));

        viewSuperviseeBtn.setBackground(new java.awt.Color(110, 139, 251));
        viewSuperviseeBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        viewSuperviseeBtn.setForeground(new java.awt.Color(0, 0, 0));
        viewSuperviseeBtn.setText("View Supervisee List");
        viewSuperviseeBtn.setBorderPainted(false);
        viewSuperviseeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewSuperviseeBtnMouseClicked(evt);
            }
        });
        jPanelSide.add(viewSuperviseeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 237, 37));

        viewReportBtn.setBackground(new java.awt.Color(110, 139, 251));
        viewReportBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        viewReportBtn.setForeground(new java.awt.Color(0, 0, 0));
        viewReportBtn.setText("View Report");
        viewReportBtn.setBorderPainted(false);
        viewReportBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewReportBtnMouseClicked(evt);
            }
        });
        jPanelSide.add(viewReportBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 237, 37));

        viewAssessmentBtn.setBackground(new java.awt.Color(110, 139, 251));
        viewAssessmentBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        viewAssessmentBtn.setForeground(new java.awt.Color(0, 0, 0));
        viewAssessmentBtn.setText("Assign Assessment");
        viewAssessmentBtn.setBorderPainted(false);
        viewAssessmentBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewAssessmentBtnMouseClicked(evt);
            }
        });
        jPanelSide.add(viewAssessmentBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 237, 37));

        viewLecturerBtn.setBackground(new java.awt.Color(110, 139, 251));
        viewLecturerBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        viewLecturerBtn.setForeground(new java.awt.Color(0, 0, 0));
        viewLecturerBtn.setText("Assign Lecturer Role");
        viewLecturerBtn.setBorderPainted(false);
        viewLecturerBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewLecturerBtnMouseClicked(evt);
            }
        });
        jPanelSide.add(viewLecturerBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 237, 37));

        jPanelContents.setBackground(new java.awt.Color(153, 153, 255));
        jPanelContents.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelDashboard.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanelDashboardLayout = new javax.swing.GroupLayout(jPanelDashboard);
        jPanelDashboard.setLayout(jPanelDashboardLayout);
        jPanelDashboardLayout.setHorizontalGroup(
            jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanelDashboardLayout.setVerticalGroup(
            jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );

        jPanelContents.add(jPanelDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 560));

        jPanelViewPresentation.setBackground(new java.awt.Color(204, 204, 255));
        jPanelViewPresentation.setPreferredSize(new java.awt.Dimension(800, 560));
        jPanelViewPresentation.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTablePresentation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Student ID", "Student Name", "Module", "Date", "Module", "Approved"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTablePresentation);
        if (jTablePresentation.getColumnModel().getColumnCount() > 0) {
            jTablePresentation.getColumnModel().getColumn(0).setMaxWidth(80);
            jTablePresentation.getColumnModel().getColumn(1).setMaxWidth(50);
            jTablePresentation.getColumnModel().getColumn(6).setMaxWidth(100);
        }

        jPanelViewPresentation.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 750, 180));

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonApprovePresentation.setText("Approve");
        jButtonApprovePresentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApprovePresentationActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonApprovePresentation, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, -1, 33));

        jPanel2.add(jComboBoxPresentations, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 510, 40));

        jPanelViewPresentation.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, 540, 290));

        jPanelContents.add(jPanelViewPresentation, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanelMarkerRequests.setBackground(new java.awt.Color(204, 204, 255));
        jPanelMarkerRequests.setPreferredSize(new java.awt.Dimension(800, 560));
        jPanelMarkerRequests.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableSecondMarkerRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Student Name", "Lecturer Name", "Request Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableSecondMarkerRequests.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSecondMarkerRequestsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableSecondMarkerRequests);
        if (jTableSecondMarkerRequests.getColumnModel().getColumnCount() > 0) {
            jTableSecondMarkerRequests.getColumnModel().getColumn(0).setMaxWidth(80);
        }

        jPanelMarkerRequests.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, 480));

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelRequest.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelRequest.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest.setText("Details:");
        jPanel1.add(jLabelRequest, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 90, 20));

        jButtonAcceptSecondMarker.setText("Accept");
        jButtonAcceptSecondMarker.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonAcceptSecondMarkerMouseClicked(evt);
            }
        });
        jPanel1.add(jButtonAcceptSecondMarker, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 80, 30));

        jLabelRequest7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelRequest7.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest7.setText("Lecturer Name:");
        jPanel1.add(jLabelRequest7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 90, 20));

        jLabelRequest8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelRequest8.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest8.setText("Approved:");
        jPanel1.add(jLabelRequest8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 90, 20));

        jLabelRequest9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelRequest9.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest9.setText("Student ID:");
        jPanel1.add(jLabelRequest9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 70, 20));

        jLabelRequestLecturerName.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequestLecturerName.setText("null");
        jPanel1.add(jLabelRequestLecturerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 100, 20));

        jLabelRequestStudentId.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequestStudentId.setText("null");
        jPanel1.add(jLabelRequestStudentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 100, 20));

        jLabelRequestStudentName.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequestStudentName.setText("null");
        jPanel1.add(jLabelRequestStudentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 100, 20));

        jLabelRequest10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelRequest10.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest10.setText("Lecturer ID:");
        jPanel1.add(jLabelRequest10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 70, 20));

        jLabelRequestLecturerId.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequestLecturerId.setText("null");
        jPanel1.add(jLabelRequestLecturerId, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 100, 20));

        jLabelRequest11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelRequest11.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest11.setText("Student Name:");
        jPanel1.add(jLabelRequest11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 90, 20));

        jLabelRequest12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelRequest12.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest12.setText("Request Type:");
        jPanel1.add(jLabelRequest12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 90, 20));

        jLabelRequest13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelRequest13.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest13.setText("Module:");
        jPanel1.add(jLabelRequest13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 90, 20));

        jLabelRequestApproved.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequestApproved.setText("null");
        jPanel1.add(jLabelRequestApproved, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 100, 20));

        jLabelRequestType.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequestType.setText("null");
        jPanel1.add(jLabelRequestType, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 100, 20));

        jLabelRequestModule.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequestModule.setText("null");
        jPanel1.add(jLabelRequestModule, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 100, 20));

        jButtonRejectSecondMarker.setText("Reject");
        jButtonRejectSecondMarker.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonRejectSecondMarkerMouseClicked(evt);
            }
        });
        jPanel1.add(jButtonRejectSecondMarker, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 80, 30));

        jPanelMarkerRequests.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 240, 410));

        jPanelContents.add(jPanelMarkerRequests, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanelViewSupervisee.setBackground(new java.awt.Color(204, 204, 255));
        jPanelViewSupervisee.setPreferredSize(new java.awt.Dimension(800, 560));

        jTableViewSupervisee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Supervisor", "Second Marker"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableViewSupervisee);
        if (jTableViewSupervisee.getColumnModel().getColumnCount() > 0) {
            jTableViewSupervisee.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jButtonSwitchToSMApply.setText("jButton1");
        jButtonSwitchToSMApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSwitchToSMApplyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelViewSuperviseeLayout = new javax.swing.GroupLayout(jPanelViewSupervisee);
        jPanelViewSupervisee.setLayout(jPanelViewSuperviseeLayout);
        jPanelViewSuperviseeLayout.setHorizontalGroup(
            jPanelViewSuperviseeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelViewSuperviseeLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jButtonSwitchToSMApply, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );
        jPanelViewSuperviseeLayout.setVerticalGroup(
            jPanelViewSuperviseeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelViewSuperviseeLayout.createSequentialGroup()
                .addGroup(jPanelViewSuperviseeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelViewSuperviseeLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelViewSuperviseeLayout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(jButtonSwitchToSMApply, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jPanelContents.add(jPanelViewSupervisee, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanelReport.setBackground(new java.awt.Color(204, 204, 255));
        jPanelReport.setPreferredSize(new java.awt.Dimension(800, 560));
        jPanelReport.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Date Submitted", "Total Marks", "Feedback"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableReportMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTableReport);
        if (jTableReport.getColumnModel().getColumnCount() > 0) {
            jTableReport.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jPanelReport.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 570, 120));

        jPanel3.setBackground(new java.awt.Color(102, 51, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Marks:");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 261, -1, -1));

        jLabelTotalMarks.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelTotalMarks.setText("jLabel7");
        jPanel3.add(jLabelTotalMarks, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 290, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setText("Total:");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 290, -1, -1));

        jLabelContents.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelContents.setText("jLabel7");
        jPanel3.add(jLabelContents, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 380, 150));

        jLabelMarks.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelMarks.setText("jLabel7");
        jPanel3.add(jLabelMarks, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 260, -1, -1));

        jLabelMoodle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelMoodle.setText("jLabel7");
        jPanel3.add(jLabelMoodle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        jLabelProjectAssessmentType.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelProjectAssessmentType.setText("jLabel7");
        jPanel3.add(jLabelProjectAssessmentType, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, -1, -1));

        jLabelSupervisor.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelSupervisor.setText("jLabel7");
        jPanel3.add(jLabelSupervisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, -1, -1));

        jLabelProjectModule.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelProjectModule.setText("jLabel7");
        jPanel3.add(jLabelProjectModule, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        jLabelStudentId.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelStudentId.setText("jLabel7");
        jPanel3.add(jLabelStudentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jLabelStudentName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelStudentName.setText("jLabel7");
        jPanel3.add(jLabelStudentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jPanelReport.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 150, 570, 370));

        jComboBoxFilterStudent.setSelectedItem(null);
        jComboBoxFilterStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFilterStudentActionPerformed(evt);
            }
        });
        jPanelReport.add(jComboBoxFilterStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 42, 150, 30));

        jTextAreaFeedback.setColumns(20);
        jTextAreaFeedback.setRows(5);
        jScrollPane5.setViewportView(jTextAreaFeedback);

        jPanelReport.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 200, 340));

        jButtonResetFilters.setText("Reset");
        jButtonResetFilters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetFiltersActionPerformed(evt);
            }
        });
        jPanelReport.add(jButtonResetFilters, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        jButtonFeedback.setText("Evaluate Report");
        jButtonFeedback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFeedbackActionPerformed(evt);
            }
        });
        jPanelReport.add(jButtonFeedback, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 200, -1));

        jLabel6.setText("Selected Report:");
        jPanelReport.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabelSelectedReport.setText("-1");
        jPanelReport.add(jLabelSelectedReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, -1, 20));

        jPanelContents.add(jPanelReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanelAssessment.setBackground(new java.awt.Color(204, 204, 255));
        jPanelAssessment.setPreferredSize(new java.awt.Dimension(800, 560));
        jPanelAssessment.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableStudentAssessmentTypes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Name", "Assessment Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(jTableStudentAssessmentTypes);
        if (jTableStudentAssessmentTypes.getColumnModel().getColumnCount() > 0) {
            jTableStudentAssessmentTypes.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jPanelAssessment.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, -1, -1));

        jPanel4.setBackground(new java.awt.Color(153, 153, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelRequest4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelRequest4.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest4.setText("Select Assessment Type");
        jPanel4.add(jLabelRequest4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 220, 40));

        jButtonAssignAssessment.setText("Assign");
        jButtonAssignAssessment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonAssignAssessmentMouseClicked(evt);
            }
        });
        jPanel4.add(jButtonAssignAssessment, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 80, 30));

        jPanel4.add(jComboBoxAssessmentType, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 160, -1));

        jPanelAssessment.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 260, 210));

        jLabelRequest2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelRequest2.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest2.setText("Select a Student from the Table, and set the Assessment Type");
        jPanelAssessment.add(jLabelRequest2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 710, -1));

        jPanelContents.add(jPanelAssessment, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanelLecturerRole.setBackground(new java.awt.Color(204, 204, 255));
        jPanelLecturerRole.setPreferredSize(new java.awt.Dimension(800, 560));
        jPanelLecturerRole.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableLecturerRoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Name", "Role"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(jTableLecturerRoles);
        if (jTableLecturerRoles.getColumnModel().getColumnCount() > 0) {
            jTableLecturerRoles.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jPanelLecturerRole.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 370, 170));

        jPanel5.setBackground(new java.awt.Color(153, 153, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelRequest5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelRequest5.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest5.setText("Select Assessment Type");
        jPanel5.add(jLabelRequest5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 220, 40));

        jButtonAssignLecturerRole.setText("Assign");
        jButtonAssignLecturerRole.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonAssignLecturerRoleMouseClicked(evt);
            }
        });
        jPanel5.add(jButtonAssignLecturerRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 80, 30));

        jPanel5.add(jComboBoxLecturerRoles, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 160, -1));

        jPanelLecturerRole.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 260, 210));

        jTableStudentAssignPM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Supervisor", "Second Marker"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(jTableStudentAssignPM);
        if (jTableStudentAssignPM.getColumnModel().getColumnCount() > 0) {
            jTableStudentAssignPM.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jPanelLecturerRole.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 380, 170));

        jPanelContents.add(jPanelLecturerRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanelSide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelContents, javax.swing.GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelSide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelContents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1065, 681));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonApprovePresentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApprovePresentationActionPerformed
        if (jComboBoxPresentations.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Please select an entry.");
            return;
        }
        Request request = (Request) jComboBoxPresentations.getSelectedItem();
        projectManager.assignPresentationSlot(request.getId(), request.getStudentId(), request.getPresentationSlotId());
        JOptionPane.showMessageDialog(null, "Presentation Request Approved!");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        populatePresentationsRequestTable();
        populatePresentationRequestComboBox();
    }//GEN-LAST:event_jButtonApprovePresentationActionPerformed

    private void jButtonSwitchToSMApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSwitchToSMApplyActionPerformed
        openAvailableSlots();
    }//GEN-LAST:event_jButtonSwitchToSMApplyActionPerformed

    private void jButtonResetFiltersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetFiltersActionPerformed
        jComboBoxFilterStudent.setSelectedIndex(-1);
        populateReportsTable();
    }//GEN-LAST:event_jButtonResetFiltersActionPerformed

    private void jComboBoxFilterStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFilterStudentActionPerformed
        if (jComboBoxFilterStudent.getSelectedIndex() != -1) {
            int id = ((Identifiable) jComboBoxFilterStudent.getSelectedItem()).getId();
            populateReportsTable(id);
        }
    }//GEN-LAST:event_jComboBoxFilterStudentActionPerformed

    private void jButtonFeedbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFeedbackActionPerformed
        String feedback = jTextAreaFeedback.getText();
        projectManager.evaluateReport(selectedReportId, feedback);
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}

        populateReportsTable();
        populateFilterStudentComboBox();
    }//GEN-LAST:event_jButtonFeedbackActionPerformed

    private void jTableReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableReportMouseClicked
        DefaultTableModel dtm = (DefaultTableModel) jTableReport.getModel();
        selectedReportId = (int) dtm.getValueAt(jTableReport.getSelectedRow(), 0);
        jLabelSelectedReport.setText(Integer.toString(selectedReportId));
        loadReportFromSelectedItem();
    }//GEN-LAST:event_jTableReportMouseClicked

    private void viewSupviseeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewSupviseeBtnMouseClicked
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(false);
        jPanelMarkerRequests.setVisible(false);
        jPanelViewSupervisee.setVisible(false);
        jPanelReport.setVisible(true);
        jPanelAssessment.setVisible(false);
        jPanelLecturerRole.setVisible(false);
    }//GEN-LAST:event_viewSupviseeBtnMouseClicked

    private void viewReportBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewReportBtnMouseClicked
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(false);
        jPanelMarkerRequests.setVisible(false);
        jPanelViewSupervisee.setVisible(false);
        jPanelReport.setVisible(true);
        jPanelAssessment.setVisible(false);
        jPanelLecturerRole.setVisible(false);
    }//GEN-LAST:event_viewReportBtnMouseClicked

    private void viewAssessmentBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewAssessmentBtnMouseClicked
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(false);
        jPanelMarkerRequests.setVisible(false);
        jPanelViewSupervisee.setVisible(false);
        jPanelReport.setVisible(false);
        jPanelAssessment.setVisible(true);
        jPanelLecturerRole.setVisible(false);
    }//GEN-LAST:event_viewAssessmentBtnMouseClicked

    private void viewLecturerBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewLecturerBtnMouseClicked
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(false);
        jPanelMarkerRequests.setVisible(false);
        jPanelViewSupervisee.setVisible(false);
        jPanelReport.setVisible(false);
        jPanelAssessment.setVisible(false);
        jPanelLecturerRole.setVisible(true);
    }//GEN-LAST:event_viewLecturerBtnMouseClicked

    private void jButtonAssignAssessmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAssignAssessmentMouseClicked
        assignStudentAssessmentType();
    }//GEN-LAST:event_jButtonAssignAssessmentMouseClicked

    private void jButtonAssignLecturerRoleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAssignLecturerRoleMouseClicked
        assignLecturerRole();
    }//GEN-LAST:event_jButtonAssignLecturerRoleMouseClicked

    private void viewSuperviseeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewSuperviseeBtnMouseClicked
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(false);
        jPanelMarkerRequests.setVisible(false);
        jPanelViewSupervisee.setVisible(true);
        jPanelReport.setVisible(false);
        jPanelAssessment.setVisible(false);
        jPanelLecturerRole.setVisible(false);
    }//GEN-LAST:event_viewSuperviseeBtnMouseClicked

    private void markerRequestTabBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_markerRequestTabBtnMouseClicked
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(false);
        jPanelMarkerRequests.setVisible(true);
        jPanelViewSupervisee.setVisible(false);
        jPanelReport.setVisible(false);
        jPanelAssessment.setVisible(false);
        jPanelLecturerRole.setVisible(false);
    }//GEN-LAST:event_markerRequestTabBtnMouseClicked

    private void jTableSecondMarkerRequestsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSecondMarkerRequestsMouseClicked
        DefaultTableModel dtm = (DefaultTableModel) jTableSecondMarkerRequests.getModel();
        int selectedRequestId = (int) dtm.getValueAt(jTableSecondMarkerRequests.getSelectedRow(), 0);

        Request request = lecSecondMarkerRequests.stream()
                .filter(r -> r.getId() == selectedRequestId)
                .findFirst()
                .orElse(null);

        if (request == null) {
            JOptionPane.showMessageDialog(null, "Request is null: " + selectedRequestId);
        }

        jLabelSelectedReport.setText(Integer.toString(selectedRequestId));
        loadRequestFromSelectedItem();
    }//GEN-LAST:event_jTableSecondMarkerRequestsMouseClicked

    private void jButtonAcceptSecondMarkerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAcceptSecondMarkerMouseClicked
        DefaultTableModel dtm = (DefaultTableModel) jTableSecondMarkerRequests.getModel();
        int selectedRow = jTableSecondMarkerRequests.getSelectedRow();
        int reqId = (int) dtm.getValueAt(selectedRow, 0);

        acceptSecondMarkerRequest(reqId);
    }//GEN-LAST:event_jButtonAcceptSecondMarkerMouseClicked

    private void jButtonRejectSecondMarkerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonRejectSecondMarkerMouseClicked
        DefaultTableModel dtm = (DefaultTableModel) jTableSecondMarkerRequests.getModel();
        int selectedRow = jTableSecondMarkerRequests.getSelectedRow();
        int reqId = (int) dtm.getValueAt(selectedRow, 0);
        
        rejectSecondMarkerRequest(reqId);
    }//GEN-LAST:event_jButtonRejectSecondMarkerMouseClicked

    private void loadRequestFromSelectedItem() {
        DefaultTableModel dtm = (DefaultTableModel) jTableSecondMarkerRequests.getModel();
        int selectedRow = jTableSecondMarkerRequests.getSelectedRow();
        if (selectedRow == -1) {
            Helper.printErr("SecondMarker Selection Labels not updated.");
            return;    
        }
        int reqId = (int) dtm.getValueAt(selectedRow, 0);
        Request request = projectManager.fetchRequest(reqId);
        
        jLabelRequestLecturerId.setText(request.getLecturerId() == 0 ? "null" : Integer.toString(request.getLecturerId()));
        jLabelRequestLecturerName.setText(request.fetchLecturer() == null ? "null" : request.fetchLecturer().getName());
        jLabelRequestStudentId.setText(request.getStudentId() == 0 ? "null" : Integer.toString(request.getStudentId()));
        jLabelRequestStudentName.setText(request.fetchStudent() == null ? "null" : request.fetchStudent().getName());
        jLabelRequestType.setText(request.getRequestType() == null ? "null" : request.getRequestType().toString());
        jLabelRequestModule.setText(request.getModule() == null ? "null" : request.getModule());
        String approvedText = "null";
        Boolean approved = request.isApproved();
        if (approved != null) {
            approvedText = Boolean.toString(approved);
        }
        jLabelRequestApproved.setText(approvedText);
    }

    private void loadReportFromSelectedItem() {
        DefaultTableModel dtm = (DefaultTableModel) jTableReport.getModel();
        int reportId = (int) dtm.getValueAt(jTableReport.getSelectedRow(), 0);
        jLabelSelectedReport.setText(Integer.toString(reportId));
        
        DataContext context = new DataContext();
        Report report = context.getById(reportId);

        jLabelMoodle.setText(report.getMoodleLink());
        jLabelMarks.setText(Integer.toString(report.getStudentMark()));
        jLabelTotalMarks.setText(Integer.toString(report.getTotalMark()));
        jLabelContents.setText(report.getContents());
        jLabelProjectAssessmentType.setText(report.fetchProject().getAssessmentType().toString());
        jLabelProjectModule.setText(report.fetchProject().getModule());

        // These values not properly added into data
        jLabelStudentName.setText(report.fetchStudent().getName());
        jLabelStudentId.setText(Integer.toString(report.fetchStudent().getId()));
        Lecturer supervisor = (Lecturer) report.fetchStudent().fetchSupervisor();
        String supervisorName = supervisor == null ? "null" : supervisor.getName();
        jLabelSupervisor.setText(supervisorName);
    }

    private void requestBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_requestBtnActionPerformed
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(true);
        jPanelMarkerRequests.setVisible(false);
        jPanelViewSupervisee.setVisible(false);
        jPanelReport.setVisible(false);
        jTablePresentation.setEnabled(false);
        jPanelAssessment.setVisible(false);
        jPanelLecturerRole.setVisible(false);
    }// GEN-LAST:event_requestBtnActionPerformed

    private void viewSupviseeBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_viewSupviseeBtnActionPerformed
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(false);
        jPanelMarkerRequests.setVisible(false);
        jPanelViewSupervisee.setVisible(true);
        jPanelReport.setVisible(false);
        jPanelAssessment.setVisible(false);
        jPanelLecturerRole.setVisible(false);
    }// GEN-LAST:event_viewSupviseeBtnActionPerformed

    private void availabelSlotsBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_availabelSlotsBtnActionPerformed
        openAvailableSlots();
    }// GEN-LAST:event_availabelSlotsBtnActionPerformed

    private void dashboardBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_dashboardBtnActionPerformed
        openDashboard();
    }// GEN-LAST:event_dashboardBtnActionPerformed

    private void feedbackBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_feedbackBtnActionPerformed
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(false);
        jPanelMarkerRequests.setVisible(false);
        jPanelViewSupervisee.setVisible(false);
        jPanelReport.setVisible(true);
        jPanelAssessment.setVisible(false);
        jPanelLecturerRole.setVisible(false);

    }// GEN-LAST:event_feedbackBtnActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel1MouseClicked
        dispose();
    }// GEN-LAST:event_jLabel1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
                * If Nimbus (introduced in Java SE 6) is not available, stay with the default
                * look and feel.
                * For details see
                * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
                */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                    .getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProjectManagerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProjectManagerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(ProjectManagerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(ProjectManagerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ProjectManagerForm mainFormBody = new ProjectManagerForm();

                    // setTheme(mainFormBody);
                    mainFormBody.setVisible(true);
                }

                // private void setTheme(JFrame frame) {
                // SwingUtilities.invokeLater(new Runnable() {
                // @Override
                // public void run() {
                // try {
                // UIManager.setLookAndFeel(new FlatLightLaf());
                // SwingUtilities.updateComponentTreeUI(frame);
                // } catch (UnsupportedLookAndFeelException ex) {
                // Logger.getLogger(LecturerForm.class.getName()).log(Level.SEVERE, null, ex);
                // }
                // }
                // });
                // }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dashboardBtn;
    private javax.swing.JButton jButtonAcceptSecondMarker;
    private javax.swing.JButton jButtonApprovePresentation;
    private javax.swing.JButton jButtonAssignAssessment;
    private javax.swing.JButton jButtonAssignLecturerRole;
    private javax.swing.JButton jButtonFeedback;
    private javax.swing.JButton jButtonRejectSecondMarker;
    private javax.swing.JButton jButtonResetFilters;
    private javax.swing.JButton jButtonSwitchToSMApply;
    private javax.swing.JComboBox<AssessmentType> jComboBoxAssessmentType;
    private javax.swing.JComboBox<Student> jComboBoxFilterStudent;
    private javax.swing.JComboBox<Role> jComboBoxLecturerRoles;
    private javax.swing.JComboBox<Request> jComboBoxPresentations;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelContents;
    private javax.swing.JLabel jLabelMarks;
    private javax.swing.JLabel jLabelMoodle;
    private javax.swing.JLabel jLabelProjectAssessmentType;
    private javax.swing.JLabel jLabelProjectModule;
    private javax.swing.JLabel jLabelRequest;
    private javax.swing.JLabel jLabelRequest10;
    private javax.swing.JLabel jLabelRequest11;
    private javax.swing.JLabel jLabelRequest12;
    private javax.swing.JLabel jLabelRequest13;
    private javax.swing.JLabel jLabelRequest2;
    private javax.swing.JLabel jLabelRequest4;
    private javax.swing.JLabel jLabelRequest5;
    private javax.swing.JLabel jLabelRequest7;
    private javax.swing.JLabel jLabelRequest8;
    private javax.swing.JLabel jLabelRequest9;
    private javax.swing.JLabel jLabelRequestApproved;
    private javax.swing.JLabel jLabelRequestLecturerId;
    private javax.swing.JLabel jLabelRequestLecturerName;
    private javax.swing.JLabel jLabelRequestModule;
    private javax.swing.JLabel jLabelRequestStudentId;
    private javax.swing.JLabel jLabelRequestStudentName;
    private javax.swing.JLabel jLabelRequestType;
    private javax.swing.JLabel jLabelSelectedReport;
    private javax.swing.JLabel jLabelStudentId;
    private javax.swing.JLabel jLabelStudentName;
    private javax.swing.JLabel jLabelSupervisor;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelTotalMarks;
    private javax.swing.JLabel jLabelUsername;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelAssessment;
    private javax.swing.JPanel jPanelContents;
    private javax.swing.JPanel jPanelDashboard;
    private javax.swing.JPanel jPanelDragLeft;
    private javax.swing.JPanel jPanelLecturerRole;
    private javax.swing.JPanel jPanelMarkerRequests;
    private javax.swing.JPanel jPanelReport;
    private javax.swing.JPanel jPanelSide;
    private javax.swing.JPanel jPanelTitle;
    private javax.swing.JPanel jPanelViewPresentation;
    private javax.swing.JPanel jPanelViewSupervisee;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTableLecturerRoles;
    private javax.swing.JTable jTablePresentation;
    private javax.swing.JTable jTableReport;
    private javax.swing.JTable jTableSecondMarkerRequests;
    private javax.swing.JTable jTableStudentAssessmentTypes;
    private javax.swing.JTable jTableStudentAssignPM;
    private javax.swing.JTable jTableViewSupervisee;
    private javax.swing.JTextArea jTextAreaFeedback;
    private javax.swing.JButton markerRequestTabBtn;
    private javax.swing.JButton requestBtn;
    private javax.swing.JButton viewAssessmentBtn;
    private javax.swing.JButton viewLecturerBtn;
    private javax.swing.JButton viewReportBtn;
    private javax.swing.JButton viewSuperviseeBtn;
    // End of variables declaration//GEN-END:variables
}
