package com.Shirai_Kuroko.DLUTMobile.Common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.Shirai_Kuroko.DLUTMobile.SplashActivity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <h3>Global Exception Handler</h3>
 * <br>
 * This class is used to catch uncaught exceptions and handle them by displaying a Toast
 * and saving crash logs for debugging.
 *
 */
@SuppressLint("SimpleDateFormat")
public class CrashHandler implements UncaughtExceptionHandler {

    public static String TAG = "MyCrash";
    // Default system UncaughtException handler
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private static CrashHandler instance = new CrashHandler();
    private Context mContext;

    // To store device information and crash details
    private Map<String, String> infos = new HashMap<>();

    /** Ensure only one CrashHandler instance */
    private CrashHandler() {
    }

    /** Get CrashHandler instance, Singleton pattern */
    public static CrashHandler getInstance() {
        return instance;
    }

    /** Initialize CrashHandler with the application context */
    public void init(Context context) {
        mContext = context;
        // Get system's default UncaughtException handler
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // Set this CrashHandler as the default handler
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * This method is triggered when an uncaught exception occurs.
     */
    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable ex) {
        // If the exception is handled, proceed to exit the app
        if (!handleException(ex) && mDefaultHandler != null) {
            // If not handled, pass it to the system default handler
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // Wait for 3 seconds before closing the app
            SystemClock.sleep(3000);
            // Kill the process and exit
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            // Restart the app (optional, for example, in SplashActivity)
            Intent intent = new Intent(mContext, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    /**
     * Handle the exception by displaying a Toast and saving crash log.
     *
     * @param ex The uncaught exception
     * @return true if the exception was handled, false otherwise
     */
    private boolean handleException(Throwable ex) {
        if (ex == null)
            return false;
        try {
            // Show a Toast with crash message
            new Thread(() -> {
                Looper.prepare();
                Toast toast = Toast.makeText(mContext, "Sorry, the app crashed. The crash log is saved in the /files/Logs directory.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0); // Ensure the toast is centered
                toast.show();
                Looper.loop();
            }).start();

            // Collect device information (e.g., model, OS version)
            collectDeviceInfo(mContext);

            // Save the crash information and stack trace to a file
            saveCrashInfoFile(ex);

            // Wait for 3 seconds to ensure the user sees the crash message
            SystemClock.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Collects device information such as version, model, and OS version.
     * @param ctx The application context
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            // Get package info (app version, version code)
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName + "";
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Error occurred while collecting package info", e);
        }

        // Collect system and hardware info (e.g., manufacturer, model, SDK version)
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), Objects.requireNonNull(field.get(null)).toString());
            } catch (Exception e) {
                Log.e(TAG, "Error occurred while collecting crash info", e);
            }
        }
    }

    /**
     * Save the crash information (device info, stack trace) to a log file.
     *
     * @param ex The uncaught exception
     * @return null
     */
    private String saveCrashInfoFile(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        try {
            // Format the date when the crash happened
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sDateFormat.format(new java.util.Date());
            sb.append("\r\n").append(date).append("\n");

            // Append device information
            for (Map.Entry<String, String> entry : infos.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
            }

            // Write the stack trace of the exception
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = writer.toString();
            sb.append(result);

            // Save the log to a file
            LogToFile.init(mContext);
            LogToFile.c(ex.getLocalizedMessage(), sb.toString());

            return null;
        } catch (Exception e) {
            Log.e(TAG, "Error occurred while writing crash log", e);
            sb.append("Error occurred while writing log...\r\n");
        }
        return null;
    }

    /**
     * Set a custom tag for logging purposes.
     *
     * @param tag The custom tag for logging
     */
    public static void setTag(String tag) {
        TAG = tag;
    }
}
