package com.comov.myapplication.apiTools;

public class APIUtils {

<<<<<<< Updated upstream
    public static final String BASE_URL = "http://192.168.18.8:3800/";
=======
    public static final String BASE_URL = "http://192.168.1.3:3800/";
>>>>>>> Stashed changes

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
