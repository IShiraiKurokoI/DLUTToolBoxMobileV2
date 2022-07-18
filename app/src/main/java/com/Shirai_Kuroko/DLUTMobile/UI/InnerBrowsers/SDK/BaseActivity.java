package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import android.content.Intent;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private PreferenceManager.OnActivityResultListener listener;

    public void startActivityForResultHere(final Intent intent, final int n, final PreferenceManager.OnActivityResultListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        super.startActivityForResult(intent, n);
    }

    @Override
    public void onActivityResult(final int n, final int n2, final Intent intent) {
        final PreferenceManager.OnActivityResultListener listener = this.listener;
        if (listener != null) {
            listener.onActivityResult(n, n2, intent);
        }
        super.onActivityResult(n, n2, intent);
    }
}
