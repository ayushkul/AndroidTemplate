package com.ayushkul.managers.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.ayushkul.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ayush Kulshrestha on 06/02/19.
 */

public class SharedPreferenceManager {
    private static SharedPreferenceManager instance;
    private SharedPreferences sharedPreferences;
    private Context context;


    /**
     * Default Constructor to register Observables
     */

    public SharedPreferenceManager(Context context) {
        super();
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE);
    }

    /**
     * Create a shared instance of the class
     */
    public static SharedPreferenceManager getInstance() {
        return instance;
    }

    public static void createInstance(Context context) {
        if (instance == null)
            instance = new SharedPreferenceManager(context);
    }

    /**
     * Method to set string in preferences
     */
    private boolean setStringInPreferences(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value == null ? "" : value);
        return editor.commit();
    }

    /**
     * Method to get string from preferences
     */
    public String getStringFromPreferences(String key) {
        return sharedPreferences.getString(key, "");
    }

    /**
     * Method to set int in preferences
     */
    public boolean setIntInPreferences(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * Method to get int from preferences
     */
    public int getIntFromPreferences(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    /**
     * Method to set double in preferences
     */
    private boolean setDoubleInPreferences(String key, Double value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, Double.doubleToLongBits(value));
        return editor.commit();
    }

    /**
     * Method to get double from preferences
     */
    private Double getDoubleFromPreferences(String key) {
        return Double.longBitsToDouble(sharedPreferences.getLong(key, Double.doubleToLongBits(-1.0)));
    }

    /**
     * Method to set boolean in preferences
     */
    public boolean setBooleanInPreferences(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * Method to get boolean from preferences
     */
    public boolean getBooleanFromPreferences(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * Method to set long in preferences
     */
    private boolean setLongInPreferences(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * Method to get long from preferences
     */
    private long getLongFromPreferences(String key) {
        return sharedPreferences.getLong(key, -1);
    }

    /**
     * Method to set float in preferences
     */
    private boolean setFloatInPreferences(String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * Method to get float from preferences
     */
    private float getFloatFromPreferences(String key) {
        return sharedPreferences.getFloat(key, -1.0f);
    }

    /**
     * Method to delete certificationModel on specific key from preferences
     */
    private boolean deleteDataFromPreferences(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        return editor.commit();
    }

    public void saveNeverAskAgainStatus(String permission, boolean value) {
        setBooleanInPreferences(permission, value);
    }

    public boolean getNeverAskAgainStatus(String permission) {
        return getBooleanFromPreferences(permission);
    }
}
