package com.Shirai_Kuroko.DLUTMobile.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Managers.CacheManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("设置");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public static class SettingsFragment extends PreferenceFragmentCompat {
        @NonNull
        private String setAsterisks ( int length){
            StringBuilder sb = new StringBuilder();
            for (int s = 0; s < length; s++) {
                sb.append("*");
            }
            return sb.toString();
        }
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            EditTextPreference UsernamePreference = findPreference("Username");
            if (UsernamePreference != null) {
                UsernamePreference.setOnBindEditTextListener(
                        editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));
            }

            EditTextPreference PasswordPreference = findPreference("Password");
            if (PasswordPreference != null) {
                PasswordPreference.setOnBindEditTextListener(
                        editText -> editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD));
                PasswordPreference.setSummaryProvider(preference -> {
                    String getPassword = PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("Password", "not set");
                    assert getPassword != null;
                    if (getPassword.equals("not set")) {
                        return "未设置";
                    } else {
                        return (setAsterisks(getPassword.length()));
                    }
                });
            }

            EditTextPreference NetworkPasswordPreference = findPreference("NetworkPassword");
            if (NetworkPasswordPreference != null) {
                NetworkPasswordPreference.setOnBindEditTextListener(
                        editText -> editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD));
                NetworkPasswordPreference.setSummaryProvider(preference -> {

                    String getPassword = PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("NetworkPassword", "not set");

                    assert getPassword != null;

                    if (getPassword.equals("not set")) {
                        return "未设置";
                    } else {
                        return (setAsterisks(getPassword.length()));
                    }
                });
            }

            EditTextPreference MailAddressPreference = findPreference("MailAddress");
            if (MailAddressPreference != null) {
                MailAddressPreference.setOnBindEditTextListener(
                        editText -> editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS));
            }

            EditTextPreference MailPasswordPreference = findPreference("MailPassword");
            if (MailPasswordPreference != null) {
                MailPasswordPreference.setOnBindEditTextListener(
                        editText -> editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD));
                MailPasswordPreference.setSummaryProvider(preference -> {
                    String getPassword = PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("MailPassword", "not set");
                    assert getPassword != null;
                    if (getPassword.equals("not set")) {
                        return "未设置";
                    } else {
                        return (setAsterisks(getPassword.length()));
                    }
                });
            }

            SwitchPreferenceCompat DarkPreference = findPreference("Dark");
            if (DarkPreference != null) {
                DarkPreference.setSummaryProvider(preference -> {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireActivity());
                    boolean ThemeType = prefs.getBoolean("Dark", false);
                    if (ThemeType) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        return "深色模式已开启";
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        return "深色模式已关闭";
                    }
                });
                DarkPreference.setOnPreferenceClickListener(preference -> {
                    Toast.makeText(getActivity(),"设置成功！", Toast.LENGTH_SHORT).show();
                    return false;
                });
            }

            SwitchPreferenceCompat AutoLoginPreference = findPreference("AutoLogin");
            if (AutoLoginPreference != null) {
                AutoLoginPreference.setOnPreferenceClickListener(preference -> {
                    Toast.makeText(getActivity(),"设置成功！", Toast.LENGTH_SHORT).show();
                    return false;
                });
            }

            Preference Clean_Cache= findPreference("Clean_Cache");
            try {
                Clean_Cache.setSummary(CacheManager.getTotalCacheSize(requireContext()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (Clean_Cache != null) {
                Clean_Cache.setOnPreferenceClickListener(preference -> {
                    new CacheManager.ClearCache().run();
                    try {
                    preference.setSummary(CacheManager.getTotalCacheSize(requireContext()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                });
            }
        }
    }
}