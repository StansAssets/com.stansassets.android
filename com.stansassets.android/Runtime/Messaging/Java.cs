using System;
using System.Collections.Generic;
using UnityEngine;
using StansAssets.Foundation.Extensions;

namespace StansAssets.Android
{
    static class Java
    {
        static readonly Dictionary<string, AndroidJavaClass> s_Classes = new Dictionary<string, AndroidJavaClass>();

        /// <summary>
        /// Get Java class reference using fully qualified class name.
        /// </summary>
        /// <param name="javaClassName">Fully qualified java class name.</param>
        /// <returns>Java class instance.</returns>
        public static AndroidJavaClass GetJavaClass(string javaClassName)
        {
            if (Application.isEditor)
                return null;

            if (s_Classes.ContainsKey(javaClassName)) {
                return s_Classes[javaClassName];
            }

            var javaClass = new AndroidJavaClass(javaClassName);
            s_Classes.Add(javaClassName, javaClass);
            return javaClass;
        }

        internal static AndroidJavaClass GetNativeLibraryJavaClass(string javaClassName)
            => GetJavaClass($"{NativeAndroidSdkPackage.JavaLibraryNamespace}.{javaClassName}");

        internal static object ConvertObjectData(object param)
        {
            switch (param) {
                case string _:
                    return param.ToString();
                case Enum _:
                    return param.ToString();
                case bool _:
                    return param;
                case int _:
                    return param;
                case long _:
                    return param;
                case float _:
                    return param;
                case Texture2D texture2D:
                    return texture2D.ToBase64();
                default:
                    return JsonUtility.ToJson(param);
            }
        }

        internal static bool IsPrimitive(Type type)
        {
            return type == typeof(byte)
                || type == typeof(ushort)
                || type == typeof(short)
                || type == typeof(uint)
                || type == typeof(int)
                || type == typeof(ulong)
                || type == typeof(long)
                || type == typeof(float)
                || type == typeof(double)
                || type == typeof(bool)
                || type == typeof(string)
                || type == typeof(char);
        }
    }
}
