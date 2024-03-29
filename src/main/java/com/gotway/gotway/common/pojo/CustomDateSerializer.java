package com.gotway.gotway.common.pojo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
 
 
public class CustomDateSerializer extends JsonSerializer<Date> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException,
            JsonProcessingException {

        jsonGenerator.writeString(sdf.format(value));
    }
}