package com.xfers.model;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.net.APIResource;

import java.util.*;

/**
 * Created by wandersen on 4/7/17.
 */
public class MockOtp {

    public static String getMockOtp(String phone_no, String userApiToken) throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("phone_no", phone_no);
        String response = APIResource.request(APIResource.RequestMethod.GET, "/authorize/get_mock_otp", params,userApiToken);
        String OTP = response.substring(8,14);
        return OTP;
    }

    public static String getMockOtp(String phone_no) throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        return getMockOtp(phone_no,null);
    }
}
