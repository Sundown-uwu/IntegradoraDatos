package com.example.demo.service.integradora.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
    String error;
    String detail;


}
