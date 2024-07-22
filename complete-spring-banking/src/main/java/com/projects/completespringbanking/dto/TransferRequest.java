package com.projects.completespringbanking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequest {
    private String transferToAccountNumber;
    private String transferFromAccountNumber;
    private BigDecimal transferAmount;
}
