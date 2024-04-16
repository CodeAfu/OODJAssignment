/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ags.pms;

import com.ags.pms.models.*;
import com.ags.pms.forms.Login;

public class ProjectManagementSystem {

    public static void main(String[] args) {
        // runApp();
        consoleTests();
    }
    
    private static void runApp() {
        new Login().setVisible(true);
    }
    
    private static void consoleTests() {
        Admin admin = new Admin();
        admin.setUsername("Admin");
        System.out.println(admin.getUsername());
    }
}