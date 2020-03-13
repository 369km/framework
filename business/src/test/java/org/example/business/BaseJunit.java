package org.example.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.TimeZone;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BaseJunit {

    private static final ObjectMapper om = new ObjectMapper()
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .setTimeZone(TimeZone.getTimeZone("GMT+8"));

    public static void toPrintJson(Object data) {
        try {
            String json = om.writeValueAsString(data);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static String toJson(Object data) {
        try {
            return om.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
