package com.projects.completespringbanking.service.impl;

import com.projects.completespringbanking.dto.TransactionDto;
import com.projects.completespringbanking.entity.Transaction;
import com.projects.completespringbanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    TransactionRepository transactionRepository;

    public void saveTransaction(TransactionDto transactionDto){
        Transaction transaction = Transaction.builder()
                .accountNumber(transactionDto.getAccountNumber())
                .transactionType(transactionDto.getTransactionType())
                .amount(transactionDto.getAmount())
                .status(transactionDto.getStatus())
                .build();

        transactionRepository.save(transaction);
    }
}
