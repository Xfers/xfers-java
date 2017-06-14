package com.xfers;

import com.xfers.model.Card;
import com.xfers.model.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardTest {
    public static void main(String[] args) {
        Xfers.apiKey = "WuTp3zM7UEpmUkeAyGPxRHmnXAx-hXJ7jzdqmxY6S1o";
        Xfers.setSGSandbox();
        String user_api_token = "osEdbc8uzxY5vaXA-oe-7E86sVWCYTCVPuHQyFQ-uPQ";
        try {
            System.out.println("Listing cards");
            List<Card> cards = Card.listAll(user_api_token);
            System.out.println("There are " + cards.size() + " cards");
            for (Card card : cards) {
                System.out.println(card.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Adding card");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("user_api_token", user_api_token);
            params.put("credit_card_token", "tok_19DrscB8MXWbQJDjXlIKkc06");
            params.put("first6", "424242");
            params.put("last4", "4242");

            Card card = Card.add(params);
            System.out.println("Added card: ");
            System.out.println(card.getCardId());
            System.out.println(card.getExpMonth());
            System.out.println(card.getExpYear());
            System.out.println(card.getCardCountry());
            System.out.println(card.getCardType());
            System.out.println(card.getLast4());
            System.out.println(card.getIsDefault());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Deleting card");
            String cardId = "card_196kDPI7jGeCrIKDlgVDBvER";
            Card card = Card.delete(cardId, user_api_token);
            System.out.println("Deleted card: " + card.toString());
            System.out.println(card.getCardId());
            System.out.println(card.getDeleted());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Setting default card");
            String cardId = "card_19C2JSI7jGeCrIKD0nVdiCHp";
            Card card = Card.setDefault(cardId, user_api_token);
            System.out.println("Default card: " + card.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Charge guest card");
            String chargeId = "ae9647515a234b95919ce5dbd6e073e8";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("txn_id", chargeId);
            params.put("credit_card_token", "tok_19DrtcB8MXWbQJDjjTYpBAsJ");
            params.put("first6", "424242");
            params.put("last4", "4242");
            Response res = Card.chargeGuest(params);
            System.out.println("Charge guest card success " + res.getSuccess());
            System.out.println("Charge guest card return url " + res.getReturnUrl());
            System.out.println("Charge guest card response " + res.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Charge existing card");
            String chargeId = "3115641fa59e45f1b31e0f60f059b3ef";
            Response res = Card.chargeExisting(chargeId);
            System.out.println("Charge existing card success " + res.getSuccess());
            System.out.println("Charge existing card return url " + res.getReturnUrl());
            System.out.println("Charge existing card response " + res.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}