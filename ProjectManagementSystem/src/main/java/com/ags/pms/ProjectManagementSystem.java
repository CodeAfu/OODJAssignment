/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ags.pms;

import com.ags.pms.models.*;

public class ProjectManagementSystem {

    public static void main(String[] args) {
        consoleTests();
    }
    
    
    private static void consoleTests() {
        Admin admin = new Admin();
        admin.setUsername("Admin");
        System.out.println(admin.getUsername());
    }
}