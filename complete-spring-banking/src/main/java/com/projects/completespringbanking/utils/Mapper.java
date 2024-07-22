package com.projects.completespringbanking.utils;

import com.projects.completespringbanking.dto.DebitCreditRequest;
import com.projects.completespringbanking.dto.TransferRequest;

public class Mapper {
    //convert transferRequest to DebitCreditRequest
    public static DebitCreditRequest mapToDebitRequest(TransferRequest transferRequest){
        return DebitCreditRequest.builder()
                .accountNumber(transferRequest.getTransferFromAccountNumber())
                .amount(transferRequest.getTransferAmount())
                .build();
    }

    public static DebitCreditRequest mapToCreditRequest(TransferRequest transferRequest){
        return DebitCreditRequest.builder()
                .accountNumber(transferRequest.getTransferToAccountNumber())
                .amount(transferRequest.getTransferAmount())
                .build();
    }
}
