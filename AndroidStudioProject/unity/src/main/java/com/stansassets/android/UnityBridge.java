package com.stansassets.android;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.unity3d.player.UnityPlayer;

public class UnityBridge {

    private static Gson sGson;
    private static Handler sUnityMainThreadHandler;

    public static Activity getCurrentActivity() {
        return UnityPlayer.currentActivity;
    }

    public static synchronized void sendCallback(final IUnityCallback callback, final Object src) {
        getUnityMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                String json = getGson().toJson(src);
                callback.OnResult(json);
            }
        });
    }

    public static String toJson(Object src) {
        return getGson().toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return getGson().fromJson(json, classOfT);
    }

    public static void printingStackTrace() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : elements) {
            AndroidLogger.log("\tat " + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName()
                    + "(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")");
        }
    }

    private static Handler getUnityMainThreadHandler() {
        if (sUnityMainThreadHandler == null) {
            sUnityMainThreadHandler = new Handler(Looper.getMainLooper());
        }
        return sUnityMainThreadHandler;
    }

    private static Gson getGson() {
        if (sGson == null) {
            sGson = new Gson();
        }
        return sGson;
    }
}
