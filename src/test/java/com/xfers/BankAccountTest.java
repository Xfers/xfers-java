package com.xfers;

import com.xfers.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankAccountTest {
    public static void main(String[] args) {
        Xfers.apiKey = "pXcfdAKNorDe_o1eou1NSp4mwssiEzem_6sg8fwnZWs";
        Xfers.setSGSandbox();

        try {
            List<BankAccount> bankAccounts = BankAccount.retrieve();
            for (BankAccount bankAccount : bankAccounts) {
                System.out.println(bankAccount.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("account_no", "03931234323");
            params.put("bank", "DBS");
            List<BankAccount> bankAccounts = BankAccount.add(params);
            for (BankAccount bankAccount : bankAccounts) {
                System.out.println(bankAccount.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("account_no", "9102031012");
            params.put("bank", "UOB");

            List<BankAccount> bankAccounts = BankAccount.update("11", params);
            for (BankAccount bankAccount : bankAccounts) {
                System.out.println(bankAccount.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<BankAccount> bankAccounts = BankAccount.delete("11");
            for (BankAccount bankAccount : bankAccounts) {
                System.out.println(bankAccount.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Map<String, Object> params = new HashMap<String, Object>();
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
            List<Withdrawal> withdrawalRequests = BankAccount.withdrawalRequests("pending");
            for (Withdrawal withdrawal : withdrawalRequests) {
                System.out.println(withdrawal.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}