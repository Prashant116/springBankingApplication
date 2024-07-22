package com.projects.completespringbanking.controller;

import com.projects.completespringbanking.dto.*;
import com.projects.completespringbanking.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name="User accounts management API")
@RequiredArgsConstructor
public class UserController {


    UserService userService;


    @Operation(
            summary = "Create new user account",
            description = "Creating new user and assigning account number"
    )
    @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }

    @GetMapping("/balanceEnquiry")
    public BankResponse getBalance(@RequestBody EnquiryRequest enquiryRequest){
        return userService.balanceEnquiry(enquiryRequest);
    }

    @GetMapping("/nameEnquiry")
    public String getName(@RequestBody EnquiryRequest enquiryRequest){
        return userService.nameEnquiry(enquiryRequest);
    }

    @PostMapping("/credit")
    public BankResponse creditAccount(@RequestBody DebitCreditRequest debitCreditRequest){
        return userService.creditAccount(debitCreditRequest);
    }

    @PostMapping("/debit")
    public BankResponse debitAccount(@RequestBody DebitCreditRequest debitCreditRequest){
        return userService.debitAccount(debitCreditRequest);
    }

    @PostMapping("/transfer")
    public BankResponse transferAmount(@RequestBody TransferRequest transferRequest){
        return userService.transferAmount(transferRequest);
    }
}
