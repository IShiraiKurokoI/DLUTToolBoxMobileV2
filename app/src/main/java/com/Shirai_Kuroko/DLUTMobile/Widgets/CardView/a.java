package com.Shirai_Kuroko.DLUTMobile.Widgets.CardView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.Shirai_Kuroko.DLUTMobile.Common.LogToFile;
import com.Shirai_Kuroko.DLUTMobile.Entities.CardInfoBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.LoginResponseBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Shirai_Kuroko
 * @version 1.0.0
 * @ClassName a.java
 * @Description CardViewDataLoader
 * @createTime 2022年09月06日 09:12
 */
public class a implements Runnable {
    public String a;
    public final CardView b;

    public a(final CardView b) {
        super();
        this.b = b;
        this.a = "";
    }

    @Override
    public void run() {
        try {
            final CardView b = this.b;
            b.d();
            String s;
            final String str = s = this.b.appBean.getCard_url();
            if (!str.startsWith("http://")) {
                s = str;
                if (!str.startsWith("https://")) {
                    s = "http://" +
                            str;
                }
            }
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(b.context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            if (!s.contains("?"))
            {
                s+="?"+"verify=" + UserBean.getData().getVerify();
            }
            else
            {
                s+="&verify=" + UserBean.getData().getVerify();
            }
            Handler handler = new Handler(Looper.getMainLooper());
            String finalS = s;
            new Thread(() -> {
                Request request = BackendUtils.CommonGetRequsetBuilderForCard(finalS);
                LogToFile.i("请求方法体", request.toString());
                Log.i("请求方法体", request.toString());
                Response response;
                try {
                    response = new OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(10,TimeUnit.SECONDS).build().newCall(request).execute();
                } catch (IOException e) {
                    handler.post(() -> this.a = e.getLocalizedMessage());
                    this.b.cardInfoBean =null;
                    new Handler(this.b.context.getMainLooper()).post(b::f);
                    e.printStackTrace();
                    return;
                }
                String ResponseBody;
                if (response.body() != null) {
                    try {
                        ResponseBody = Objects.requireNonNull(response.body()).string();
                    } catch (IOException e) {
                        handler.post(() -> this.a = e.getLocalizedMessage());
                        this.b.cardInfoBean =null;
                        new Handler(this.b.context.getMainLooper()).post(b::f);
                        e.printStackTrace();
                        return;
                    }
                    String result = ResponseBody;
                    if (!result.contains("verify failed")) {
                        handler.post(() -> {
                            a=result;
                            Log.i("卡片请求结果", "请求：\n"+ request +"\n返回：\n"+a);
                            LogToFile.i("卡片请求结果", "请求：\n"+ request +"\n返回：\n"+a);
                            try {
                                this.b.cardInfoBean = JSON.parseObject(a, CardInfoBean.class);
                                this.b.f();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                                this.b.cardInfoBean =null;
                                this.b.f();
                            }
                        });
                    }
                }
            }).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

