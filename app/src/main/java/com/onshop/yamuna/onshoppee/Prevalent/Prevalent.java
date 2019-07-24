package com.onshop.yamuna.onshoppee.Prevalent;

import com.onshop.yamuna.onshoppee.Models.Users;

public class Prevalent {
    public static Users currentonlineUser;
    public static final String UserPhoneKey ="UserPhone";
    public static final String UserPasswordKey ="UserPassword";
    public static String convertCodeToStatus(String status) {
        if (status.equals("0")) {
            return "Placed";
        } else if (status.equals("1")) {
            return "On my way";
        } else {
            return "Shipped";

        }
    }
}
