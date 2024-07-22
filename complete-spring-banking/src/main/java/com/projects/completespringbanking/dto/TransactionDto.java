package com.projects.completespringbanking.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private String accountNumber;
    private String transactionId;
    private String transactionType;
    private String amount;
    private String status;
}
