package com.projects.completespringbanking.repository;

import com.projects.completespringbanking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByAccountNumber(String accountNumber);

    User findByAccountNumber(String accountNumber);

}
