package com.comov.myapplication.apiTools;

public class APIUtils {

    public static final String BASE_URL = "http://192.168.1.12:3800/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
