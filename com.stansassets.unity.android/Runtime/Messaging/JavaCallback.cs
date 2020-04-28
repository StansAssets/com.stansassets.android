using System;
using StansAssets.Foundation.Async;
using UnityEngine;

namespace StansAssets.Android
{
    static class JavaCallback
    {
        class AndroidCallbackHandler<T> : AndroidJavaProxy
        {
            readonly Action<T> m_ResultHandler;

            public AndroidCallbackHandler(Action<T> resultHandler) : base($"{NativeAndroidSdkPackage.JavaLibraryNamespace}.IUnityCallback")
            {
                m_ResultHandler = resultHandler;
            }

            public void OnResult(string json)
            {
                AndroidLogger.LogJavaCallbackAsync(json);
                var result = JsonUtility.FromJson<T>(json);
                MainThreadDispatcher.Enqueue(() =>
                {
                    m_ResultHandler.Invoke(result);
                });
            }
        }

        public static AndroidJavaProxy ActionToJavaObject<T>(Action<T> action)
        {
            if (Application.platform != RuntimePlatform.Android)
                return null;

            MainThreadDispatcher.Init();
            return new AndroidCallbackHandler<T>(action);
        }
    }
}
