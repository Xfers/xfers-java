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
import java.util.ArrayList;
import java.util.Map;

public class BankAccountWithdrawalOnBehalfExample {
    public static void main(String[] args) throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        String merchantApiToken = "FILL IN THE BLANK";
        String userApiToken = "FILL IN THE BLANK";
        Xfers.setIDSandbox();

        Xfers.apiKey = userApiToken; //set customer's token

        List<BankAccount> bankAccounts = new ArrayList();

        System.out.println("===== Listing all available xfers Banks =====");
        for (BankAccount bankAccount : BankAccount.availableBanks()) {
            System.out.println(bankAccount.toString());
        }

        try {
            System.out.println("===== Retrieving Bank Account =====");
            bankAccounts = BankAccount.retrieve();
            System.out.println(bankAccounts);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            System.out.println("===== Adding Bank Account =====");
            params.put("account_no", "0393123432");
            params.put("bank", "DBS");
            bankAccounts = BankAccount.add(params);
            System.out.println(bankAccounts);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            System.out.println("===== Updating Bank Account =====");
            params.put("account_no", "9102031012");
            params.put("bank", "UOB");
            BankAccount currentBankAccount = bankAccounts.get(bankAccounts.size() - 1);
            bankAccounts = BankAccount.update(currentBankAccount.getId(), params);
            System.out.println(bankAccounts);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Xfers.apiKey = merchantApiToken; // To perform withdrawal on behalf

        Withdrawal withdrawal = new Withdrawal();
        String idempotency_id = String.valueOf(System.currentTimeMillis());

        try {
            // The last bank account we created
            BankAccount currentBankAccount = bankAccounts.get(bankAccounts.size() - 1);

            Map<String, Object> params = new HashMap<String, Object>();
            System.out.println("===== Making a withdrawal on behalf request =====");
            params.put("amount", "50000");
            params.put("express", true);
            params.put("user_api_token", userApiToken);
            params.put("idempotency_id", idempotency_id);

            withdrawal = BankAccount.withdraw(currentBankAccount.getId(), params);
            System.out.println(withdrawal);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("===== Mocking a withdrawal request status =====");
            Withdrawal result = BankAccount.mockWithdrawalResult(idempotency_id, "failed");
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("===== Getting a withdrawal request =====");
            Withdrawal result = BankAccount.retrieveWithdrawalRequest(withdrawal.getId());
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("===== Listing all withdrawal request =====");
            List<Withdrawal> withdrawalRequests = BankAccount.withdrawalRequests("pending");

            for (Withdrawal result : withdrawalRequests) {
                System.out.println(result.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("===== Retrieving Bank Account =====");
            bankAccounts = BankAccount.retrieve();

            System.out.println("===== Deleting all existing Bank Accounts =====");
            for (BankAccount bankAccount : bankAccounts) {
                BankAccount.delete(bankAccount.getId());
            }

            System.out.println("===== Retrieving Bank Account =====");
            bankAccounts = BankAccount.retrieve();
            System.out.println(bankAccounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}