package com.example.demo.service.integradora.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getters and setter
@AllArgsConstructor //Constructor parametrizado (Con todos los atributos)
@NoArgsConstructor //Constructor sin parametros
public class CustomRequest {

    String data;


}

