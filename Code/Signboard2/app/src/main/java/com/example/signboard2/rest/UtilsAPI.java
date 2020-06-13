package com.example.signboard2.rest;

public class UtilsAPI {
    private static final String BASE_URL_API = "http://192.168.0.100:8086/api/v1/";

    public static BaseAPIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseAPIService.class);
    }
}
