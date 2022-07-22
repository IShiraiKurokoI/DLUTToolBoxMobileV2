package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import static com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils.GetHeaderForNonce;
import static com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils.GetHeaderForSign;
import static com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils.GetHeaderForTimeStamp;
import static com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils.GetHeaderForVersion;
import static com.Shirai_Kuroko.DLUTMobile.Utils.BackendUtils.GetHeaderValueFora;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.Shirai_Kuroko.DLUTMobile.Entities.LoginResponseBean;
import com.Shirai_Kuroko.DLUTMobile.Helpers.ConfigHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressWarnings("ALL")
public class HttpRequestDataCommand
{
    public String cmdName;
    public BrowserProxy proxy;
    public String cmdId;

    public HttpRequestDataCommand(final BrowserProxy browserProxy, final String s, final String s2) {
        super();
        this.proxy = browserProxy;
        this.cmdId = s;
        this.cmdName = s2;
    }

    public static String getParams(Map<String, String> paramValues)
    {
        StringBuilder params= new StringBuilder();
        Set<String> key = paramValues.keySet();
        String beginLetter="";

        for (String s : key) {
            if (params.toString().equals("")) {
                params.append(beginLetter).append(s).append("=").append(paramValues.get(s));
            } else {
                params.append("&").append(s).append("=").append(paramValues.get(s));
            }
        }
        return params.toString();
    }

    @SuppressLint("HandlerLeak")
    public void execute(final JSONObject jsonObject) {
        try {
                if (jsonObject.has("requestUrl")) {
                    //PARAS:{"methodName":"GET","requestUrl":"https://xiaoyuan.m.dlut.edu.cn/proxy/api/ws?m=score&a=getLotteryList","requestParams":{"aid":"100175170","verify":"100175170:dlut_android_62d8d1dfbd0f6_f3ae5500205202b46abdc269c7483e70","school":"dlut","device_type":"android","identity":"student"}}
                    final String requestUrl = jsonObject.getString("requestUrl");
                    String MethodName = jsonObject.getString("methodName");
                    final HashMap<String, String> hashMap = new HashMap<>();
                    if (jsonObject.has("requestParams")) {
                        final JSONObject jsonObject2 = jsonObject.getJSONObject("requestParams");
                        final Iterator<String> keys = jsonObject2.keys();
                        while (keys.hasNext()) {
                            final String key = keys.next();
                            hashMap.put(key, jsonObject2.getString(key));
                        }
                    }

//                    HashMap<String, String> headers = null;
//                    if (jsonObject.has("headers")) {
//                        final JSONObject jsonObject3 = jsonObject.getJSONObject("headers");
//                        final HashMap<String, String> hashMap2 = new HashMap<>();
//                        final Iterator<String> keys2 = jsonObject3.keys();
//                        while (true) {
//                            headers = hashMap2;
//                            if (!keys2.hasNext()) {
//                                break;
//                            }
//                            final String key2 = (String)keys2.next();
//                            hashMap2.put(key2, jsonObject3.getString(key2));
//                        }
//                    }

                    switch (MethodName)
                    {
                        case "GET":
                        {
                            new Thread(() -> {
                                LoginResponseBean UserBean = ConfigHelper.GetUserBean(proxy.context);
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
                                String url = null;
                                try {
                                    url = requestUrl+ "&" + getParams(hashMap);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                String Sign = GetHeaderForSign((url != null ? url.split("\\?") : new String[0])[1], Timestamp, Nonce, a);
                                OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                                Request request = new Request.Builder()
                                        .url(url)
                                        .header("a", a)
                                        .header("sign", Sign)
                                        .header("local_timestamp", LocalTimestamp)
                                        .header("nonce", Nonce)
                                        .header("version", GetHeaderForVersion())
                                        .header("timestamp", Timestamp)
                                        .header("Connection", "Keep-Alive")
                                        .build();//创建Request 对象
                                Response response;
                                try {
                                    response = client.newCall(request).execute();
                                    if(response.isSuccessful())
                                    {
                                        String ResponseBody = null;
                                        if (response.body() != null) {
                                            ResponseBody = response.body().string();
                                        }
                                        final JSONObject jsonObject3 = new JSONObject();
                                        String encode;
                                        encode = URLEncoder.encode(URLEncoder.encode(ResponseBody).replaceAll("\\+", "%20"));
                                        try {
                                            jsonObject3.put("responseData", encode);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            String sb = "JSONException : " +
                                                    e.getMessage();
                                            this.sendFailedResult(0, sb);
                                        }
                                        this.sendSucceedResult(jsonObject3);
                                    }
                                } catch (IOException e) {
                                    String sb = "IOException : " +
                                            e.getMessage();
                                    this.sendFailedResult(0, sb);
                                }
                            }).start();
                            break;
                        }
                        case "POST":
                        {
                            Toast.makeText(proxy.context, "抱歉，此POST功能暂未实现", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
        }
        catch (JSONException ex) {
            String sb = "JSONException : " +
                    ex.getMessage();
            this.sendFailedResult(0, sb);
        }
    }

    public void sendFailedResult(final int n, final String s) {
        this.proxy.sendFailedResult(this.cmdId, this.cmdName, n, s);
    }

    public void sendCancelResult() {
        this.proxy.sendCancelResult(this.cmdId, this.cmdName);
    }

    public void sendFailedResult(String replace) {
        replace = replace.replace(":", " ").replace("'", " ").replace("\"", " ");
        this.proxy.sendFailedResult(this.cmdId, this.cmdName, replace);
    }

    public void sendSucceedResult(final JSONObject jsonObject) {
        this.proxy.sendSucceedResult(this.cmdId, this.cmdName, jsonObject);
    }
}
