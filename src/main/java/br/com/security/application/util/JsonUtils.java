package br.com.security.application.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author Elvis Fernandes on 28/10/19
 */
public class JsonUtils {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    public static String toJson(Object object) {

        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
