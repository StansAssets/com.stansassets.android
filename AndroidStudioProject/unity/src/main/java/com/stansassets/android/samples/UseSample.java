package com.stansassets.android.samples;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;

import com.stansassets.android.IUnityCallback;
import com.stansassets.android.UnityBridge;

import java.util.List;

public class UseSample {

    // Models should match Unity code convention

    public class ButtonInfo {
        String m_Id;
        String m_Text;
        int m_Type;
    }

    public class AlertDialogInfo {
        String m_Title;
        String m_Message;
        boolean m_Cancelable;
        int m_ThemeId;

        List<ButtonInfo> m_Buttons;
    }

    public static class AlertDialogCloseInfo {
        String m_ButtonId;
    }

    private static final int NEUTRAL  = 0;
    private static final int POSITIVE  = 1;
    private static final int NEGATIVE  = 2;

    public static void show(String json, final IUnityCallback callback) {

        AlertDialogInfo info = UnityBridge.fromJson(json, AlertDialogInfo.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(UnityBridge.getCurrentActivity(), info.m_ThemeId));
        builder.setTitle(info.m_Title);
        builder.setMessage(info.m_Message);
        builder.setCancelable(info.m_Cancelable);


        for (final ButtonInfo button : info.m_Buttons) {
            switch (button.m_Type) {
                case NEUTRAL:
                    builder.setNeutralButton(button.m_Text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            OnAlertButtonClick(button, callback);
                        }
                    });
                    break;

                case POSITIVE:
                    builder.setPositiveButton(button.m_Text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            OnAlertButtonClick(button, callback);
                        }
                    });
                    break;

                case NEGATIVE:
                    builder.setNegativeButton(button.m_Text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            OnAlertButtonClick(button, callback);
                        }
                    });
                    break;

            }
        }

        builder.show();
    }

    private static void OnAlertButtonClick(ButtonInfo button, final IUnityCallback callback) {
        AlertDialogCloseInfo result = new AlertDialogCloseInfo();
        result.m_ButtonId = button.m_Id;
        UnityBridge.sendCallback(callback, result);
    }
}
