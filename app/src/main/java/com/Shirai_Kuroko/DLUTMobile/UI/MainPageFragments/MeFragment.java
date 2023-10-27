package com.Shirai_Kuroko.DLUTMobile.UI.MainPageFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.AboutActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.AccountSafeActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.CardActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.FeedbackActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.GiftActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.ParentBindActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.PersonalInfoActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.SettingsActivity;
import com.Shirai_Kuroko.DLUTMobile.Utils.MobileUtils;

public class MeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    public void UIInitialize() {
        Button button_Info;
        try {
            button_Info = getActivity().findViewById(R.id.button_Info);//打开个人信息页面
        } catch (Exception e) {
            return;
        }
        button_Info.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PersonalInfoActivity.class);
            startActivity(intent);
        });
        Button button_Card = requireActivity().findViewById(R.id.button_Card);//打开证件页面
        button_Card.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CardActivity.class);
            startActivity(intent);
        });
        Button button_Security = requireActivity().findViewById(R.id.button_Security);//打开重置密码界面
        button_Security.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AccountSafeActivity.class);
            startActivity(intent);
        });
        Button button_Gift = requireActivity().findViewById(R.id.button_Gift);//打开礼物界面
        button_Gift.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), GiftActivity.class);
            startActivity(intent);
        });
        Button button_Feedback = requireActivity().findViewById(R.id.button_Feedback);//发送反馈邮件
        button_Feedback.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FeedbackActivity.class);
            startActivity(intent);
        });
        Button button_Setting = requireActivity().findViewById(R.id.button_Setting);//打开设置界面
        button_Setting.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        });
        Button button_Parent = requireActivity().findViewById(R.id.button_Parent);//打开设置界面
        button_Parent.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ParentBindActivity.class);
            startActivity(intent);
        });
        Button button_About = requireActivity().findViewById(R.id.button_About);//打开关于界面
        button_About.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutActivity.class);
            startActivity(intent);
        });
        InfoInitialize();
    }

    public void InfoInitialize() {
        ImageView StudentHeader = requireView().findViewById(R.id.head);
        TextView StudentName = requireView().findViewById(R.id.name);
        ImageView StudentSex = requireView().findViewById(R.id.icon_sex);
        ImageView StudentIdentity = requireView().findViewById(R.id.icon_identity);
        TextView StudentOrg = requireView().findViewById(R.id.parent_org);
        TextView StudentScore = requireView().findViewById(R.id.tv_score);
        MobileUtils.InitializeMeFragmentInfo(StudentHeader, StudentName, StudentSex, StudentIdentity, StudentOrg, StudentScore, requireContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        UIInitialize();
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