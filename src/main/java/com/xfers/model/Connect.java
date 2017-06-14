package com.xfers.model;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.net.APIResource;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;

public class Connect {
    private static final String resourceUrl = "/authorize";

    public static Response getToken(Map<String, Object> params, String appKey, String secretKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/get_token";
        String phoneNumber = (String) params.get("phone_no");
        String OTP = (String) params.get("otp");

        params.put("signature", getSignature(phoneNumber, secretKey, OTP) );

        String response = APIResource.requestConnect(APIResource.RequestMethod.GET, url, params, appKey);
        Gson gson = new Gson();
        return gson.fromJson(response, Response.class);
    }

    public static Response authorize(Map<String, Object> params, String appKey, String secretKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {

        String url = resourceUrl + "/signup_login";
        String phoneNumber = (String) params.get("phone_no");
        params.put("signature", getSignature(phoneNumber, secretKey) );
        String response = APIResource.requestConnect(APIResource.RequestMethod.POST, url, params, appKey);
        Gson gson = new Gson();
        return gson.fromJson(response, Response.class);

    }

    public static Response privateAuthorize (Map<String, Object> params, String appKey, String secretKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {

        String url = resourceUrl + "/private_wallet";
        String phoneNumber = (String) params.get("phone_no");
        params.put("signature", getSignature(phoneNumber, secretKey) );
        String response = APIResource.requestConnect(APIResource.RequestMethod.POST, url, params, appKey);
        Gson gson = new Gson();
        return gson.fromJson(response, Response.class);

    }

    public static String validate_phone(String phoneNumber)
    {
        // if already using the plus format user should have used the correct format
        if(phoneNumber.length() == 0)
        {
            return null;
        }
        if(phoneNumber.charAt(0) == '+')
        {
            // people may write +087785725657
            if(phoneNumber.charAt(1) == '0')
            {
                return "+".concat("62").concat(phoneNumber.substring(2));
            }
            return phoneNumber;
        }
        // if first char is not 0 and length is 8 , it is probably a Singaporean number
        if(phoneNumber.charAt(0) != '0' && phoneNumber.length() == 8)
        {
            return "+65".concat(phoneNumber);
        }
        // assume the others are indonesian
        else
        {
            if(phoneNumber.charAt(0) == '0')
            {
                return "+62".concat(phoneNumber.substring(1));
            }
            else
            {
                return "+62".concat(phoneNumber);
            }
        }

    }
    public static String getSignature(String phoneNumber, String secretKey, String OTP) {
        String beforeSHA1;
        phoneNumber = validate_phone(phoneNumber);

        if(OTP == null)
        {
            beforeSHA1 = phoneNumber.concat(secretKey);
        }
        else
        {
            beforeSHA1 = phoneNumber.concat(OTP).concat(secretKey);
        }
        String signature = DigestUtils.sha1Hex(beforeSHA1);
        return signature;
    }

    public static String getSignature(String phoneNumber, String secretKey) {
        return getSignature(phoneNumber, secretKey,null);
    }

}