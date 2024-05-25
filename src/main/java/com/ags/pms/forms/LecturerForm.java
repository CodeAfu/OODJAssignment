/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ags.pms.forms;

import com.ags.pms.data.DataContext;
import com.ags.pms.models.Identifiable;
import com.ags.pms.models.Lecturer;
import com.ags.pms.models.Report;
import com.ags.pms.models.Request;
import com.ags.pms.models.Role;
import com.ags.pms.models.Student;
import com.ags.pms.models.User;
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


/**
 *
 * @author Genzoku
 */
public class LecturerForm extends javax.swing.JFrame {

    private Lecturer lecturer;
    private int selectedReportId;

    public LecturerForm() {
    initComponents();

    try {
        this.lecturer = new Lecturer(2001, "Joshua", "11/01/1980", "joshua@lecturer.com",
                        "josh_lecturer","verySecurePasswordMate", Role.NONE);
    } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
            | BadPaddingException | InvalidAlgorithmParameterException e) {
        e.printStackTrace();
    }

        openDashboard();  
        populateData();
    }

    public LecturerForm(User user) {
        initComponents();
        this.lecturer = (Lecturer) user;

        openDashboard();
        populateData();
    }

    private void populateData() {
        jLabelUsername.setText(lecturer.getUsername());
        populatePresentationsRequestTable();
        populateSecondMarkerTable();
        populateSecondMarkerAcceptence();
        populateSecondMarkerComboBox();
        populateSupervisees();
        populatePresentationRequestComboBox();
        populateReportsTable();
        populateFilterStudentComboBox();
    }

    private void openDashboard() {
        jPanelDashboard.setVisible(true);
        jPanelViewPresentation.setVisible(false);
        jPanelAvailableSlots.setVisible(false);
        jPanelViewSupervisee.setVisible(false);
        jPanelReport.setVisible(false);
    }

    private void openAvailableSlots() {
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(false);
        jPanelAvailableSlots.setVisible(true);
        jPanelViewSupervisee.setVisible(false);
        jPanelReport.setVisible(false);
    }

    private void populateSecondMarkerTable() {
        DefaultTableModel model = (DefaultTableModel) jTableSecondMarkerSlots.getModel();
        model.setRowCount(0);
        ArrayList<Student> students = lecturer.viewSecondMarkerSlots();

        for (int i = 0; i < students.size(); i++) {

             Object rowData[] = new Object[3];
             rowData[0] = students.get(i).getId();
             rowData[1] = students.get(i).getName();
             rowData[2] = students.get(i).getSecondMarkerId() != 0;
     
             model.addRow(rowData);
        }

        jTableSecondMarkerSlots.setFocusable(false);
        jTableSecondMarkerSlots.setRowSelectionAllowed(false);
        jTableSecondMarkerSlots.setCellSelectionEnabled(false);

    }
    
    private void populateSecondMarkerComboBox() {
        jComboBoxStudentSecondMarker.removeAllItems();
        ArrayList<Student> studentSecondMarkerList = lecturer.viewSecondMarkerSlots();
        studentSecondMarkerList.forEach(s -> jComboBoxStudentSecondMarker.addItem(s));
    }

    private void populatePresentationRequestComboBox() {
        jComboBoxPresentations.removeAllItems();
        ArrayList<Request> presentationRequests = lecturer.viewPendingPresentationRequests();
        presentationRequests.forEach(p -> jComboBoxPresentations.addItem(p));
    }

    private void populateSupervisees() {
        DefaultTableModel model = (DefaultTableModel) jTableViewSupervisee.getModel();
        model.setRowCount(0);
        ArrayList<Map<String, Object>> students = lecturer.viewSupervisees();

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
        ArrayList<Map<String, Object>> studentsWithReports = lecturer.viewAllStudentsWithReports();

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
        ArrayList<Map<String, Object>> studentsWithReports = lecturer.viewAllStudentsWithReports();

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
        lecturer.viewAllStudentsWithReports().forEach(s -> {
            students.add((Student) s.get("student"));
        }); 

        students.forEach(s -> jComboBoxFilterStudent.addItem(s));
    }

    private void populateSecondMarkerAcceptence() {
        Map<String, Object> result = lecturer.viewSecondMarkerAcceptance();

        Student student = (Student) result.get("student");
        Lecturer myLecturer = (Lecturer) result.get("lecturer");
        boolean isApproved = (boolean) result.get("approved");

        jLabelRequestLecturerId.setText(Integer.toString(myLecturer.getId()));
        jLabelRequestLecturerName.setText(myLecturer.getName());
        jLabelRequestStudentId.setText(Integer.toString(student.getId()));
        jLabelRequestStudentName.setText(student.getName());
        jLabelRequestIsApproved.setText(isApproved ? "Approved" : "Pending");

        DefaultTableModel model = (DefaultTableModel) jTablePresentation.getModel();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
        }

    }

    private void populatePresentationsRequestTable() {
        DefaultTableModel model = (DefaultTableModel) jTablePresentation.getModel();

        model.setRowCount(0);

        ArrayList<Request> presentations = lecturer.viewPendingPresentationRequests();
        
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
        jLabelUsername = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanelSide = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanelDragLeft = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        requestBtn = new javax.swing.JButton();
        dashboardBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        viewSupviseeBtn = new javax.swing.JButton();
        availabelSlotsBtn = new javax.swing.JButton();
        feedbackBtn = new javax.swing.JButton();
        jPanelContents = new javax.swing.JPanel();
        jPanelDashboard = new javax.swing.JPanel();
        jPanelViewPresentation = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePresentation = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButtonApprovePresentation = new javax.swing.JButton();
        jComboBoxPresentations = new javax.swing.JComboBox<>();
        jPanelAvailableSlots = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableSecondMarkerSlots = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jComboBoxStudentSecondMarker = new javax.swing.JComboBox<>();
        jLabelRequest = new javax.swing.JLabel();
        jLabelRequest1 = new javax.swing.JLabel();
        jButtonApplySecondMarker = new javax.swing.JButton();
        jLabelRequest3 = new javax.swing.JLabel();
        jLabelRequestIsApproved = new javax.swing.JLabel();
        jLabelRequest6 = new javax.swing.JLabel();
        jLabelRequest7 = new javax.swing.JLabel();
        jLabelRequest8 = new javax.swing.JLabel();
        jLabelRequest9 = new javax.swing.JLabel();
        jLabelRequestLecturerName = new javax.swing.JLabel();
        jLabelRequestStudentId = new javax.swing.JLabel();
        jLabelRequestStudentName = new javax.swing.JLabel();
        jLabelRequest10 = new javax.swing.JLabel();
        jLabelRequestLecturerId = new javax.swing.JLabel();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 700));
        setResizable(false);

        jPanelTitle.setBackground(new java.awt.Color(245, 246, 248));

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 2, 36)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(0, 0, 51));
        jLabelTitle.setText("Project Management System");

        jLabelUsername.setForeground(new java.awt.Color(0, 0, 0));
        jLabelUsername.setText("sample");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Welcome!");

        javax.swing.GroupLayout jPanelTitleLayout = new javax.swing.GroupLayout(jPanelTitle);
        jPanelTitle.setLayout(jPanelTitleLayout);
        jPanelTitleLayout.setHorizontalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitleLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jLabelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );
        jPanelTitleLayout.setVerticalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitleLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTitleLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelUsername))
                    .addComponent(jLabelTitle))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanelSide.setBackground(new java.awt.Color(83, 116, 240));
        jPanelSide.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanelSide.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 266, -1, -1));

        jPanelDragLeft.setBackground(new java.awt.Color(102, 102, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Lecturer");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/Male User_3.png"))); // NOI18N

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Logout");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelDragLeftLayout = new javax.swing.GroupLayout(jPanelDragLeft);
        jPanelDragLeft.setLayout(jPanelDragLeftLayout);
        jPanelDragLeftLayout.setHorizontalGroup(
            jPanelDragLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(requestBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDragLeftLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(dashboardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelDragLeftLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelDragLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanelDragLeftLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        jPanelDragLeftLayout.setVerticalGroup(
            jPanelDragLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDragLeftLayout.createSequentialGroup()
                .addGroup(jPanelDragLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDragLeftLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE))
                    .addGroup(jPanelDragLeftLayout.createSequentialGroup()
                        .addGroup(jPanelDragLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDragLeftLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelDragLeftLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)))
                .addComponent(dashboardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(requestBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        jPanelSide.add(jPanelDragLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        viewSupviseeBtn.setBackground(new java.awt.Color(110, 139, 251));
        viewSupviseeBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        viewSupviseeBtn.setForeground(new java.awt.Color(0, 0, 0));
        viewSupviseeBtn.setText("View Supervisee List");
        viewSupviseeBtn.setBorderPainted(false);
        viewSupviseeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSupviseeBtnActionPerformed(evt);
            }
        });
        jPanelSide.add(viewSupviseeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 237, 37));

        availabelSlotsBtn.setBackground(new java.awt.Color(110, 139, 251));
        availabelSlotsBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        availabelSlotsBtn.setForeground(new java.awt.Color(0, 0, 0));
        availabelSlotsBtn.setText("Available Slots");
        availabelSlotsBtn.setBorderPainted(false);
        availabelSlotsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                availabelSlotsBtnActionPerformed(evt);
            }
        });
        jPanelSide.add(availabelSlotsBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 223, 240, 37));

        feedbackBtn.setBackground(new java.awt.Color(110, 139, 251));
        feedbackBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        feedbackBtn.setForeground(new java.awt.Color(0, 0, 0));
        feedbackBtn.setText("Report With Feedback");
        feedbackBtn.setBorderPainted(false);
        feedbackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feedbackBtnActionPerformed(evt);
            }
        });
        jPanelSide.add(feedbackBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 237, 37));

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

        jPanelAvailableSlots.setBackground(new java.awt.Color(204, 204, 255));
        jPanelAvailableSlots.setPreferredSize(new java.awt.Dimension(800, 560));
        jPanelAvailableSlots.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableSecondMarkerSlots.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Has Marker"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Boolean.class
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
        jScrollPane2.setViewportView(jTableSecondMarkerSlots);
        if (jTableSecondMarkerSlots.getColumnModel().getColumnCount() > 0) {
            jTableSecondMarkerSlots.getColumnModel().getColumn(0).setMaxWidth(80);
            jTableSecondMarkerSlots.getColumnModel().getColumn(2).setPreferredWidth(20);
        }

        jPanelAvailableSlots.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, 480));

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.add(jComboBoxStudentSecondMarker, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 100, -1));

        jLabelRequest.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelRequest.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest.setText("My Request:");
        jPanel1.add(jLabelRequest, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 90, 20));

        jLabelRequest1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelRequest1.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest1.setText("Apply for Second Marker");
        jPanel1.add(jLabelRequest1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 220, 40));

        jButtonApplySecondMarker.setText("Apply");
        jButtonApplySecondMarker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApplySecondMarkerActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonApplySecondMarker, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, 80, 30));

        jLabelRequest3.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest3.setText("Student");
        jPanel1.add(jLabelRequest3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 60, 20));

        jLabelRequestIsApproved.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequestIsApproved.setText("null");
        jPanel1.add(jLabelRequestIsApproved, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 100, 20));

        jLabelRequest6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelRequest6.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest6.setText("Approved:");
        jPanel1.add(jLabelRequest6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 60, 20));

        jLabelRequest7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelRequest7.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest7.setText("Lecturer Name:");
        jPanel1.add(jLabelRequest7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 90, 20));

        jLabelRequest8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelRequest8.setForeground(new java.awt.Color(0, 51, 51));
        jLabelRequest8.setText("Student Name:");
        jPanel1.add(jLabelRequest8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 90, 20));

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

        jPanelAvailableSlots.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 240, 410));

        jPanelContents.add(jPanelAvailableSlots, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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
        lecturer.assignPresentationSlot(request.getId(), request.getStudentId(), request.getPresentationSlotId());
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
        lecturer.evaluateReport(selectedReportId, feedback);
        
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

    private void loadReportFromSelectedItem() {
        DataContext context = new DataContext();
        Report report = context.getById(selectedReportId);

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

    private void jButtonApplySecondMarkerActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonApplySecondMarkerActionPerformed
        if (jComboBoxStudentSecondMarker.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Please select an entry.");
            return;
        }
        Student student = (Student) jComboBoxStudentSecondMarker.getSelectedItem();
        if (lecturer.hasSecondMarkerRequest()) {
            String message = "You already have a pending request. Overwrite?";
            int reply = JOptionPane.showConfirmDialog(null, message, "", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.NO_OPTION) {
                return;
            }
        }
        lecturer.applyForSecondMarker(student.getId());
        JOptionPane.showMessageDialog(null, 
            "Second Marker Request has been submitted. Please wait for review from Project Manager");
        populateSecondMarkerComboBox();
        populateSecondMarkerAcceptence();
    }// GEN-LAST:event_jButtonApplySecondMarkerActionPerformed

    private void requestBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_requestBtnActionPerformed
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(true);
        jPanelAvailableSlots.setVisible(false);
        jPanelViewSupervisee.setVisible(false);
        jPanelReport.setVisible(false);
        jTablePresentation.setEnabled(false);
    }// GEN-LAST:event_requestBtnActionPerformed

    private void viewSupviseeBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_viewSupviseeBtnActionPerformed
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(false);
        jPanelAvailableSlots.setVisible(false);
        jPanelViewSupervisee.setVisible(true);
        jPanelReport.setVisible(false);
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
        jPanelAvailableSlots.setVisible(false);
        jPanelViewSupervisee.setVisible(false);
        jPanelReport.setVisible(true);

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
            java.util.logging.Logger.getLogger(LecturerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LecturerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(LecturerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(LecturerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LecturerForm mainFormBody = new LecturerForm();

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
    private javax.swing.JButton availabelSlotsBtn;
    private javax.swing.JButton dashboardBtn;
    private javax.swing.JButton feedbackBtn;
    private javax.swing.JButton jButtonApplySecondMarker;
    private javax.swing.JButton jButtonApprovePresentation;
    private javax.swing.JButton jButtonFeedback;
    private javax.swing.JButton jButtonResetFilters;
    private javax.swing.JButton jButtonSwitchToSMApply;
    private javax.swing.JComboBox<Student> jComboBoxFilterStudent;
    private javax.swing.JComboBox<Request> jComboBoxPresentations;
    private javax.swing.JComboBox<Student> jComboBoxStudentSecondMarker;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelContents;
    private javax.swing.JLabel jLabelMarks;
    private javax.swing.JLabel jLabelMoodle;
    private javax.swing.JLabel jLabelProjectAssessmentType;
    private javax.swing.JLabel jLabelProjectModule;
    private javax.swing.JLabel jLabelRequest;
    private javax.swing.JLabel jLabelRequest1;
    private javax.swing.JLabel jLabelRequest10;
    private javax.swing.JLabel jLabelRequest3;
    private javax.swing.JLabel jLabelRequest6;
    private javax.swing.JLabel jLabelRequest7;
    private javax.swing.JLabel jLabelRequest8;
    private javax.swing.JLabel jLabelRequest9;
    private javax.swing.JLabel jLabelRequestIsApproved;
    private javax.swing.JLabel jLabelRequestLecturerId;
    private javax.swing.JLabel jLabelRequestLecturerName;
    private javax.swing.JLabel jLabelRequestStudentId;
    private javax.swing.JLabel jLabelRequestStudentName;
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
    private javax.swing.JPanel jPanelAvailableSlots;
    private javax.swing.JPanel jPanelContents;
    private javax.swing.JPanel jPanelDashboard;
    private javax.swing.JPanel jPanelDragLeft;
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
    private javax.swing.JTable jTablePresentation;
    private javax.swing.JTable jTableReport;
    private javax.swing.JTable jTableSecondMarkerSlots;
    private javax.swing.JTable jTableViewSupervisee;
    private javax.swing.JTextArea jTextAreaFeedback;
    private javax.swing.JButton requestBtn;
    private javax.swing.JButton viewSupviseeBtn;
    // End of variables declaration//GEN-END:variables
}
