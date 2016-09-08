package com.xfers.net;


import com.google.common.base.Strings;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import com.xfers.Xfers;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;

import java.util.HashMap;
import java.util.Map;

public class APIResource {

    public enum RequestMethod {
        GET, POST, PUT, DELETE
    }

    private static String handleResponse(HttpResponse<JsonNode> httpResponse) throws InvalidRequestException, AuthenticationException, APIException {
        Integer status = httpResponse.getStatus();
        String body = httpResponse.getBody().toString();
        if (status == 200) {
            return body;
        } else if (status == 400) {
            throw new InvalidRequestException(body, status);
        } else if (status == 401) {
            throw new AuthenticationException(body, status);
        } else {
            throw new APIException(body, status);
        }
    }

    public static String request(APIResource.RequestMethod method,
                                   String resourceUrl, Map<String, Object> params, String apiKey) throws AuthenticationException,
            InvalidRequestException, APIException, UnirestException, APIConnectionException {

        String authHeader = "X-XFERS-APP-API-KEY";
        if (Strings.isNullOrEmpty(apiKey)) {
            authHeader = "X-XFERS-USER-API-KEY";
            apiKey = Xfers.apiKey;
        }

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("accept", "application/json");
        headers.put(authHeader, apiKey);

        String url = Xfers.getApiBase() + resourceUrl;
        HttpResponse<JsonNode> httpResponse = null;

        switch (method) {
            case GET:
                httpResponse = Unirest.get(url)
                        .headers(headers)
                        .queryString(params)
                        .asJson();
                return handleResponse(httpResponse);
            case POST:
                httpResponse = Unirest.post(url)
                        .headers(headers)
                        .fields(params)
                        .asJson();
                return handleResponse(httpResponse);
            case PUT:
                httpResponse = Unirest.put(url)
                        .headers(headers)
                        .fields(params)
                        .asJson();
                return handleResponse(httpResponse);
            case DELETE:
                httpResponse = Unirest.delete(url)
                        .headers(headers)
                        .asJson();
                return handleResponse(httpResponse);
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