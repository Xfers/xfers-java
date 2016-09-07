package com.xfers.net;


import com.google.common.base.Strings;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import com.xfers.Xfers;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.model.User;

import java.util.HashMap;
import java.util.Map;

public class APIResource {

    public enum RequestMethod {
        GET, POST, PUT, DELETE
    }



    public static HttpResponse<JsonNode> request(APIResource.RequestMethod method,
                                   String resourceUrl, Map<String, Object> params, String apiKey) throws AuthenticationException,
            InvalidRequestException, APIException, APIConnectionException, UnirestException {

        String authHeader = "X-XFERS-APP-API-KEY";
        if (Strings.isNullOrEmpty(apiKey)) {
            authHeader = "X-XFERS-USER-API-KEY";
            apiKey = Xfers.apiKey;
        }

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("accept", "application/json");
        headers.put(authHeader, apiKey);

        String url = Xfers.getApiBase() + resourceUrl;

        switch (method) {
            case GET:
                return Unirest.get(url)
                        .headers(headers)
                        .queryString(params)
                        .asJson();
            case POST:
                return Unirest.post(url)
                        .headers(headers)
                        .fields(params)
                        .asJson();
            case PUT:
                return Unirest.put(url)
                        .headers(headers)
                        .fields(params)
                        .asJson();
            case DELETE:
                return Unirest.delete(url)
                        .headers(headers)
                        .asJson();
            default:
                throw new APIConnectionException(
                        String.format(
                                "Unrecognized HTTP method %s. "
                                        + "This indicates a bug in the Xfers bindings. Please contact "
                                        + "support@xfers.io for assistance.",
                                method));
        }

    }
}