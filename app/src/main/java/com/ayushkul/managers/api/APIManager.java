package com.ayushkul.managers.api;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import com.ayushkul.constants.AppConstants;
import com.ayushkul.utility.ProgressDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ayush Kulshrestha on 06/02/19.
 */

public class APIManager {
    private static APIManager instance;
    private static ProgressDialog progressDialog;
    private APIService mAPIService;


    public static synchronized void init() {
        if (instance == null)
            instance = new APIManager();
    }

    public static APIManager getInstance() {
        return instance;
    }


    public APIManager() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(20);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(300, TimeUnit.SECONDS)
                .connectTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .dispatcher(dispatcher)
                .build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mAPIService = mRetrofit.create(APIService.class);
    }


    public static void showProgressDialog(Context context) {
        if (context == null)
            return;
        if (progressDialog == null) {
            progressDialog = ProgressDialog.createProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            if (progressDialog.getWindow() != null) {
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.getWindow().setDimAmount(0);
            }
            progressDialog.show();
        }
    }

    public static void hideProgressDialog() {
        try {
            if (progressDialog == null)
                return;

            if (progressDialog.isShowing())
                progressDialog.dismiss();

            progressDialog = null;
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
    }
}
