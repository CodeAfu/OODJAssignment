package com.ags.pms.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.ags.pms.Helper;

public class JsonHandler implements JsonIO {

    private String path = "../JsonData.txt";

    public String readFile() {
        return "";
    }

    public void writeFile() {

        try {
            FileWriter writer = new FileWriter("../data/Users.txt");
        } catch (Exception ex) {
            Helper.printErr(Helper.getStackTraceString(ex));
        }
        
    }

    @Override
    public void read(JsonReadable read) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public void write(JsonWritable write) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'write'");
    }
}
