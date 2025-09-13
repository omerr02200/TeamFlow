package com.works.configs;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

@RestControllerAdvice
public class GlobalException {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object methodArgumentNotValid(MethodArgumentNotValidException ex) {
        return parseError(ex.getFieldErrors());
    }

    private Set parseError(List<FieldError> fieldErrors) {
        Set<Map<String, Object>> ls = new LinkedHashSet();
        for (FieldError fieldError : fieldErrors) {
            Map<String, Object> map = new HashMap();
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            Object rejectedValue = fieldError.getRejectedValue();
            map.put("field", field);
            map.put("message", message);
            map.put("rejectedValue", rejectedValue);
            ls.add(map);
        }
        return ls;
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public Object dataIntegrityViolation(DataIntegrityViolationException ex) {
//        String error = ex.getLocalizedMessage();
//        String rt = "";
//        if(error.contains("tekrar eden kayıt")) {
//            rt = "Email zaten mevcut";
//        }
//        return rt;
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Object methodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map map =  new HashMap();
        map.put("message", ex.getMessage());
        map.put("status", false);
        return map;
    }

    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Object constraintViolationException(ConstraintViolationException ex) {
        Map map = new LinkedHashMap();
        map.put("status", false);
        map.put("message", ex.getMessage());
        return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map map = new LinkedHashMap();
        map.put("status", false);
        map.put("message", ex.getMessage());
        return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Object dataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map map = new LinkedHashMap();
        map.put("status", false);
        map.put("message", ex.getMessage());
        return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Object illegalArgumentException(IllegalArgumentException ex) {
//        Map map = new LinkedHashMap();
//        map.put("status", false);
//        map.put("message", ex.getMessage());
//        return new ResponseEntity(map, HttpStatus.BAD_REQUEST);

        return exception(ex);
    }

    public Object exception(Exception ex) {
        Map map = new LinkedHashMap();
        map.put("status", false);
        map.put("message", ex.getMessage());
        return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
    }

   @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object missingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return exception(ex);
   }

}
