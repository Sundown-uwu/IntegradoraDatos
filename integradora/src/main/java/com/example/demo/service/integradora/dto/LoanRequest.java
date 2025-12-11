package com.example.demo.service.integradora.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoanRequest {
    Integer userId;
    Integer bookId;
}
