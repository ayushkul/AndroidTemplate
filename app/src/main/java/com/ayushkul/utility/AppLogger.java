package com.ayushkul.utility;

import android.support.annotation.IntDef;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Ayush Kulshrestha on 06/02/19.
 */

public class AppLogger {
    private static final String U = " - ";
    private static final String APPNAME = "WizardPublication";

    /**
     * To Print error log in Android Monitor console
     *
     * @param devName
     * @param className
     * @param methodTag
     * @param message
     * @param payload
     */
    public static void error(String devName, String className, String methodTag, String message, Object payload) {
        StringBuilder errorBuilder = new StringBuilder();
        errorBuilder.append(devName)
                .append(U)
                .append(APPNAME)
                .append(U)
                .append(className)
                .append(U)
                .append(methodTag)
                .append(U)
                .append(message);
        if (payload != null)
            errorBuilder.append("\n").append(payload.toString());
        Log.e(methodTag, errorBuilder.toString());
    }


    /**
     * To Print Debug log in Android Monitor console
     *
     * @param devName
     * @param className
     * @param methodTag
     * @param message
     * @param payload
     */
    public static void debug(String devName, String className, String methodTag, String message, Object payload) {
        StringBuilder debugBuilder = new StringBuilder();
        debugBuilder.append(devName)
                .append(U)
                .append(APPNAME)
                .append(U)
                .append(className)
                .append(U)
                .append(methodTag)
                .append(U)
                .append(message);
        if (payload != null)
            debugBuilder.append("\n").append(payload.toString());
        Log.e(methodTag, debugBuilder.toString());
    }


    /**
     * @param tag:     defines the TAG which is used to filter the logs
     * @param message: defines the message you want to print in the Log
     * @param logType: defines the type of Log you want to print
     */
    public static void print(String tag, String message, @LogType int logType) {
        switch (logType) {
            case Log.VERBOSE:
                Log.v(tag, message);
                break;
            case Log.INFO:
                Log.i(tag, message);
                break;
            case Log.WARN:
                Log.w(tag, message);
                break;
            case Log.ERROR:
                Log.e(tag, message);
                break;
            default:
                Log.d(tag, message);
        }
    }

    /**
     * @param tag:       defines the TAG which is used to filter the logs
     * @param message:   defines the message you want to print in the Log
     * @param logType:   defines the type of Log you want to print
     * @param throwable: contains a snapshot of the execution stack of its
     *                   thread at the time it was created. It can also contain a message
     *                   string that gives more information about the error.
     */
    public static void print(String tag, String message, @LogType int logType, Throwable throwable) {
        switch (logType) {
            case Log.VERBOSE:
                Log.v(tag, message, throwable);
                break;
            case Log.INFO:
                Log.i(tag, message, throwable);
                break;
            case Log.WARN:
                Log.w(tag, message, throwable);
                break;
            case Log.ERROR:
                Log.e(tag, message, throwable);
                break;
            default:
                Log.d(tag, message, throwable);
        }
    }

    @IntDef({Log.DEBUG, Log.VERBOSE, Log.INFO, Log.ERROR, Log.WARN})
    @Retention(RetentionPolicy.SOURCE)
    @interface LogType {
    }
}
