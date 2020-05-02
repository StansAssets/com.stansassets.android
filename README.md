# Android Native plugins Dev Kit for Unity
At [Stan's Assets](https://stansassets.com/), we are making android native plugins for **Unity** for quite a while already. 
Check out our most popular [Android Native Pro](https://github.com/StansAssets/com.stansassets.android-native) and [Ultimate Mobile Pro](https://github.com/StansAssets/com.stansassets.ultimate-mobile) plugins for the Android platfrom.

[![NPM Package](https://img.shields.io/npm/v/com.stansassets.android)](https://www.npmjs.com/package/com.stansassets.android)
[![Licence](https://img.shields.io/npm/l/com.stansassets.android)](https://github.com/StansAssets/com.stansassets.android/blob/master/LICENSE)
[![Issues](https://img.shields.io/github/issues/StansAssets/com.stansassets.android)](https://github.com/StansAssets/com.stansassets.android/issues)

## What problems are we solving?
When Unity developer is trying to make an Android Native plugin he will face the same well-known problems and the goal for this product is to provide solutions and describe best particles for the most common issues you might have while working on your very own Android Native plugin for Unity.
Let us know if you need some specific features or you feel like something is missing in this package.
* [Handle Android Plugin's Callback in Unity3d without SendUnityMessage.](https://github.com/StansAssets/com.stansassest.unity.android/wiki/Handle-Android-Plugin's-Callback-in-Unity3d-without-SendUnityMessage)
* [Send Texture2D from Unity the native code.](https://github.com/StansAssets/com.stansassest.unity.android/wiki/Send-Texture2D-from-Unity-the-native-code)
* [Send image from the native code to Unity.](https://github.com/StansAssets/com.stansassest.unity.android/wiki/Send-image-from-the-native-code-to-Unity)
* [AndroidLogger Utility.](https://github.com/StansAssets/com.stansassest.unity.android/wiki/AndroidLogger-Utility)

## How to install
* Navigate to the `Packages` directory of your project.
* Adjust the [project manifest file](https://docs.unity3d.com/Manual/upm-manifestPrj.html) `manifest.json` in a text editor.
* Ensure `https://registry.npmjs.org/` is part of `scopedRegistries`.
  * Ensure `com.stansassets` is part of `scopes`.
  * Add `com.stansassets.android` to the `dependencies`, stating the latest version.

A minimal example ends up looking like this. Please note that the version `X.Y.Z` stated here is to be replaced with [the latest released version](https://www.npmjs.com/package/com.stansassets.android) which is currently [![NPM Package](https://img.shields.io/npm/v/com.stansassets.android)](https://www.npmjs.com/package/com.stansassets.android).
  ```json
  {
    "scopedRegistries": [
      {
        "name": "npmjs",
        "url": "https://registry.npmjs.org/",
        "scopes": [
          "com.stansasset"
        ]
      }
    ],
    "dependencies": {
      "com.stansassets.android": "X.Y.Z",
      ...
    }
  }
  ```
* Switch back to the Unity software and wait for it to finish importing the added package.
