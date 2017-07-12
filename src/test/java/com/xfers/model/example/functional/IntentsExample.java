package com.xfers.model.example.functional;

import com.xfers.Xfers;
import com.xfers.model.Intent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntentsExample {
    public static void main(String[] args) {
        Xfers.apiKey = "G-zsfAEScrqdU8GhWTEdjfdnb3XRdU8q1fH-nuWfSzo";
        Xfers.setSGSandbox();

        String intentId = "";

        try {
            System.out.println("Creating an intent");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("amount", "5.54");
            params.put("currency", "SGD");
            params.put("bank", "OCBC");
            params.put("request_id", "AD0008");
            params.put("notify_url", "https://mysite.com/topup_notification");

            Intent intent = Intent.create(params);
            intentId = intent.getId();

            System.out.println(intent.getId());
            System.out.println(intent.getAmount());
            System.out.println(intent.getCurrency());
            System.out.println(intent.getBank());
            System.out.println(intent.getStatus());
            System.out.println(intent.getBankAccountNo());
            System.out.println(intent.getRequestId());
            System.out.println(intent.getNotifyUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Retrieving latest intent");
            Intent intent = Intent.retrieve();
            System.out.println(intent.getId());
            System.out.println(intent.getAmount());
            System.out.println(intent.getCurrency());
            System.out.println(intent.getBankName());
            System.out.println(intent.getBankAbbrev());
            System.out.println(intent.getBankAccountNo());
            System.out.println(intent.getRequestId());
            System.out.println(intent.getNotifyUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Cancelling an intent");
            Intent intent = Intent.cancel(intentId);
            System.out.println(intent.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
