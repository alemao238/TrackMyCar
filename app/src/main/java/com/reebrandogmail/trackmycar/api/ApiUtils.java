package com.reebrandogmail.trackmycar.api;

/**
 * Created by renanribeirobrando on 25/07/17.
 */

public class ApiUtils {

    private static final String BASE_URL = "http://www.mocky.io";

    public static UserAPI getUserAPI() {
        return RetrofitClient.getClient(BASE_URL).create(UserAPI.class);
    }
}
