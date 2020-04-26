package com.stansassets.unity.android.samples;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;

import com.stansassets.unity.android.IUnityCallback;
import com.stansassets.unity.android.UnityBridge;

import java.util.List;

public class UseSample {

    // Models should natch Unity code convention

    public class ButtonInfo {
        public String m_Id;
        public String m_Text;
        public int m_Type;
    }

    public class AlertDialogInfo {
        public String m_Id;
        public String m_Title;
        public String m_Message;
        public boolean m_Cancelable;
        public int m_ThemeId;

        public List<ButtonInfo> m_Buttons;
    }

    public static class AlertDialogCloseInfo {
        public String m_ButtonId;
    }

    private static final int NEUTRAL  = 0;
    private static final int POSITIVE  = 1;
    private static final int NEGATIVE  = 2;

    public static void Show(String json, final IUnityCallback callback) {

        AlertDialogInfo info = UnityBridge.fromJson(json, AlertDialogInfo.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(UnityBridge.currentActivity, info.m_ThemeId));
        builder.setTitle(info.m_Title);
        builder.setMessage(info.m_Message);
        builder.setCancelable(info.m_Cancelable);


        for (final ButtonInfo button : info.m_Buttons) {
            switch (button.m_Type) {
                case NEUTRAL:
                    builder.setNeutralButton(button.m_Text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialogCloseInfo result = new AlertDialogCloseInfo();
                        result.m_ButtonId = button.m_Id;
                        UnityBridge.sendCallback(callback, result);
                        }
                    });
                    break;

                case POSITIVE:
                    builder.setPositiveButton(button.m_Text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialogCloseInfo result = new AlertDialogCloseInfo();
                        result.m_ButtonId = button.m_Id;
                        UnityBridge.sendCallback(callback, result);
                        }
                    });
                    break;

                case NEGATIVE:
                    builder.setNegativeButton(button.m_Text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialogCloseInfo result = new AlertDialogCloseInfo();
                        result.m_ButtonId = button.m_Id;
                        UnityBridge.sendCallback(callback, result);
                        }
                    });
                    break;

            }
        }

        AlertDialog dialog = builder.show();
    }
}
