package com.xfers.model.example.functional;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.Xfers;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankAccountExample {
    public static void main(String[] args) throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Xfers.apiKey = "6BY-yFUzu3jG5vj95PyD5VCGzGZUz9SBauEnhgFB9Ds"; // API Token of demo@xfers.io
        Xfers.setSGSandbox();
        List<BankAccount> bankAccounts;

        System.out.println("Listing all available xfers Banks");
        bankAccounts = BankAccount.availableBanks();
        for (BankAccount bankAccount : bankAccounts) {
            System.out.println(bankAccount.toString());
        }

        try {
            System.out.println("Retrieving Bank Account");
            bankAccounts = BankAccount.retrieve();
            for (BankAccount bankAccount : bankAccounts) {
                System.out.println(bankAccount.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            System.out.println("Adding Bank Account");
            params.put("account_no", "0393123432");
            params.put("bank", "DBS");
            bankAccounts = BankAccount.add(params);
            for (BankAccount bankAccount : bankAccounts) {
                System.out.println(bankAccount.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            System.out.println("Updating Bank Account");
            params.put("account_no", "9102031012");
            params.put("bank", "UOB");

            bankAccounts = BankAccount.update("11", params);
            for (BankAccount bankAccount : bankAccounts) {
                System.out.println(bankAccount.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Deleting Bank Account");
            bankAccounts = BankAccount.delete("11");
            for (BankAccount bankAccount : bankAccounts) {
                System.out.println(bankAccount.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            System.out.println("Making a withdrawal request");
            params.put("amount", "5");
            params.put("express", true);

            Withdrawal withdrawal = BankAccount.withdraw("10", params);
            System.out.println(withdrawal.getId());
            System.out.println(withdrawal.getAccountNo());
            System.out.println(withdrawal.getBankAbbrev());
            System.out.println(withdrawal.getAmount());
            System.out.println(withdrawal.getFees());
            System.out.println(withdrawal.getExpress());
            System.out.println(withdrawal.getStatus());
            System.out.println(withdrawal.getArrival());
            System.out.println(withdrawal.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Listing all withdrawal request");
            List<Withdrawal> withdrawalRequests = BankAccount.withdrawalRequests("pending");
            for (Withdrawal withdrawal : withdrawalRequests) {
                System.out.println(withdrawal.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}