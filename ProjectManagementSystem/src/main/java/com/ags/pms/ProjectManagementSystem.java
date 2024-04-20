/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ags.pms;

import com.ags.pms.models.*;
import com.ags.pms.services.*;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

import com.ags.pms.forms.Login;

public class ProjectManagementSystem {

    public static void main(String[] args) {
        // app();
        try {
            consoleTests();
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }
    }
    
    private static void app() {
        new Login().setVisible(true);
    }
    
    private static void consoleTests() throws Exception {
        PasswordHandler password = new PasswordHandler();

        password.init();

        String encryptedPassword = password.encryptPassword("Come_Test_This_IV");
        String decryptedPassword = password.decryptPassword(encryptedPassword);

        Helper.printErr(encryptedPassword);
        Helper.printErr(decryptedPassword);
        Helper.printErr(password.exportKeys());

    }


}