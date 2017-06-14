package com.xfers.model;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.net.APIResource;

import java.util.Map;

public class Connect {
    private static final String resourceUrl = "/authorize";

    public static Response getToken(Map<String, Object> params, String connectKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/get_token";
        String response = APIResource.request(APIResource.RequestMethod.GET, url, params, connectKey);
        Gson gson = new Gson();
        return gson.fromJson(response, Response.class);
    }

    public static Response authorize(Map<String, Object> params, String connectKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/signup_login";
        String response = APIResource.request(APIResource.RequestMethod.POST, url, params, connectKey);
        Gson gson = new Gson();
        return gson.fromJson(response, Response.class);
    }
}