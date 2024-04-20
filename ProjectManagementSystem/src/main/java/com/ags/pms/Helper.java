package com.ags.pms;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Helper {
    
    public static void printErr(String text) {
        System.err.print("\u001B[31m");
        System.err.println(text);
        System.err.print("\u001B[0m");
    }

    public static String getStackTraceString(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
