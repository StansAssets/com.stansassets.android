package com.stansassets.unity.android;

import android.app.Activity;
import android.os.Handler;
import android.os.StrictMode;

import com.google.gson.Gson;
import com.unity3d.player.UnityPlayer;

public class UnityBridge {

    private static Gson sGson;
    private static Handler sUnityMainThreadHandler;

    public static Activity getCurrentActivity() {
        return UnityPlayer.currentActivity;
    }

    public static void registerMessageHandler() {
        AndroidLogger.log("Message Handler registered");

        if(sUnityMainThreadHandler == null) {
            sUnityMainThreadHandler = new Handler();
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public static void sendCallback(final IUnityCallback callback, final Object src) {
        sendCallback(callback, src, false);
    }

    public static void sendCallback(final IUnityCallback callback, final Object src, final boolean forceMainThread) {
        runOnUnityThread(new Runnable() {
            @Override
            public void run() {
                String json = getGson().toJson(src);
                callback.OnResult(json, forceMainThread);
            }
        });
    }

    public static String toJson(Object src) {
        return getGson().toJson(src);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return getGson().fromJson(json, classOfT);
    }

    private  static  void printingStackTrace() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < elements.length; i++) {
            StackTraceElement s = elements[i];
            AndroidLogger.log("\tat " + s.getClassName() + "." + s.getMethodName()
                    + "(" + s.getFileName() + ":" + s.getLineNumber() + ")");
        }
    }

    private static void runOnUnityThread(Runnable runnable) {
        if(sUnityMainThreadHandler != null && runnable != null) {
            try {
                if(sUnityMainThreadHandler.getLooper().getThread().isAlive()) {
                    sUnityMainThreadHandler.post(runnable);
                }
            } catch (Exception ex) {
                AndroidLogger.log("Can't post on a unityMainThreadHandler");
            }
        }
    }

    private static Gson getGson() {
        if(sGson == null) {
            sGson = new Gson();
        }
        return  sGson;
    }
}
