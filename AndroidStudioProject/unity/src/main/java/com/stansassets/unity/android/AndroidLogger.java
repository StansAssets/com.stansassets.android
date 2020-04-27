package com.stansassets.unity.android;

import android.util.Log;

public class AndroidLogger {

    private static final String TAG = "Unity.Android";

    private static boolean sPrintInfo = true;
    private static boolean sPrintWarning = true;
    private static boolean sPrintError = true;
    private static boolean sEnableWTFLogging = true;

    public static void log(String message) { printLog(message, UnityLogType.INFO); }
    public static void logWarning(String message) { printLog(message, UnityLogType.WARNING); }
    public static void logError(String message) { printLog(message, UnityLogType.ERROR); }

    public static void setLogLevel(boolean info, boolean warning, boolean error, boolean enableWTFLogging) {
        sPrintInfo = info;
        sPrintWarning = warning;
        sPrintError = error;
        sEnableWTFLogging = enableWTFLogging;

        log("Log Level is set");
    }

    private static void printLog(String message, UnityLogType type) {

        boolean shouldPrint = true;
        if (type == UnityLogType.INFO && !sPrintInfo) {
            shouldPrint = false;
        }

        if (type == UnityLogType.WARNING && !sPrintWarning) {
            shouldPrint = false;
        }

        if (type == UnityLogType.ERROR && !sPrintError) {
            shouldPrint = false;
        }

        if (shouldPrint) {
            printNativeLog(message, type);
        }
    }

    private static void printNativeLog(String message, UnityLogType type) {

        if (sEnableWTFLogging) {
            Log.wtf(TAG, message);
            return;
        }

        switch (type) {
            case INFO:
                Log.d(TAG, message);
                break;
            case WARNING:
                Log.w(TAG, message);
                break;
            case ERROR:
                Log.e(TAG, message);
                break;
        }
    }
}
