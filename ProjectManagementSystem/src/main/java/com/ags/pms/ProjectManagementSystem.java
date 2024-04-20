/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.ags.pms;

import com.ags.pms.models.*;
import com.ags.pms.services.*;

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
        testAES();
    }


    private static void testAES() throws Exception {
        PasswordHandler handler = new PasswordHandler();

        // password.init();
        handler.initFromStrings("9Vs+DfEF1+3tF8fCKLp9BQ==", "JoprQnQRq95s/Nuz");

        String encryptedPassword = handler.encryptPassword("TestPasswordDamn");
        String decryptedPassword = handler.decryptPassword("yQC5XJSuqubdkMm319tlVm4rzj06iqYyaWo12SaDbA0=");

        Helper.printErr("Encrypted: " + encryptedPassword);
        Helper.printErr("Decrypted: " + decryptedPassword);
        Helper.printErr("Secret Key: " + handler.exportKeys()[0]);
        Helper.printErr("IV: " + handler.exportKeys()[1]);
    }

}