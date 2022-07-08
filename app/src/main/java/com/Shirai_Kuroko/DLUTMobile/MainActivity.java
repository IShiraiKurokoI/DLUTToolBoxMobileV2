package com.Shirai_Kuroko.DLUTMobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Helpers.PermissionHelper;
import com.Shirai_Kuroko.DLUTMobile.Services.IntentService;
import com.Shirai_Kuroko.DLUTMobile.UI.LoginActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;
import com.Shirai_Kuroko.DLUTMobile.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(getResources().getColor(R.color.main_theme_color));
        com.Shirai_Kuroko.DLUTMobile.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
        PermissionHelper.GetAllPermission(this);
        com.igexin.sdk.PushManager.getInstance().initialize(this, IntentService.class);
        com.igexin.sdk.PushManager.getInstance().setDebugLogger(this, s -> Log.i("PUSH_LOG",s));
        MobileUtils.CheckUpDateOnStartUp(this);
        MobileUtils.CheckConfigUpdates(this);
        if(ConfigHelper.NeedLogin(this))
        {
            Log.i("", "需要登陆");
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            String Un = prefs.getString("Username","");
            String Pd = prefs.getString("Password","");
            if(Un.length()*Pd.length()!=0)
            {
                Log.i("", "静默登陆");
                BackendUtils.Login(this,Un,Pd);
            }
            else
            {
                Log.i("", "弹出登陆");
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }
        else
        {
            Log.i("", "重收信息");
            BackendUtils.ReSendUserInfo(this);
        }
    }

    private static boolean isExit=false;

    @SuppressLint("HandlerLeak")
    static Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            isExit=false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }

    private void exit(){
        if(!isExit){
            isExit=true;
            Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
                    //利用handler延迟发送更改状态信息
                    handler.sendEmptyMessageDelayed(0,1800);
        }
        else{
            finish();
            System.exit(0);
        }
    }
    /**
     * 禁止系统显示缩放
     */
    @Override
    public Resources getResources() {
        return MobileUtils.getResources(super.getResources());
    }
}