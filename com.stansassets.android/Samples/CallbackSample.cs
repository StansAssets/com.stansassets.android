using System;
using StansAssets.Android;
using UnityEngine;

[Serializable]
public class CallbackDataSample
{
    [SerializeField]
    string m_StringData = default;

    [SerializeField]
    int m_IntData = default;

    public int IntData => m_IntData;
    public string StringData => m_StringData;
}

public static class CallbackSample
{
    const string k_FullJavaClassName = "com.stansassets.android.samples.CallbackSample";

    public static void ModelCallbackSample(int x, int y, string name, Action<CallbackDataSample> callback)
    {
        var requestBuilder = new JavaRequestBuilder(k_FullJavaClassName, nameof(ModelCallbackSample));
        requestBuilder.AddArgument(x);
        requestBuilder.AddArgument(y);
        requestBuilder.AddArgument(name);
        requestBuilder.AddAction(callback);
        requestBuilder.Invoke();
    }
}
