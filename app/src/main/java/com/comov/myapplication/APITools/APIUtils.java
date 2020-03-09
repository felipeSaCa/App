package com.comov.myapplication.APITools;

public class APIUtils {
    public static final String BASE_URL = "http://172.22.74.232:3800";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
