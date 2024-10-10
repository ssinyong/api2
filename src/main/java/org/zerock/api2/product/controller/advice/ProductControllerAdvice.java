package org.zerock.api2.product.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ProductControllerAdvice {

    //Advice는 무조건!
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<java.util.Map<String, String>> methodArgEx(MethodArgumentNotValidException ex) {

        Map<String, String> map = new HashMap<>();
        map.put("message", "Parameter Type check plz....");

        return ResponseEntity.status(500).body(map);

    }
}
