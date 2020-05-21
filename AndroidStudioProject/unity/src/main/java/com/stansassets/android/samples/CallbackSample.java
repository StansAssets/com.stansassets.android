package com.stansassets.android.samples;

import com.stansassets.android.IUnityCallback;
import com.stansassets.android.UnityBridge;

public class CallbackSample {

    public static class CallbackDataSample {
        String m_StringData;
        int m_IntData;
    }

    public static void modelCallbackSample(int x, int y, String name, final IUnityCallback callback) {
        CallbackDataSample dataSample = new CallbackDataSample();

        dataSample.m_StringData = "My String data";
        dataSample.m_IntData = 100;

        UnityBridge.registerMessageHandler();
        UnityBridge.sendCallback(callback, dataSample);
    }
}
