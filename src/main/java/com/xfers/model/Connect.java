package com.xfers.model;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.model.response.ConnectResponse;
import com.xfers.net.APIResource;
import com.xfers.serializer.SnakeToCamelDeserializer;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;

public class Connect {
    private static final String resourceUrl = "/authorize";

    /**
     * After user receives the OTP SMS, merchant's app should capture the OTP and pass it to Xfers via this API.
     * @return An instance of ConnectResponse, which contains the user's unique ID and user's API KEY.
     * @param returnURL Optional parameter, fill with a null value if not needed.
     */
    public static ConnectResponse getToken(String phoneNumber, String OTP, String returnURL, String appKey, String secretKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("phone_no", phoneNumber);
        params.put("otp", OTP);
        params.put("signature", getSignature(phoneNumber, secretKey, OTP));
        if (null != returnURL) {
            params.put("return_url", returnURL);
        }

        String url = resourceUrl + "/get_token";
        String response = APIResource.requestConnect(APIResource.RequestMethod.GET, url, params, appKey);
        return SnakeToCamelDeserializer.create().fromJson(response, ConnectResponse.class);
    }

    /**
     * Register a new user and send an OTP to the phone number in parameter.
     * @return A message whether it is success or not.
     */
    public static String authorize(String phoneNumber, String appKey, String secretKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("phone_no", phoneNumber);
        params.put("signature", getSignature(phoneNumber, secretKey));

        String url = resourceUrl + "/signup_login";
        String response = APIResource.requestConnect(APIResource.RequestMethod.POST, url, params, appKey);
        return SnakeToCamelDeserializer.create().fromJson(response, ConnectResponse.class).getMessage();
    }

    /**
     * Register a new user without the need of OTP or get the user API KEY.
     * @return An instance of ConnectResponse, which contains the user's unique ID and user's API KEY. Ignore SignUpURL and WalletName.
     */
    public static ConnectResponse privateAuthorize(String phoneNumber, String appKey, String secretKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("phone_no", phoneNumber);
        params.put("signature", getSignature(phoneNumber, secretKey));

        String url = resourceUrl + "/private_wallet";
        String response = APIResource.requestConnect(APIResource.RequestMethod.POST, url, params, appKey);
        return SnakeToCamelDeserializer.create().fromJson(response, ConnectResponse.class);
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