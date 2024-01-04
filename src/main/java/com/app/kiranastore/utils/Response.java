package com.app.kiranastore.utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;


public class Response {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("data", responseObj);
        map.put("errors", new ArrayList<>());
        map.put("success", true);

        return new ResponseEntity<Object>(map, status);
    }

    public static Map<String, Object> generateError(String message, List<String> errors) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("data", null);
        map.put("errors", errors);
        map.put("success", false);

        return map;
    }
}