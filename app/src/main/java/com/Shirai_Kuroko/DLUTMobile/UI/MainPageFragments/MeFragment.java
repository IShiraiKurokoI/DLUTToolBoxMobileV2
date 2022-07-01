package com.Shirai_Kuroko.DLUTMobile.UI.MainPageFragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.MainActivity;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.AboutActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.CardActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.GiftActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InfoActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.ParentBindActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.AccountSafeActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.SettingsActivity;

import java.util.Arrays;
import java.util.Date;

public class MeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.SetActionBarTitle("");
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    public void UIInitialize()
    {
        Button button_Info;
        try {
            button_Info = getActivity().requireViewById(R.id.button_Info);//打开个人信息页面
        }
        catch (Exception e)
        {
            return;
        }
        button_Info.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InfoActivity.class);
            startActivity(intent);
        });
        Button button_Card = requireActivity().requireViewById(R.id.button_Card);//打开证件页面
        button_Card.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CardActivity.class);
            startActivity(intent);
        });
        Button button_Security = requireActivity().requireViewById(R.id.button_Security);//打开重置密码界面
        button_Security.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AccountSafeActivity.class);
            startActivity(intent);
        });
        Button button_Gift = requireActivity().requireViewById(R.id.button_Gift);//打开礼物界面
        button_Gift.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), GiftActivity.class);
            startActivity(intent);
        });
        Button button_Feedback = requireActivity().requireViewById(R.id.button_Feedback);//发送反馈邮件
        button_Feedback.setOnClickListener(v -> {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"ishirai_kurokoi@foxmail.com"});
            String Date ="日期: " + new Date().toLocaleString();
            String Version ="\n程序版本: "+ getAppVersionName(getContext());
            String OSVersion = "\nAndroid系统版本: " +getSdkVersion();
            email.putExtra(Intent.EXTRA_SUBJECT, Arrays.toString(new String[]{"i大工+使用反馈"}));
            email.putExtra(Intent.EXTRA_TEXT, Date+Version+OSVersion);
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "选择您想发送反馈的邮件客户端"));
        });
        Button button_Setting = requireActivity().requireViewById(R.id.button_Setting);//打开设置界面
        button_Setting.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        });
        Button button_Parent = requireActivity().requireViewById(R.id.button_Parent);//打开设置界面
        button_Parent.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ParentBindActivity.class);
            startActivity(intent);
        });
        Button button_About = requireActivity().requireViewById(R.id.button_About);//打开关于界面
        button_About.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutActivity.class);
            startActivity(intent);
        });
        if(ConfigHelper.GetThemeType(requireContext()))
        {
            View bg = requireActivity().findViewById(R.id.bg_view);
            bg.setBackgroundResource(R.drawable.bg_mine_panel_dark);
        }
        InfoInitialize();
    }

    public void InfoInitialize()
    {
        ImageView StudentHeader = requireView().findViewById(R.id.head);
        TextView StudentName = requireView().findViewById(R.id.name);
        ImageView StudentSex = requireView().findViewById(R.id.icon_sex);
        ImageView StudentIdentity = requireView().findViewById(R.id.icon_identity);
        TextView StudentOrg = requireView().findViewById(R.id.parent_org);
        TextView StudentScore = requireView().findViewById(R.id.tv_score);
        //ToDo:增加初始化学生内容信息的部分
    }
    @Override
    public void onResume() {
        super.onResume();
        UIInitialize();
        MainActivity.SetActionBarTitle("");
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



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UIInitialize();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}