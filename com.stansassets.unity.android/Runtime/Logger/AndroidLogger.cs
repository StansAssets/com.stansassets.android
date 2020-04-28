using System.Collections.Generic;
using System.IO;
using UnityEngine;

namespace StansAssets.Android
{
    /// <summary>
    /// Class containing methods to ease debugging while developing a native unity android plugin.
    /// </summary>
    public static class AndroidLogger
    {
        static AndroidJavaClass s_NativeLogger;

        static bool s_PrintInfo = true;
        static bool s_PrintWarning = true;
        static bool s_PrintError = true;
        const string k_SentToJavaLogPrefix = "Sent to Java -> ";
        const string k_SentToUnitySyncLogPrefix = "[Sync] Sent to Unity -> ";
        const string k_SentToUnityAsyncLogPrefix = "[Async] Sent to Unity -> ";

        [RuntimeInitializeOnLoadMethod]
        static void Init() {
            if (Application.platform != RuntimePlatform.Android)
                return;

            s_NativeLogger = Java.GetNativeLibraryJavaClass(nameof(AndroidLogger));
            SetLogLevel(s_PrintInfo, s_PrintWarning, s_PrintError, false);
        }

        /// <summary>
        /// Set Native logger messaging level.
        /// </summary>
        /// <param name="info">Set `true` if info level is allowed.</param>
        /// <param name="warning">Set `warning` if info level is allowed.</param>
        /// <param name="error">Set `error` if info level is allowed.</param>
        /// <param name="enableWtfLogging">Set `true` to force wtf level.</param>
        public static void SetLogLevel(bool info, bool warning, bool error, bool enableWtfLogging)
        {
            s_PrintInfo = info;
            s_PrintWarning = warning;
            s_PrintError = error;
            s_NativeLogger.CallStatic("setLogLevel", info, warning, error, enableWtfLogging);
        }

        /// <summary>
        /// Logs a message.
        /// </summary>
        /// <param name="message">String or object to be converted to string representation for display.</param>
        public static void Log(object message) {
            if (!s_PrintInfo)
                return;

            if(Application.platform == RuntimePlatform.Android)
                s_NativeLogger.CallStatic("log", $"{nameof(AndroidLogger)}: {message}");
            else
                Debug.Log(message);
        }

        /// <summary>
        /// A variant of <see cref="Log"/> that logs a warning message.
        /// </summary>
        /// <param name="message">String or object to be converted to string representation for display.</param>
        public static void LogWarning(object message) {
            if (!s_PrintWarning)
                return;

            if(Application.platform == RuntimePlatform.Android)
                s_NativeLogger.CallStatic("logWarning", $"{nameof(AndroidLogger)}: {message}");
            else
                Debug.LogWarning(message);
        }

        /// <summary>
        /// A variant of <see cref="Log"/> that logs an error message.
        /// </summary>
        /// <param name="message">String or object to be converted to string representation for display.</param>
        public static void LogError(object message) {
            if (!s_PrintError)
                return;

            if(Application.platform == RuntimePlatform.Android)
                s_NativeLogger.CallStatic("logError", $"{nameof(AndroidLogger)}: {message}");
            else
                Debug.LogError(message);
        }

        internal static void LogJavaMethodCall(string className, string methodName, IEnumerable<object> arguments)
        {
            className = Path.GetExtension(className);
            var argumentsLog = MakeArgumentsLogString(arguments);
            if(!string.IsNullOrEmpty(argumentsLog))
                argumentsLog = " :: " + argumentsLog;

            Debug.Log($"{k_SentToJavaLogPrefix}{className}.{methodName}{argumentsLog}");
        }

        internal static void LogJavaCallbackSync(object message) => Debug.Log($"{k_SentToUnitySyncLogPrefix}{message}");
        internal static void LogJavaCallbackAsync(object message) => Debug.Log($"{k_SentToUnityAsyncLogPrefix}{message}");

        static string MakeArgumentsLogString(IEnumerable<object> arguments)
        {
            var log = string.Empty;
            foreach(var p in arguments)
            {
                if (p == null) continue;

                if(log != string.Empty)
                {
                    log += " | ";
                }
                log += p.ToString();
            }

            return log;
        }
    }
}
