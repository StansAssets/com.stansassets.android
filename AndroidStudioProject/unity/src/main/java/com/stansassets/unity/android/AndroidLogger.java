package com.stansassets.unity.android;

import android.util.Log;

public class AndroidLogger {

    private static final String TAG = "Unity.Android";

    private static boolean sPrintInfo = true;
    private static boolean sPrintWarning = true;
    private static boolean sPrintError = true;
    private static boolean sEnableWTFLogging = true;

    public static void log(String message) {
        printLog(message, UnityLogType.Info);
    }
    public static void logWarning(String message) { printLog(message, UnityLogType.Warning); }
    public static void logError(String message) { printLog(message, UnityLogType.Error); }

    public static void setLogLevel(boolean info, boolean warning, boolean error, boolean enableWTFLogging) {
        sPrintInfo = info;
        sPrintWarning = warning;
        sPrintError = error;
        sEnableWTFLogging = enableWTFLogging;

        Log.wtf(TAG, "Log Level is set");
    }

    private static void printLog(String message, UnityLogType type) {

        boolean shouldPrint = true;
        if (type == UnityLogType.Info && !sPrintInfo) {
            shouldPrint = false;
        }

        if (type == UnityLogType.Warning && !sPrintWarning) {
            shouldPrint = false;
        }

        if (type == UnityLogType.Error && !sPrintError) {
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
            case Info:
                Log.d(TAG, message);
                break;
            case Warning:
                Log.w(TAG, message);
                break;
            case Error:
                Log.e(TAG, message);
                break;
        }
    }
}
