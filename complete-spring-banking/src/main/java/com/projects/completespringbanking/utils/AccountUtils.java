package com.projects.completespringbanking.utils;

import java.time.Year;
import java.util.Random;


public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "User account already exists.";
    public static final String ACCOUNT_CREATION_SUCCESS = "002";
    public static final String ACCOUNT_CREATION_SUCCESS_MESSAGE = "Account successfully created.";

    public static final String ACCOUNT_NOT_EXIST_CODE = "003";

    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "Account does not exist.";

    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_FOUND_MESSAGE = "Account found successfully.";

    public static final String ACCOUNT_CREDITED = "005";

    public static final String ACCOUNT_CREDITED_MESSAGE = "Account credited successfully";

    public static final String ACCOUNT_DEBITED = "006";

    public static final String ACCOUNT_DEBITED_MESSAGE = "Account debited.";

    public static final String ACCOUNT_DEBIT_FAILED = "007";

    public static final String ACCOUNT_DEBIT_FAILED_MESSAGE = "Insufficient balance";

    public static final String BALANCE_TRANSFERRED = "008";

    public static final String BALANCE_TRANSFERRED_MESSAGE = "Balance transferred successfully.";

    public static String generateAccountNumber(){
    /*
    get current year and concatenate six digit random number at the end of year
     */
    Year currerntYear = Year.now();
    int max = 900000;
    int adder = 100000;

    Random random = new Random();
    int randomNumber =random.nextInt(max) + adder;

    String year = String.valueOf(currerntYear);
    String randNumber = String.valueOf(randomNumber);

    StringBuilder stringBuilder = new StringBuilder();

    return stringBuilder.append(year).append(randomNumber).toString();
    }

}
