package com.example.demo.service.integradora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.integradora.dto.CustomRequest;
import com.example.demo.service.integradora.dto.ErrorResponse;
import com.example.demo.service.integradora.service.SortService;


@RestController
@RequestMapping("/api/sort") // path
public class SortController {

    @Autowired
    SortService service;

    @PostMapping("/insertion") // endpoint
    public ResponseEntity<?> postMethodName(@RequestBody CustomRequest request) {


            if (request.getData() == null || request.getData().isBlank()) {
                ErrorResponse error = new ErrorResponse();
                error.setError("Error en la entradad de datos");
                error.setDetail("Necesitamos que llenes data con datos numericos");
                return ResponseEntity.badRequest().body(error);
            }

            if(!request.getData().contains(",")){
                ErrorResponse error = new ErrorResponse();
                error.setError("Error de datos de entrada");
                error.setDetail("Necesitamos las comas nena");
                return ResponseEntity.badRequest().body(error);
            }

            String requestData = request.getData();
            String[] stringData = requestData.split(",");
            int[] arrNum = new int[stringData.length];
            for (int i = 0; i < stringData.length; i++) {

                try {
                    arrNum[i] = Integer.parseInt(stringData[i]);
                } catch (IllegalArgumentException e) {
                    ErrorResponse error = new ErrorResponse();
                    error.setError("Hubo un error de datos");
                    error.setDetail("El dato: " + stringData[i] + " Es un dato incorrecto");
                    System.out.println("El dato: " + arrNum[i] + " es un dato incorrecto");
                    return ResponseEntity.badRequest().body(error);
                }
            }
            return ResponseEntity.ok(service.sort(arrNum));

    }

    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    

}
