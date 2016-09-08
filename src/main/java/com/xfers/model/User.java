package com.xfers.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.xfers.exception.APIConnectionException;
import com.xfers.exception.APIException;
import com.xfers.exception.AuthenticationException;
import com.xfers.exception.InvalidRequestException;
import com.xfers.net.APIResource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class User {
    private static final String resourceUrl = "/user";
    @SerializedName("available_balance") private BigDecimal availableBalance;
    @SerializedName("ledger_balance") private BigDecimal ledgerBalance;
    @SerializedName("credit_card_rates") private BigDecimal creditCardRates;
    @SerializedName("credit_card_fees") private BigDecimal creditCardFees;
    @SerializedName("bank_transfer_rates") private BigDecimal bankTransferRates;
    @SerializedName("bank_transfer_fees") private BigDecimal bankTransferFees;
    @SerializedName("first_name") private String firstName;
    @SerializedName("last_name") private String lastName;
    @SerializedName("address_line_1") private String addressLine1;
    @SerializedName("address_line_2") private String addressLine2;
    @SerializedName("postal_code") private String postalCode;
    @SerializedName("identity_no") private String identityNo;
    @SerializedName("id_document") private String idDocument;
    @SerializedName("id_back") private String idBack;
    @SerializedName("id_front") private String idFront;
    @SerializedName("id_selfie") private String idSelfie;
    @SerializedName("phone_no") private String phoneNo;
    @SerializedName("multi_bank_account_detected") private Boolean multiBankAccountDetected;
    @SerializedName("account_locked") private Boolean accountLocked;
    @SerializedName("kyc_limit_remaining") private BigDecimal kycLimitRemaining;
    @SerializedName("kyc_verified") private Boolean kycVerified;
    @SerializedName("date_of_birth") private String dateOfBirth;
    @SerializedName("bank_accounts") private List<BankAccount> bankAccounts;
    @SerializedName("meta_data") private String metaData;

    private String country;
    private String email;
    private String nationality;

    public static User retrieve(String connectKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String response = APIResource.request(APIResource.RequestMethod.GET, resourceUrl, null, connectKey);
        Gson gson = new Gson();
        return gson.fromJson(response, User.class);
    }

    public static User retrieve()
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return retrieve(null);
    }

    public static User update(Map<String, Object> params, String connectKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String response = APIResource.request(APIResource.RequestMethod.PUT, resourceUrl, params, connectKey);
        Gson gson = new Gson();
        return gson.fromJson(response, User.class);
    }

    public static User update(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return update(params, null);
    }

    public static TransferInfo transferInfo(String connectKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String response = APIResource.request(APIResource.RequestMethod.GET, TransferInfo.resourceUrl, null, connectKey);
        Gson gson = new Gson();
        return gson.fromJson(response, TransferInfo.class);
    }

    public static TransferInfo transferInfo()
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return transferInfo(null);
    }

    public static List<Activity> activities(String connectKey)
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        String str = APIResource.request(APIResource.RequestMethod.GET, Activity.resourceUrl, null, connectKey);
        Gson gson = new Gson();
        Response response =  gson.fromJson(str, Response.class);
        return response.getActivities();
    }

    public static List<Activity> activities()
            throws AuthenticationException, InvalidRequestException, APIException, APIConnectionException, UnirestException {
        return activities(null);
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public BigDecimal getLedgerBalance() {
        return ledgerBalance;
    }

    public BigDecimal getCreditCardRates() {
        return creditCardRates;
    }

    public BigDecimal getCreditCardFees() {
        return creditCardFees;
    }

    public BigDecimal getBankTransferRates() {
        return bankTransferRates;
    }

    public BigDecimal getBankTransferFees() {
        return bankTransferFees;
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

    public String getIdDocument() {
        return idDocument;
    }

    public String getIdBack() {
        return idBack;
    }

    public String getIdFront() {
        return idFront;
    }

    public String getIdSelfie() {
        return idSelfie;
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

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
