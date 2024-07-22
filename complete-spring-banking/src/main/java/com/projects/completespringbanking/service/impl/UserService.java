package com.projects.completespringbanking.service.impl;

import com.projects.completespringbanking.dto.*;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest request);

    String nameEnquiry(EnquiryRequest request);

    BankResponse creditAccount(DebitCreditRequest debitCreditRequest);

    BankResponse debitAccount(DebitCreditRequest debitCreditRequest);

    BankResponse transferAmount(TransferRequest transferRequest);
}
