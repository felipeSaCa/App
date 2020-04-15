package com.comov.myapplication.apiTools;

public class APIUtils {

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
