package com.xfers.model;

import com.xfers.Xfers;
import com.xfers.model.Connect;
import com.xfers.model.Response;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConnectTest {
    public static void main(String[] args) {
        Xfers.setSGSandbox();
        String xfersAppApiKey = "AeWpKz5cdPoJFUwF53sBee_WsSoqym_hspiX3bcoB_Y";
        String xfersSecretApiKey = "AeWpKz5cdPoJFUwF53sBee_WsSoqym_hspiX3bcoB_Y";

        try {
            System.out.println("Authorizing");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("phone_no", "+6597288608");
            params.put("signature", "a4f001729fe3accdbb0d9cfaf3b49b0678a4c91b");
            Response response = Connect.authorize(params, xfersAppApiKey, xfersSecretApiKey);
            System.out.println(response.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Getting token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("otp", "541231");
            params.put("phone_no", "+6597288608");
            params.put("signature", "132e60cc2b6076824fac1ac4c1bb6b47cc3f9036");
            params.put("return_url", "https://mywebsite.com/api/v3/account_registration/completed");
            Response response = Connect.getToken(params, xfersAppApiKey, xfersSecretApiKey);
            System.out.println(response.getMsg());
            System.out.println(response.getUserApiToken());
            System.out.println(response.getSignUpUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Authorizing");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("phone_no", "+6597288608");
            params.put("signature", "a4f001729fe3accdbb0d9cfaf3b49b0678a4c91b");
            Response response = Connect.privateAuthorize(params, xfersAppApiKey, xfersSecretApiKey);
            System.out.println(response.getMsg());
            System.out.println(response.getUserApiToken());
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testGetSignature() {
        String xfersAppSecretKey = "AeWpKz5cdPoJFUwF53sBee_WsSoqym_hspiX3bcoB_Y";

        String signup_signature;

        System.out.println("Testing create signature without OTP");
        signup_signature = Connect.getSignature("+6597288608",xfersAppSecretKey);
        assertEquals("b5e973032fc11b64245038376342cba7d1624593",signup_signature);

        System.out.println("Testing create signature with OTP");
        signup_signature = Connect.getSignature("+6597288608",xfersAppSecretKey, "541231");
        assertEquals("ca57354ed306683b72dd689008fe6a23761ec67a",signup_signature);

        System.out.println("Testing create signature with phone number not properly written sg");
        signup_signature = Connect.getSignature("97288608",xfersAppSecretKey, "541231");
        assertEquals("ca57354ed306683b72dd689008fe6a23761ec67a",signup_signature);

        System.out.println("Testing create signature with phone number not properly written id");
        signup_signature = Connect.getSignature("087785725657",xfersAppSecretKey, "541231");
        assertEquals("ccd43bab5cf3ba9eb4be34b558e0880946ce0dae",signup_signature);

        System.out.println("Testing create signature with phone number not properly written id");
        signup_signature = Connect.getSignature("+087785725657",xfersAppSecretKey, "541231");
        assertEquals("ccd43bab5cf3ba9eb4be34b558e0880946ce0dae",signup_signature);

    }
}