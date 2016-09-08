package com.xfers;

import com.xfers.model.Charge;
import com.xfers.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChargesTest {
    public static void main(String[] args) {
        Xfers.apiKey = "pXcfdAKNorDe_o1eou1NSp4mwssiEzem_6sg8fwnZWs";
        Xfers.setSGSandbox();

        try {
            System.out.println("Listing charges without filter");
            List<Charge> charges = Charge.listAll();
            for (Charge charge : charges) {
                System.out.println(charge.toString());
                List<Item> items = charge.getItems();
                for (Item item : items) {
                    System.out.println(item.toString());
                }
            }

            System.out.println("Listing charges with filter");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("limit", "1");

            charges = Charge.listAll(params);
            for (Charge charge : charges) {
                System.out.println(charge.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Creating a charge");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("amount", "19.99");
            params.put("currency", "SGD");
            params.put("order_id", "A012315");
            params.put("description", "Carousell user - Konsolidate");

            Charge charge = Charge.create(params);
            System.out.println(charge.getId());
            System.out.println(charge.getAmount());
            System.out.println(charge.getCheckoutUrl());
            System.out.println(charge.getOrderId());
            System.out.println(charge.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Retrieving a charge");
            Charge charge = Charge.retrieve("da454bce431a4f368667aa1db59427ad");
            System.out.println(charge.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Cancelling a charge");
            Charge charge = Charge.cancel("da454bce431a4f368667aa1db59427ad");
            System.out.println(charge.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Settling a charge");
            Charge charge = Charge.settle("da454bce431a4f368667aa1db59427ad");
            System.out.println(charge.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Refunding a charge");
            Charge charge = Charge.refund("da454bce431a4f368667aa1db59427ad");
            System.out.println(charge.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Validating a charge");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("total_amount", "19.99");
            params.put("currency", "SGD");
            params.put("order_id", "A012312");
            params.put("status", "paid");
            String message = Charge.validate("da454bce431a4f368667aa1db59427ad", params);
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
