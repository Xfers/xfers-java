package com.xfers.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.net.APIResource;
import com.xfers.serializer.SnakeToCamelDeserializer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class User {
    private static final String resourceUrl = "/user";

    private BigDecimal availableBalance;
    private BigDecimal ledgerBalance;
    private String firstName;
    private String lastName;
    @SerializedName("address_line_1") private String addressLine1;
    @SerializedName("address_line_2") private String addressLine2;
    private String postalCode;
    private String identityNo;
    private String phoneNo;
    private Boolean multiBankAccountDetected;
    private Boolean accountLocked;
    private BigDecimal kycLimitRemaining;
    private Boolean kycVerified;
    private String dateOfBirth;
    private List<BankAccount> bankAccounts;
    private String metaData;
    private String kycStatus;
    private String country;
    private String email;
    private String nationality;

    // KTP Fields
    private String idFrontUrl;
    @SerializedName("selfie_2id_url") private String selfie2idUrl;
    private String motherMaidenName;
    private String state;
    private String city;
    private String fullName;
    private String placeOfBirth;
    private String gender;
    private String bloodType;
    private String rtRw;
    private String administrativeVillage;
    private String district;
    private String religion;
    private String maritalStatus;
    private String occupation;

    public static Response registerCallback(Map<String, Object> params, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/balance_callback";
        String response = APIResource.request(APIResource.RequestMethod.POST, url, params, userApiToken);
        System.out.println(response);
        Gson gson = new Gson();
        return gson.fromJson(response, Response.class);
    }

    public static Response registerCallback(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return registerCallback(params,null);
    }

    public static Response deleteCallback(String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String url = resourceUrl + "/balance_callback";
        String response = APIResource.request(APIResource.RequestMethod.DELETE, url, null, userApiToken);
        Gson gson = new Gson();
        return gson.fromJson(response, Response.class);
    }

    public static Response deleteCallback()
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return deleteCallback(null);
    }

    public static User retrieve(String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String response = APIResource.request(APIResource.RequestMethod.GET, resourceUrl, null, userApiToken);
        return SnakeToCamelDeserializer.create().fromJson(response, User.class);
    }

    public static User retrieve()
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return retrieve(null);
    }

    public static User update(Map<String, Object> params, String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String response = APIResource.request(APIResource.RequestMethod.PUT, resourceUrl, params, userApiToken);
        return SnakeToCamelDeserializer.create().fromJson(response, User.class);
    }

    public static User update(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return update(params, null);
    }

    public static TransferInfo transferInfo(String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String response = APIResource.request(APIResource.RequestMethod.GET, TransferInfo.resourceUrl, null, userApiToken);
        Gson gson = new Gson();
        return gson.fromJson(response, TransferInfo.class);
    }

    public static TransferInfo transferInfo()
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return transferInfo(null);
    }

    public static List<Activity> activities(String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String str = APIResource.request(APIResource.RequestMethod.GET, Activity.resourceUrl, null, userApiToken);
        Gson gson = new Gson();
        Response response =  gson.fromJson(str, Response.class);
        return response.getActivities();
    }

    public static List<Activity> activities()
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return activities(null);
    }

    public static void mockVerify(String userApiToken)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        APIResource.request(APIResource.RequestMethod.PATCH, resourceUrl + "/verify", null, userApiToken);
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public BigDecimal getLedgerBalance() {
        return ledgerBalance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public Boolean getMultiBankAccountDetected() {
        return multiBankAccountDetected;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public BigDecimal getKycLimitRemaining() {
        return kycLimitRemaining;
    }

    public Boolean getKycVerified() {
        return kycVerified;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getNationality() {
        return nationality;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public String getMetaData() {
        return metaData;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public String getIdFrontUrl() {
        return this.idFrontUrl;
    }

    public String getSelfie2idUrl() {
        return this.selfie2idUrl;
    }

    public String getMotherMaidenName() {
        return this.motherMaidenName;
    }

    public String getState() {
        return this.state;
    }

    public String getCity() {
        return this.city;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getPlaceOfBirth() {
        return this.placeOfBirth;
    }

    public String getGender() {
        return this.gender;
    }

    public String getBloodType() {
        return this.bloodType;
    }

    public String getRtRw() {
        return this.rtRw;
    }

    public String getAdministrativeVillage() {
        return this.administrativeVillage;
    }

    public String getDistrict() {
        return this.district;
    }

    public String getReligion() {
        return this.religion;
    }

    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public String getOccupation() {
        return this.occupation;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
