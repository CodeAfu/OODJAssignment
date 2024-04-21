package com.ags.pms.io;

public interface Jsonable {
    Jsonable jsonToObject(String json);
    void objectToJson(Jsonable obj);
}
