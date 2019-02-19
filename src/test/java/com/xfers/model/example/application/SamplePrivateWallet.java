package com.xfers.model.example.application;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.Xfers;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.model.*;
import com.xfers.model.response.ConnectResponse;

import java.util.*;

/**
 * Created by wandersen on 13/6/17.
 */
public class SamplePrivateWallet {
    public static String username = "Winston Andersen";


    public static String getRandomNumberOfLength(int length)
    {
        // this has limitation of int
        Random r = new Random();
        int High = (int) Math.pow(10,length);
        int Low = High/10;
        int Result = r.nextInt(High-Low) + Low;
        String randomNumber = String.valueOf(Result);
        return randomNumber;
    }

    public static void main(String[] args) {
        Xfers.setIDSandbox();
//        Xfers.setEndpoint("http://localhost:3000/api/v3");

        String xfersAppApiKey = "";
        String xfersAppSecretKey = "";
        Xfers.apiKey = "";


        try {
            //sign up the user
            String phoneNumber = "+628128276162";

            String identity_no = getRandomNumberOfLength(10);

            String userApiToken = signUp(xfersAppApiKey, xfersAppSecretKey, phoneNumber,identity_no); // user api token should be saved in database and this function should only be called once per user

            System.out.println("Retrieving current user transfer info");
            TransferInfo transferInfo = User.transferInfo(userApiToken);

            List<TransferInfo> transferInfoArray = transferInfo.getTransferInfoArray();
            for (TransferInfo info : transferInfoArray) {
                System.out.println("Hi " + username +  " please transfer to the following bank: " + info.getBankNameFull() + " to the following account number: " + info.getBankAccountNo());
            }

            System.out.println("Retrieving current user");
            User user = User.retrieve(userApiToken);
            System.out.println(user.getFirstName());
            System.out.println(user.getDateOfBirth());
            System.out.println(user.getAvailableBalance());
            for (BankAccount bankAccount : user.getBankAccounts()) {
                System.out.println(bankAccount.toString());
            }
            System.out.println(user.toString());


            System.out.println("Creating a callback");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("callback_url", "http://de12f55e.ngrok.io"); // this is a required field
            params.put("events", "[ \"deposit\" ]");
            params.put("name", "Winston");

            Response callbackResponse = User.registerCallback(params,userApiToken);
            System.out.println(callbackResponse.toString());

            Response callbackDeleteResponse = User.deleteCallback(userApiToken);
            System.out.println(callbackDeleteResponse.toString());

//            System.out.println("User API token is "+userApiToken);
                // start of the receive money process
//            // create a bill that the user has to pay following amount to the following VA
//            createIntent(userApiToken);// this function will give a callback to https://mysite.com/topup_notification when user pay
//
//            seeOutstandingIntent(userApiToken);
////            // user make a transfer
//            makeTransfer();
//
//            // after do the simulate transfer, this function must be called
//            // let's assume that when https://mysite.com/topup_notification get a callback, the following function is called
//                moneyReceivedCallback(userApiToken);
//            // end of the sending money process
//
//            // start of the sending money process
//            String bankAccountId = addBankAndCheck(userApiToken);

//
//            sendMoney(bankAccountId, userApiToken);
                // end of the sending money process



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static boolean fuzzyCheck(String detectedName){
        //Code if you want to handle the name check by yourself code to handle wrong bank name
        return true;
    }

    public static void makeTransfer()
    {
        System.out.println("Technology is not there yet. Go to sandbox-id.xfers.com to make mock transfer");
    }

    public static void seeOutstandingIntent(String userApiToken) throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Intent seePendingTransaction = Intent.retrieve(userApiToken);

        System.out.println("The user: " + username + " has this following unpaid transaction:");
        List<TransferInfo> transferInfoArray = seePendingTransaction.getTransferInfoArray();
        for (TransferInfo transferInfo : transferInfoArray) {
            System.out.println( username + " needs to transfer the following amount: " + seePendingTransaction.getAmount() + " of the following bank: " + transferInfo.getBankNameFull() + " to the following account number: " + transferInfo.getBankAccountNo());
        }
    }

    public static Charge moneyReceivedCallback(String userApiToken) throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Charge charge = null;
        System.out.println("Creating a charge");
        Map<String, Object> params = new HashMap<String, Object>();
        String someRandomString = UUID.randomUUID().toString();

        params.put("amount", "10000"); // this is a required field
        params.put("currency", "IDR"); // this is a required field
        params.put("order_id", someRandomString); // this is a required field and must be unique
        params.put("notify_url", "https://mysite.com/payment_notification");
        params.put("description", "any description");
        params.put("redirect", false);
        params.put("debit_only", "true"); // this should not be forgotten
        params.put("user_api_token", userApiToken); // put this as the user api token that we got from during the signup process


        charge = Charge.create(params, Xfers.apiKey); // you can change this line to only  Charge.create(params) if you want
        System.out.println("Charge has been created, result:");
        charge.getMetaData();
        System.out.println(charge.toString());
        return charge;

    }


    public static void createIntent(String userApiToken) throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        // start of the receiving money process

        String someRandomString = UUID.randomUUID().toString();
        System.out.println("Creating an intent");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", "99999"); // this is a required field
        params.put("currency", "IDR"); // this is a required field
        params.put("request_id", someRandomString); // this is a required field and must be unique

        params.put("notify_url", "http://99ac99a9.ngrok.io"); // notify url will give you the callback URL when the money has been transferred to the account
        Intent intent = Intent.create(params, userApiToken);

        List<TransferInfo> transferInfoArray = intent.getTransferInfoArray();
        for (TransferInfo transferInfo : transferInfoArray) {
            System.out.println("Hi " + username + " please Transfer the following amount: " + intent.getAmount() + " of the following bank: " + transferInfo.getBankNameFull() + " to the following account number: " + transferInfo.getBankAccountNo());
        }

    }

    public static String signUp(String xfersAppApiKey,String xfersAppSecretKey,String phoneNumber, String identity_no) throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        // start of the sign up process
        // this function should only be called once per user
        System.out.println("Authorizing");
        ConnectResponse response = Connect.privateAuthorize(phoneNumber, xfersAppApiKey, xfersAppSecretKey);
        String userApiToken = response.getUserApiToken(); // save this value in database!


        // required data to be given is first_name, last_name, identity_no, address_line_1, date_of_birth, nationality, gender
        System.out.println("Updating current user");
        Map<String, Object> updateParams = new HashMap<String, Object>();
        updateParams.put("first_name", "Winston");
        updateParams.put("last_name", "Andersen");
        updateParams.put("identity_no", identity_no); // KTP number
        updateParams.put("address_line_1", "Blk 712 loyang Avenue 5");
        updateParams.put("address_line_2", "#01-41");
        updateParams.put("date_of_birth", "1986-02-27"); // format is yyyy-mm-dd
        updateParams.put("nationality", "Indonesian");
        updateParams.put("gender", "male"); // male / female
        updateParams.put("country", "id"); // just set this to id always

        User.update(updateParams, userApiToken);
        return userApiToken;

    }

    public static String addBankAndCheck(String userApiToken) throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        System.out.println("Adding Bank account and checking its legitimacy");
        Map<String, Object> params = new HashMap<String, Object>();


        String randomName = UUID.randomUUID().toString();

        params.put("account_no", "1234567890");  // this is a required field
        params.put("bank", "BCA");  // this is a required field
        params.put("account_holder_name", "Some Name");
        List<BankAccount> bankAccounts = BankAccount.add(params,userApiToken);
        String bankAccountId = null;
        for (BankAccount bankAccount : bankAccounts) {
            System.out.println(bankAccount.toString());
            bankAccountId = bankAccount.getId();
            fuzzyCheck(bankAccount.getDetectedName());
        }
        return bankAccountId;
    }

    public static void sendMoney(String bankAccountId,String userApiToken) throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException, InterruptedException {
        System.out.println("Creating a payout");
        String someRandomString = UUID.randomUUID().toString();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", "108000"); // this is a required field
        params.put("invoice_id", someRandomString); // this is a required field and must be unique
        params.put("user_api_token", userApiToken); // put this as the user api token that we got from during the signup process
        params.put("descriptions", "some description");

        Payout payout = Payout.create(params,Xfers.apiKey); // you can change this line to only  Payout.create(params) if you want

        System.out.println("Making a withdrawal request");
        params = new HashMap<String, Object>();
        params.put("amount", "100000"); // this is a required field
        params.put("express", true); // this must be set to be true for real time money sending
        Withdrawal withdrawal = BankAccount.withdraw(bankAccountId, params, userApiToken);

        System.out.println("Result of Withdrawal:");
        System.out.println(withdrawal.toString());
    }

}
