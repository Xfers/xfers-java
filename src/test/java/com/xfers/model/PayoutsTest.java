package com.xfers.model;

import com.xfers.Xfers;
import com.xfers.model.Payout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayoutsTest {
    public static void main(String[] args) {
        Xfers.apiKey = "pXcfdAKNorDe_o1eou1NSp4mwssiEzem_6sg8fwnZWs";
        Xfers.setSGSandbox();
        String payoutId = "";

        try {
            System.out.println("Creating a payout");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("amount", "2.34");
            params.put("invoice_id", "GYM-MEMBERSHIP");
            params.put("recipient", "demo@xfers.io");

            Payout payout = Payout.create(params);
            payoutId = payout.getId();
            System.out.println(payout.getId());
            System.out.println(payout.getRecipient());
            System.out.println(payout.getAmount());
            System.out.println(payout.getCurrency());
            System.out.println(payout.getDescriptions());
            System.out.println(payout.getBank());
            System.out.println(payout.getBankAccountNo());
            System.out.println(payout.getCreatedDate());
            System.out.println(payout.getCompletedDate());
            System.out.println(payout.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Retrieving a payout");
            Payout payout = Payout.retrieve(payoutId);
            System.out.println(payout.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Listing payouts");
            List<Payout> payouts = Payout.listAll();
            for (Payout payout : payouts) {
                System.out.println(payout.getId());
                System.out.println(payout.getInvoiceId());
                System.out.println(payout.getRecipient());
                System.out.println(payout.getAmount());
                System.out.println(payout.getCurrency());
                System.out.println(payout.getDescriptions());
                System.out.println(payout.getBank());
                System.out.println(payout.getBankAccountNo());
                System.out.println(payout.getCreatedDate());
                System.out.println(payout.getCompletedDate());
                System.out.println(payout.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
