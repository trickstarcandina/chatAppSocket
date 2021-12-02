package com.example.chattogether.data.auth;

import com.example.chattogether.data.Constant;
import com.example.chattogether.data.api.ApiService;
import com.example.chattogether.data.api.BaseClient;

public class TempClient extends BaseClient {

    private static ApiService apiService;

    public static ApiService getInstance(){
        if(apiService == null)
            return createService(ApiService.class, Constant.BASE_URL);
        return apiService;
    }

}
