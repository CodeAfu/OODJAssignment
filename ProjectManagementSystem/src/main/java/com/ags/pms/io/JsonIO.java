package com.ags.pms.io;

public interface JsonIO {
    void read(JsonReadable read);
    void write(JsonWritable write);
}
