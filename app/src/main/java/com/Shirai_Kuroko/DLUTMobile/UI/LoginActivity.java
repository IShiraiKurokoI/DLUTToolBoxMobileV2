package com.Shirai_Kuroko.DLUTMobile.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;
import com.Shirai_Kuroko.DLUTMobile.Widgets.LoadingView;

public class LoginActivity extends AppCompatActivity {
    private LoadingView loading;
    private Context mContext;
    EditText UserName;
    EditText Password;
    CheckBox Privacy;
    Button Login;
    ImageView UserNameClear;
    ImageView LoginInfo;
    ImageView PasswordClear;
    TextView FindPassword;
    TextView PrivacyText;
    boolean usr=false;
    boolean pwd=false;
    boolean pry=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setContentView(R.layout.activity_login);
        ActionBar actionBar =getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("统一身份认证");
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String Un = prefs.getString("Username","");
        UserName=findViewById(R.id.et_login_user_name);
        Password=findViewById(R.id.et_login_pwd);
        Privacy= findViewById(R.id.PrivacyCheckbox);
        Login=findViewById(R.id.btn_login);
        UserNameClear=findViewById(R.id.iv_login_et_num_clear);
        LoginInfo=findViewById(R.id.iv_login_i);
        PasswordClear=findViewById(R.id.iv_login_et_pwd_clear);
        FindPassword=findViewById(R.id.tv_login_find_pwd);
        PrivacyText=findViewById(R.id.txt_privacy);
        UserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                usr= s.length() != 0;
                if(s.length()!=0)
                {
                    UserNameClear.setVisibility(View.VISIBLE);
                }
                else {
                    UserNameClear.setVisibility(View.INVISIBLE);
                }
                CheckState();
            }
        });
        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pwd= s.length() != 0;
                if(s.length()!=0)
                {
                    PasswordClear.setVisibility(View.VISIBLE);
                }
                else {
                    PasswordClear.setVisibility(View.INVISIBLE);
                }
                CheckState();
            }
        });
        Privacy.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pry=isChecked;
            CheckState();
        });
        LoginInfo.setOnClickListener(v -> Toast.makeText(mContext,"请使用统一身份认证系统中的职工号/学号和密码进行登陆",Toast.LENGTH_LONG).show());
        UserNameClear.setOnClickListener(v -> UserName.setText(""));
        PasswordClear.setOnClickListener(v -> Password.setText(""));
        Login.setOnClickListener(v -> {
            if(Privacy.isChecked())
            {
                loading = new LoadingView(mContext,R.style.CustomDialog);
                loading.show();
                Toast.makeText(mContext,"账户名："+UserName.getText()+"  密码："+UserName.getText(),Toast.LENGTH_LONG).show();
                this.finish();
            }
        });
        FindPassword.setOnClickListener(v -> {
            Intent intent=new Intent(mContext, PureBrowserActivity.class);
            intent.putExtra("Name","找回密码");
            intent.putExtra("Url","https://sso.dlut.edu.cn/cas/pwd?ip=service.m.dlut.edu.cn&verify=null");
            startActivity(intent);
        });
        PrivacyText.setText("已阅读并同意");
        SpannableString spannableString1=new SpannableString("隐私政策");
        spannableString1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, PureBrowserActivity.class);
                intent.putExtra("Name","隐私政策");
                intent.putExtra("Url","https://its.dlut.edu.cn/upload/app/privacy/index.html");
                startActivity(intent);
            }
        }, 0, spannableString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        PrivacyText.append(spannableString1);
        PrivacyText.append("、");
        SpannableString spannableString2=new SpannableString("服务协议");
        spannableString2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, PureBrowserActivity.class);
                intent.putExtra("Name","服务协议");
                intent.putExtra("Url","https://its.dlut.edu.cn/upload/app/agreements/index.html");
                startActivity(intent);
            }
        }, 0, spannableString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        PrivacyText.append(spannableString2);
        PrivacyText.setMovementMethod(LinkMovementMethod.getInstance());
        UserName.setText(Un);
    }

    public void CheckState()
    {
        Login.setEnabled(usr&&pwd&&pry);
    }
    /**
     * 禁止系统显示缩放
     */
    @Override
    public Resources getResources() {
        return MobileUtils.getResources(super.getResources());
    }
}