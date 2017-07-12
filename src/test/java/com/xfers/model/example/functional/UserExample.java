package com.xfers.model.example.functional;

import com.xfers.Xfers;
import com.xfers.model.Activity;
import com.xfers.model.BankAccount;
import com.xfers.model.TransferInfo;
import com.xfers.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserExample {
    public static void main(String[] args) {
        Xfers.apiKey = "pXcfdAKNorDe_o1eou1NSp4mwssiEzem_6sg8fwnZWs";
        Xfers.setSGSandbox();

        try {
            System.out.println("Retrieving current user");
            User user = User.retrieve();
            System.out.println(user.getFirstName());
            System.out.println(user.getDateOfBirth());
            for (BankAccount bankAccount : user.getBankAccounts()) {
                System.out.println(bankAccount.toString());
            }
            System.out.println(user.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Updating current user");
            Map<String, Object> updateParams = new HashMap<String, Object>();
            updateParams.put("first_name", "Ah Seng");
            updateParams.put("last_name", "Tan");
            updateParams.put("date_of_birth", "1986-02-27");
            updateParams.put("address_line_1", "Blk 608 Jurong East");
            updateParams.put("address_line_2", "#08-41");
            updateParams.put("nationality", "Singaporean");
            updateParams.put("postal_code", "510608");
            updateParams.put("identity_no", "S8692038G");
            updateParams.put("id_front_url", "http://angelsgateadvisory.sg/wp-content/uploads/2015/10/Logo.jpg");
            updateParams.put("id_back_url", "http://angelsgateadvisory.sg/wp-content/uploads/2015/10/Logo.jpg");
            updateParams.put("selfie_2id_url", "http://angelsgateadvisory.sg/wp-content/uploads/2015/10/Logo.jpg");
            updateParams.put("proof_of_address_url", "http://angelsgateadvisory.sg/wp-content/uploads/2015/10/Logo.jpg");
            updateParams.put("meta_data", "foobar");

            User user = User.update(updateParams);
            System.out.println(user.getFirstName());
            System.out.println(user.getLastName());
            System.out.println(user.getDateOfBirth());
            System.out.println(user.getMetaData());
            System.out.println(user.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Retrieving current user transfer info");
            TransferInfo transferInfo = User.transferInfo();
            System.out.println(transferInfo.getBankNameFull());
            System.out.println(transferInfo.getBankNameAbbreviation());
            System.out.println(transferInfo.getBankAccountNo());
            System.out.println(transferInfo.getBankCode());
            System.out.println(transferInfo.getBranchCode());
            System.out.println(transferInfo.getBranchArea());
            System.out.println(transferInfo.getUniqueId());
            System.out.println(transferInfo.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Retrieving current user activities");
            List<Activity> activities = User.activities();
            for (Activity activity : activities) {
                System.out.println(activity.getTransType());
                System.out.println(activity.getTransactionItems().toString());
                System.out.println(activity.getDisplayAmount());
                System.out.println(activity.getPlusMinus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}