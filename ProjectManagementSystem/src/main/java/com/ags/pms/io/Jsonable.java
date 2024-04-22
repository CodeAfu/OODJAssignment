package com.ags.pms.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface Jsonable {
    Jsonable jsonToObject(String json) throws JsonMappingException, JsonProcessingException ;
    void objectToJson(Jsonable obj);
}
