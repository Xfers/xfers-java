package com.xfers;

import com.google.gson.Gson;
import com.xfers.model.Charge;
import com.xfers.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ChargesTest {
    public static void main(String[] args) {
        Xfers.apiKey = "uoGUpNsfuFUXZUz2DQPAQXksG1JuwWVsy8zwsWvS29x";
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
            Vector<Map<String, String>> items = new Vector<Map<String, String>>();
            Map<String, String> item = new HashMap<String, String>();
            item.put("description", "Red dress Size M");
            item.put("price", "9.99");
            item.put("quantity", "1");
            item.put("name", "Red dress");
            items.add(item);

            Map<String, String> meta_data = new HashMap<String, String>();
            meta_data.put("firstname", "Tianwei");
            meta_data.put("lastname", "Liu");

            Map<String, Object> params = new HashMap<String, Object>();
            Gson gson = new Gson();

            params.put("amount", "9.99");
            params.put("currency", "SGD");
            params.put("notify_url", "https://mysite.com/payment_notification");
            params.put("return_url", "https://mysite.com/return");
            params.put("cancel_url", "https://mysite.com/cancel");
            params.put("order_id", "AZ9912");
            params.put("description", "unused red dress");
            params.put("shipping", "2.50");
            params.put("tax", "0.0");
            params.put("items", gson.toJson(items));
            params.put("meta_data", gson.toJson(meta_data));

            Charge charge = Charge.create(params);
            System.out.println(charge.getId());
            System.out.println(charge.getAmount());
            System.out.println(charge.getCheckoutUrl());
            System.out.println(charge.getOrderId());
            System.out.println(charge.getStatus());
            System.out.println(charge.toString());

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
