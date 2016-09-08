package com.xfers;

import com.xfers.model.Connect;
import com.xfers.model.Response;

import java.util.HashMap;
import java.util.Map;

public class ConnectTest {
    public static void main(String[] args) {
        Xfers.setSGSandbox();
        String xfersAppApiKey = "AeWpKz5cdPoJFUwF53sBee_WsSoqym_hspiX3bcoB_Y";

        try {
            System.out.println("Authorizing");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("phone_no", "+6597288608");
            params.put("signature", "a4f001729fe3accdbb0d9cfaf3b49b0678a4c91b");
            Response response = Connect.authorize(params, xfersAppApiKey);
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
            Response response = Connect.getToken(params, xfersAppApiKey);
            System.out.println(response.getMsg());
            System.out.println(response.getUserApiToken());
            System.out.println(response.getSignUpUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}