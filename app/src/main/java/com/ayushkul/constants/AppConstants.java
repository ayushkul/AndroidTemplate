package com.ayushkul.constants;

/**
 * Created by Ayush Kulshrestha on 06/02/19.
 */

public class AppConstants {

    public static final String EMAIL_PATTERN = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w{2,}+)*\\.\\w{2,}+([-.]\\w{2,}+)*$";
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    public static final String TXT_FILE_EXTENSION = ".txt";
    public static final String HTML_BOLD_TAG_START = "<b>";
    public static final String HTML_BOLD_TAG_END = "</b>";
    /*
           Permission constants-:
    */
    //    public static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 124;
    public static final int PERMISSIONS_REQUEST_CAMERA = 125;
    public static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 126;
    public static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 127;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 128;
    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 112;
    public static final int RESPONSE_CODE_UNAUTHORIZED = 401;

    /*
     * S3 credentials : Keep access key and secret key in encrypted mode-
     */
    public static final String BUCKET_NAME = "YOUR_S3_BUCKET_NAME";
    public static final String BUCKET_REGION = "YOUR_S3_BUCKET_REGION";
    public static final String BUCKET_ACCESS_KEY = "YOUR_S3_BUCKET_ACCESS_KEY";
    public static final String BUCKET_SECRET_KEY = "YOUR_S3_BUCKET_SECRET_KEY";

    public static final String BASE_URL = "http://localhost:3000/";

    /*
           Market place URLs-:
    */
    public static final String MARKET_PLACE_URL = "market://details?id=";
    public static final String PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=";

    /**
     * Date Formats
     */
    public static final String DD_MMM = "dd MMM";
    public static final String MM_YYYY = "MM-YYYY";
    public static final String DD_MMMM_YYYY = "dd MMMM, yyyy";
    public static final String HH_MM_DD_MMM_YYYY = "HH:mm a, dd MMM yyyy";
    public static final String HH_MM = "HH:mm a";
    /*
           App update constants-:
    */
    public static final String APP_VERSION = "appVersion";
    public static final String APP_VERSION_CODE = "appVersionCode";
    public static final String IS_APP_UPDATE_AVAILABLE = "isAppUpdateAvailable";
    public static final String IS_APP_FORCE_UPDATE = "isAppForceUpdate";
}
