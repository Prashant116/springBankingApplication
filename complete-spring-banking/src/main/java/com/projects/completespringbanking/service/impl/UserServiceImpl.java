package com.projects.completespringbanking.service.impl;

import com.projects.completespringbanking.dto.*;
import com.projects.completespringbanking.entity.User;
import com.projects.completespringbanking.repository.TransactionRepository;
import com.projects.completespringbanking.repository.UserRepository;
import com.projects.completespringbanking.utils.AccountUtils;
import com.projects.completespringbanking.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    TransactionService transactionService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        /*
        Check if the user already exists
        Creating user and saving it to the database
         */
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .status("ACTIVE")
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .build();

        User savedUser = userRepository.save(newUser);
        EmailDetails emailDetails = EmailDetails.builder()
                .messageBody("Congratulations! Your account has been successfully created.\nYour account info:\n" +
                        "Account name: " + savedUser.getFirstName() + " " + savedUser.getLastName() + "\n" +
                        "Account number: " + savedUser.getAccountNumber())
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .build();

        emailService.sendEmailAlert(emailDetails);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName())
                        .accountNumber(savedUser.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        if (!userRepository.existsByAccountNumber(request.getAccountNumber())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(
                        AccountInfo.builder()
                                .accountName(foundUser.getFirstName() + " " + foundUser.getLastName())
                                .accountBalance(foundUser.getAccountBalance())
                                .accountNumber(foundUser.getAccountNumber())
                                .build()
                )
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {
        if (!userRepository.existsByAccountNumber(request.getAccountNumber())) {
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }
        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());

        return foundUser.getFirstName() + " " + foundUser.getLastName();
    }

    @Override
    public BankResponse creditAccount(DebitCreditRequest debitCreditRequest) {
        if (!userRepository.existsByAccountNumber(debitCreditRequest.getAccountNumber())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User creditedUser = userRepository.findByAccountNumber(debitCreditRequest.getAccountNumber());
        creditedUser.setAccountBalance(creditedUser.getAccountBalance().add(debitCreditRequest.getAmount()));
        userRepository.save(creditedUser);

        //send mail to the user when the account is credited.
        EmailDetails creditedEmail = EmailDetails.builder()
                .subject("Account Credited Successfully")
                .recipient(creditedUser.getEmail())
                .messageBody("Congratulations! Your account has been credited by " + debitCreditRequest.getAmount())
                .build();

//        emailService.sendEmailAlert(creditedEmail);

        //save transaction
        transactionService.saveTransaction(TransactionDto.builder()
                        .accountNumber(creditedUser.getAccountNumber())
                        .amount(debitCreditRequest.getAmount().toString())
                        .transactionType("Credit")
                        .status("SUCCESS")
                .build());

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_MESSAGE)
                .accountInfo(
                        AccountInfo.builder()
                                .accountNumber(creditedUser.getAccountNumber())
                                .accountBalance(creditedUser.getAccountBalance())
                                .accountName(creditedUser.getFirstName() + " " + creditedUser.getLastName())
                                .build()
                )
                .build();
    }

    @Override
    public BankResponse debitAccount(DebitCreditRequest debitCreditRequest) {
        //check if the account exists or not
        if(!userRepository.existsByAccountNumber(debitCreditRequest.getAccountNumber())){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User debitUser = userRepository.findByAccountNumber(debitCreditRequest.getAccountNumber());

        int compareResult = debitUser.getAccountBalance().compareTo(debitCreditRequest.getAmount());
        if(compareResult < 0){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBIT_FAILED)
                    .responseMessage(AccountUtils.ACCOUNT_DEBIT_FAILED_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountName(null)
                            .build())
                    .build();
        }

        debitUser.setAccountBalance(debitUser.getAccountBalance().subtract(debitCreditRequest.getAmount()));

        userRepository.save(debitUser);

        //save transaction
        transactionService.saveTransaction(TransactionDto.builder()
                .accountNumber(debitUser.getAccountNumber())
                .amount(debitCreditRequest.getAmount().toString())
                .transactionType("Debit")
                .status("SUCCESS")
                .build());

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_DEBITED)
                .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(debitUser.getFirstName() + " " + debitUser.getLastName())
                        .accountNumber(debitUser.getAccountNumber())
                        .accountBalance(debitUser.getAccountBalance())
                        .build())
                .build();
    }

    @Override
    public BankResponse transferAmount(TransferRequest transferRequest) {
        //check if accounts exist
        Boolean isTransferFromAccount = userRepository.existsByAccountNumber(transferRequest.getTransferFromAccountNumber());
        Boolean isTransferToAccount = userRepository.existsByAccountNumber(transferRequest.getTransferToAccountNumber());
        if(!(isTransferToAccount ||isTransferFromAccount) ){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User debitUser = userRepository.findByAccountNumber(transferRequest.getTransferFromAccountNumber());

        //check if there is sufficient balance
        int compareResult = debitUser.getAccountBalance().compareTo(transferRequest.getTransferAmount());
        if(compareResult < 0){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBIT_FAILED)
                    .responseMessage(AccountUtils.ACCOUNT_DEBIT_FAILED_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountName(null)
                            .build())
                    .build();
        }

        //debit from transferFromAccount
        debitUser.setAccountBalance(debitUser.getAccountBalance().subtract(transferRequest.getTransferAmount()));

        User creditedUser = userRepository.findByAccountNumber(transferRequest.getTransferToAccountNumber());
        creditedUser.setAccountBalance(creditedUser.getAccountBalance().add(transferRequest.getTransferAmount()));

        //save debit-credit accounts to database
        userRepository.save(debitUser);
        userRepository.save(creditedUser);

        //save credit transaction
        transactionService.saveTransaction(TransactionDto.builder()
                .accountNumber(creditedUser.getAccountNumber())
                .amount(transferRequest.getTransferAmount().toString())
                .transactionType("Credit - Fund Transfer")
                .status("SUCCESS")
                .build());

        //save debit transaction
        transactionService.saveTransaction(TransactionDto.builder()
                .accountNumber(creditedUser.getAccountNumber())
                .amount(transferRequest.getTransferAmount().toString())
                .transactionType("Debit - Fund Transfer")
                .status("SUCCESS")
                .build());


        return BankResponse.builder()
                .responseCode(AccountUtils.BALANCE_TRANSFERRED)
                .responseMessage(AccountUtils.BALANCE_TRANSFERRED_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(debitUser.getFirstName() + " " + debitUser.getLastName())
                        .accountNumber(debitUser.getAccountNumber())
                        .accountBalance(debitUser.getAccountBalance())
                        .build())
                .build();
    }
}