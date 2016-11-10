package com.xfers.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.net.APIResource;

import java.util.*;

public class Card {
    private static final String resourceUrl = "/cards";

    @SerializedName("card_id") private String cardId;
    @SerializedName("last_4") private String last4;
    @SerializedName("card_type") private String cardType;
    @SerializedName("card_country") private String cardCountry;
    @SerializedName("exp_yr") private String expYear;
    @SerializedName("exp_month") private String expMonth;
    @SerializedName("is_default") private String isDefault;

    private Boolean deleted;


    public static List<Card> listAll(String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "?user_api_token=" + userApiToken;
        String response = APIResource.request(APIResource.RequestMethod.GET, url, null, null);
        Gson gson = new Gson();
        return new ArrayList<Card>(Arrays.asList(gson.fromJson(response, Card[].class)));
    }


    public static Card add(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String response = APIResource.request(APIResource.RequestMethod.POST, resourceUrl, params, null);
        Gson gson = new Gson();
        return gson.fromJson(response, Card.class);
    }

    public static Card delete(String cardId, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/" + cardId;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("user_api_token", userApiToken);
        String response = APIResource.request(APIResource.RequestMethod.DELETE, url, params, null);
        Gson gson = new Gson();
        return gson.fromJson(response, Card.class);
    }

    public static Card setDefault(String cardId, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("user_api_token", userApiToken);
        String url = resourceUrl + "/" + cardId + "/set_default";
        String response = APIResource.request(APIResource.RequestMethod.POST, url, params, null);
        Gson gson = new Gson();
        return gson.fromJson(response, Card.class);
    }

    public static Response chargeGuest(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = "/credit_card_charges/charge_card_guest";
        String str = APIResource.request(APIResource.RequestMethod.POST, url, params, null);
        Gson gson = new Gson();
        return gson.fromJson(str, Response.class);
    }

    public static Response chargeExisting(String chargeId)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = "/credit_card_charges/charge_card";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("txn_id", chargeId);
        String str = APIResource.request(APIResource.RequestMethod.POST, url, params, null);
        Gson gson = new Gson();
        return gson.fromJson(str, Response.class);
    }




    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getIsDefault() {
        return isDefault;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public String getCardCountry() {
        return cardCountry;
    }

    public String getCardType() {
        return cardType;
    }

    public String getLast4() {
        return last4;
    }

    public String getCardId() {
        return cardId;
    }

    public Boolean getDeleted() {
        return deleted;
    }
}