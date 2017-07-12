package com.xfers.model;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.Xfers;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.model.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BankAccountTest {

    String apiKey = "6BY-yFUzu3jG5vj95PyD5VCGzGZUz9SBauEnhgFB9Ds";// API Token of demo@xfers.io
    // because we cannot delete bank account after a pending withdrawal, let's just make one other user who get dumped this withdrawal request
    String apiKey2 = "2zsujd47H3-UmsxDL784beVnYbxCYCzL4psSbwZ_Ngk";// API Token of docs@xfers.com


    @Before
    public void setEnvironment()
    {
        Xfers.apiKey = "6BY-yFUzu3jG5vj95PyD5VCGzGZUz9SBauEnhgFB9Ds"; // API Token of demo@xfers.io
        Xfers.setSGSandbox();
    }

    @Test
    public void testAllBankAccount() throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        // assume currently no bank account added to the user

//      delete all bank account for this user that may be caused from previous test failure
        deleteAllBankAccounts();

//      add a bank account
        String bankId = testAddBankAccount();
//        retrieve a bank account
        testRetrieveBankAccount();
//        update the bank account
        testUpdateBankAccount(bankId);
//        delete the bank account
        testDeleteBankAccount(bankId);
//        test list bank account
        testListBankAccounts();

        String BankIdTwo = "399";
        testMakeWithdrawalRequest(BankIdTwo);
//        make a withdrawal from this bank account

        testListWithdrawalRequest();
    }


    public void deleteAllBankAccounts() throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {

        System.out.println("Retrieving Bank Account");
        List<BankAccount> bankAccounts = BankAccount.retrieve();
        for (BankAccount bankAccount : bankAccounts) {
            BankAccount.delete(bankAccount.getId(),apiKey);
        }
    }

    public void testListBankAccounts() throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        // make sure that 10 first banks is there

        System.out.println("Listing all available xfers Banks");
        List<BankAccount> bankAccounts = BankAccount.availableBanks(apiKey);

        BankAccount bankAccount = bankAccounts.get(0);
        assertEquals("Development Bank of Singapore",bankAccount.getName());
        assertEquals("DBS",bankAccount.getAbbreviation());
        bankAccount = bankAccounts.get(1);
        assertEquals("United Oversea Bank",bankAccount.getName());
        assertEquals("UOB",bankAccount.getAbbreviation());
        bankAccount = bankAccounts.get(2);
        assertEquals("Malaysia Banking BHD",bankAccount.getName());
        assertEquals("MBB",bankAccount.getAbbreviation());
        bankAccount = bankAccounts.get(3);
        assertEquals("Oversea-Chinese Banking Corporation Limited",bankAccount.getName());
        assertEquals("OCBC",bankAccount.getAbbreviation());
        bankAccount = bankAccounts.get(4);
        assertEquals("Citibank Singapore",bankAccount.getName());
        assertEquals("CITI",bankAccount.getAbbreviation());


    }


    public String testAddBankAccount() throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Map<String, Object> params = new HashMap<String, Object>();
        System.out.println("Adding Bank Account");
        params.put("account_no", "0393123432");
        params.put("bank", "DBS");
        List<BankAccount> bankAccounts = BankAccount.add(params,apiKey);
        BankAccount bankAccount = bankAccounts.get(0);
        System.out.print(bankAccount.toString());
        assertEquals("DBS",bankAccount.getBankAbbrev());
        assertEquals("0393123432",bankAccount.getAccountNo());
        return bankAccount.getId();
    }

    public void testRetrieveBankAccount() throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        System.out.println("Retrieving Bank Account");
        List<BankAccount> bankAccounts = BankAccount.retrieve(apiKey);
        BankAccount bankAccount = bankAccounts.get(0);
        assertEquals("DBS",bankAccount.getBankAbbrev());
        assertEquals("0393123432",bankAccount.getAccountNo());
    }


    public void testUpdateBankAccount(String bankId) throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Map<String, Object> params = new HashMap<String, Object>();
        System.out.println("Updating Bank Account");
        params.put("account_no", "9102031012");
        params.put("bank", "UOB");

        List<BankAccount> bankAccounts = BankAccount.update(bankId, params,apiKey);
        BankAccount bankAccount = bankAccounts.get(0);
        assertEquals("UOB",bankAccount.getBankAbbrev());
        assertEquals("9102031012",bankAccount.getAccountNo());
    }

    public void testDeleteBankAccount(String bankId) throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        System.out.println("Deleting Bank Account");
        List<BankAccount> bankAccounts = BankAccount.delete(bankId,apiKey);
        for (BankAccount bankAccount : bankAccounts) {
            System.out.println(bankAccount.toString());
        }
    }

    public void testMakeWithdrawalRequest(String bankId) throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Map<String, Object> params = new HashMap<String, Object>();
        System.out.println("Making a withdrawal request");
        params.put("amount", "5");

        Withdrawal withdrawal = BankAccount.withdraw(bankId, params,apiKey2);
        assertEquals("0393123433",withdrawal.getAccountNo());
        assertEquals("DBS",withdrawal.getBankAbbrev());
        assertEquals(new BigDecimal(5),withdrawal.getAmount());
    }


    public void testListWithdrawalRequest() throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        System.out.println("Listing all withdrawal request");
        List<Withdrawal> withdrawalRequests = BankAccount.withdrawalRequests("pending",apiKey2);
        Withdrawal withdrawal = withdrawalRequests.get(withdrawalRequests.size() - 1);
        assertEquals("0393123433",withdrawal.getAccountNo());
        assertEquals("DBS",withdrawal.getBankAbbrev());
        assertEquals(new BigDecimal(5),withdrawal.getAmount());
    }
}