package com.example.signboard2.rest;

public class UtilsAPI {
    public static final String BASE_URL_API = "http://103.35.64.5:8086/api/v1/";
    public static final String BASE_URL_SOCKET = "http://103.35.64.5:8086";
    public static BaseAPIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseAPIService.class);
    }
}
