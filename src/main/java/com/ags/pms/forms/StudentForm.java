/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ags.pms.forms;

import com.ags.pms.data.DataContext;
import com.ags.pms.data.IDHandler;
import com.ags.pms.models.Identifiable;
import com.ags.pms.models.Lecturer;
import com.ags.pms.models.PresentationSlot;
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
import java.util.Arrays;
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
public class StudentForm extends javax.swing.JFrame {

    private Student student;

    public StudentForm() {
    initComponents();

    try {
        this.student = new Student(4002, "John Kumar", "09/03/2024", "johnkumar@email.com", "john_kumar", "GoodStuff", new ArrayList<Integer>(Arrays.asList(8000, 8001, 8002)));
    } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
            | BadPaddingException | InvalidAlgorithmParameterException e) {
        e.printStackTrace();
    }
        openDashboard();  
        populateData();
    }

    public StudentForm(User user) {
        initComponents();
        this.student = (Student) user;

        openDashboard();
        populateData();
    }

    private void populateData() {
        jLabelDashboardName.setText(student.getName());
        jLabelUsername.setText(student.getUsername());

        populateReportsTables();
        populatePendingPresentationTable();
        populateApprovedPresentationTable();

    }

    private void openDashboard() {
        jPanelDashboard.setVisible(true);
        jPanelPresentation.setVisible(false);
        jPanelReports.setVisible(false);
        jPanelMyRequests.setVisible(false);
    }

    private void populateReportsTables() {
        DefaultTableModel model = (DefaultTableModel) jTableReport.getModel();
        DefaultTableModel model2 = (DefaultTableModel) jTableDashboardReport.getModel();

        model.setRowCount(0);
        model2.setRowCount(0);

        ArrayList<Report> reports = student.fetchReports();
        
        for (int i = 0; i < reports.size(); i++) {
            Object rowData[] = new Object[6]; // Moved declaration inside the loop
            
            rowData[0] = reports.get(i).getId();
            rowData[1] = student.getName();
            rowData[2] = reports.get(i).fetchProject().getAssessmentType();
            rowData[3] = reports.get(i).getMoodleLink();
            rowData[4] = reports.get(i).getStudentMark();
            rowData[5] = reports.get(i).getTotalMark();

            model.addRow(rowData);
            model2.addRow(rowData);
        }
    }

    private void addReport() {
        int projectId = Integer.parseInt(jTextFieldProjectID.getText());
        int studentId = Integer.parseInt(jTextFieldStudentID.getText());
        String moodle = jTextFieldMoodleLink.getText();
        String contents = jTextAreaReportContents.getText();

        student.createReport(studentId, projectId, moodle, contents);

        JOptionPane.showMessageDialog(null, "Report Created");

        jFrameCreateReport.dispose();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}

        populateReportsTables();
    }

    private void createReportPopup() {
        IDHandler id = new IDHandler();
        id.initFromFile();

        jTextFieldProjectID.setText(Integer.toString(id.getNextProjectId() - 1));
        jTextFieldStudentID.setText(Integer.toString(student.getId()));
        
        jFrameCreateReport.pack();
        jFrameCreateReport.setLocationRelativeTo(null);
        jFrameCreateReport.setResizable(false);
        jFrameCreateReport.setVisible(true);
    }

    private void removeReport() {
        DefaultTableModel model = (DefaultTableModel) jTableReport.getModel();
        int selectedIndex = jTableReport.getSelectedRow();

        if (selectedIndex == -1 ) {
            JOptionPane.showMessageDialog(null, "Please select a report from the table.");
            return;
        }
        
        int reportId = (int) model.getValueAt(selectedIndex, 0);
        
        int response = JOptionPane.showConfirmDialog(null, "Remove Report: " + reportId + "?", "Confirm?", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "Operation Cancelled");
            return;
        }
        
        student.removeReport(reportId);
        
        JOptionPane.showMessageDialog(null, "Report Deleted");

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}
        
        populateReportsTables();
    }

    private void populatePendingPresentationTable() {
        DefaultTableModel model = (DefaultTableModel) jTablePendingPresentations.getModel();

        model.setRowCount(0);

        ArrayList<Request> requests = student.viewPendingPresentationRequests();
        
        for (int i = 0; i < requests.size(); i++) {
            Object rowData[] = new Object[5]; // Moved declaration inside the loop
            
            // id name module presentation availability
            rowData[0] = requests.get(i).getId();
            rowData[1] = student.getName();
            rowData[2] = requests.get(i).getModule();
            rowData[3] = requests.get(i).fetchPresentationSlot().getPresentationDate();
            rowData[4] = requests.get(i).fetchPresentationSlot().isAvailable();

            model.addRow(rowData);
        }
    }

    private void populateApprovedPresentationTable() {
        DefaultTableModel model = (DefaultTableModel) jTableApprovedPresentations.getModel();

        model.setRowCount(0);

        ArrayList<Request> requests = student.viewApprovedPresentationRequests();
        
        for (int i = 0; i < requests.size(); i++) {
            Object rowData[] = new Object[5]; // Moved declaration inside the loop
            
            rowData[0] = requests.get(i).getId();
            rowData[1] = student.getName();
            rowData[2] = requests.get(i).getModule();
            rowData[3] = requests.get(i).fetchPresentationSlot().getPresentationDate();
            rowData[4] = requests.get(i).fetchPresentationSlot().isAvailable();

            model.addRow(rowData);
        }
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

        jFrameCreateReport = new javax.swing.JFrame();
        jPanelCreateReport = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldStudentID = new javax.swing.JTextField();
        jTextFieldProjectID = new javax.swing.JTextField();
        jTextFieldMoodleLink = new javax.swing.JTextField();
        jButtonCreate = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextAreaReportContents = new javax.swing.JTextArea();
        jFrameRequestPresentation = new javax.swing.JFrame();
        jPanelRequestPresentation = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextFieldModule = new javax.swing.JTextField();
        jTextFieldPresentationDate = new javax.swing.JTextField();
        jTextFieldStudentIDPresentation = new javax.swing.JTextField();
        jTextFieldAvailability = new javax.swing.JTextField();
        jButtonRequestPresentation = new javax.swing.JButton();
        jPanelTitle = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jPanelSide = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanelDragLeft = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dashboardBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        reportBtn = new javax.swing.JButton();
        requestsBtn = new javax.swing.JButton();
        presentationBtn = new javax.swing.JButton();
        jPanelContents = new javax.swing.JPanel();
        jPanelDashboard = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabelUsername = new javax.swing.JLabel();
        jLabelDashboardName = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableDashboardReport = new javax.swing.JTable();
        jPanelPresentation = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableApprovedPresentations = new javax.swing.JTable();
        jButtonRequest = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTablePendingPresentations = new javax.swing.JTable();
        jPanelReports = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableReport = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jButtonRemoveReport = new javax.swing.JButton();
        jButtonAddReport = new javax.swing.JButton();
        jPanelMyRequests = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableApprovedRequests = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablePendingRequests = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jFrameCreateReport.setAutoRequestFocus(false);
        jFrameCreateReport.setBackground(new java.awt.Color(83, 116, 240));

        jPanelCreateReport.setBackground(new java.awt.Color(83, 116, 240));
        jPanelCreateReport.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Student ID:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Project ID:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Contents:");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Moodle Link: ");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Create Report");

        jTextFieldStudentID.setEnabled(false);
        jTextFieldStudentID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldStudentIDActionPerformed(evt);
            }
        });

        jTextFieldProjectID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldProjectIDActionPerformed(evt);
            }
        });

        jTextFieldMoodleLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMoodleLinkActionPerformed(evt);
            }
        });

        jButtonCreate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonCreate.setText("Create");
        jButtonCreate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonCreateMouseClicked(evt);
            }
        });

        jTextAreaReportContents.setColumns(20);
        jTextAreaReportContents.setRows(5);
        jScrollPane5.setViewportView(jTextAreaReportContents);

        javax.swing.GroupLayout jPanelCreateReportLayout = new javax.swing.GroupLayout(jPanelCreateReport);
        jPanelCreateReport.setLayout(jPanelCreateReportLayout);
        jPanelCreateReportLayout.setHorizontalGroup(
            jPanelCreateReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCreateReportLayout.createSequentialGroup()
                .addGroup(jPanelCreateReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCreateReportLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel18))
                    .addGroup(jPanelCreateReportLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jButtonCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCreateReportLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanelCreateReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16)
                            .addGroup(jPanelCreateReportLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldProjectID))
                            .addGroup(jPanelCreateReportLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldStudentID))
                            .addGroup(jPanelCreateReportLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldMoodleLink, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane5))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanelCreateReportLayout.setVerticalGroup(
            jPanelCreateReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCreateReportLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel18)
                .addGap(35, 35, 35)
                .addGroup(jPanelCreateReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jTextFieldProjectID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelCreateReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelCreateReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jTextFieldMoodleLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButtonCreate)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout jFrameCreateReportLayout = new javax.swing.GroupLayout(jFrameCreateReport.getContentPane());
        jFrameCreateReport.getContentPane().setLayout(jFrameCreateReportLayout);
        jFrameCreateReportLayout.setHorizontalGroup(
            jFrameCreateReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
            .addGroup(jFrameCreateReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jFrameCreateReportLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanelCreateReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jFrameCreateReportLayout.setVerticalGroup(
            jFrameCreateReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 548, Short.MAX_VALUE)
            .addGroup(jFrameCreateReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jFrameCreateReportLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanelCreateReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanelRequestPresentation.setBackground(new java.awt.Color(83, 116, 240));
        jPanelRequestPresentation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Module:");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Student ID:");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Presentation Date:");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Availability:");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Request Presentation");

        jTextFieldModule.setText("jTextField1");
        jTextFieldModule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldModuleActionPerformed(evt);
            }
        });

        jTextFieldPresentationDate.setText("jTextField1");
        jTextFieldPresentationDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPresentationDateActionPerformed(evt);
            }
        });

        jTextFieldStudentIDPresentation.setText("jTextField1");
        jTextFieldStudentIDPresentation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldStudentIDPresentationActionPerformed(evt);
            }
        });

        jTextFieldAvailability.setText("jTextField1");
        jTextFieldAvailability.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAvailabilityActionPerformed(evt);
            }
        });

        jButtonRequestPresentation.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonRequestPresentation.setText("Request");

        javax.swing.GroupLayout jPanelRequestPresentationLayout = new javax.swing.GroupLayout(jPanelRequestPresentation);
        jPanelRequestPresentation.setLayout(jPanelRequestPresentationLayout);
        jPanelRequestPresentationLayout.setHorizontalGroup(
            jPanelRequestPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRequestPresentationLayout.createSequentialGroup()
                .addGroup(jPanelRequestPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRequestPresentationLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanelRequestPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelRequestPresentationLayout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldStudentIDPresentation))
                            .addGroup(jPanelRequestPresentationLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldPresentationDate))
                            .addGroup(jPanelRequestPresentationLayout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldAvailability, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelRequestPresentationLayout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldModule))))
                    .addGroup(jPanelRequestPresentationLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel23))
                    .addGroup(jPanelRequestPresentationLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jButtonRequestPresentation, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanelRequestPresentationLayout.setVerticalGroup(
            jPanelRequestPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRequestPresentationLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel23)
                .addGap(35, 35, 35)
                .addGroup(jPanelRequestPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addComponent(jTextFieldStudentIDPresentation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelRequestPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19)
                    .addComponent(jTextFieldModule, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelRequestPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextFieldPresentationDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelRequestPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22)
                    .addComponent(jTextFieldAvailability, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 228, Short.MAX_VALUE)
                .addComponent(jButtonRequestPresentation)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout jFrameRequestPresentationLayout = new javax.swing.GroupLayout(jFrameRequestPresentation.getContentPane());
        jFrameRequestPresentation.getContentPane().setLayout(jFrameRequestPresentationLayout);
        jFrameRequestPresentationLayout.setHorizontalGroup(
            jFrameRequestPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
            .addGroup(jFrameRequestPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jFrameRequestPresentationLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanelRequestPresentation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jFrameRequestPresentationLayout.setVerticalGroup(
            jFrameRequestPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 548, Short.MAX_VALUE)
            .addGroup(jFrameRequestPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jFrameRequestPresentationLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanelRequestPresentation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanelTitle.setBackground(new java.awt.Color(245, 246, 248));

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 2, 36)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(0, 0, 51));
        jLabelTitle.setText("Project Management System");

        javax.swing.GroupLayout jPanelTitleLayout = new javax.swing.GroupLayout(jPanelTitle);
        jPanelTitle.setLayout(jPanelTitleLayout);
        jPanelTitleLayout.setHorizontalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitleLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jLabelTitle)
                .addContainerGap(250, Short.MAX_VALUE))
        );
        jPanelTitleLayout.setVerticalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitleLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabelTitle)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanelSide.setBackground(new java.awt.Color(83, 116, 240));
        jPanelSide.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanelSide.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 266, -1, -1));

        jPanelDragLeft.setBackground(new java.awt.Color(102, 102, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Student");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/Male User_3.png"))); // NOI18N

        dashboardBtn.setBackground(new java.awt.Color(110, 139, 251));
        dashboardBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dashboardBtn.setForeground(new java.awt.Color(0, 0, 0));
        dashboardBtn.setText("Dashboard");
        dashboardBtn.setBorderPainted(false);
        dashboardBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardBtnMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Logout");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        reportBtn.setBackground(new java.awt.Color(110, 139, 251));
        reportBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        reportBtn.setForeground(new java.awt.Color(0, 0, 0));
        reportBtn.setText("Reports");
        reportBtn.setBorderPainted(false);
        reportBtn.setMaximumSize(new java.awt.Dimension(93, 27));
        reportBtn.setMinimumSize(new java.awt.Dimension(93, 27));
        reportBtn.setName("reportBtn"); // NOI18N
        reportBtn.setPreferredSize(new java.awt.Dimension(93, 27));
        reportBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelDragLeftLayout = new javax.swing.GroupLayout(jPanelDragLeft);
        jPanelDragLeft.setLayout(jPanelDragLeftLayout);
        jPanelDragLeftLayout.setHorizontalGroup(
            jPanelDragLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDragLeftLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanelDragLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dashboardBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                    .addComponent(reportBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addGap(18, 18, 18)
                .addComponent(reportBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelSide.add(jPanelDragLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        requestsBtn.setBackground(new java.awt.Color(110, 139, 251));
        requestsBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        requestsBtn.setForeground(new java.awt.Color(0, 0, 0));
        requestsBtn.setText("My Requests");
        requestsBtn.setBorderPainted(false);
        requestsBtn.setMaximumSize(new java.awt.Dimension(93, 27));
        requestsBtn.setMinimumSize(new java.awt.Dimension(93, 27));
        requestsBtn.setPreferredSize(new java.awt.Dimension(93, 27));
        requestsBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                requestsBtnMouseClicked(evt);
            }
        });
        jPanelSide.add(requestsBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 237, 37));

        presentationBtn.setBackground(new java.awt.Color(110, 139, 251));
        presentationBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        presentationBtn.setForeground(new java.awt.Color(0, 0, 0));
        presentationBtn.setText("Presentation");
        presentationBtn.setBorderPainted(false);
        presentationBtn.setMaximumSize(new java.awt.Dimension(93, 27));
        presentationBtn.setMinimumSize(new java.awt.Dimension(93, 27));
        presentationBtn.setName("presentationBtn"); // NOI18N
        presentationBtn.setPreferredSize(new java.awt.Dimension(93, 27));
        presentationBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                presentationBtnMouseClicked(evt);
            }
        });
        jPanelSide.add(presentationBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 237, 37));

        jPanelContents.setBackground(new java.awt.Color(153, 153, 255));
        jPanelContents.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelDashboard.setBackground(new java.awt.Color(204, 204, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Welcome,");

        jLabelUsername.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabelUsername.setForeground(new java.awt.Color(0, 0, 0));
        jLabelUsername.setText("sample");

        jLabelDashboardName.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabelDashboardName.setForeground(new java.awt.Color(0, 0, 0));
        jLabelDashboardName.setText("jLabel12");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("My Reports");

        jTableDashboardReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Assesement Type", "Moodle", "My Marks", "Total Marks"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
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
        jScrollPane4.setViewportView(jTableDashboardReport);

        javax.swing.GroupLayout jPanelDashboardLayout = new javax.swing.GroupLayout(jPanelDashboard);
        jPanelDashboard.setLayout(jPanelDashboardLayout);
        jPanelDashboardLayout.setHorizontalGroup(
            jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDashboardLayout.createSequentialGroup()
                .addGroup(jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDashboardLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelDashboardLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelDashboardName))))
                    .addGroup(jPanelDashboardLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanelDashboardLayout.setVerticalGroup(
            jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDashboardLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDashboardName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );

        jPanelContents.add(jPanelDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 560));

        jPanelPresentation.setBackground(new java.awt.Color(204, 204, 255));
        jPanelPresentation.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelPresentation.setPreferredSize(new java.awt.Dimension(800, 560));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Student Requests");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Approved Requests");

        jTableApprovedPresentations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Module", "Presentation Date", "Availability"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(jTableApprovedPresentations);

        jButtonRequest.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRequest.setText("New Request");
        jButtonRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRequestActionPerformed(evt);
            }
        });

        jTablePendingPresentations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Module", "Presentation Date", "Availability"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(jTablePendingPresentations);

        javax.swing.GroupLayout jPanelPresentationLayout = new javax.swing.GroupLayout(jPanelPresentation);
        jPanelPresentation.setLayout(jPanelPresentationLayout);
        jPanelPresentationLayout.setHorizontalGroup(
            jPanelPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPresentationLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanelPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelPresentationLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanelPresentationLayout.setVerticalGroup(
            jPanelPresentationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPresentationLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );

        jPanelContents.add(jPanelPresentation, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 560));

        jPanelReports.setBackground(new java.awt.Color(204, 204, 255));
        jPanelReports.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelReports.setPreferredSize(new java.awt.Dimension(800, 560));

        jTableReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Assesement Type", "Moodle", "My Marks", "Total Marks"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
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
        jScrollPane1.setViewportView(jTableReport);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Report");

        jButtonRemoveReport.setText("Remove Report");
        jButtonRemoveReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonRemoveReportMouseClicked(evt);
            }
        });

        jButtonAddReport.setText("Add Report");
        jButtonAddReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonAddReportMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelReportsLayout = new javax.swing.GroupLayout(jPanelReports);
        jPanelReports.setLayout(jPanelReportsLayout);
        jPanelReportsLayout.setHorizontalGroup(
            jPanelReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReportsLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanelReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelReportsLayout.createSequentialGroup()
                        .addComponent(jButtonAddReport, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRemoveReport, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(616, 616, 616))
        );
        jPanelReportsLayout.setVerticalGroup(
            jPanelReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReportsLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanelReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRemoveReport)
                    .addComponent(jButtonAddReport))
                .addContainerGap(171, Short.MAX_VALUE))
        );

        jPanelContents.add(jPanelReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 560));

        jPanelMyRequests.setBackground(new java.awt.Color(204, 204, 255));
        jPanelMyRequests.setToolTipText("");
        jPanelMyRequests.setPreferredSize(new java.awt.Dimension(800, 560));

        jTableApprovedRequests.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableApprovedRequests);

        jTablePendingRequests.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTablePendingRequests);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Pending Requests");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Approved Requests");

        javax.swing.GroupLayout jPanelMyRequestsLayout = new javax.swing.GroupLayout(jPanelMyRequests);
        jPanelMyRequests.setLayout(jPanelMyRequestsLayout);
        jPanelMyRequestsLayout.setHorizontalGroup(
            jPanelMyRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMyRequestsLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanelMyRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanelMyRequestsLayout.setVerticalGroup(
            jPanelMyRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMyRequestsLayout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );

        jPanelContents.add(jPanelMyRequests, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 560));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanelSide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelContents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

    private void jButtonRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRequestActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRequestActionPerformed

    private void reportBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportBtnMouseClicked
        jPanelDashboard.setVisible(false);
        jPanelPresentation.setVisible(false);
        jPanelReports.setVisible(true);
        jPanelMyRequests.setVisible(false);
    }//GEN-LAST:event_reportBtnMouseClicked

    private void jTextFieldStudentIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldStudentIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldStudentIDActionPerformed

    private void jTextFieldProjectIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldProjectIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProjectIDActionPerformed

    private void jTextFieldMoodleLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMoodleLinkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMoodleLinkActionPerformed

    private void jTextFieldModuleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldModuleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldModuleActionPerformed

    private void jTextFieldPresentationDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPresentationDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPresentationDateActionPerformed

    private void jTextFieldStudentIDPresentationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldStudentIDPresentationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldStudentIDPresentationActionPerformed

    private void jTextFieldAvailabilityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAvailabilityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAvailabilityActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void dashboardBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardBtnMouseClicked
        openDashboard();
    }//GEN-LAST:event_dashboardBtnMouseClicked

    private void presentationBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_presentationBtnMouseClicked
        jPanelDashboard.setVisible(false);
        jPanelPresentation.setVisible(true);
        jPanelReports.setVisible(false);
        jPanelMyRequests.setVisible(false);
    }//GEN-LAST:event_presentationBtnMouseClicked

    private void requestsBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_requestsBtnMouseClicked
        jPanelDashboard.setVisible(false);
        jPanelPresentation.setVisible(false);
        jPanelReports.setVisible(false);
        jPanelMyRequests.setVisible(true);
    }//GEN-LAST:event_requestsBtnMouseClicked

    private void jButtonAddReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAddReportMouseClicked
        createReportPopup();
    }//GEN-LAST:event_jButtonAddReportMouseClicked

    private void jButtonRemoveReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonRemoveReportMouseClicked
        removeReport();
    }//GEN-LAST:event_jButtonRemoveReportMouseClicked

    private void jButtonCreateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonCreateMouseClicked
        addReport();
    }//GEN-LAST:event_jButtonCreateMouseClicked


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
            java.util.logging.Logger.getLogger(StudentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(StudentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(StudentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                StudentForm mainFormBody = new StudentForm();

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
    private javax.swing.JButton jButtonAddReport;
    private javax.swing.JButton jButtonCreate;
    private javax.swing.JButton jButtonRemoveReport;
    private javax.swing.JButton jButtonRequest;
    private javax.swing.JButton jButtonRequestPresentation;
    private javax.swing.JFrame jFrameCreateReport;
    private javax.swing.JFrame jFrameRequestPresentation;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelDashboardName;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelUsername;
    private javax.swing.JPanel jPanelContents;
    private javax.swing.JPanel jPanelCreateReport;
    private javax.swing.JPanel jPanelDashboard;
    private javax.swing.JPanel jPanelDragLeft;
    private javax.swing.JPanel jPanelMyRequests;
    private javax.swing.JPanel jPanelPresentation;
    private javax.swing.JPanel jPanelReports;
    private javax.swing.JPanel jPanelRequestPresentation;
    private javax.swing.JPanel jPanelSide;
    private javax.swing.JPanel jPanelTitle;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTableApprovedPresentations;
    private javax.swing.JTable jTableApprovedRequests;
    private javax.swing.JTable jTableDashboardReport;
    private javax.swing.JTable jTablePendingPresentations;
    private javax.swing.JTable jTablePendingRequests;
    private javax.swing.JTable jTableReport;
    private javax.swing.JTextArea jTextAreaReportContents;
    private javax.swing.JTextField jTextFieldAvailability;
    private javax.swing.JTextField jTextFieldModule;
    private javax.swing.JTextField jTextFieldMoodleLink;
    private javax.swing.JTextField jTextFieldPresentationDate;
    private javax.swing.JTextField jTextFieldProjectID;
    private javax.swing.JTextField jTextFieldStudentID;
    private javax.swing.JTextField jTextFieldStudentIDPresentation;
    private javax.swing.JButton presentationBtn;
    private javax.swing.JButton reportBtn;
    private javax.swing.JButton requestsBtn;
    // End of variables declaration//GEN-END:variables
}