package com.ayushkul.managers.api;

import com.ayushkul.constants.WebServiceConstants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Ayush Kulshrestha on 06/02/19.
 */

public interface APIService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST
    Call<Object> getUserInfo(@Url String url, @Header(WebServiceConstants.CONTENT_TYPE) String contentType, @Header(WebServiceConstants.DEVICE_ID) String deviceId, @Header(WebServiceConstants.SESSION_TOKEN) String sessionToken, @Body Map<String, Object> params);

}
