package com.tsi.rooprang.retrofit;

public class BaseUrl {

    public static final String BASE_URL = "https://api.ichhawarsoftware.com/";
   // public static final String BASE_URL2 = "http://svttesting.online/rooprangApi/";
    public static final String BASE_URL2 = "http://thelittleshoppers.com/API/";

    public static final String FORGOT_PASS = "forgetPassword.php";
    public static final String CHECK_PINCODE = "checkPincodeForDeliveryArea.php";
    public static final String FILTER_MENU = "showFilterMenu.php?";

    public static String getMethodUrl(String methodName) {
        return BASE_URL2 + methodName;
    }
}
