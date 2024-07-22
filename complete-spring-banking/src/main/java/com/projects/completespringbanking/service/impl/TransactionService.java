package com.projects.completespringbanking.service.impl;

import com.projects.completespringbanking.dto.TransactionDto;
import com.projects.completespringbanking.entity.Transaction;
import com.projects.completespringbanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface TransactionService{

    public void saveTransaction(TransactionDto transactionDto);
}
