package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
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

import com.Shirai_Kuroko.DLUTMobile.Managers.CacheManager;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.Services.BackgroudWIFIMonitorService;

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
        private String setAsterisks(int length) {
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
                        return "强制深色模式已开启";
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        return "深色模式将会跟随系统";
                    }
                });
                DarkPreference.setOnPreferenceClickListener(preference -> {
                    Toast.makeText(getActivity(), "设置成功！", Toast.LENGTH_SHORT).show();
                    return false;
                });
            }

            SwitchPreferenceCompat AutoLoginPreference = findPreference("AutoLogin");
            if (AutoLoginPreference != null) {
                AutoLoginPreference.setOnPreferenceClickListener(preference -> {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
                    boolean Autologin = prefs.getBoolean("AutoLogin", false);
                    if (Autologin) {
                        Dialog Dialog = new Dialog(requireContext(), R.style.CustomDialog);
                        @SuppressLint("InflateParams") View view = LayoutInflater.from(requireContext()).inflate(
                                R.layout.dialog_center_single_btn, null);
                        final TextView title = view.findViewById(R.id.title);
                        title.setText("⚠请注意⚠");
                        final TextView msg = view.findViewById(R.id.msg);
                        msg.setText("请手动为i大工+打开后台运行和开机启动权限\n举例：华为系统在应用启动管理中调整i大工+为手动管理并允许自启动，关联启动和后台活动,并且添加至内存清理白名单\n否则此功能无法正常使用");
                        final Button ok = view.findViewById(R.id.ok);
                        ok.setText("知道了");
                        ok.setOnClickListener(v -> Dialog.dismiss());
                        Window window = Dialog.getWindow();
                        window.setContentView(view);
                        window.setGravity(Gravity.CENTER);
                        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                                android.view.WindowManager.LayoutParams.WRAP_CONTENT);
                        Dialog.setCanceledOnTouchOutside(false);
                        WindowManager.LayoutParams lp = requireActivity().getWindow().getAttributes();
                        lp.alpha = 0.5f;
                        requireActivity().getWindow().setAttributes(lp);
                        Dialog.setOnDismissListener(dialogInterface -> {
                            WindowManager.LayoutParams lp1 = requireActivity().getWindow().getAttributes();
                            lp1.alpha = 1f;
                            requireActivity().getWindow().setAttributes(lp1);
                        });
                        Dialog.show();
                        Intent ServiceIntent = new Intent(getContext(), BackgroudWIFIMonitorService.class);
                        requireContext().startForegroundService(ServiceIntent);
                    } else {
                        Intent ServiceIntent = new Intent(getContext(), BackgroudWIFIMonitorService.class);
                        requireContext().stopService(ServiceIntent);
                    }
                    Toast.makeText(getActivity(), "设置成功！", Toast.LENGTH_SHORT).show();
                    return false;
                });
            }

            Preference Clean_Cache = findPreference("Clean_Cache");
            try {
                if (Clean_Cache != null) {
                    Clean_Cache.setSummary(CacheManager.getTotalCacheSize(requireContext()));
                }
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

            Preference Exit = findPreference("Exit");
            if (Exit != null) {
                Exit.setOnPreferenceClickListener(preference -> {
                    Dialog Dialog = new Dialog(requireActivity(), R.style.CustomDialog);
                    @SuppressLint("InflateParams") View view = LayoutInflater.from(requireActivity()).inflate(
                            R.layout.dialog_confirm_center, null);
                    final TextView title = view.findViewById(R.id.title);
                    title.setText("请确认");
                    final TextView msg = view.findViewById(R.id.msg);
                    msg.setText("是否退出登录?");
                    final Button ok = view.findViewById(R.id.ok);
                    ok.setOnClickListener(view1 -> {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
                        prefs.edit().remove("UserBean").remove("Password").apply();
                        Intent intent = new Intent(requireContext(), LoginActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                        Dialog.dismiss();
                    });
                    final Button cancel = view.findViewById(R.id.cancel);
                    cancel.setOnClickListener(view12 -> Dialog.dismiss());
                    Window window = Dialog.getWindow();
                    window.setContentView(view);
                    window.setGravity(Gravity.CENTER);
                    window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                            android.view.WindowManager.LayoutParams.WRAP_CONTENT);
                    Dialog.setCanceledOnTouchOutside(false);
                    WindowManager.LayoutParams lp = requireActivity().getWindow().getAttributes();
                    lp.alpha = 0.5f;
                    requireActivity().getWindow().setAttributes(lp);
                    Dialog.setOnDismissListener(dialogInterface -> {
                        WindowManager.LayoutParams lp1 = requireActivity().getWindow().getAttributes();
                        lp1.alpha = 1f;
                        requireActivity().getWindow().setAttributes(lp);
                    });
                    Dialog.show();
                    return false;
                });
            }
        }
    }
}