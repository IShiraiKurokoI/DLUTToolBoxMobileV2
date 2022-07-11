package com.Shirai_Kuroko.DLUTMobile.Helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("ALL")
public class SecurityHelper {
    public static String[] a = new String[]{"/dev/socket/qemud", "/dev/qemu_pipe"};

    public static boolean a() {
        Process exec = null;
        try {
            final Process process = Runtime.getRuntime().exec(new String[]{"which", "su"});
            final InputStreamReader in = new InputStreamReader(process.getInputStream());
            final BufferedReader bufferedReader = new BufferedReader(in);
            exec = process;
            bufferedReader.readLine();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (exec != null) {
                exec.destroy();
            }
        }
    }

    public static boolean a(final Context context) {
        if (!Build.FINGERPRINT.startsWith("generic") && !Build.FINGERPRINT.toLowerCase().contains("vbox") && !Build.FINGERPRINT.toLowerCase().contains("test-keys") && !Build.MODEL.contains("google_sdk") && !Build.MODEL.contains("Emulator") && !Build.MODEL.contains("Android SDK built for x86") && !Build.MANUFACTURER.contains("Genymotion") && (!Build.BRAND.startsWith("generic") || !Build.DEVICE.startsWith("generic")) && !"google_sdk".equals(Build.PRODUCT)) {
            String lowerCase;
            try {
                final Process start = new ProcessBuilder("/system/bin/cat", "/proc/cpuinfo").start();
                final StringBuilder sb = new StringBuilder();
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(start.getInputStream(), StandardCharsets.UTF_8));
                while (true) {
                    final String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                }
                bufferedReader.close();
                lowerCase = sb.toString().toLowerCase();
            } catch (IOException ex) {
                lowerCase = "";
            }
            if (!lowerCase.contains("intel") && !lowerCase.contains("amd")) {
                int n = 0;
                boolean b2;
                while (true) {
                    final String[] a = SecurityHelper.a;
                    if (n >= a.length) {
                        b2 = false;
                        break;
                    }
                    if (new File(a[n]).exists()) {
                        b2 = true;
                        break;
                    }
                    ++n;
                }
                if (!b2) {
                    final Intent registerReceiver = context.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
                    final int intExtra = registerReceiver.getIntExtra("voltage", 99999);
                    final int intExtra2 = registerReceiver.getIntExtra("temperature", 99999);
                    return (intExtra == 0 && intExtra2 == 0) || (intExtra == 10000 && intExtra2 == 0);
                }
            }
        }
        return true;
    }

    public static long b() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            final StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return statFs.getAvailableBlocks() * (long) statFs.getBlockSize();
        }
        return -1L;
    }
}
