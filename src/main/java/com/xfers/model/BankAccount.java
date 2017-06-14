package com.xfers.model;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.net.APIResource;

import java.util.*;

public class BankAccount {
    private static final String resourceUrl = "/user/bank_account";

    @SerializedName("bank_abbrev") private String bankAbbrev;
    @SerializedName("account_no") private String accountNo;
    @SerializedName("detected_name") private String detected_name;

    // for list availableBanks
    @SerializedName("name") private String name;
    @SerializedName("abbreviation") private String abbreviation;
    @SerializedName("img_src") private String img_src;

    private String id;

    public static boolean isBankAvailable (String bankAbbrev) throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        List<BankAccount> availableBanks = availableBanks(null);
        for (BankAccount bankAccount : availableBanks) {
            if(bankAccount.getAbbreviation().equals(bankAbbrev.toUpperCase()) )
            {
                return true;
            }
        }
        return false;
    }

    public static List<BankAccount> availableBanks(String userApiToken) throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        String response = APIResource.request(APIResource.RequestMethod.GET, "/banks", null,userApiToken);
        Gson gson = new Gson();
        return new ArrayList<BankAccount>(Arrays.asList(gson.fromJson(response, BankAccount[].class)));
    }

    public static List<BankAccount> availableBanks() throws APIException, UnirestException, AuthenticationException, InvalidRequestException, APIConnectionException {
        return availableBanks(null);
    }

    public static List<BankAccount> retrieve(String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String response = APIResource.request(APIResource.RequestMethod.GET, resourceUrl, null, userApiToken);
        Gson gson = new Gson();
        return new ArrayList<BankAccount>(Arrays.asList(gson.fromJson(response, BankAccount[].class)));
    }

    public static List<BankAccount> retrieve()
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return retrieve(null);
    }

    public static List<BankAccount> add(Map<String, Object> params, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String response = APIResource.request(APIResource.RequestMethod.POST, resourceUrl, params, userApiToken);
        Gson gson = new Gson();
        return new ArrayList<BankAccount>(Arrays.asList(gson.fromJson(response, BankAccount[].class)));
    }

    public static List<BankAccount> add(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return add(params, null);
    }

    public static List<BankAccount> update(String id, Map<String, Object> params, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/" + id;
        String response = APIResource.request(APIResource.RequestMethod.PUT, url, params, userApiToken);
        Gson gson = new Gson();
        return new ArrayList<BankAccount>(Arrays.asList(gson.fromJson(response, BankAccount[].class)));
    }

    public static List<BankAccount> update(String id, Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return update(id, params, null);
    }

    public static List<BankAccount> delete(String id, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/" + id;
        String response = APIResource.request(APIResource.RequestMethod.DELETE, url, null, userApiToken);
        Gson gson = new Gson();
        return new ArrayList<BankAccount>(Arrays.asList(gson.fromJson(response, BankAccount[].class)));
    }

    public static List<BankAccount> delete(String id)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return delete(id, null);
    }

    public static Withdrawal withdraw(String bankAccountId, Map<String, Object> params, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/" + bankAccountId + "/withdraw";
        String str = APIResource.request(APIResource.RequestMethod.POST, url, params, userApiToken);
        Gson gson = new Gson();
        Response response = gson.fromJson(str, Response.class);
        return response.getWithdrawalRequest();
    }

    public static Withdrawal withdraw(String bankAccountId, Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return withdraw(bankAccountId, params, null);
    }

    public static List<Withdrawal> withdrawalRequests(String filter, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/withdrawal_requests";

        Map<String, Object> params = new HashMap<String, Object>();
        if (!Strings.isNullOrEmpty(filter)) params.put("filter", filter);

        String str = APIResource.request(APIResource.RequestMethod.GET, url, params, userApiToken);
        Gson gson = new Gson();
        Response response = gson.fromJson(str, Response.class);
        return response.getWithdrawalRequests();
    }

    public static List<Withdrawal> withdrawalRequests(String filter)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return withdrawalRequests(filter, null);
    }

    public String getBankAbbrev() {
        return bankAbbrev;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getDetectedName() {
        return detected_name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getImgSrc() {
        return img_src;
    }


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}