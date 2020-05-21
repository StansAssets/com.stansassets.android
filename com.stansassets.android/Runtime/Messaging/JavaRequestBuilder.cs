using System;
using System.Collections.Generic;
using UnityEngine;

namespace StansAssets.Android
{
    /// <summary>
    /// Provides a convenient way to build call to the java native static method.
    /// </summary>
    public class JavaRequestBuilder
    {
        readonly string m_TypeName;
        readonly string m_MethodName;
        readonly List<object> m_Arguments = new List<object>();

        /// <summary>
        /// Constructs a new <see cref="JavaRequestBuilder"/> instance.
        /// </summary>
        /// <param name="typeName">Fully qualified java class name.</param>
        /// <param name="methodName">Method name.</param>
        public JavaRequestBuilder(string typeName, string methodName)
        {
            m_TypeName = typeName;
            m_MethodName = methodName;
        }

        /// <summary>
        /// Add method argument.
        /// </summary>
        /// <param name="arg">method argument</param>
        /// <returns>Current instance of the class</returns>
        public JavaRequestBuilder AddArgument(object arg)
        {
            var convertedArg =  Java.ConvertObjectData(arg);
            m_Arguments.Add(convertedArg);
            return this;
        }

        /// <summary>
        /// Add action callback as method argument.
        /// </summary>
        /// <param name="callback">Action callback.</param>
        /// <typeparam name="T">Action generic type.</typeparam>
        /// <returns>Current instance of the class</returns>
        public JavaRequestBuilder AddAction<T>(Action<T> callback)
        {
            m_Arguments.Add(JavaCallback.ActionToJavaObject(callback));
            return this;
        }

        /// <summary>
        /// Combine all of the options that have been set and invoke native method.
        /// </summary>
        public void Invoke()
        {
            AndroidLogger.LogJavaMethodCall(m_TypeName, m_MethodName, m_Arguments);
            if (Application.isEditor)
                return;

            var javaClass =  Java.GetJavaClass(m_TypeName);
            javaClass.CallStatic(m_MethodName, m_Arguments.ToArray());
        }

        /// <summary>
        /// Combine all of the options that have been set and invoke native method.
        /// </summary>
        /// <typeparam name="TR">Method return type.</typeparam>
        /// <returns>Native method result.</returns>
        public TR Invoke<TR>()
        {
            AndroidLogger.LogJavaMethodCall(m_TypeName, m_MethodName, m_Arguments);
            if (Application.isEditor)
                return default;

            var javaClass =  Java.GetJavaClass(m_TypeName);
            if ( Java.IsPrimitive(typeof(TR)))
            {
                var result =  javaClass.CallStatic<TR>(m_MethodName, m_Arguments.ToArray());
                AndroidLogger.LogJavaCallbackSync(result);
                return result;
            }

            var json = javaClass.CallStatic<string>(m_MethodName, m_Arguments.ToArray());
            AndroidLogger.LogJavaCallbackSync(json);
            return JsonUtility.FromJson<TR>(json);
        }
    }
}
