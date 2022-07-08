package com.Shirai_Kuroko.DLUTMobile.UI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.R;

public class FeedbackActivity extends AppCompatActivity {

    private String Contact="";
    private String Content="";
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        context =this;
        TextView Return = requireViewById(R.id.iv_back);
        Return.setOnClickListener(v -> finish());
        Button Submit = requireViewById(R.id.Submit);
        TextView Email = requireViewById(R.id.tv_feedback_qq_group);
        Email.setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText("ishirai_kurokoi@foxmail.com");
            Toast.makeText(this, "已复制作者邮箱到剪切板", Toast.LENGTH_SHORT).show();
        });
        EditText number_et = requireViewById(R.id.number_et);
        number_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                Contact = editable.toString();
            }
        });
        EditText content_et = requireViewById(R.id.content_et);
        TextView comment_words_limit = requireViewById(R.id.comment_words_limit);
        content_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                Content=editable.toString();
                Submit.setEnabled(editable.length()>4);
                comment_words_limit.setText(String.valueOf(140-Content.length()));
            }
        });
        Submit.setOnClickListener(view -> MailFeedBack(context));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                //根据判断关闭软键盘
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            // 点击EditText的事件，忽略它。
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        return false;
    }

    public void MailFeedBack(Context context)
    {
        String Version ="程序版本: "+ getAppVersionName(context);
        String OSVersion = "\nAndroid系统版本: " +getSdkVersion();
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        email.setType("plain/text");
        String[] emailReciver = new String[]{"ishirai_kurokoi@foxmail.com"};
        String  emailTitle = "i大工+使用反馈";
        String emailContent = Version+OSVersion+"\n联系方式： "+Contact+"\n\n"+Content;
        email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReciver);
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, emailTitle);
        email.putExtra(android.content.Intent.EXTRA_TEXT, emailContent);
        Dialog Dialog =new Dialog(context, R.style.CustomDialog);
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_custom, null);
        final TextView dialog_custom_title = view.findViewById(R.id.dialog_custom_title);
        dialog_custom_title.setText("发送反馈");
        final TextView dialog_custom_content = view.findViewById(R.id.dialog_custom_content);
        dialog_custom_content.setText("程序将会请求打开邮件客户端发送反馈邮件");
        final TextView dialog_custom_no = view.findViewById(R.id.dialog_custom_no);
        dialog_custom_no.setOnClickListener(v -> Dialog.dismiss());
        final TextView dialog_custom_yes = view.findViewById(R.id.dialog_custom_yes);
        dialog_custom_yes.setOnClickListener(v -> {
            startActivity(Intent.createChooser(email, "请选择邮件发送软件"));
            Dialog.dismiss();
        });
        Window window =Dialog.getWindow();
        window.setContentView(view);
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        Dialog.setCanceledOnTouchOutside(false);
        Dialog.show();
    }

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public static String getSdkVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
}