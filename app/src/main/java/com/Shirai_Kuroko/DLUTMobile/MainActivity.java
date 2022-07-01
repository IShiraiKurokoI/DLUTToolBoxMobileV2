package com.Shirai_Kuroko.DLUTMobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Helpers.PermissionHelper;
import com.Shirai_Kuroko.DLUTMobile.Services.IntentService;
import com.Shirai_Kuroko.DLUTMobile.UI.SettingsActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;
import com.Shirai_Kuroko.DLUTMobile.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.igexin.sdk.GetuiPushException;
import com.igexin.sdk.IUserLoggerInterface;
import com.igexin.sdk.PushManager;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    private static ActionBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.Shirai_Kuroko.DLUTMobile.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bar=getSupportActionBar();
        if(bar!=null)
        {
            bar.setTitle("首页");
            bar.setElevation(0);
        }
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
        PermissionHelper.GetAllPermission(this);
        com.igexin.sdk.PushManager.getInstance().initialize(this, IntentService.class);
        com.igexin.sdk.PushManager.getInstance().setDebugLogger(this, new IUserLoggerInterface() {
            @Override
            public void log(String s) {
                Log.i("PUSH_LOG",s);
            }
        });
        if(ConfigHelper.NeedConfig())
        {
            Toast.makeText(this, "⚠请完善配置信息！⚠", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
    }

    public static void SetActionBarTitle(String Title)
    {
        if(bar!=null)
        {
            bar.setTitle(Title);
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