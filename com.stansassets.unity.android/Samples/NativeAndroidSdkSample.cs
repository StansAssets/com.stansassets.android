using StansAssets.Android;
using UnityEngine;

public class NativeAndroidSdkSample : MonoBehaviour
{
    // Using IMGUI to avoid uGUI package dependency.
    void OnGUI()
    {
        var buttonPressed= GUILayout.Button("Android Logger Test", GUILayout.Width(500), GUILayout.Height(150));
        if (buttonPressed)
        {
            AndroidLogger.Log("Printing Info level log.");
            AndroidLogger.LogWarning("Printing warning level log.");
            AndroidLogger.LogError("Printing error level log.");
        }
    }
}
