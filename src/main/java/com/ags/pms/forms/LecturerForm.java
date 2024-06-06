/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ags.pms.forms;

import com.ags.pms.Helper;
import com.ags.pms.data.DataContext;
import com.ags.pms.models.Lecturer;
import com.ags.pms.models.Report;
import com.ags.pms.models.Request;
import com.ags.pms.models.Role;
import com.ags.pms.models.Student;
import com.ags.pms.models.User;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
/**
 *
 * @author Genzoku
 */
public class LecturerForm extends javax.swing.JFrame {

    private Lecturer lecturer;
    private int selectedReportId;
    private int selectedStudentId;

    public LecturerForm() {
    initComponents();

    try {
        this.lecturer = new Lecturer(2001, "Joshua Samuel", "01/01/1980", "joshua.lecturer@gmail.com", "lc", "lc", Role.NONE);
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
        jTextAreaSelectedReportDetails.setEditable(false);
        jLabelUsername.setText(lecturer.getUsername());
        populatePresentationsRequestTable();
        populateSecondMarkerTable();
        populateSecondMarkerAcceptence();
        populateSecondMarkerComboBox();
        populatePresentationRequestComboBox();
        populateReportsTable();
    }

    private void openDashboard() {
        jPanelDashboard.setVisible(true);
        jPanelViewPresentation.setVisible(false);
        jPanelAvailableSlots.setVisible(false);
        jPanelReport.setVisible(false);
    }

    private void openAvailableSlots() {
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(false);
        jPanelAvailableSlots.setVisible(true);
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
        
        DateFormat formatter = Helper.getDateFormat();

        for (int i = 0; i < reports.size(); i++) {
            Object rowData[] = new Object[5];
            
            rowData[0] = reports.get(i).getId();
            rowData[1] = reports.get(i).getStudentId();
            rowData[2] = formatter.format(reports.get(i).getDateSubmitted());
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

        sleep(300);
    }

    private void populatePresentationsRequestTable() {
        DefaultTableModel model = (DefaultTableModel) jTablePresentation.getModel();

        model.setRowCount(0);

        ArrayList<Request> presentations = lecturer.viewPendingPresentationRequests();
        DateFormat formatter = Helper.getDateFormat();        

        for (int i = 0; i < presentations.size(); i++) {
            Object rowData[] = new Object[6]; // Moved declaration inside the loop
            
            rowData[0] = presentations.get(i).getId();
            rowData[1] = presentations.get(i).getStudentId();
            rowData[2] = presentations.get(i).viewUser().getName();
            rowData[4] = presentations.get(i).getModule();
            rowData[3] = formatter.format(presentations.get(i).fetchPresentationSlot().getPresentationDate());
            rowData[5] = presentations.get(i).isApproved();

            model.addRow(rowData);
        }
        
        jTablePresentation.setFocusable(false);
        jTablePresentation.setRowSelectionAllowed(false);
        jTablePresentation.setCellSelectionEnabled(false);
    }

    private void popupModuleSelection(int studentId) {
        ArrayList<String> modules = lecturer.getStudentModules(studentId);
        jComboBoxStudentModules.removeAllItems();
        modules.forEach(m -> jComboBoxStudentModules.addItem(m));

        jFrameSecondMarkerModuleSelect.pack();
        jFrameSecondMarkerModuleSelect.setResizable(false);
        jFrameSecondMarkerModuleSelect.setLocationRelativeTo(null);
        jFrameSecondMarkerModuleSelect.setVisible(true);
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

        jFrameSecondMarkerModuleSelect = new javax.swing.JFrame();
        jPanelMeh = new javax.swing.JPanel();
        jComboBoxStudentModules = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jButtonSelectModule = new javax.swing.JButton();
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
        availabelSlotsBtn = new javax.swing.JButton();
        feedbackBtn = new javax.swing.JButton();
        jPanelContents = new javax.swing.JPanel();
        jPanelDashboard = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
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
        jPanelReport = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableReport = new javax.swing.JTable();
        jPanelReportArea = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaSelectedReportDetails = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextAreaFeedback = new javax.swing.JTextArea();
        jButtonFeedback = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabelSelectedReport = new javax.swing.JLabel();

        jFrameSecondMarkerModuleSelect.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelMeh.setBackground(new java.awt.Color(204, 204, 255));

        jComboBoxStudentModules.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Select Module");

        jButtonSelectModule.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonSelectModule.setText("Select");
        jButtonSelectModule.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSelectModuleMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelMehLayout = new javax.swing.GroupLayout(jPanelMeh);
        jPanelMeh.setLayout(jPanelMehLayout);
        jPanelMehLayout.setHorizontalGroup(
            jPanelMehLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMehLayout.createSequentialGroup()
                .addGroup(jPanelMehLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMehLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel8))
                    .addGroup(jPanelMehLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jButtonSelectModule))
                    .addGroup(jPanelMehLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jComboBoxStudentModules, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanelMehLayout.setVerticalGroup(
            jPanelMehLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMehLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBoxStudentModules, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonSelectModule)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jFrameSecondMarkerModuleSelect.getContentPane().add(jPanelMeh, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 170));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
        jPanelSide.add(feedbackBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 237, 37));

        jPanelContents.setBackground(new java.awt.Color(153, 153, 255));
        jPanelContents.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelDashboard.setBackground(new java.awt.Color(204, 204, 255));
        jPanelDashboard.setPreferredSize(new java.awt.Dimension(800, 560));

        jPanel4.setBackground(new java.awt.Color(0, 0, 102));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("12");

        jLabel17.setFont(new java.awt.Font("STZhongsong", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Currently remaining Slots");

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("STZhongsong", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Slots");
        jLabel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(31, 31, 31))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(23, 23, 23))
        );

        jPanel5.setBackground(new java.awt.Color(0, 153, 153));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("8");

        jLabel22.setFont(new java.awt.Font("STZhongsong", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Request made already");

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("STZhongsong", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Presentation Request");
        jLabel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(22, 22, 22))
        );

        jTable1.setBackground(new java.awt.Color(153, 255, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable1);

        jLabel11.setBackground(new java.awt.Color(51, 255, 0));
        jLabel11.setFont(new java.awt.Font("STZhongsong", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 0));
        jLabel11.setText("Supervisee List");
        jLabel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanelDashboardLayout = new javax.swing.GroupLayout(jPanelDashboard);
        jPanelDashboard.setLayout(jPanelDashboardLayout);
        jPanelDashboardLayout.setHorizontalGroup(
            jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        jPanelDashboardLayout.setVerticalGroup(
            jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelDashboardLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(2, 2, 2)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(321, Short.MAX_VALUE))
        );

        jPanelContents.add(jPanelDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanelViewPresentation.setBackground(new java.awt.Color(204, 204, 255));
        jPanelViewPresentation.setPreferredSize(new java.awt.Dimension(800, 560));
        jPanelViewPresentation.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTablePresentation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Student ID", "Student Name", "Module", "Date", "Approved"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
            jTablePresentation.getColumnModel().getColumn(5).setMaxWidth(100);
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
        jPanel2.add(jButtonApprovePresentation, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, -1, 33));

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

        jPanelReport.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 780, 110));

        jPanelReportArea.setBackground(new java.awt.Color(204, 204, 255));
        jPanelReportArea.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane3.setEnabled(false);
        jScrollPane3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jTextAreaSelectedReportDetails.setBackground(new java.awt.Color(255, 255, 255));
        jTextAreaSelectedReportDetails.setColumns(20);
        jTextAreaSelectedReportDetails.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextAreaSelectedReportDetails.setForeground(new java.awt.Color(0, 0, 0));
        jTextAreaSelectedReportDetails.setRows(5);
        jTextAreaSelectedReportDetails.setFocusable(false);
        jScrollPane3.setViewportView(jTextAreaSelectedReportDetails);

        jPanelReportArea.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 370));

        jPanelReport.add(jPanelReportArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 150, 570, 370));

        jTextAreaFeedback.setColumns(20);
        jTextAreaFeedback.setRows(5);
        jScrollPane5.setViewportView(jTextAreaFeedback);

        jPanelReport.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 200, 340));

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
                .addComponent(jPanelContents, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE))
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
        lecturer.assignPresentationSlot(request.getId(), request.getStudentId(), request.getPresentationSlotId(), request.getModule());
        JOptionPane.showMessageDialog(null, "Presentation Request Approved!");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        populatePresentationsRequestTable();
        populatePresentationRequestComboBox();
    }//GEN-LAST:event_jButtonApprovePresentationActionPerformed

    private void jButtonFeedbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFeedbackActionPerformed
        String feedback = jTextAreaFeedback.getText();
        lecturer.evaluateReport(selectedReportId, feedback);
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}

        populateReportsTable();
    }//GEN-LAST:event_jButtonFeedbackActionPerformed

    private void jTableReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableReportMouseClicked
        DefaultTableModel dtm = (DefaultTableModel) jTableReport.getModel();
        selectedReportId = (int) dtm.getValueAt(jTableReport.getSelectedRow(), 0);
        jLabelSelectedReport.setText(Integer.toString(selectedReportId));
        loadReportFromSelectedItem();

    }//GEN-LAST:event_jTableReportMouseClicked

    private void jButtonSelectModuleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSelectModuleMouseClicked
        if (jComboBoxStudentModules.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(null, "Please select a module.");
            return;
        }
        String selectedModule = jComboBoxStudentModules.getSelectedItem().toString();

        lecturer.applyForSecondMarker(selectedStudentId, selectedModule);
        JOptionPane.showMessageDialog(null, 
            "Second Marker Request has been submitted. Please wait for review from Project Manager");
        
        jFrameSecondMarkerModuleSelect.dispose();

        sleep(100);

        populateSecondMarkerComboBox();
        populateSecondMarkerAcceptence();

    }//GEN-LAST:event_jButtonSelectModuleMouseClicked



    private void loadReportFromSelectedItem() {
        DataContext context = new DataContext();
        Report report = context.getById(selectedReportId);

        String studentId = Integer.toString(report.fetchStudent().getId());
        String studentName = report.fetchStudent().getName();
        
        Lecturer supervisor = (Lecturer) report.fetchStudent().fetchSupervisor();
        String supervisorName = supervisor == null ? "null" : supervisor.getName();
        
        String projectModule = report.fetchProject().getModule();
        String projectAssessmentType = report.fetchProject().getAssessmentType().toString();
        String contents = report.getContents();
        String totalMarks = Integer.toString(report.getTotalMark());
        String studentMarks = Integer.toString(report.getStudentMark());
        String moodleLink = report.getMoodleLink();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Student ID: " + studentId).append("\n")
            .append("Student Name: " + studentName).append("\n")
            .append("Supervisor Name: " + supervisorName).append("\n")
            .append("Module: " + projectModule).append("\n")
            .append("Assessment Type: " + projectAssessmentType).append("\n")
            .append("Contents: " + contents).append("\n")
            .append("Total Marks: " + totalMarks).append("\n")
            .append("Student Marks: " + studentMarks).append("\n")
            .append("Moodle Link: " + moodleLink);
        
        String reportDetails = stringBuilder.toString();
        jTextAreaSelectedReportDetails.setText(reportDetails);
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
        selectedStudentId = student.getId();

        popupModuleSelection(student.getId());


    }// GEN-LAST:event_jButtonApplySecondMarkerActionPerformed


    private void requestBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_requestBtnActionPerformed
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(true);
        jPanelAvailableSlots.setVisible(false);
        jPanelReport.setVisible(false);
        jTablePresentation.setEnabled(false);
    }// GEN-LAST:event_requestBtnActionPerformed

    private void viewSupviseeBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_viewSupviseeBtnActionPerformed
        jPanelDashboard.setVisible(false);
        jPanelViewPresentation.setVisible(false);
        jPanelAvailableSlots.setVisible(false);
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

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton availabelSlotsBtn;
    private javax.swing.JButton dashboardBtn;
    private javax.swing.JButton feedbackBtn;
    private javax.swing.JButton jButtonApplySecondMarker;
    private javax.swing.JButton jButtonApprovePresentation;
    private javax.swing.JButton jButtonFeedback;
    private javax.swing.JButton jButtonSelectModule;
    private javax.swing.JComboBox<Request> jComboBoxPresentations;
    private javax.swing.JComboBox<String> jComboBoxStudentModules;
    private javax.swing.JComboBox<Student> jComboBoxStudentSecondMarker;
    private javax.swing.JFrame jFrameSecondMarkerModuleSelect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelUsername;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelAvailableSlots;
    private javax.swing.JPanel jPanelContents;
    private javax.swing.JPanel jPanelDashboard;
    private javax.swing.JPanel jPanelDragLeft;
    private javax.swing.JPanel jPanelMeh;
    private javax.swing.JPanel jPanelReport;
    private javax.swing.JPanel jPanelReportArea;
    private javax.swing.JPanel jPanelSide;
    private javax.swing.JPanel jPanelTitle;
    private javax.swing.JPanel jPanelViewPresentation;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTablePresentation;
    private javax.swing.JTable jTableReport;
    private javax.swing.JTable jTableSecondMarkerSlots;
    private javax.swing.JTextArea jTextAreaFeedback;
    private javax.swing.JTextArea jTextAreaSelectedReportDetails;
    private javax.swing.JButton requestBtn;
    // End of variables declaration//GEN-END:variables
}
