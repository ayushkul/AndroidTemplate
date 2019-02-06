package com.ayushkul.managers;

import android.app.Application;
import android.content.Context;

import com.ayushkul.constants.AppConstants;
import com.ayushkul.managers.amazonS3.S3Manager;
import com.ayushkul.managers.api.APIManager;
import com.ayushkul.managers.preferences.SharedPreferenceManager;
import com.ayushkul.utility.AppLogger;
import com.ayushkul.utility.AppUtility;

/**
 * Created by Ayush Kulshrestha on 06/02/19.
 */

public class AppDelegate extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppLogger.debug("Ayush", "AppDelegate", "onCreate", null, null);
        initializeManagers();
        initializeS3TransferUtility(getApplicationContext());
    }

    private void initializeManagers() {
        SharedPreferenceManager.createInstance(this);
        APIManager.init();

    }

    public static void initializeS3TransferUtility(Context context) {
//        if (context != null && !AppUtility.isEmpty(AppConstants.BUCKET_NAME))
//            S3Manager.getTransferUtility(context);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AppLogger.debug("Ayush", "AppDelegate", "onTerminate", null, null);
    }
}
