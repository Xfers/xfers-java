package com.xfers.net;


import com.google.common.base.Strings;
import com.mashape.unirest.http.HttpResponse;
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
        GET, POST, PUT, DELETE, PATCH
    }

    private static String handleResponse(HttpResponse<String> httpResponse) throws InvalidRequestException, AuthenticationException, APIException {
        Integer status = httpResponse.getStatus();
        String body = httpResponse.getBody();

        switch(status) {
            case 200:
                return body;
            case 400:
                throw new InvalidRequestException(body, status);
            case 401:
                throw new AuthenticationException(body, status);
            case 499:
            case 504:
                throw new APIException("Timeout", status);
            default:
                throw new APIException(body, status);
        }
    }

    public static String request(APIResource.RequestMethod method, String resourceUrl, Map<String, Object> params, String apiKey)
            throws AuthenticationException, InvalidRequestException, APIException, UnirestException, APIConnectionException {

        String authHeader = "X-XFERS-USER-API-KEY";
        if (Strings.isNullOrEmpty(apiKey)) {
            apiKey = Xfers.apiKey;
        }
        return requestHelper(method, resourceUrl, params, apiKey, authHeader);
    }

    public static String requestConnect(APIResource.RequestMethod method, String resourceUrl, Map<String, Object> params, String apiKey)
            throws AuthenticationException, InvalidRequestException, APIException, UnirestException, APIConnectionException {

        String authHeader = "X-XFERS-APP-API-KEY";
        if (Strings.isNullOrEmpty(apiKey)) {
            throw new AuthenticationException("You must put your X-XFERS-APP-API-KEY", 400);
        }
        return requestHelper(method, resourceUrl, params, apiKey, authHeader);
    }

    private static String requestHelper(APIResource.RequestMethod method, String resourceUrl, Map<String, Object> params, String apiKey, String authHeader)
            throws AuthenticationException, InvalidRequestException, APIException, UnirestException, APIConnectionException {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("accept", "application/json");
        headers.put(authHeader, apiKey);

        String url = Xfers.getApiBase() + resourceUrl;
        HttpResponse<String> httpResponse = null;

        switch (method) {
            case GET:
                httpResponse = Unirest.get(url)
                        .headers(headers)
                        .queryString(params)
                        .asString();
                return handleResponse(httpResponse);
            case POST:
                httpResponse = Unirest.post(url)
                        .headers(headers)
                        .fields(params)
                        .asString();
                return handleResponse(httpResponse);
            case PUT:
                httpResponse = Unirest.put(url)
                        .headers(headers)
                        .fields(params)
                        .asString();
                return handleResponse(httpResponse);
            case DELETE:
                httpResponse = Unirest.delete(url)
                        .headers(headers)
                        .fields(params)
                        .asString();
                return handleResponse(httpResponse);
            case PATCH:
                httpResponse = Unirest.patch(url)
                        .headers(headers)
                        .fields(params)
                        .asString();
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
