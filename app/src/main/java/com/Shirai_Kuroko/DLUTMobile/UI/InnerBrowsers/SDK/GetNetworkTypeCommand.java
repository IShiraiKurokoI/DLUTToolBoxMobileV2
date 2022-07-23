package com.Shirai_Kuroko.DLUTMobile.UI.InnerBrowsers.SDK;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

import androidx.core.app.ActivityCompat;

import org.json.JSONException;
import org.json.JSONObject;

public class GetNetworkTypeCommand {
    public String cmdName;
    public BrowserProxy proxy;
    public String cmdId;
    private JSONObject jsonObject;

    public GetNetworkTypeCommand(final BrowserProxy browserProxy, final String s, final String s2) {
        super();
        this.proxy = browserProxy;
        this.cmdId = s;
        this.cmdName = s2;
    }

    public void execute(JSONObject jsonObject) {
        try {
            final String s = "";
            final int n = 0;
            int networkType = 0;
            Label_0093:
            {
                try {
                    final NetworkInfo activeNetworkInfo = ((ConnectivityManager) proxy.context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                    if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable() || !activeNetworkInfo.isConnected()) {
                        networkType = -1;
                        break Label_0093;
                    }
                    final int type = activeNetworkInfo.getType();
                    if (type == 1) {
                        networkType = -101;
                        break Label_0093;
                    }
                    if (type == 0) {
                        if (ActivityCompat.checkSelfPermission(proxy.context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        networkType = ((TelephonyManager) proxy.context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkType();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            int n2;
            if (networkType != -101) {
                if (networkType != -1) {
                    switch (networkType) {
                        default: {
                            n2 = n;
                            break;
                        }
                        case 13: {
                            n2 = 3;
                            break;
                        }
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 14:
                        case 15: {
                            n2 = 2;
                            break;
                        }
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                        case 11: {
                            n2 = 1;
                            break;
                        }
                    }
                } else {
                    n2 = -1;
                }
            } else {
                n2 = -101;
            }
            final JSONObject jsonObject2 = new JSONObject();
            if (n2 != -1) {
                String s2;
                if (n2 != -101) {
                    if (n2 != 0) {
                        if (n2 != 1) {
                            if (n2 != 2) {
                                s2 = "4g";
                            } else {
                                s2 = "3g";
                            }
                        } else {
                            s2 = "2g";
                        }
                    } else {
                        s2 = "unknown";
                    }
                } else {
                    s2 = "wifi";
                }
                try {
                    jsonObject2.put("networkType", s2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (n2 == -101) {
                    try {
                        jsonObject2.put("netaddress", Formatter.formatIpAddress(((WifiManager) proxy.context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getIpAddress()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Label_0453:
                    {
                        Label_0448:
                        {
                            try {
                                final NetworkInfo activeNetworkInfo2 = ((ConnectivityManager) proxy.context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                                if (activeNetworkInfo2 != null && activeNetworkInfo2.isAvailable() && activeNetworkInfo2.isConnected() && activeNetworkInfo2.getType() == 1) {
                                    final WifiInfo connectionInfo = ((WifiManager) proxy.context.getApplicationContext().getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
                                    if (connectionInfo != null) {
                                        if (connectionInfo.getSSID() == null) {
                                            jsonObject = new JSONObject();
                                        }
                                        try {
                                            jsonObject = new JSONObject((jsonObject.toString()).replaceAll("\"", ""));
                                            break Label_0453;
                                        } catch (Exception ex2) {
                                            break Label_0448;
                                        }
                                    }
                                }
                                jsonObject = new JSONObject();
                            } catch (Exception ex2) {
                                jsonObject = new JSONObject();
                            }
                        }
                    }
                    try {
                        jsonObject2.put("ssid", jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String bssid;
                    try {
                        final NetworkInfo activeNetworkInfo3 = ((ConnectivityManager) proxy.context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                        bssid = s;
                        if (activeNetworkInfo3 != null) {
                            bssid = s;
                            if (activeNetworkInfo3.isAvailable()) {
                                bssid = s;
                                if (activeNetworkInfo3.isConnected()) {
                                    bssid = s;
                                    if (activeNetworkInfo3.getType() == 1) {
                                        final WifiInfo connectionInfo2 = ((WifiManager) proxy.context.getApplicationContext().getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
                                        bssid = s;
                                        if (connectionInfo2 != null) {
                                            bssid = connectionInfo2.getBSSID();
                                            if (bssid == null) {
                                                bssid = s;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception ex3) {
                        ex3.printStackTrace();
                        bssid = s;
                    }
                    try {
                        jsonObject2.put("bssid", bssid);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            this.sendSucceedResult(jsonObject2);
        } catch (Exception e) {
            this.sendFailedResult(e.getMessage());
        }
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
