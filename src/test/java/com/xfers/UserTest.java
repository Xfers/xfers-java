package com.xfers;

import com.xfers.Xfers;
import com.xfers.exception.XfersException;
import com.xfers.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserTest {
    public static void main(String[] args) {
        Xfers.apiKey = "pXcfdAKNorDe_o1eou1NSp4mwssiEzem_6sg8fwnZWs";
        Xfers.setSandbox();

        try {
            User user = User.retrieve();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            Map<String, Object> updateParams = new HashMap<String, Object>();
//            updateParams.put("first_name", "Tommy");
//            updateParams.put("last_name", "Tan");
//            updateParams.put("email", "tommy@xfers.com");
//            updateParams.put("date_of_birth", "1986-02-27");
//            updateParams.put("address_line_1", "Blk 608 Jurong East");
//            updateParams.put("address_line_2", "#08-41");
//            updateParams.put("nationality", "Singaporean");
//            updateParams.put("postal_code", "510608");
//            updateParams.put("identity_no", "S8692038G");
//            updateParams.put("id_front_url", "http://angelsgateadvisory.sg/wp-content/uploads/2015/10/Logo.jpg");
//            updateParams.put("id_back_url", "http://angelsgateadvisory.sg/wp-content/uploads/2015/10/Logo.jpg");
//            updateParams.put("selfie_2id_url", "http://angelsgateadvisory.sg/wp-content/uploads/2015/10/Logo.jpg");
//            updateParams.put("proof_of_address_url", "http://angelsgateadvisory.sg/wp-content/uploads/2015/10/Logo.jpg");
//            updateParams.put("meta_data", "foobar");
//
//            User user = User.update(updateParams);
//            System.out.println(user);
//        } catch (XfersException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            User transferInfo = User.transferInfo();
//            System.out.println(transferInfo);
//        } catch (XfersException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            User activities = User.activities();
//            System.out.println(activities);
//        } catch (XfersException e) {
//            e.printStackTrace();
//        }

    }
}