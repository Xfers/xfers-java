package com.xfers.model.example.functional;

import static org.junit.Assert.assertEquals;

import com.xfers.Xfers;
import com.xfers.model.Connect;
import com.xfers.model.response.ConnectResponse;
import org.junit.Test;

public class ConnectExample {
    public static void main(String[] args) {
        Xfers.setSGSandbox();
        String xfersAppApiKey = "AeWpKz5cdPoJFUwF53sBee_WsSoqym_hspiX3bcoB_Y";
        String xfersSecretApiKey = "AeWpKz5cdPoJFUwF53sBee_WsSoqym_hspiX3bcoB_Y";

        try {
            System.out.println("Authorizing");
            String phoneNumber = "+65xxxxxxxx";
            String message = Connect.authorize(phoneNumber, xfersAppApiKey, xfersSecretApiKey);
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Getting token");
            String phoneNumber = "+65xxxxxxxx";
            String OTP = "541231";
            String returnURL = "https://mywebsite.com/api/v3/account_registration/completed";
            ConnectResponse response = Connect.getToken(phoneNumber, OTP, returnURL, xfersAppApiKey, xfersSecretApiKey);
            System.out.println(response.getMessage());
            System.out.println(response.getUserApiToken());
            System.out.println(response.getSignUpUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Authorizing");
            String phoneNumber = "+65xxxxxxxx";
            ConnectResponse response = Connect.privateAuthorize(phoneNumber, xfersAppApiKey, xfersSecretApiKey);
            System.out.println(response.getMessage());
            System.out.println(response.getUserApiToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetSignature() {
        String xfersAppSecretKey = "AeWpKz5cdPoJFUwF53sBee_WsSoqym_hspiX3bcoB_Y";

        String signup_signature;

        System.out.println("Testing create signature without OTP");
        signup_signature = Connect.getSignature("+6588888888", xfersAppSecretKey);
        assertEquals("8c8dc202904a6314c984179bd536c36f8b5a9d35", signup_signature);

        System.out.println("Testing create signature with OTP");
        signup_signature = Connect.getSignature("+6588888888", xfersAppSecretKey, "541231");
        assertEquals("d6822e49286c049bbaac638cf1bd1db5bccfbd67", signup_signature);

        System.out.println("Testing create signature with phone number not properly written sg");
        signup_signature = Connect.getSignature("88888888", xfersAppSecretKey, "541231");
        assertEquals("d6822e49286c049bbaac638cf1bd1db5bccfbd67", signup_signature);

        System.out.println("Testing create signature with phone number not properly written id");
        signup_signature = Connect.getSignature("087888888888", xfersAppSecretKey, "541231");
        assertEquals("d021946467dd7c92dbc5031109d5eee5b8125171", signup_signature);

        System.out.println("Testing create signature with phone number not properly written id");
        signup_signature = Connect.getSignature("+087888888888", xfersAppSecretKey, "541231");
        assertEquals("d021946467dd7c92dbc5031109d5eee5b8125171", signup_signature);
    }
}
