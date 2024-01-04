package com.app.kiranastore.advice;

import com.app.kiranastore.exception.ConflictException;
import com.app.kiranastore.utils.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ConflictAdvice {

    @ResponseBody
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handler(ConflictException expection) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        List<String> errors = new ArrayList<>();
        errors.add(expection.getMessage());

        Map<String, Object> res = Response.generateError("Something went wrong", errors);
        return res;
    };
}