package com.Shirai_Kuroko.DLUTMobile.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Shirai_Kuroko.DLUTMobile.Adapters.ADBannerAdapter;
import com.Shirai_Kuroko.DLUTMobile.Adapters.CommentDetailAdapter;
import com.Shirai_Kuroko.DLUTMobile.Adapters.ExchangeRecordAdapter;
import com.Shirai_Kuroko.DLUTMobile.Adapters.PresentListAdapter;
import com.Shirai_Kuroko.DLUTMobile.Adapters.RankAdapter;
import com.Shirai_Kuroko.DLUTMobile.Adapters.ScoreDetailAdapter;
import com.Shirai_Kuroko.DLUTMobile.Common.CenterToast;
import com.Shirai_Kuroko.DLUTMobile.Common.LogToFile;
import com.Shirai_Kuroko.DLUTMobile.Entities.ADBannerBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.AppDetailNowResultBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.CardQRCodeResult;
import com.Shirai_Kuroko.DLUTMobile.Entities.CodeRefreshResult;
import com.Shirai_Kuroko.DLUTMobile.Entities.CommentDetailResultBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.ExchangeRecordResult;
import com.Shirai_Kuroko.DLUTMobile.Entities.GalleryListBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.GiftDetailResponse;
import com.Shirai_Kuroko.DLUTMobile.Entities.GiftExchangeResponse;
import com.Shirai_Kuroko.DLUTMobile.Entities.HeadUploadResult;
import com.Shirai_Kuroko.DLUTMobile.Entities.IDPhotoResult;
import com.Shirai_Kuroko.DLUTMobile.Entities.LoginResponseBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.MsgInfoResult;
import com.Shirai_Kuroko.DLUTMobile.Entities.MsgResult;
import com.Shirai_Kuroko.DLUTMobile.Entities.NewMsgInfo;
import com.Shirai_Kuroko.DLUTMobile.Entities.NotificationHistoryDataBaseBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal.DLUTNoticeContentBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.Oringinal.FileResponseBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.PresentListResult;
import com.Shirai_Kuroko.DLUTMobile.Entities.ResendedUserInfo;
import com.Shirai_Kuroko.DLUTMobile.Entities.ResponseErrorBean;
import com.Shirai_Kuroko.DLUTMobile.Entities.ScoreDetailResult;
import com.Shirai_Kuroko.DLUTMobile.Entities.ScoreFunResultbean;
import com.Shirai_Kuroko.DLUTMobile.Entities.SkeyRefreshResult;
import com.Shirai_Kuroko.DLUTMobile.Entities.TGTRefreshResult;
import com.Shirai_Kuroko.DLUTMobile.Entities.UserScoreBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ContextHelper;
import com.Shirai_Kuroko.DLUTMobile.Helpers.NotificationHelper;
import com.Shirai_Kuroko.DLUTMobile.Helpers.QRCodeHelper;
import com.Shirai_Kuroko.DLUTMobile.Managers.MsgHistoryManager;
import com.Shirai_Kuroko.DLUTMobile.R;
import com.Shirai_Kuroko.DLUTMobile.UI.GiftDetailActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.GiftExchangeActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.PureBrowserActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK.UploadImageCommand;
import com.Shirai_Kuroko.DLUTMobile.UI.LoginActivity;
import com.Shirai_Kuroko.DLUTMobile.UI.ServiceManagement.AppDetailActivity;
import com.Shirai_Kuroko.DLUTMobile.Widgets.LoadingView;
import com.Shirai_Kuroko.DLUTMobile.Widgets.PinView;
import com.Shirai_Kuroko.DLUTMobile.Widgets.PreferenceRightDetailView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.igexin.sdk.PushManager;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BackendUtils {
    private static OkHttpClient createHttpclient() {
        final OkHttpClient.Builder builder =  new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        return builder.build();
    }

    public static OkHttpClient client;

    static {
        client = createHttpclient();
    }

    @SuppressWarnings("ALL")
    public static String GetHeaderValueFora() {
        return c.GetHeaderValueFora();
    }

    public static int I0(final int n, final int n2) {
        return c.I0(n, n2);
    }

    public static String GetHeaderForVersion() {
        return c.GetHeaderForVersion();
    }

    public static String GetHeaderForNonce() {
        return c.GetHeaderForNonce();
    }

    public static String GetwhistleVersion() {
        return c.GetwhistleVersion();
    }

    @SuppressWarnings("ALL")
    public static String[] GetHeaderForTimeStamp() {
        return c.GetHeaderForTimeStamp();
    }

    public static String GetHeaderForSign(String str, String timestamp, String nonce, String a) {
        return c.GetHeaderForSign(str, timestamp, nonce, a);
    }

    public static String aa(final String s, final String s2) throws Exception {
        return c.aa(s, s2);
    }

    public static String GetEncodedPassword(String password) {
        return c.GetEncodedPassword(password);
    }

    public static String GetDeviceName() {
        return URLEncoder.encode(Settings.Global.getString(ContextHelper.getContext().getContentResolver(), Settings.Global.DEVICE_NAME));
    }

    public static Request CommonGetRequsetBuilder(String Url) {
        String a = GetHeaderValueFora();
        String[] timestamps = GetHeaderForTimeStamp();
        String Timestamp = timestamps[0];
        String LocalTimestamp = timestamps[1];
        String Nonce = GetHeaderForNonce();
        String Sign = GetHeaderForSign((Url != null ? Url.split("\\?") : new String[0])[1], Timestamp, Nonce, a);
        return new Request.Builder()
                .url(Url)
                .header("a", a)
                .header("sign", Sign)
                .header("local_timestamp", LocalTimestamp)
                .header("nonce", Nonce)
                .header("version", GetHeaderForVersion())
                .header("timestamp", Timestamp)
                .header("Host", "service.m.dlut.edu.cn")
                .header("Connection", "Keep-Alive")
                .header("Cookie2", "$Version=1")
                .build();
    }

    public static Request CommonGetRequsetBuilderForCard(String Url) {
        String a = GetHeaderValueFora();
        String[] timestamps = GetHeaderForTimeStamp();
        String Timestamp = timestamps[0];
        String LocalTimestamp = timestamps[1];
        String Nonce = GetHeaderForNonce();
        String Sign = GetHeaderForSign((Url != null ? Url.split("\\?") : new String[0])[1], Timestamp, Nonce, a);
        return new Request.Builder()
                .url(Url)
                .header("a", a)
                .header("sign", Sign)
                .header("local_timestamp", LocalTimestamp)
                .header("nonce", Nonce)
                .header("version", GetHeaderForVersion())
                .header("timestamp", Timestamp)
                .header("Connection", "Keep-Alive")
                .header("Cookie2", "$Version=1")
                .build();
    }

    public static synchronized void Login(Context context, String Username, String Password) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            String PasswordEncoded = null;
            try {
                PasswordEncoded = GetEncodedPassword(Password);
            } catch (Exception e) {
                e.printStackTrace();
                LogToFile.e("错误", e.toString());
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=user&a=userLoginCas&phone_type=" + GetDeviceName() + "&verfiy_image_code=&app_version=" + GetwhistleVersion() + "&stu_identity=&os_version=10&device_type=android&equipment_type=phone&client_id=" + PushManager.getInstance().getClientid(context) + "&platform=android&uid=0&password=" + PasswordEncoded + "&student_number=" + Username + "&school=dlut&identity=&equipment_id=null&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> {
                        Toast.makeText(context, "后端服务器无响应，正在重试", Toast.LENGTH_SHORT).show();
                        Login(context, Username, Password);
                    });
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                            if (ResponseBody.contains("{\"ret\":-1,\"")) {
                                handler.post(() -> {
                                    Toast.makeText(context, "认证失败，请重新登录", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    context.startActivity(intent);
                                    Log.d("后端交互日志", "登录失败返回" + ResponseBody);
                                    LogToFile.d("后端交互日志", "登录失败返回" + ResponseBody);
                                });
                            } else {
                                handler.post(() -> {
                                    Log.d("后端交互日志", "登录成功返回" + ResponseBody);
                                    LogToFile.d("后端交互日志", "登录成功返回" + ResponseBody);
                                    Toast.makeText(context, "登陆成功", Toast.LENGTH_SHORT).show();
                                    ConfigHelper.SaveLoginResultToPref(ResponseBody, context);
                                    RefreshNotice(context);
                                    GetScore(context);
                                });
                            }
                        }
                    } else {
                        String ResponseBody;
                        if (response.body() != null) {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                            String finalResponseBody = ResponseBody;
                            handler.post(() -> {
                                Toast.makeText(context, "认证失败，请重新登录", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                                Log.d("后端交互日志", "登录失败返回" + finalResponseBody);
                                LogToFile.d("后端交互日志", "登录失败返回" + finalResponseBody);
                            });
                        }
                    }
                }
            });
        }).start();
    }

    public static void LoginForLogin(Context context, String Username, String Password, LoginActivity loginactivity, LoadingView load, String VerifyCode) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            String PasswordEncoded = null;
            try {
                PasswordEncoded = GetEncodedPassword(Password);
            } catch (Exception e) {
                e.printStackTrace();
                LogToFile.e("错误", e.toString());
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=user&a=userLoginCas&phone_type=" + GetDeviceName() + "&verfiy_image_code=" + URLEncoder.encode(VerifyCode) + "&app_version=" + GetwhistleVersion() + "&stu_identity=&os_version=10&device_type=android&equipment_type=phone&client_id=" + PushManager.getInstance().getClientid(context) + "&platform=android&uid=0&password=" + PasswordEncoded + "&student_number=" + Username + "&school=dlut&identity=&equipment_id=null&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            Log.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.postDelayed(() -> {
                        Toast.makeText(context, "后端服务器无响应，正在重试", Toast.LENGTH_SHORT).show();
                        LoginForLogin(context, Username, Password, loginactivity, load, VerifyCode);
                    }, 2000);
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String ResponseBody = Objects.requireNonNull(response.body()).string();
                            Log.d("返回结果", ResponseBody);
                            LogToFile.d("返回结果", ResponseBody);
                            if (ResponseBody.contains("{\"ret\":-1,\"")) {
                                handler.post(() -> {
                                    ResponseErrorBean responseErrorBean = JSON.parseObject(ResponseBody, ResponseErrorBean.class);
                                    Toast.makeText(context, responseErrorBean.getErrmsg(), Toast.LENGTH_LONG).show();
                                    if (!Objects.equals(responseErrorBean.getData().getImage(), "")) {
                                        Log.d("后端交互日志", "需要验证码" + responseErrorBean.getData().getImage());
                                        LogToFile.d("后端交互日志", "需要验证码" + responseErrorBean.getData().getImage());
                                        Dialog dialog = new Dialog(context, R.style.CustomDialog);
                                        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(
                                                R.layout.dialog_identify_code, null);
                                        final ImageView iv_identify_code = view.findViewById(R.id.iv_identify_code);
                                        String base64 = responseErrorBean.getData().getImage();
                                        base64 = base64.replace("\\/", "/");
                                        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
                                        Bitmap Code = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        iv_identify_code.setImageBitmap(Code);
                                        final PinView pv_identify_code = view.findViewById(R.id.pv_identify_code);
                                        final AppCompatButton btn_refresh = view.findViewById(R.id.btn_refresh);
                                        btn_refresh.setOnClickListener(view12 ->
                                                RefreshVerifyCode(context, iv_identify_code, Username, load));
                                        final AppCompatButton submit = view.findViewById(R.id.submit);
                                        submit.setOnClickListener(view1 -> {
                                            if (pv_identify_code.getText().toString().length() == 4) {
                                                loginactivity.Verify_code = pv_identify_code.getText().toString();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(context, "无效的验证码", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        final View btn_close = view.findViewById(R.id.btn_close);
                                        btn_close.setOnClickListener(v -> dialog.dismiss());
                                        Window window = dialog.getWindow();
                                        window.setContentView(view);
                                        window.setGravity(Gravity.CENTER);
                                        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                                                WindowManager.LayoutParams.WRAP_CONTENT);
                                        dialog.setCanceledOnTouchOutside(false);
                                        WindowManager.LayoutParams lp = loginactivity.getWindow().getAttributes();
                                        lp.alpha = 0.5f;
                                        loginactivity.getWindow().setAttributes(lp);
                                        dialog.setOnDismissListener(dialogInterface -> {
                                            WindowManager.LayoutParams lp1 = loginactivity.getWindow().getAttributes();
                                            lp1.alpha = 1f;
                                            loginactivity.getWindow().setAttributes(lp1);
                                        });
                                        dialog.show();
                                    }
                                });
                            } else {
                                handler.post(() -> {
                                    ConfigHelper.SaveLoginResultToPref(ResponseBody, context);
                                    Toast.makeText(context, "登陆成功", Toast.LENGTH_SHORT).show();
                                    load.dismiss();
                                    loginactivity.finish();
                                    GetScore(context);
                                    RefreshNotice(context);
                                });
                            }
                        }
                    } else {
                        String ResponseBody = null;
                        if (response.body() != null) {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                        }
                        Log.d("后端交互日志", "登陆失败" + ResponseBody);
                        LogToFile.d("后端交互日志", "登陆失败" + ResponseBody);
                        String finalResponseBody = ResponseBody;
                        handler.post(() -> {
                            ResponseErrorBean responseErrorBean = JSON.parseObject(finalResponseBody, ResponseErrorBean.class);
                            Toast.makeText(context, Objects.requireNonNull(responseErrorBean).getErrmsg(), Toast.LENGTH_LONG).show();
                            if (!Objects.equals(responseErrorBean.getData().getImage(), "")) {
                                Log.d("后端交互日志", "需要验证码" + responseErrorBean.getData().getImage());
                                LogToFile.d("后端交互日志", "需要验证码" + responseErrorBean.getData().getImage());
                                Dialog dialog = new Dialog(context, R.style.CustomDialog);
                                @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(
                                        R.layout.dialog_identify_code, null);
                                final ImageView iv_identify_code = view.findViewById(R.id.iv_identify_code);
                                String base64 = responseErrorBean.getData().getImage();
                                base64 = base64.replace("\\/", "/");
                                byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
                                Bitmap Code = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                iv_identify_code.setImageBitmap(Code);
                                final PinView pv_identify_code = view.findViewById(R.id.pv_identify_code);
                                final AppCompatButton btn_refresh = view.findViewById(R.id.btn_refresh);
                                btn_refresh.setOnClickListener(view12 ->
                                        RefreshVerifyCode(context, iv_identify_code, Username, load));
                                final AppCompatButton submit = view.findViewById(R.id.submit);
                                submit.setOnClickListener(view1 -> {
                                    if (pv_identify_code.getText().toString().length() == 4) {
                                        loginactivity.Verify_code = pv_identify_code.getText().toString();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(context, "无效的验证码", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                final View btn_close = view.findViewById(R.id.btn_close);
                                btn_close.setOnClickListener(v -> dialog.dismiss());
                                Window window = dialog.getWindow();
                                window.setContentView(view);
                                window.setGravity(Gravity.CENTER);
                                window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                                        WindowManager.LayoutParams.WRAP_CONTENT);
                                dialog.setCanceledOnTouchOutside(false);
                                WindowManager.LayoutParams lp = loginactivity.getWindow().getAttributes();
                                lp.alpha = 0.5f;
                                loginactivity.getWindow().setAttributes(lp);
                                dialog.setOnDismissListener(dialogInterface -> {
                                    WindowManager.LayoutParams lp1 = loginactivity.getWindow().getAttributes();
                                    lp1.alpha = 1f;
                                    loginactivity.getWindow().setAttributes(lp1);
                                });
                                dialog.show();
                            }
                        });
                    }
                }
            });
        }).start();
    }

    public static void RefreshVerifyCode(Context context, ImageView imageView, String Username, LoadingView loadingView) {
        loadingView.show();
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            String a = GetHeaderValueFora();
            String[] timestamps = GetHeaderForTimeStamp();
            String Timestamp = timestamps[0];
            String LocalTimestamp = timestamps[1];
            String Nonce = GetHeaderForNonce();
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=user&a=checkVerifyImageCode";
            String PostBody = "uid=0&verfiy_image_code=&student_number=" + Username + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&device_type=android&type=1&platform=android&city_id=10";
            String Sign = GetHeaderForSign(PostBody, Timestamp, Nonce, a);
            OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
            Request request = new Request.Builder()
                    .url(url)
                    .header("a", a)
                    .header("sign", Sign)
                    .header("local_timestamp", LocalTimestamp)
                    .header("nonce", Nonce)
                    .header("version", GetHeaderForVersion())
                    .header("timestamp", Timestamp)
                    .header("Host", "service.m.dlut.edu.cn")
                    .header("Connection", "Keep-Alive")
                    .header("Cookie2", "$Version=1")
                    .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), PostBody))
                    .build();//创建Request 对象
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> {
                        Toast.makeText(context, "刷新失败", Toast.LENGTH_SHORT).show();
                        loadingView.dismiss();
                    });
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("刷新验证码请求返回", ResponseBody);
                            CodeRefreshResult codeRefreshResult = JSON.parseObject(ResponseBody, CodeRefreshResult.class);
                            if (codeRefreshResult.getRet() == 0 && !Objects.equals(codeRefreshResult.getData().getImage(), "")) {
                                handler.post(() -> {
                                    String base64 = codeRefreshResult.getData().getImage();
                                    base64 = base64.replace("\\/", "/");
                                    byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
                                    Bitmap Code = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    imageView.setImageBitmap(Code);
                                    loadingView.dismiss();
                                });
                            } else {
                                handler.post(() -> {
                                    Toast.makeText(context, "刷新失败:" + codeRefreshResult.getErrmsg(), Toast.LENGTH_SHORT).show();
                                    loadingView.dismiss();
                                });
                            }
                        }
                    } else {
                        handler.post(() -> {
                            Toast.makeText(context, "刷新失败", Toast.LENGTH_SHORT).show();
                            loadingView.dismiss();
                        });
                    }
                }
            });
        }).start();
    }

    public static void ReSendUserInfo(Context context) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO;
            try {
                if (UserBean == null) {
                    handler.post(() -> {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                        String Un = prefs.getString("Username", "");
                        String Pd = prefs.getString("Password", "");
                        Login(context, Un, Pd);
                    });
                    return;
                }
                infoDTO = UserBean.getData().getMy_info();
                if (infoDTO == null) {
                    handler.post(() -> {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                        String Un = prefs.getString("Username", "");
                        String Pd = prefs.getString("Password", "");
                        Login(context, Un, Pd);
                    });
                    return;
                }
            } catch (Exception e) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=orginfo&a=getUserInfo&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&device_type=android&otheraid=" + infoDTO.getUser_id() + "&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                    handler.post(() -> ReSendUserInfo(context));
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                            if (ResponseBody.contains("{\"ret\":-1,\"")) {
                                handler.post(() -> {
                                    if (ResponseBody.contains("verify failed")) {
                                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                        String Un = prefs.getString("Username", "");
                                        String Pd = prefs.getString("Password", "");
                                        Login(context, Un, Pd);
                                    } else {
                                        if (!ResponseBody.contains("No Response")) {
                                            Toast.makeText(context, ResponseBody.split("\"errmsg\":\"")[1].split("\"")[0], Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    Log.d("后端交互日志", "刷新用户数据失败" + ResponseBody);
                                    LogToFile.d("后端交互日志", "刷新用户数据失败" + ResponseBody);
                                });
                            } else {
                                handler.post(() -> {
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                    String Un = prefs.getString("Username", "");
                                    String Pd = prefs.getString("Password", "");
                                    boolean b = Un.length() * Pd.length() != 0;
                                    if (ResponseBody.contains("verify failed")) {
                                        if (b) {
                                            Login(context, Un, Pd);
                                        } else {
                                            Toast.makeText(context, "认证失败，请重新登录", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(context, LoginActivity.class);
                                            context.startActivity(intent);
                                        }
                                    } else {
                                        ResendedUserInfo resendedUserInfo = JSON.parseObject(ResponseBody, ResendedUserInfo.class);
                                        LoginResponseBean loginResponseBean = ConfigHelper.GetUserBean(context);
                                        String skey = loginResponseBean.getData().getMy_info().getSkey();
                                        String Expires_skey = loginResponseBean.getData().getMy_info().getExpires_skey();
                                        try {
                                            if (resendedUserInfo.getData().getMyInfo() == null) {
                                                return;
                                            }
                                        } catch (Exception e) {
                                            Login(context, Un, Pd);
                                            return;
                                        }
                                        loginResponseBean.getData().setMy_info(resendedUserInfo.getData().getMyInfo());
                                        if (skey == null) {
                                            if (b) {
                                                Login(context, Un, Pd);
                                            }
                                            return;
                                        }
                                        if (resendedUserInfo.getData().getMyInfo().getSkey() == null) {
                                            loginResponseBean.getData().getMy_info().setSkey(skey);
                                            loginResponseBean.getData().getMy_info().setExpires_skey(Expires_skey);
                                        }
                                        ConfigHelper.SaveLoginResultToPref(JSON.toJSONString(loginResponseBean), context);
                                        Log.d("后端交互日志", "刷新用户数据成功");
                                        LogToFile.d("后端交互日志", "刷新用户数据成功");
                                        CheckTGT(context);
                                    }
                                });
                            }
                        }
                    } else {
                        String ResponseBody = null;
                        if (response.body() != null) {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                        }
                        Log.d("后端交互日志", "刷新失败");
                        LogToFile.d("后端交互日志", "刷新失败");
                        String finalResponseBody = ResponseBody;
                        handler.post(() -> {
                            if (finalResponseBody != null) {
                                Toast.makeText(context, finalResponseBody.split("\"errmsg\":\"")[1].split("\"")[0], Toast.LENGTH_SHORT).show();
                            }
                            ReSendUserInfo(context);
                        });
                    }
                }
            });
        }).start();
    }

    public static void SetClientID(Context context, String ClientID) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO;
            try {
                if (UserBean == null) {
                    return;
                }
                infoDTO = UserBean.getData().getMy_info();
                if (infoDTO == null) {
                    return;
                }
            } catch (Exception e) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=user&a=setClientId&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&device_type=android&aid=" + infoDTO.getUser_id() + "&client_id=" + ClientID + "&platform=android&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> {
                        SetClientID(context, ClientID);
                        Log.d("后端交互日志", "设置个推客户端ID失败" + e.getLocalizedMessage());
                        LogToFile.d("后端交互日志", "设置个推客户端ID失败" + e.getLocalizedMessage());
                    });
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                            handler.post(() -> {
                                if (ResponseBody.contains("verify failed")) {
                                    SetClientID(context, ClientID);
                                } else {
                                    Log.d("后端交互日志", "设置个推客户端ID成功");
                                    LogToFile.d("后端交互日志", "设置个推客户端ID成功");
                                }
                            });
                        }
                    } else {
                        String ResponseBody = null;
                        if (response.body() != null) {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                        }
                        String finalResponseBody = ResponseBody;
                        handler.post(() -> {
                            SetClientID(context, ClientID);
                            Log.d("后端交互日志", "设置个推客户端ID失败" + finalResponseBody);
                            LogToFile.d("后端交互日志", "设置个推客户端ID失败" + finalResponseBody);
                        });
                    }
                }
            });
        }).start();
    }

    @SuppressWarnings("ALL")
    public static void GetGallery(Context context, Banner banner) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO;
            try {
                if (UserBean == null) {
                    return;
                }
                infoDTO = UserBean.getData().getMy_info();
                if (infoDTO == null) {
                    return;
                }
            } catch (Exception e) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=orginfo&a=getGallery&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&device_type=android&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> GetGallery(context, banner));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                            handler.post(() -> {
                                if (!ResponseBody.contains("verify failed")) {
                                    List<ADBannerBean> Gallery = new ArrayList<>();
                                    GalleryListBean galleryListBean = JSON.parseObject(ResponseBody, GalleryListBean.class);
                                    for (GalleryListBean.DataDTO.CarouselGalleryDTO carouselGalleryDTO : galleryListBean.getData().getCarousel_gallery()) {
                                        Gallery.add(new ADBannerBean(carouselGalleryDTO.getPic_uri(), carouselGalleryDTO.getUrl()));
                                    }
                                    banner.setAdapter(new ADBannerAdapter(Gallery, context));
                                    banner.addBannerLifecycleObserver((FragmentActivity) context);
                                    banner.setIndicator(new RectangleIndicator(context));
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                    prefs.edit().putString("GalleryCache", JSON.toJSONString(Gallery)).apply();
                                    LocalDate date = LocalDate.now();
                                    prefs.edit().putString("GalleryCacheDate", date.toString()).apply();
                                    Log.d("后端交互日志 获取轮播数据成功", ResponseBody);
                                    LogToFile.d("后端交互日志 获取轮播数据成功", ResponseBody);
                                }
                            });
                        }
                    } else {
                        String ResponseBody = null;
                        if (response.body() != null) {
                            ResponseBody = response.body().string();
                            LogToFile.d("请求返回", ResponseBody);
                        }
                        Log.d("后端交互日志 轮播数据获取失败", ResponseBody);
                        LogToFile.d("后端交互日志 轮播数据获取失败", ResponseBody);
                        handler.post(() -> GetGallery(context, banner));
                    }
                }
            });
        }).start();
    }

    public static void GetScore(Context context) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO;
            try {
                if (UserBean == null) {
                    return;
                }
                infoDTO = UserBean.getData().getMy_info();
                if (infoDTO == null) {
                    return;
                }
            } catch (Exception e) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=score&a=snapShot&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&device_type=android&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> GetScore(context));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                            handler.post(() -> {
                                if (!ResponseBody.contains("verify failed")) {
                                    ConfigHelper.SaveUserScoreBean(context, ResponseBody);
                                    Log.d("后端交互日志", "获取分数数据成功");
                                    LogToFile.d("后端交互日志", "获取分数数据成功");
                                    Log.d("后端交互日志", "启动初始化完成");
                                    LogToFile.d("后端交互日志", "启动初始化完成");
                                    RefreshNotice(context);
                                    GainScore(context);
                                }
                            });
                        }
                    } else {
                        String ResponseBody = null;
                        if (response.body() != null) {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                        }
                        Log.d("后端交互日志", "分数数据获取失败" + ResponseBody);
                        LogToFile.d("后端交互日志", "分数数据获取失败" + ResponseBody);
                        handler.post(() -> GetScore(context));
                    }
                }
            });
        }).start();
    }


    public static void GetScore(Context context, TextView Score) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=score&a=snapShot&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&device_type=android&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> GetScore(context, Score));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                            handler.post(() -> {
                                if (!ResponseBody.contains("verify failed")) {
                                    Log.d("后端交互日志 获取分数数据成功", ResponseBody);
                                    LogToFile.d("后端交互日志 获取分数数据成功", ResponseBody);
                                    ConfigHelper.SaveUserScoreBean(context, ResponseBody);
                                    MobileUtils.GetScore(context, Score);
                                }
                            });
                        }
                    } else {
                        String ResponseBody = null;
                        if (response.body() != null) {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                        }
                        Log.d("后端交互日志 分数数据获取失败", ResponseBody);
                        LogToFile.d("后端交互日志 分数数据获取失败", ResponseBody);
                        handler.post(() -> GetScore(context, Score));
                    }
                }
            });
        }).start();
    }

    @SuppressLint("SetTextI18n")
    public static void GetScore(Context context, TextView Score, TextView Rank, TextView Income, TextView Outcome) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=score&a=snapShot&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&device_type=android&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> GetScore(context, Score, Rank, Income, Outcome));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                            if (ResponseBody.contains("verify failed")) {
                                handler.post(() -> {
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                    String Un = prefs.getString("Username", "");
                                    String Pd = prefs.getString("Password", "");
                                    if (Un.length() * Pd.length() != 0) {
                                        Login(context, Un, Pd);
                                    } else {
                                        Toast.makeText(context, "认证失败，请重新登录", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(context, LoginActivity.class);
                                        context.startActivity(intent);
                                    }
                                });
                            } else {
                                handler.post(() -> {
                                    Log.d("后端交互日志 获取分数数据成功", ResponseBody);
                                    LogToFile.d("后端交互日志 获取分数数据成功", ResponseBody);
                                    ConfigHelper.SaveUserScoreBean(context, ResponseBody);
                                    UserScoreBean userScoreBean = ConfigHelper.GetUserScoreBean(context);
                                    Score.setText(String.valueOf(userScoreBean.getData().getUser_points()));
                                    Rank.setText("超过了全校" + userScoreBean.getData().getRank() + "的人");
                                    Income.setText(String.valueOf(userScoreBean.getData().getIn_points()));
                                    Outcome.setText(String.valueOf(userScoreBean.getData().getOut_points()));
                                });
                            }
                        }
                    } else {
                        String ResponseBody = null;
                        if (response.body() != null) {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                        }
                        Log.d("后端交互日志 分数数据获取失败", ResponseBody);
                        LogToFile.d("后端交互日志 分数数据获取失败", ResponseBody);
                        handler.post(() -> GetScore(context, Score, Rank, Income, Outcome));
                    }
                }
            });
        }).start();
    }

    public static void LoadQRCode(Context context, ImageView imageView) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                handler.post(() -> LoadQRCode(context, imageView));
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean != null ? UserBean.getData().getMy_info() : null;
            if (infoDTO == null) {
                handler.post(() -> LoadQRCode(context, imageView));
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=QRCode&a=generateTimeLimitedStr&uid=0&student_number=" + (infoDTO != null ? infoDTO.getStudentNumber() : null) + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + (UserBean != null ? UserBean.getData().getVerify().replace(":", "%3A") : null) + "&device_type=android&aid=" + infoDTO.getUser_id() + "&platform=android&ver_type=myCard&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> LoadQRCode(context, imageView));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String ResponseBody = Objects.requireNonNull(response.body()).string();
                            Log.d("后端交互日志 二维码数据返回", ResponseBody);
                            LogToFile.d("后端交互日志 二维码数据返回", ResponseBody);
                            if (ResponseBody.contains("verify failed")) {
                                handler.post(() -> {
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                    String Un = prefs.getString("Username", "");
                                    String Pd = prefs.getString("Password", "");
                                    if (Un.length() * Pd.length() != 0) {
                                        Login(context, Un, Pd);
                                    } else {
                                        Toast.makeText(context, "认证失败，请重新登录", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(context, LoginActivity.class);
                                        context.startActivity(intent);
                                    }
                                });
                            } else {
                                handler.post(() -> {
                                    CardQRCodeResult cardQRCodeResult = JSON.parseObject(ResponseBody, CardQRCodeResult.class);
                                    String content = cardQRCodeResult.getData().getUrl();
                                    Bitmap qr = QRCodeHelper.createQRCodeBitmap(content, 400, 400, "UTF-8", "H", "0", Color.BLACK, Color.WHITE);
                                    imageView.setImageBitmap(qr);
                                });
                            }
                        }
                    } else {
                        String ResponseBody = null;
                        if (response.body() != null) {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                        }
                        Log.d("后端交互日志 二维码数据获取失败", ResponseBody);
                        LogToFile.d("后端交互日志 二维码数据获取失败", ResponseBody);
                        handler.post(() -> LoadQRCode(context, imageView));
                    }
                }
            });
        }).start();
    }

    public static void LoadIDPhoto(Context context, ImageView imageView) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO;
            try {
                if (UserBean == null) {
                    return;
                }
                infoDTO = UserBean.getData().getMy_info();
                if (infoDTO == null) {
                    return;
                }
            } catch (Exception e) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=orginfo&a=reSendMsgInfo&app_version=" + GetwhistleVersion() + "&stu_identity=&device_type=android&client_id=" + PushManager.getInstance().getClientid(context) + "&platform=android&uid=0&is_load_idphoto=1&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&aid=" + infoDTO.getUser_id() + "&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> LoadIDPhoto(context, imageView));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String ResponseBody = Objects.requireNonNull(response.body()).string();
                            Log.d("后端交互日志 证件照数据返回", ResponseBody);
                            LogToFile.d("后端交互日志 证件照数据返回", ResponseBody);
                            if (ResponseBody.contains("verify failed")) {
                                handler.post(() -> {
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                    String Un = prefs.getString("Username", "");
                                    String Pd = prefs.getString("Password", "");
                                    if (Un.length() * Pd.length() != 0) {
                                        Login(context, Un, Pd);
                                    } else {
                                        Toast.makeText(context, "认证失败，请重新登录", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(context, LoginActivity.class);
                                        context.startActivity(intent);
                                    }
                                });
                            } else {
                                handler.post(() -> {
                                    ConfigHelper.SaveIDPhoto(context, ResponseBody);
                                    IDPhotoResult idPhotoResult = JSON.parseObject(ResponseBody, IDPhotoResult.class);
                                    String base64 = idPhotoResult.getData().getId_photo();
                                    base64 = base64.replace("\\/", "/");
                                    byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
                                    Bitmap myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    imageView.setImageBitmap(myBitmap);
                                    MsgResult msgResult = JSON.parseObject(ResponseBody, MsgResult.class);
                                    if (msgResult.getRet() == 0) {
                                        if (msgResult.getData().getMsgId().size() != 0) {
                                            for (String id : msgResult.getData().getMsgId()) {
                                                new Thread(() -> BackendUtils.GetMsgNewDetailInfo(context, id)).start();
                                            }
                                        }
                                        if (msgResult.getData().getAppMsgId().size() != 0) {
                                            for (String id : msgResult.getData().getAppMsgId()) {
                                                new Thread(() -> BackendUtils.GetMsgNewDetailInfo(context, id)).start();
                                            }
                                        }
                                        if (msgResult.getData().getConverMsgId().size() != 0) {
                                            for (String id : msgResult.getData().getConverMsgId()) {
                                                new Thread(() -> BackendUtils.GetMsgNewDetailInfo(context, id)).start();
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    } else {
                        String ResponseBody = null;
                        if (response.body() != null) {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                        }
                        Log.d("后端交互日志 证件照数据获取失败", ResponseBody);
                        LogToFile.d("后端交互日志 证件照数据获取失败", ResponseBody);
                        handler.post(() -> LoadIDPhoto(context, imageView));
                    }
                }
            });
        }).start();
    }

    public static void RefreshNotice(Context context) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO;
            try {
                if (UserBean == null) {
                    return;
                }
                infoDTO = UserBean.getData().getMy_info();
                if (infoDTO == null) {
                    return;
                }
            } catch (Exception e) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=orginfo&a=reSendMsgInfo&app_version=" + GetwhistleVersion() + "&stu_identity=&device_type=android&client_id=" + PushManager.getInstance().getClientid(context) + "&platform=android&uid=0&is_load_idphoto=0&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&aid=" + infoDTO.getUser_id() + "&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String ResponseBody = Objects.requireNonNull(response.body()).string();
                            Log.d("后端交互日志", "通知刷新数据返回 " + ResponseBody);
                            LogToFile.d("后端交互日志", "通知刷新数据返回 " + ResponseBody);
                            if (!ResponseBody.contains("verify failed")) {
                                handler.post(() -> {
                                    MsgResult msgResult = JSON.parseObject(ResponseBody, MsgResult.class);
                                    if (msgResult.getRet() == 0) {
                                        if (msgResult.getData().getMsgId().size() != 0) {
                                            for (String id : msgResult.getData().getMsgId()) {
                                                new Thread(() -> BackendUtils.GetMsgNewDetailInfo(context, id)).start();
                                            }
                                        }
                                        if (msgResult.getData().getAppMsgId().size() != 0) {
                                            for (String id : msgResult.getData().getAppMsgId()) {
                                                new Thread(() -> BackendUtils.GetMsgNewDetailInfo(context, id)).start();
                                            }
                                        }
                                        if (msgResult.getData().getConverMsgId().size() != 0) {
                                            for (String id : msgResult.getData().getConverMsgId()) {
                                                new Thread(() -> BackendUtils.GetMsgNewDetailInfo(context, id)).start();
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            });
        }).start();
    }

    public static void ChangeNickName(Context context, String NewNickName, PreferenceRightDetailView textView) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO;
            try {
                if (UserBean == null) {
                    return;
                }
                infoDTO = UserBean.getData().getMy_info();
                if (infoDTO == null) {
                    return;
                }
            } catch (Exception e) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=orginfo&a=setUserInfo&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&param=%7B%22nick_name%22%3A%22" + URLEncoder.encode(NewNickName) + "%22%7D&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&device_type=android&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> ChangeNickName(context, NewNickName, textView));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String ResponseBody = Objects.requireNonNull(response.body()).string();
                            Log.d("后端交互日志 昵称修改返回", ResponseBody);
                            LogToFile.d("后端交互日志 昵称修改返回", ResponseBody);
                            if (ResponseBody.contains("verify failed")) {
                                handler.post(() -> {
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                    String Un = prefs.getString("Username", "");
                                    String Pd = prefs.getString("Password", "");
                                    if (Un.length() * Pd.length() != 0) {
                                        Login(context, Un, Pd);
                                    } else {
                                        Toast.makeText(context, "认证失败，请重新登录", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(context, LoginActivity.class);
                                        context.startActivity(intent);
                                    }
                                });
                            } else {
                                handler.post(() -> {
                                    if (ResponseBody.contains("{\"data\":null,\"ret\":0,\"errcode\":0,\"errmsg\":\"ok\"}")) {
                                        textView.SetContentText(NewNickName);
                                        Toast.makeText(context, "昵称修改成功", Toast.LENGTH_SHORT).show();
                                        BackendUtils.ReSendUserInfo(context);
                                    } else {
                                        textView.SetContentText(ConfigHelper.GetUserBean(context).getData().getMy_info().getNick_name());
                                        Toast.makeText(context, "昵称修改失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    } else {
                        String ResponseBody = null;
                        if (response.body() != null) {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                        }
                        Log.d("后端交互日志 昵称修改失败", ResponseBody);
                        LogToFile.d("后端交互日志 昵称修改失败", ResponseBody);
                        handler.post(() -> ChangeNickName(context, NewNickName, textView));
                    }
                }
            });
        }).start();
    }

    public static Response GetAccessToken(Context context) {
        LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
        LoginResponseBean.DataDTO.MyInfoDTO infoDTO;
        try {
            if (UserBean == null) {
                return null;
            }
            infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        String a = GetHeaderValueFora();
        String[] timestamps = GetHeaderForTimeStamp();
        String Timestamp = timestamps[0];
        String LocalTimestamp = timestamps[1];
        String Nonce = GetHeaderForNonce();
        String requestbodystring;
        requestbodystring = "app_key=20460cbb2ccf1c97&app_secret=1dcc14a227a6f8d9b37792b7b053f671&app_version=" + GetwhistleVersion() + "&grant_type=client_credentials&platform=android&scope=all";
        String Sign = GetHeaderForSign(requestbodystring, Timestamp, Nonce, a);
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
        Request request = new Request.Builder()
                .url("https://api.m.dlut.edu.cn/oauth/token")
                .header("a", a)
                .header("sign", Sign)
                .header("local_timestamp", LocalTimestamp)
                .header("nonce", Nonce)
                .header("version", GetHeaderForVersion())
                .header("timestamp", Timestamp)
                .header("Host", "service.m.dlut.edu.cn")
                .header("Connection", "Keep-Alive")
                .header("Cookie2", "$Version=1")
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), requestbodystring))
                .build();//创建Request 对象
        LogToFile.d("请求方法体", request.toString());
        Response response;
        try {
            response = client.newCall(request).execute();
            LogToFile.d("请求返回", response.toString());
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    public static Response GetCourseList(Context context, String accesstoken) {
        LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
        LoginResponseBean.DataDTO.MyInfoDTO infoDTO;
        try {
            if (UserBean == null) {
                return null;
            }
            infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        String url;
        url = "https://api.m.dlut.edu.cn/lightappapi/course/get_today_course?access_token=" + accesstoken + "&app_version=" + GetwhistleVersion() + "&domain=dlut&identity=" + infoDTO.getIdentity() + "&platform=android&student_number=" + infoDTO.getStudentNumber();
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
        Request request = new Request.Builder()
                .url(url)
                .build();//创建Request 对象
        LogToFile.d("请求方法体", request.toString());
        Response response;
        try {
            response = client.newCall(request).execute();
            LogToFile.d("请求返回", response.toString());
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    public static void CheckTGT(Context context) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO;
            try {
                if (UserBean == null) {
                    return;
                }
                infoDTO = UserBean.getData().getMy_info();
                if (infoDTO == null) {
                    return;
                }
            } catch (Exception e) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=user&a=checkCasService&app_version=" + GetwhistleVersion() + "&tgtinfo=" + URLEncoder.encode(JSON.toJSONString(UserBean.getData().getTgtinfo())) + "&stu_identity=&device_type=android&platform=android&uid=0&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=" + infoDTO.getIdentity() + "&pword=" + (UserBean.getData() != null ? UserBean.getData().getPword() : null) + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&aid=" + infoDTO.getUser_id() + "&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> CheckTGT(context));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                            if (ResponseBody.contains("verify failed")) {
                                handler.post(() -> {
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                    String Un = prefs.getString("Username", "");
                                    String Pd = prefs.getString("Password", "");
                                    if (Un.length() * Pd.length() != 0) {
                                        Login(context, Un, Pd);
                                    } else {
                                        Toast.makeText(context, "认证失败，请重新登录", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(context, LoginActivity.class);
                                        context.startActivity(intent);
                                    }
                                });
                            } else {
                                handler.post(() -> {
                                    TGTRefreshResult tgtRefreshResult = JSON.parseObject(ResponseBody, TGTRefreshResult.class);
                                    TGTRefreshResult.DataDTO dataDTO = tgtRefreshResult.getData();
                                    if (dataDTO == null) {
                                        Log.d("后端交互日志", "TGT刷新失败,数据为空");
                                        LogToFile.d("后端交互日志", "TGT刷新失败,数据为空");
                                    } else {
                                        String USER_FIRST_LOGIN = dataDTO.getUSER_FIRST_LOGIN();
                                        String cas_IS_EXPIRED_PWD = dataDTO.getCas_IS_EXPIRED_PWD();
                                        String pword = dataDTO.getPword();
                                        List<LoginResponseBean.DataDTO.TgtinfoDTO> tgtinfoDTO = dataDTO.getTgtinfo();
                                        LoginResponseBean loginResponseBean = ConfigHelper.GetUserBean(context);
                                        loginResponseBean.getData().setPword(pword);
                                        loginResponseBean.getData().setTgtinfo(tgtinfoDTO);
                                        loginResponseBean.getData().setUSER_FIRST_LOGIN(USER_FIRST_LOGIN);
                                        loginResponseBean.getData().setCas_IS_EXPIRED_PWD(cas_IS_EXPIRED_PWD);
                                        ConfigHelper.SaveLoginResultToPref(JSON.toJSONString(loginResponseBean), context);
                                        Log.d("后端交互日志", "TGT刷新成功");
                                        LogToFile.d("后端交互日志", "TGT刷新成功");
                                        if (ResponseBody.contains("\"ret\":-1,")) {
                                            Log.d("后端交互日志", "密码错误,重新登陆");
                                            LogToFile.d("后端交互日志", "密码错误,重新登陆");
                                            Toast.makeText(context, "请重新登录", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(context, LoginActivity.class);
                                            context.startActivity(intent);
                                        } else {
                                            RefreshSkeyInfo(context);
                                        }
                                    }
                                });
                            }
                        }
                    } else {
                        String ResponseBody = null;
                        if (response.body() != null) {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                            LogToFile.d("请求返回", ResponseBody);
                        }
                        Log.d("后端交互日志", "TGT刷新失败" + ResponseBody);
                        LogToFile.d("后端交互日志", "TGT刷新失败" + ResponseBody);
                        handler.post(() -> CheckTGT(context));
                    }
                }
            });
        }).start();
    }

    public static void UploadNewHead(Context context, ImageView head, String HeadPath) {
        Log.d("后端交互日志 要上传的头像路径", HeadPath);
        LogToFile.d("后端交互日志 要上传的头像路径", HeadPath);
        Toast.makeText(context, "正在上传头像", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            try {
                File Header = new File(context.getExternalFilesDir(null).getAbsolutePath() + HeadPath);
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)  //其他信息
                        .addFormDataPart("name", Header.getName())
                        .addFormDataPart("filename", Header.getName(),
                                RequestBody.create(MediaType.parse("application/octet-stream"), Header))//文件
                        .build();
                Request request = new Request.Builder()
                        .url("https://store.m.dlut.edu.cn//image/upload2").post(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.e("后端交互日志 上传失败", "", e);
                        handler.post(() -> Toast.makeText(context, "头像上传出错！", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String result = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                        if (response.isSuccessful()) {
                            Log.d("后端交互日志 上传成功", result);
                            LogToFile.d("后端交互日志 上传成功", result);
                            handler.post(() -> {
                                HeadUploadResult headUploadResult = JSON.parseObject(result, HeadUploadResult.class);
                                String Headurl = null;
                                if (headUploadResult != null) {
                                    Headurl = headUploadResult.getUri();
                                }
                                Headurl = "https://store.m.dlut.edu.cn" + Headurl;
                                Log.d("后端交互日志 头像地址", Headurl);
                                LogToFile.d("后端交互日志 头像地址", Headurl);
                                SetNewHead(context, Headurl, head);
                            });
                        } else {
                            Log.d("后端交互日志 上传失败", result);
                            LogToFile.d("后端交互日志 上传失败", result);
                        }
                    }
                });
            } catch (Exception e) {
                Log.e("后端交互日志 上传失败", "", e);
                handler.post(() -> Toast.makeText(context, "头像上传出错！", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public static void UploadImage(String Path, UploadImageCommand uploadImageCommand) {
        Log.d("后端交互日志 要上传的图片路径", Path);
        LogToFile.d("后端交互日志 要上传的图片路径", Path);
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            try {
                File Header = new File(Path);
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)  //其他信息
                        .addFormDataPart("name", Header.getName())
                        .addFormDataPart("filename", Header.getName(),
                                RequestBody.create(MediaType.parse("application/octet-stream"), Header))//文件
                        .build();
                Request request = new Request.Builder()
                        .url("https://store.m.dlut.edu.cn//image/upload2").post(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Toast.makeText(uploadImageCommand.proxy.context, "出现错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        LogToFile.e("错误", e.toString());
                        uploadImageCommand.sendCancelResult();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String result = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                        if (result != null) {
                            if (response.isSuccessful() && result.contains("\"error\":0")) {
                                Log.d("后端交互日志 上传成功", result);
                                LogToFile.d("后端交互日志 上传成功", result);
                                handler.post(() -> {
                                    FileResponseBean fileResponseBean = JSON.parseObject(result, FileResponseBean.class);
                                    final String string = "https://store.m.dlut.edu.cn" +
                                            fileResponseBean.getUri();
                                    final JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("imageUrl", string);
                                    } catch (JSONException e) {
                                        Toast.makeText(uploadImageCommand.proxy.context, "出现错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                        LogToFile.e("错误", e.toString());
                                        uploadImageCommand.sendCancelResult();
                                        return;
                                    }
                                    uploadImageCommand.sendSucceedResult(jsonObject);
                                });
                            } else {
                                Log.d("后端交互日志 上传失败", result);
                                LogToFile.d("后端交互日志 上传失败", result);
                                handler.post(uploadImageCommand::sendCancelResult);
                            }
                        } else {
                            handler.post(uploadImageCommand::sendCancelResult);
                        }
                    }
                });
            } catch (Exception e) {
                Log.e("后端交互日志 上传失败", "", e);
                Toast.makeText(uploadImageCommand.proxy.context, "出现错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                LogToFile.e("错误", e.toString());
                handler.post(uploadImageCommand::sendCancelResult);
            }
        }).start();
    }

    public static void SetNewHead(Context context, String HeadUrl, ImageView head) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO;
            try {
                if (UserBean == null) {
                    return;
                }
                infoDTO = UserBean.getData().getMy_info();
                if (infoDTO == null) {
                    return;
                }
            } catch (Exception e) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=orginfo&a=setUserInfo&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&param=%7B%22photo_live%22%3A%22" + URLEncoder.encode(HeadUrl.replace("/", "\\/")) + "%22%7D&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify() + "&device_type=android&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> SetNewHead(context, HeadUrl, head));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String ResponseBody = Objects.requireNonNull(response.body()).string();
                            Log.d("后端交互日志 头像设置返回", ResponseBody);
                            LogToFile.d("后端交互日志 头像设置返回", ResponseBody);
                            if (ResponseBody.contains("verify failed")) {
                                handler.post(() -> {
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                    String Un = prefs.getString("Username", "");
                                    String Pd = prefs.getString("Password", "");
                                    if (Un.length() * Pd.length() != 0) {
                                        Login(context, Un, Pd);
                                    } else {
                                        Toast.makeText(context, "认证失败，请重新登录", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(context, LoginActivity.class);
                                        context.startActivity(intent);
                                    }
                                });
                            } else {
                                handler.post(() -> {
                                    if (ResponseBody.contains("{\"data\":null,\"ret\":0,\"errcode\":0,\"errmsg\":\"ok\"}")) {
                                        Glide.with(context).load(HeadUrl).into(head);
                                        LoginResponseBean loginResponseBean = ConfigHelper.GetUserBean(context);
                                        loginResponseBean.getData().getMy_info().setHead(HeadUrl);
                                        ConfigHelper.SaveLoginResultToPref(JSON.toJSONString(loginResponseBean), context);
                                        BackendUtils.ReSendUserInfo(context);
                                        Toast.makeText(context, "头像修改成功", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(context, "头像设置失败\n" + ResponseBody, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    } else {
                        String ResponseBody = null;
                        if (response.body() != null) {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                        }
                        Log.d("后端交互日志 头像设置失败", ResponseBody);
                        LogToFile.d("后端交互日志 头像设置失败", ResponseBody);
                        handler.post(() -> SetNewHead(context, HeadUrl, head));
                    }
                }
            });
        }).start();
    }

    public static void LoginFunInfo(Context context) throws IOException {
        LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
        if (UserBean == null) {
            throw new IOException("用户为空");
        }
        LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
        if (infoDTO == null) {
            throw new IOException("用户信息为空");
        }
        String a = GetHeaderValueFora();
        String[] timestamps = GetHeaderForTimeStamp();
        String Timestamp = timestamps[0];
        String LocalTimestamp = timestamps[1];
        String Nonce = GetHeaderForNonce();
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
        String requestbodystring = "uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&optfun=%7B%22phone_type%22%3A%22" + GetDeviceName() + "%22%2C%22student_number%22%3A%22" + infoDTO.getStudentNumber() + "%22%2C%22os_version%22%3A%22" + Build.VERSION.RELEASE + "%22%2C%22interface_name%22%3A%22m%5Cu003dstatistics%5Cu0026a%5Cu003dsetFunInfo%22%2C%22name%22%3A%22" + URLEncoder.encode(infoDTO.getName(), "UTF-8") + "%22%2C%22opt_type%22%3A%222%22%2C%22equipment_type%22%3A%22phone%22%2C%22fun_info%22%3A%22%E5%90%AF%E5%8A%A8%22%2C%22org_name%22%3A%22" + URLEncoder.encode(infoDTO.getOrg().get(0).getName(), "UTF-8") + "%22%7D&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&device_type=android&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
        String Sign = GetHeaderForSign(requestbodystring, Timestamp, Nonce, a);
        Request request = new Request.Builder()
                .header("a", a)
                .header("sign", Sign)
                .header("local_timestamp", LocalTimestamp)
                .header("nonce", Nonce)
                .header("version", GetHeaderForVersion())
                .header("timestamp", Timestamp)
                .url("https://service.m.dlut.edu.cn/whistlenew/index.php?m=statistics&a=setFunInfo")
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), requestbodystring))
                .build();
        LogToFile.d("请求方法体", request.toString());
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                Log.d("后端交互日志 每日登录返回", result);
                LogToFile.d("后端交互日志 每日登录返回", result);
            }
        });
    }

    public static void UseAppFunInfo(Context context) throws IOException {
        LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
        if (UserBean == null) {
            throw new IOException("用户为空");
        }
        LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
        if (infoDTO == null) {
            throw new IOException("用户信息为空");
        }
        String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=app&a=setAppPV&phone_type=" + GetDeviceName() + "&app_version=" + GetwhistleVersion() + "&stu_identity=&os_version=" + Build.VERSION.RELEASE + "&device_type=android&equipment_type=phone&type=1&platform=android&uid=0&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=" + infoDTO.getIdentity() + "&name=" + URLEncoder.encode(infoDTO.getName(), "UTF-8") + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&org_name=" + URLEncoder.encode(infoDTO.getOrg().get(0).getName(), "UTF-8") + "&aid=" + infoDTO.getUser_id() + "&app_id=19b32196decf419a&equipment_id=null&city_id=10";
        Request request = CommonGetRequsetBuilder(url);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String ResponseBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                Log.d("后端交互日志 APP使用返回", ResponseBody);
                LogToFile.d("后端交互日志 APP使用返回", ResponseBody);
            }
        });
    }

    @SuppressWarnings("ALL")
    public static void CommentAppFunInfo(Context context, String appid, String comment, int score, AppDetailActivity appDetailActivity) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=comment&a=add&app_version=" + GetwhistleVersion() + "&stu_identity=&device_type=android&platform=android&score=" + String.valueOf(score) + "&uid=0&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=" + infoDTO.getIdentity() + "&verify=" + URLEncoder.encode(UserBean.getData().getVerify()) + "&comment=" + URLEncoder.encode(comment) + "&app_id=" + appid + "&aid=" + infoDTO.getUser_id() + "&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> RefreshSkeyInfo(context));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String ResponseBody = null;
                    try {
                        ResponseBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogToFile.e("错误", e.toString());
                    }
                    String finalResponseBody = ResponseBody;
                    handler.post(() -> {
                        if (finalResponseBody != null && !finalResponseBody.contains("verify failed")) {
                            ScoreFunResultbean scoreFunResultbean = JSON.parseObject(finalResponseBody, ScoreFunResultbean.class);
                            if (scoreFunResultbean.getRet() != -1) {
                                LogToFile.d("应用评论", finalResponseBody);
                                if (scoreFunResultbean.getData().getAdd_score() != -1) {
                                    CenterToast.makeText(context, "添加评论成功！\n积分+" + scoreFunResultbean.getData().getAdd_score() + "\n现有积分：" + scoreFunResultbean.getData().getSum(), Toast.LENGTH_LONG).show();
                                    BackendUtils.GetScore(context);
                                } else {
                                    CenterToast.makeText(context, "添加评论成功！", Toast.LENGTH_LONG).show();
                                }
                                BackendUtils.GetAppCommentNow(context, appid, appDetailActivity, appDetailActivity.findViewById(R.id.comment_list));
                            } else {
                                LogToFile.e("应用评论", finalResponseBody);
                                CenterToast.makeText(context, scoreFunResultbean.getErrmsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }).start();
    }

    public static void AddAppFunInfo(Context context) throws IOException {
        LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
        if (UserBean == null) {
            throw new IOException("用户为空");
        }
        LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
        if (infoDTO == null) {
            throw new IOException("用户信息为空");
        }
        String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=collection&a=add&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&device_type=android&app_id=53fef93fd4364221&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
        Request request = CommonGetRequsetBuilder(url);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String ResponseBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                Log.d("后端交互日志 添加返回", ResponseBody);
                LogToFile.d("后端交互日志 添加返回", ResponseBody);
            }
        });
    }

    public static void RemoveAppFunInfo(Context context) throws IOException {
        LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
        if (UserBean == null) {
            throw new IOException("用户为空");
        }
        LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
        if (infoDTO == null) {
            throw new IOException("用户信息为空");
        }
        String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=collection&a=remove&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&device_type=android&app_id=53fef93fd4364221&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
        Request request = CommonGetRequsetBuilder(url);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String ResponseBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                Log.d("后端交互日志 移除返回", ResponseBody);
                LogToFile.d("后端交互日志 移除返回", ResponseBody);
            }
        });
    }

    public static void ShareFunInfo(Context context) throws IOException {
        LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
        if (UserBean == null) {
            throw new IOException("用户为空");
        }
        LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
        if (infoDTO == null) {
            throw new IOException("用户信息为空");
        }
        String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=score&a=setTaskScore&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&device_type=android&task_type=6&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
        Request request = CommonGetRequsetBuilder(url);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String ResponseBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                Log.d("后端交互日志 分享返回", ResponseBody);
                LogToFile.d("后端交互日志 分享返回", ResponseBody);
            }
        });
    }

    public static void AddCardFunInfo(Context context) throws IOException {
        LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
        if (UserBean == null) {
            throw new IOException("用户为空");
        }
        LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
        if (infoDTO == null) {
            throw new IOException("用户信息为空");
        }
        String a = GetHeaderValueFora();
        String[] timestamps = GetHeaderForTimeStamp();
        String Timestamp = timestamps[0];
        String LocalTimestamp = timestamps[1];
        String Nonce = GetHeaderForNonce();
        String requestbodystring;
        requestbodystring = "app_version=" + GetwhistleVersion() + "&stu_identity=&device_type=android&sort=-7&platform=android&uid=0&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&app_id=50ac974f2840944c&aid=" + infoDTO.getUser_id() + "&city_id=10";
        String Sign = GetHeaderForSign(requestbodystring, Timestamp, Nonce, a);
        Request request = new Request.Builder()
                .header("a", a)
                .header("sign", Sign)
                .header("local_timestamp", LocalTimestamp)
                .header("nonce", Nonce)
                .header("version", GetHeaderForVersion())
                .header("timestamp", Timestamp)
                .url("https://service.m.dlut.edu.cn/whistlenew/index.php?m=app&a=addCard")
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), requestbodystring))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String ResponseBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                Log.d("后端交互日志 添加卡片返回", ResponseBody);
                LogToFile.d("后端交互日志 添加卡片返回", ResponseBody);
            }
        });
    }

    public static void RemoveCardFunInfo(Context context) throws IOException {
        LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
        if (UserBean == null) {
            throw new IOException("用户为空");
        }
        LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
        if (infoDTO == null) {
            throw new IOException("用户信息为空");
        }
        String a = GetHeaderValueFora();
        String[] timestamps = GetHeaderForTimeStamp();
        String Timestamp = timestamps[0];
        String LocalTimestamp = timestamps[1];
        String Nonce = GetHeaderForNonce();
        String requestbodystring;
        requestbodystring = "uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&device_type=android&app_id=50ac974f2840944c&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
        String Sign = GetHeaderForSign(requestbodystring, Timestamp, Nonce, a);
        Request request = new Request.Builder()
                .header("a", a)
                .header("sign", Sign)
                .header("local_timestamp", LocalTimestamp)
                .header("nonce", Nonce)
                .header("version", GetHeaderForVersion())
                .header("timestamp", Timestamp)
                .url("https://service.m.dlut.edu.cn/whistlenew/index.php?m=app&a=delCard")
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), requestbodystring))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String ResponseBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                Log.d("后端交互日志 移除卡片返回", ResponseBody);
                LogToFile.d("后端交互日志 移除卡片返回", ResponseBody);
            }
        });
    }

    public static void RefreshSkeyInfo(Context context) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=statistics&a=getSkeyInfo&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify() + "&device_type=android&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> RefreshSkeyInfo(context));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String ResponseBody = null;
                    try {
                        ResponseBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogToFile.e("错误", e.toString());
                    }
                    String finalResponseBody = ResponseBody;
                    handler.post(() -> {
                        if (finalResponseBody != null && !finalResponseBody.contains("verify failed")) {
                            SkeyRefreshResult skeyRefreshResult = JSON.parseObject(finalResponseBody, SkeyRefreshResult.class);
                            if (skeyRefreshResult.getData() != null) {
                                String skey = skeyRefreshResult.getData().getSkey();
                                if (skey != null) {
                                    LoginResponseBean loginResponseBean = ConfigHelper.GetUserBean(context);
                                    loginResponseBean.getData().getMy_info().setSkey(skey);
                                    ConfigHelper.SaveLoginResultToPref(JSON.toJSONString(loginResponseBean), context);
                                    Log.d("后端交互日志", "刷新Skey成功");
                                    LogToFile.d("后端交互日志", "刷新Skey成功");
                                    GetScore(context);
                                }
                            }
                        }
                    });
                }
            });
        }).start();
    }

    public static void GetAppConfigNow(Context context, String appid, AppDetailActivity appDetailActivity) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=app&a=detail&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify() + "&device_type=android&app_id=" + appid + "&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> RefreshSkeyInfo(context));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String ResponseBody = null;
                    try {
                        ResponseBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogToFile.e("错误", e.toString());
                    }
                    String finalResponseBody = ResponseBody;
                    handler.post(() -> {
                        if (finalResponseBody != null && !finalResponseBody.contains("verify failed")) {
                            AppDetailNowResultBean appDetailNowResultBean = JSON.parseObject(finalResponseBody, AppDetailNowResultBean.class);
                            if (appDetailNowResultBean.getRet() != -1) {
                                RatingBar tdr = appDetailActivity.findViewById(R.id.app_rating);
                                tdr.setVisibility(View.VISIBLE);
                                tdr.setRating(appDetailNowResultBean.getData().getScore().getAverage().floatValue());
                                TextView tdh = appDetailActivity.findViewById(R.id.app_hot);
                                tdh.setText(String.valueOf(appDetailNowResultBean.getData().getPopularity()));
                                RelativeLayout detail = appDetailActivity.findViewById(R.id.detail);
                                LinearLayout comment_area = appDetailActivity.findViewById(R.id.comment_area);

                                RadioButton app_instruction = appDetailActivity.findViewById(R.id.app_instruction);
                                RadioButton app_user_comment = appDetailActivity.findViewById(R.id.app_user_comment);
                                if (comment_area.getVisibility() != View.VISIBLE) {
                                    app_instruction.setChecked(true);
                                    app_user_comment.setChecked(false);
                                }
                                app_instruction.setOnClickListener(view -> {
                                    detail.setVisibility(View.VISIBLE);
                                    comment_area.setVisibility(View.GONE);
                                });
                                app_user_comment.setOnClickListener(view -> {
                                    detail.setVisibility(View.GONE);
                                    comment_area.setVisibility(View.VISIBLE);
                                });
                                RecyclerView comment_list = appDetailActivity.findViewById(R.id.comment_list);
                                app_user_comment.setEnabled(true);
                                GetAppCommentNow(context, appid, appDetailActivity, comment_list);
                            } else {
                                RadioButton app_user_comment = appDetailActivity.findViewById(R.id.app_user_comment);
                                app_user_comment.setEnabled(true);
                                app_user_comment.setOnClickListener(view -> CenterToast.makeText(context, "抱歉，该应用暂不支持评论", Toast.LENGTH_SHORT).show());
                                CenterToast.makeText(context, appDetailNowResultBean.getErrmsg(), Toast.LENGTH_SHORT).show();
                                LogToFile.e("请求应用信息", finalResponseBody);
                            }
                        }
                    });
                }
            });
        }).start();
    }

    @SuppressLint("SetTextI18n")
    public static void GetAppCommentNow(Context context, String appid, AppDetailActivity appDetailActivity, RecyclerView comment_list) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=comment&a=list&offset=0&app_version=" + GetwhistleVersion() + "&stu_identity=&device_type=android&platform=android&uid=0&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=" + infoDTO.getIdentity() + "&limit=100&verify=" + UserBean.getData().getVerify() + "&device_type=android&app_id=" + appid + "&aid=" + infoDTO.getUser_id() + "&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> RefreshSkeyInfo(context));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String ResponseBody = null;
                    try {
                        ResponseBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogToFile.e("错误", e.toString());
                    }
                    String finalResponseBody = ResponseBody;
                    handler.post(() -> {
                        if (finalResponseBody != null && !finalResponseBody.contains("verify failed")) {
                            CommentDetailResultBean commentDetailResultBean = JSON.parseObject(finalResponseBody, CommentDetailResultBean.class);
                            TextView comment = appDetailActivity.findViewById(R.id.comment);
                            if (commentDetailResultBean.getRet() != -1) {
                                comment_list.setLayoutManager(new LinearLayoutManager(context));
                                CommentDetailAdapter commentDetailAdapter = new CommentDetailAdapter(context, commentDetailResultBean.getData().getList_data());
                                comment_list.setAdapter(commentDetailAdapter);
                                comment_list.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));
                                if (commentDetailResultBean.getData().getIscomment() == 0) {
                                    comment.setOnClickListener(view -> appDetailActivity.CommentApp());
                                } else {
                                    comment.setText("已评论");
                                    comment.setEnabled(false);
                                }
                                RatingBar app_average = appDetailActivity.findViewById(R.id.app_average);
                                app_average.setRating(commentDetailResultBean.getData().getStatistics().getAverage().floatValue());
                                TextView app_rating_times = appDetailActivity.findViewById(R.id.app_rating_times);
                                app_rating_times.setText("总评分人数" + commentDetailResultBean.getData().getStatistics().getTotal() + "人");
                                double total = Double.parseDouble(commentDetailResultBean.getData().getTotal());
                                JSONObject jsonObject = null;
                                int $1 = 0;
                                int $2 = 0;
                                int $3 = 0;
                                int $4 = 0;
                                int $5 = 0;
                                try {
                                    jsonObject = new JSONObject(finalResponseBody);
                                    jsonObject = (JSONObject) jsonObject.get("data");
                                    jsonObject = (JSONObject) jsonObject.get("statistics");
                                    jsonObject = (JSONObject) jsonObject.get("score");
                                    $1 = jsonObject.getInt("1");
                                    $2 = jsonObject.getInt("2");
                                    $3 = jsonObject.getInt("3");
                                    $4 = jsonObject.getInt("4");
                                    $5 = jsonObject.getInt("5");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    LogToFile.e("错误", e.toString());
                                }
                                ProgressBar app_score_5star_parent = appDetailActivity.findViewById(R.id.app_score_5star_parent);
                                app_score_5star_parent.setProgress((int) (($5 / total) * 1000000), true);
                                ProgressBar app_score_4star_parent = appDetailActivity.findViewById(R.id.app_score_4star_parent);
                                app_score_4star_parent.setProgress((int) (($4 / total) * 1000000), true);
                                ProgressBar app_score_3star_parent = appDetailActivity.findViewById(R.id.app_score_3star_parent);
                                app_score_3star_parent.setProgress((int) (($3 / total) * 1000000), true);
                                ProgressBar app_score_2star_parent = appDetailActivity.findViewById(R.id.app_score_2star_parent);
                                app_score_2star_parent.setProgress((int) (($2 / total) * 1000000), true);
                                ProgressBar app_score_1star_parent = appDetailActivity.findViewById(R.id.app_score_1star_parent);
                                app_score_1star_parent.setProgress((int) (($1 / total) * 1000000), true);
                            } else {
                                comment.setText("暂时无法评论");
                                comment.setEnabled(false);
                                LogToFile.e("请求应用评论", finalResponseBody);
                                CenterToast.makeText(context, commentDetailResultBean.getErrmsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }).start();
    }

    public static void GetMsgDetailInfo(Context context, String msgid) {
        LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
        if (UserBean == null) {
            return;
        }
        LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
        if (infoDTO == null) {
            return;
        }
        String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=orginfo&a=getMsgInfo&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify() + "&device_type=android&msg_id=" + msgid + "&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
        Request request = CommonGetRequsetBuilder(url);
        LogToFile.d("请求方法体", request.toString());
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                LogToFile.e("错误", e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String ResponseBody;
                try {
                    ResponseBody = response != null ? response.body() != null ? Objects.requireNonNull(response.body()).string() : null : null;
                } catch (IOException e) {
                    GetMsgDetailInfo(context, msgid);
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                    return;
                }
                Log.d("后端交互日志 消息内容返回", ResponseBody);
                LogToFile.d("后端交互日志 消息内容返回", ResponseBody);
                String result = ResponseBody;
                if (result == null) {
                    GetMsgDetailInfo(context, msgid);
                    return;
                }
                if (result.contains("verify failed")) {
                    GetMsgDetailInfo(context, msgid);
                } else {
                    MsgInfoResult msgInfoResult = JSON.parseObject(result, MsgInfoResult.class);
                    String str = msgInfoResult.getData().getMsg_info().get(0).getMsg_content();
                    try {
                        str = URLDecoder.decode(str, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        LogToFile.e("错误", e.toString());
                    }
                    if (str.startsWith("[") && str.endsWith("]")) {
                        DLUTNoticeContentBean dlutNoticeContentBean = JSON.parseArray(str, DLUTNoticeContentBean.class).get(0);
                        str = JSON.toJSONString(dlutNoticeContentBean);
                    }
                    MsgHistoryManager msgHistoryManager = new MsgHistoryManager(context);
                    NotificationHistoryDataBaseBean original = msgHistoryManager.query(msgid);
                    if (original != null) {
                        msgHistoryManager.update(msgid, str, msgInfoResult.getData().getMsg_info().get(0).getApp_id());
                        Log.d("后端交互日志 获取消息内容", "成功");
                        LogToFile.d("后端交互日志 获取消息内容", "成功");
                    } else {
                        Log.d("后端交互日志 获取消息内容", "失败");
                        LogToFile.d("后端交互日志 获取消息内容", "失败");
                    }
                }
            }
        });
    }

    public static void ParentBindTestFuction(Context context, String phone) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String a = GetHeaderValueFora();
            String[] timestamps = GetHeaderForTimeStamp();
            String Timestamp = timestamps[0];
            String LocalTimestamp = timestamps[1];
            String Nonce = GetHeaderForNonce();
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=user&a=parentInviteDebug";
            String PostBody = "stu_identity=" + infoDTO.getIdentity() + "&parent_bind=" + phone + "&device_type=android&type=1&platform=android&uid=0&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=student&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&aid=" + infoDTO.getUser_id() + "&city_id=10";
            Log.d("test", PostBody);
            LogToFile.d("test", PostBody);
            String Sign = GetHeaderForSign(PostBody, Timestamp, Nonce, a);
            Request request = new Request.Builder()
                    .url(url)
                    .header("a", a)
                    .header("sign", Sign)
                    .header("local_timestamp", LocalTimestamp)
                    .header("nonce", Nonce)
                    .header("version", GetHeaderForVersion())
                    .header("timestamp", Timestamp)
                    .header("Host", "service.m.dlut.edu.cn")
                    .header("Connection", "Keep-Alive")
                    .header("Cookie2", "$Version=1")
                    .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), PostBody))
                    .build();//创建Request 对象
            LogToFile.d("请求方法体", request.toString());
            Log.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String ResponseBody;
                    try {
                        ResponseBody = response != null ? response.body() != null ? Objects.requireNonNull(response.body()).string() : null : null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogToFile.e("错误", e.toString());
                        return;
                    }
                    Log.d("测试内容返回", ResponseBody);
                    LogToFile.d("测试内容返回", ResponseBody);
                    handler.post(() -> Toast.makeText(context, ResponseBody, Toast.LENGTH_LONG).show());
                }
            });
        }).start();
    }

    public static void ParentReBindTestFuction(Context context, String phone) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String a = GetHeaderValueFora();
            String[] timestamps = GetHeaderForTimeStamp();
            String Timestamp = timestamps[0];
            String LocalTimestamp = timestamps[1];
            String Nonce = GetHeaderForNonce();
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=user&a=parentInviteAdd";
            String PostBody = "stu_identity=" + infoDTO.getIdentity() + "&parent_bind=" + phone + "&device_type=android&type=1&platform=android&uid=0&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=student&verify=" + UserBean.getData().getVerify().replace(":", "%3A") + "&aid=" + infoDTO.getUser_id() + "&city_id=10";
            Log.d("test", PostBody);
            LogToFile.d("test", PostBody);
            String Sign = GetHeaderForSign(PostBody, Timestamp, Nonce, a);
            Request request = new Request.Builder()
                    .url(url)
                    .header("a", a)
                    .header("sign", Sign)
                    .header("local_timestamp", LocalTimestamp)
                    .header("nonce", Nonce)
                    .header("version", GetHeaderForVersion())
                    .header("timestamp", Timestamp)
                    .header("Host", "service.m.dlut.edu.cn")
                    .header("Connection", "Keep-Alive")
                    .header("Cookie2", "$Version=1")
                    .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), PostBody))
                    .build();//创建Request 对象
            LogToFile.d("请求方法体", request.toString());
            Log.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String ResponseBody;
                    try {
                        ResponseBody = response != null ? response.body() != null ? Objects.requireNonNull(response.body()).string() : null : null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogToFile.e("错误", e.toString());
                        return;
                    }
                    Log.d("测试内容返回", ResponseBody);
                    LogToFile.d("测试内容返回", ResponseBody);
                    handler.post(() -> Toast.makeText(context, ResponseBody, Toast.LENGTH_LONG).show());
                }
            });
        }).start();
    }

    public static void GetMsgNewDetailInfo(Context context, String msgid) {
        LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
        if (UserBean == null) {
            return;
        }
        LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
        if (infoDTO == null) {
            return;
        }
        String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=orginfo&a=getMsgInfo&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify() + "&device_type=android&msg_id=" + msgid + "&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
        Request request = CommonGetRequsetBuilder(url);
        LogToFile.d("请求方法体", request.toString());
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                LogToFile.e("错误", e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String ResponseBody;
                try {
                    ResponseBody = response != null ? response.body() != null ? Objects.requireNonNull(response.body()).string() : null : null;
                } catch (IOException e) {
                    GetMsgNewDetailInfo(context, msgid);
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                    return;
                }
                Log.d("后端交互日志 消息内容返回", ResponseBody);
                LogToFile.d("后端交互日志 消息内容返回", ResponseBody);
                String result = ResponseBody;
                if (result.contains("verify failed")) {
                    GetMsgNewDetailInfo(context, msgid);
                } else {
                    NewMsgInfo msgInfoResult = JSON.parseObject(result, NewMsgInfo.class);
                    String str;
                    try {
                        str = URLDecoder.decode(msgInfoResult.getData().getMsgInfo().get(0).getMsg_content(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        LogToFile.e("错误", e.toString());
                        return;
                    }
                    if (str.startsWith("[") && str.endsWith("]")) {
                        DLUTNoticeContentBean dlutNoticeContentBean = JSON.parseArray(str, DLUTNoticeContentBean.class).get(0);
                        str = JSON.toJSONString(dlutNoticeContentBean);
                    }
                    MsgHistoryManager msgHistoryManager = new MsgHistoryManager(context);
                    try {
                        msgHistoryManager.insert(msgInfoResult.getData().getMsgInfo().get(0).getMsg_id(), msgInfoResult.getData().getMsgInfo().get(0).getCreate_time(), msgInfoResult.getData().getMsgInfo().get(0).getApp_id(), 0, msgInfoResult.getData().getMsgInfo().get(0).getTitle(), str);
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                        prefs.edit().putBoolean("unread", true).putInt("unreadcount", prefs.getInt("unreadcount", 0) + 1).apply();
                        Intent intent = new Intent("com.Shirai_Kuroko.DLUTMobile.ReceivedNew");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        DLUTNoticeContentBean dlutNoticeContentBean = JSON.parseObject(str, DLUTNoticeContentBean.class);
                        Intent intent1 = new Intent(context, PureBrowserActivity.class);
                        intent1.putExtra("Name", "");
                        intent1.putExtra("Url", dlutNoticeContentBean.getUrl());
                        intent1.putExtra("MsgID", msgid);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //奶奶滴，msgid之前写进intent里了，终于找到不更新已读状态的问题所在了
                        @SuppressLint("UnspecifiedImmutableFlag")
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) (Math.random() * 200), intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                        new NotificationHelper().Notify(context, pendingIntent, "1919810", "消息通知", URLDecoder.decode(msgInfoResult.getData().getMsgInfo().get(0).getTitle()), Integer.parseInt(msgid));
                    } catch (Exception e) {
                        msgHistoryManager.closedb();
                        Log.e("后端交互日志", "GetMsgNewDetailInfo: ", e);
                    }
                }
            }
        });
    }

    public static synchronized void GetRank(Context context, String type, RankAdapter rankAdapter, LoadingView loadingView) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=score&a=pointRanking&offset=0&app_version=" + GetwhistleVersion() + "&stu_identity=&device_type=android&type=" + type + "&platform=android&uid=0&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=" + infoDTO.getIdentity() + "&limit=30&verify=" + UserBean.getData().getVerify() + "&aid=" + infoDTO.getUser_id() + "&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                    handler.post(() -> GetRank(context, type, rankAdapter, loadingView));
                }

                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()){
                        String ResponseBody = null;
                        try {
                            ResponseBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                        } catch (IOException e) {
                            e.printStackTrace();
                            LogToFile.e("错误", e.toString());
                        }
                        String result = ResponseBody;
                        if (result == null) {
                            handler.post(() -> GetRank(context, type, rankAdapter, loadingView));
                            return;
                        }
                        if (!result.contains("verify failed")) {
                            Log.d("后端交互日志 分数排名返回", result);
                            com.Shirai_Kuroko.DLUTMobile.Entities.RankResult rankResult = JSON.parseObject(result, com.Shirai_Kuroko.DLUTMobile.Entities.RankResult.class);
                            List<com.Shirai_Kuroko.DLUTMobile.Entities.RankResult.DataDTO.ListDTO> dtoArrayList = rankResult.getData().getList();
                            handler.post(() -> {
                                rankAdapter.datarefresh(dtoArrayList);
                                loadingView.dismiss();
                            });
                            LogToFile.d("后端交互日志 分数排名返回", result);
                        }
                    }
                }
            });
        }).start();
    }


    public static synchronized void GetScoreDetail(Context context, String type, ScoreDetailAdapter scoreDetailAdapter, LoadingView loadingView) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=score&a=getUserPointsDetail&offset=0&app_version=" + GetwhistleVersion() + "&stu_identity=&device_type=android&type=" + type + "&platform=android&uid=0&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=" + infoDTO.getIdentity() + "&limit=100&verify=" + UserBean.getData().getVerify() + "&aid=" + infoDTO.getUser_id() + "&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            Log.d("请求方法体", request.toString());
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                    handler.post(() -> GetScoreDetail(context, type, scoreDetailAdapter, loadingView));
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    String ResponseBody = null;
                    try {
                        ResponseBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogToFile.e("错误", e.toString());
                    }
                    String result = ResponseBody;
                    if (result == null) {
                        handler.post(() -> GetScoreDetail(context, type, scoreDetailAdapter, loadingView));
                        return;
                    }
                    if (!result.contains("verify failed")) {
                        com.Shirai_Kuroko.DLUTMobile.Entities.ScoreDetailResult Scoreresult = JSON.parseObject(result, com.Shirai_Kuroko.DLUTMobile.Entities.ScoreDetailResult.class);
                        List<ScoreDetailResult.DataDTO.ListDTO> dtoArrayList = Scoreresult.getData().getList();
                        handler.post(() -> {
                            scoreDetailAdapter.datarefresh(dtoArrayList);
                            loadingView.dismiss();
                        });
                        Log.d("后端交互日志 分数明细返回", JSON.toJSONString(dtoArrayList));
                        LogToFile.d("后端交互日志 分数明细返回", JSON.toJSONString(dtoArrayList));
                    }
                }
            });
        }).start();
    }

    public static synchronized void GetExchangeRecord(Context context, String type, ExchangeRecordAdapter exchangeRecordAdapter, LoadingView loadingView) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=score&a=exchangeDetail&offset=0&app_version=" + GetwhistleVersion() + "&stu_identity=&device_type=android&type=" + type + "&platform=android&uid=0&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=" + infoDTO.getIdentity() + "&limit=100&verify=" + UserBean.getData().getVerify() + "&aid=" + infoDTO.getUser_id() + "&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                    handler.post(() -> GetExchangeRecord(context, type, exchangeRecordAdapter, loadingView));
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    String ResponseBody = null;
                    try {
                        ResponseBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogToFile.e("错误", e.toString());
                    }
                    String result = ResponseBody;
                    if (result == null) {
                        handler.post(() -> GetExchangeRecord(context, type, exchangeRecordAdapter, loadingView));
                        return;
                    }
                    if (!result.contains("verify failed")) {
                        com.Shirai_Kuroko.DLUTMobile.Entities.ExchangeRecordResult ExchangeRecordresult = JSON.parseObject(result, com.Shirai_Kuroko.DLUTMobile.Entities.ExchangeRecordResult.class);
                        List<ExchangeRecordResult.DataDTO.ListDTO> dtoArrayList = ExchangeRecordresult.getData().getList();
                        handler.post(() -> {
                            exchangeRecordAdapter.datarefresh(dtoArrayList);
                            loadingView.dismiss();
                        });
                        Log.d("后端交互日志 兑换明细返回", JSON.toJSONString(dtoArrayList));
                        LogToFile.d("后端交互日志 兑换明细返回", JSON.toJSONString(dtoArrayList));
                    }
                }
            });
        }).start();
    }

    public static synchronized void GetGiftList(Context context, PresentListAdapter presentListAdapter, LoadingView loadingView) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=score&a=getPresentList&offset=0&app_version=" + GetwhistleVersion() + "&stu_identity=&device_type=android&store_type=0&type=0&platform=android&points_range=0&uid=0&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=" + infoDTO.getIdentity() + "&limit=100&order_by=1&verify=" + UserBean.getData().getVerify() + "&aid=" + infoDTO.getUser_id() + "&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> GetGiftList(context, presentListAdapter, loadingView));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    String ResponseBody;
                    if (response.body() != null) {
                        try {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                        } catch (IOException e) {
                            handler.post(() -> GetGiftList(context, presentListAdapter, loadingView));
                            e.printStackTrace();
                            LogToFile.e("错误", e.toString());
                            return;
                        }
                        String result = ResponseBody;
                        if (!result.contains("verify failed")) {
                            PresentListResult ListResult = JSON.parseObject(result, PresentListResult.class);
                            List<PresentListResult.DataDTO.ListDTO> dtoArrayList = ListResult.getData().getList();
                            handler.post(() -> {
                                presentListAdapter.datarefresh(dtoArrayList);
                                loadingView.dismiss();
                            });
                            Log.d("后端交互日志 礼物明细返回", JSON.toJSONString(dtoArrayList));
                            LogToFile.d("后端交互日志 礼物明细返回", JSON.toJSONString(dtoArrayList));
                        }
                    }
                }
            });
        }).start();
    }

    public static synchronized void cancelGiftExchange(Context context, long id) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=score&a=cancelPresentStatus&app_version=" + GetwhistleVersion() + "&stu_identity=&device_type=android&platform=android&uid=0&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify() + "&id=" + id + "&aid=" + infoDTO.getUser_id() + "&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> cancelGiftExchange(context, id));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    String ResponseBody;
                    if (response.body() != null) {
                        try {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                        } catch (IOException e) {
                            handler.post(() -> cancelGiftExchange(context, id));
                            e.printStackTrace();
                            LogToFile.e("错误", e.toString());
                            return;
                        }
                        String result = ResponseBody;
                        if (!result.contains("verify failed")) {
                            ResponseErrorBean responseError = JSON.parseObject(result,ResponseErrorBean.class);
                            Log.d("后端交互日志 取消兑换返回", JSON.toJSONString(responseError));
                            LogToFile.d("后端交互日志 取消兑换返回", JSON.toJSONString(responseError));
                            if (responseError.getErrcode()==0){
                                handler.post(()->Toast.makeText(context,"取消兑换成功，请一小时后再次查看！",Toast.LENGTH_LONG).show());
                            }
                        }
                    }
                }
            });
        }).start();
    }

    public static synchronized void GiftExchange(Context context, int present_id) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=score&a=exchangePresent&present_id="+present_id+"&app_version=" + GetwhistleVersion() + "&stu_identity=&device_type=android&platform=android&uid=0&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify() + "&aid=" + infoDTO.getUser_id() + "&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> GiftExchange(context, present_id));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    String ResponseBody;
                    if (response.body() != null) {
                        try {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                        } catch (IOException e) {
                            handler.post(() -> GiftExchange(context, present_id));
                            e.printStackTrace();
                            LogToFile.e("错误", e.toString());
                            return;
                        }
                        String result = ResponseBody;
                        if (!result.contains("verify failed")) {
                            Log.d("后端交互日志 兑换返回", result);
                            LogToFile.d("后端交互日志 兑换返回", result);
                            GiftExchangeResponse giftExchangeResponse = JSON.parseObject(result,GiftExchangeResponse.class);
                            if (giftExchangeResponse.getErrcode()==0){
                                handler.post(()-> {
                                    Toast.makeText(context, "兑换成功！", Toast.LENGTH_LONG).show();
                                    ConfigHelper.SetIntros(context,"ExchangeIntro",giftExchangeResponse.getData().getPointsConfig().getGetExplainSet());
                                    ConfigHelper.SetIntros(context,"ScoreIntro",giftExchangeResponse.getData().getPointsConfig().getPointsExplainSet());
                                    Intent intent = new Intent(context, GiftExchangeActivity.class);
                                    intent.putExtra("data", JSON.toJSONString(giftExchangeResponse.getData().getList()));
                                    context.startActivity(intent);
                                });
                            }else {
                                handler.post(()-> {
                                    Toast.makeText(context, "兑换失败："+giftExchangeResponse.getErrmsg(), Toast.LENGTH_LONG).show();
                                });
                            }
                        }
                    }
                }
            });
        }).start();
    }

    public static synchronized void SetGiftExchangeHeaderClickListener(GiftExchangeActivity context, long id) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=score&a=getPresentDetail&app_version=" + GetwhistleVersion() + "&stu_identity=&device_type=android&platform=android&uid=0&student_number=" + infoDTO.getStudentNumber() + "&school=dlut&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify() + "&present_id=" + id + "&aid=" + infoDTO.getUser_id() + "&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> cancelGiftExchange(context, id));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    String ResponseBody;
                    if (response.body() != null) {
                        try {
                            ResponseBody = Objects.requireNonNull(response.body()).string();
                        } catch (IOException e) {
                            handler.post(() -> cancelGiftExchange(context, id));
                            e.printStackTrace();
                            LogToFile.e("错误", e.toString());
                            return;
                        }
                        String result = ResponseBody;
                        if (!result.contains("verify failed")) {
                            GiftDetailResponse giftDetailResponse = JSON.parseObject(result,GiftDetailResponse.class);
                            Log.d("后端交互日志 礼物信息返回", JSON.toJSONString(giftDetailResponse));
                            LogToFile.d("后端交互日志 礼物信息返回", JSON.toJSONString(giftDetailResponse));
                            if (giftDetailResponse.getErrcode()==0){
                                if (giftDetailResponse.getData().getDetail().size()>0)
                                {
                                    handler.post(()-> context.findViewById(R.id.exchange_gift_header).setOnClickListener(view -> {
                                        Intent intent = new Intent(context, GiftDetailActivity.class);
                                        intent.putExtra("GiftObject", JSON.toJSONString(giftDetailResponse.getData().getDetail().get(0)));
                                        context.startActivity(intent);
                                    }));
                                }
                            }
                        }
                    }
                }
            });
        }).start();
    }

    public static synchronized void QRLogin(Context context, String UUID) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=user&a=scanQRStatusSo&uid=0&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&uuid_url=" + URLEncoder.encode(UUID) + "&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify() + "&device_type=android&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                    handler.post(() -> QRLogin(context, UUID));
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    String ResponseBody = null;
                    try {
                        ResponseBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogToFile.e("错误", e.toString());
                    }
                    String result = ResponseBody;
                    if (result == null) {
                        handler.post(() -> QRLogin(context, UUID));
                        return;
                    }
                    if (!result.contains("verify failed")) {
                        handler.post(() -> {
                            if (result.contains("\\u4e8c\\u7ef4\\u7801\\u5df2\\u7ecf\\u5931\\u6548")) {
                                Toast.makeText(context, "二维码已失效", Toast.LENGTH_SHORT).show();
                                Activity activity = (Activity) context;
                                activity.finish();
                            } else {
                                Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                                Activity activity = (Activity) context;
                                activity.finish();
                            }
                            Log.d("扫码登录结果", result);
                            LogToFile.d("扫码登录结果", result);
                        });
                    }
                }
            });
        }).start();
    }

    public static synchronized void ApiQRPreLogin(Context context, String whistle_info) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=user&a=scanQRStatus&uid=0&whistle_info=" + whistle_info + "&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify() + "&device_type=android&aid=" + infoDTO.getUser_id() + "&platform=android&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                    handler.post(() -> ApiQRPreLogin(context, whistle_info));
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    String ResponseBody = null;
                    try {
                        ResponseBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogToFile.e("错误", e.toString());
                    }
                    String result = ResponseBody;
                    if (result == null) {
                        handler.post(() -> ApiQRPreLogin(context, whistle_info));
                        return;
                    }
                    if (!result.contains("verify failed")) {
                        handler.post(() -> {
                            if (result.contains("\\u4e8c\\u7ef4\\u7801\\u5df2\\u7ecf\\u5931\\u6548")) {
                                Toast.makeText(context, "二维码已失效", Toast.LENGTH_SHORT).show();
                                Activity activity = (Activity) context;
                                activity.finish();
                            } else {
                                if (result.contains("{\"data\":[],\"ret\":0,\"errcode\":0,\"errmsg\":\"ok\"}")) {
                                    ApiQRLogin(context, whistle_info);
                                } else {
                                    if (!result.contains("No Response")) {
                                        Toast.makeText(context, result.split("data\":")[1].split(",\"ret\"")[0], Toast.LENGTH_SHORT).show();
                                    }
                                    Activity activity = (Activity) context;
                                    activity.finish();
                                }
                            }
                            Log.d("扫码登录结果", result);
                        });
                    }
                }
            });
        }).start();
    }

    public static synchronized void ApiQRLogin(Context context, String whistle_info) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            LoginResponseBean UserBean = ConfigHelper.GetUserBean(context);
            if (UserBean == null) {
                return;
            }
            LoginResponseBean.DataDTO.MyInfoDTO infoDTO = UserBean.getData().getMy_info();
            if (infoDTO == null) {
                return;
            }
            String url = "https://service.m.dlut.edu.cn/whistlenew/index.php?m=user&a=setWebScanQRStatus&uid=0&whistle_info=" + whistle_info + "&student_number=" + infoDTO.getStudentNumber() + "&app_version=" + GetwhistleVersion() + "&school=dlut&stu_identity=&identity=" + infoDTO.getIdentity() + "&verify=" + UserBean.getData().getVerify() + "&device_type=android&aid=" + infoDTO.getUser_id() + "&platform=android&type=login&city_id=10";
            Request request = CommonGetRequsetBuilder(url);
            LogToFile.d("请求方法体", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    handler.post(() -> ApiQRLogin(context, whistle_info));
                    e.printStackTrace();
                    LogToFile.e("错误", e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    try {
                        String ResponseBody = Objects.requireNonNull(response.body()).string();
                        if (!ResponseBody.contains("verify failed")) {
                            Log.d("后端交互日志 扫码登陆返回", ResponseBody);
                            LogToFile.d("后端交互日志 扫码登陆返回", ResponseBody);
                            handler.post(() -> {
                                if (ResponseBody.contains("\\u4e8c\\u7ef4\\u7801\\u5df2\\u7ecf\\u5931\\u6548")) {
                                    Toast.makeText(context, "二维码已失效", Toast.LENGTH_SHORT).show();
                                    Activity activity = (Activity) context;
                                    activity.finish();
                                } else {
                                    if (ResponseBody.contains("{\"data\":[],\"ret\":0,\"errcode\":0,\"errmsg\":\"ok\"}")) {
                                        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                                        Activity activity = (Activity) context;
                                        activity.finish();
                                    } else {
                                        Toast.makeText(context, ResponseBody.split("data\":")[1].split(",\"ret\"")[0], Toast.LENGTH_SHORT).show();
                                        Activity activity = (Activity) context;
                                        activity.finish();
                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogToFile.e("错误", e.toString());
                        handler.post(() -> ApiQRLogin(context, whistle_info));
                    }
                }
            });
        }).start();
    }

    public static synchronized void GainScore(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String Gain = prefs.getString("GainDate", "");
        if (!Gain.equals("")) {
            if (Gain.contains(LocalDate.now().toString())) {
                Log.d("积分获取", "今日已获取积分");
                return;
            }
        }
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            try {
                //登陆分数
                LoginFunInfo(context);
                Thread.sleep(500);
                //使用app分数
                UseAppFunInfo(context);
                Thread.sleep(500);
                UseAppFunInfo(context);
                Thread.sleep(500);
                UseAppFunInfo(context);
                Thread.sleep(500);
                UseAppFunInfo(context);
                Thread.sleep(500);
                //添加服务
                AddAppFunInfo(context);
                Thread.sleep(500);
                RemoveAppFunInfo(context);
                Thread.sleep(500);
                AddAppFunInfo(context);
                Thread.sleep(500);
                RemoveAppFunInfo(context);
                Thread.sleep(500);
                AddAppFunInfo(context);
                Thread.sleep(500);
                RemoveAppFunInfo(context);
                Thread.sleep(500);
                AddAppFunInfo(context);
                Thread.sleep(500);
                RemoveAppFunInfo(context);
                Thread.sleep(500);
                //分享
                ShareFunInfo(context);
                Thread.sleep(500);
                ShareFunInfo(context);
                Thread.sleep(500);
                //添加首页卡片
                AddCardFunInfo(context);
                Thread.sleep(500);
                RemoveCardFunInfo(context);
                Thread.sleep(500);
                AddCardFunInfo(context);
                Thread.sleep(500);
                RemoveCardFunInfo(context);
                handler.post(() -> {
                    LocalDate date = LocalDate.now();
                    prefs.edit().putString("GainDate", date.toString()).apply();
                    Log.d("积分获取", "积分获取完成");
                    LogToFile.d("积分获取", "积分获取完成");
                    GetScore(context);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e("间隔错误", e.toString());
                LogToFile.e("间隔错误", e.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("输入输出错误", e.toString());
                LogToFile.e("输入输出错误", e.toString());
            }
        }).start();
    }
}
