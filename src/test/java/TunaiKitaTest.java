import com.google.gson.Gson;
import com.xfers.Xfers;
import com.xfers.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by wandersen on 13/6/17.
 */
public class TunaiKitaTest {
    public static void main(String[] args) {
        Xfers.setIDSandbox();
        // the 3 keys below are usable (doesn't need to be changed)
        String xfersAppApiKey = "y_9SYLDYB5sgwg6RtPz-T3fXkv47HMvJ72WxhU5yzMA";
        String xfersAppSecretKey = "Zym_nnWsRLg7dUS8b6iweMphjUvxytvWEa5kiE382Rx";
        Xfers.apiKey = "pK7jy7cKBv5qL_VMULxaZ4hxNm2fpcSrPfz5Vv2nFKk";
        String phoneNumber = "+6282298822632";
        String userApiToken;
        String someRandomString = UUID.randomUUID().toString();

        // Done when someone login
        try {

            // start of the sign up process
            // this function should only be called once per user

            System.out.println("Authorizing");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("phone_no", phoneNumber); // this is a required field
            Response response = Connect.privateAuthorize(params, xfersAppApiKey, xfersAppSecretKey);
            userApiToken = response.getUserApiToken(); // save this value in database!


            System.out.println("Updating current user");
            Map<String, Object> updateParams = new HashMap<String, Object>();
            updateParams.put("first_name", "wenbin");
            updateParams.put("last_name", "tay");
            updateParams.put("address_line_1", "Blk 712 loyang Avenue 5");
            updateParams.put("address_line_2", "#01-41");
            updateParams.put("nationality", "Singaporean");
            updateParams.put("postal_code", "340712");
            updateParams.put("identity_no", "s86917127G");
            updateParams.put("country", "sg");

            User user = User.update(updateParams, userApiToken);
            // end of the log in process

            // start of the receiving money process
            System.out.println("Creating an intent");
            params = new HashMap<String, Object>();
            params.put("amount", "100000"); // this is a required field
            params.put("currency", "IDR"); // this is a required field
            params.put("request_id", someRandomString); // this is a required field and must be unique
            params.put("bank", "BCA");
            params.put("notify_url", "https://tunaikita.com/topup_notification"); // notify url will give you the callback URL when the money has been transferred to the account
            Intent intent = Intent.create(params, userApiToken); // may be updated soon

            System.out.println("Hi User please Transfer the following amount: " + intent.getAmount() + intent.getBankAccountNo());

            Intent seePendingTransaction = Intent.retrieve(userApiToken);

            System.out.println("The user: " + intent.getAmount() + "has this following unpaid transaction:");
            System.out.println(seePendingTransaction.toString());

            // TODO:  simulate user already paying

            seePendingTransaction = Intent.retrieve(userApiToken);
            System.out.println("The user: " + intent.getAmount() + "has this following unpaid transaction:");
            System.out.println(seePendingTransaction.toString());


            //let's assume that when https://tunaikita.com/topup_notification get a callback, the following function is called
            moneyReceivedCallback(userApiToken);

            // end of the receiving money process


            // start of the sending money process

            // Check if the bank account and account holder is correct
            System.out.println("Adding Bank account and checking its legitimacy");
            params = new HashMap<String, Object>();
            params.put("account_no", "1680366060");  // this is a required field
            params.put("bank", "BCA");  // this is a required field
            params.put("account_holder_name", "Winston Andersen");

            List<BankAccount> bankAccounts = BankAccount.add(params,userApiToken);
            String bankAccountId = null;
            for (BankAccount bankAccount : bankAccounts) {
                System.out.println(bankAccount.toString());
                bankAccountId = bankAccount.getId();
                fuzzyCheck(bankAccount.toString());
            }



            System.out.println("Creating a payout");
            params = new HashMap<String, Object>();
            params.put("amount", "5000"); // this is a required field
            params.put("invoice_id", someRandomString); // this is a required field and must be unique
            params.put("user_api_token", userApiToken); // put this as the user api token that we got from during the signup process
            params.put("descriptions", "some description");

            Payout payout = Payout.create(params,Xfers.apiKey); // you can change this line to only  Payout.create(params) if you want

            TimeUnit.SECONDS.sleep(30); // Withdrawal request is taken after payout from Tunaikita's wallet to user's wallet

            System.out.println("Making a withdrawal request");
            params = new HashMap<String, Object>();
            params.put("amount", "5000"); // this is a required field
            params.put("express", true); // this must be set to be true for real time money sending
            Withdrawal withdrawal = BankAccount.withdraw(bankAccountId, params, userApiToken);

            System.out.println("Result of Withdrawal:");
            System.out.println(withdrawal.toString());

            // TODO: what happens to withdrawal in sandbox?

            // end of the sending money process

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static boolean fuzzyCheck(String someJSON){
        //Tunai Kita code to handle wrong bank name
        return true;
    }

    public static Charge moneyReceivedCallback(String userApiToken) {
        Charge charge = null;
        try {
        System.out.println("Creating a charge");
        Map<String, Object> params = new HashMap<String, Object>();
        String someRandomString = UUID.randomUUID().toString();

        params.put("amount", "100000"); // this is a required field
        params.put("currency", "IDR"); // this is a required field
        params.put("order_id", someRandomString); // this is a required field and must be unique
        params.put("notify_url", "https://tunaikita.com/payment_notification");
        params.put("description", "any description");
        params.put("redirect", false);
        params.put("debit_only", "true"); // this should not be forgotten


        charge = Charge.create(params, userApiToken);
        System.out.println("Charge has been succesfully created: ");
        System.out.println(charge.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return charge;

    }

}
