package com.projects.completespringbanking.repository;

import com.projects.completespringbanking.entity.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
