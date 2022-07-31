package com.Shirai_Kuroko.DLUTMobile.Common;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

/**
 * 将Log日志写入文件中
 * <p>
 * 使用单例模式是因为要初始化文件存放位置
 * <p>
 * Created by waka on 2016/3/14.
 */
public class LogToFile {

    private static String TAG = "FileLogHelper";

    private static String logPath = null;//log日志存放路径

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.PRC);//日期格式;

    private static Date date = new Date();//因为log日志是使用日期命名的，使用静态成员变量主要是为了在整个程序运行期间只存在一个.log文件中;

    /**
     * 初始化，须在使用之前设置，最好在Application创建时调用
     */
    public static void init(Context context) {
        logPath = getFilePath(context) + "/Logs";//获得文件储存路径,在后面加"/Logs"建立子文件夹
        DeleteOverdueLogFile();
    }


    private static void DeleteOverdueLogFile() {
        File mfolder = new File(logPath); //打开目录文件夹
        if (mfolder.isDirectory()) {
            File[] AllFiles = mfolder.listFiles(); //列出目录下的所有文件
            ArrayList<String> mFilesList = new ArrayList<String>();  //存放/myLog 下的所有文件
            for (int i = 0; i < AllFiles.length; i++) {
                File mFile = AllFiles[i]; //得到文件
                String Name = mFile.getName(); //得到文件的名字
                if (Name.length() < 1)
                    return;
                if (Name.startsWith("程序运行日志") && Name.endsWith(".log")) {  //筛选出log
                    mFilesList.add(Name); //把文件名添加到链表里
                }
            }
            Collections.sort(mFilesList);   // 将文件按自然排序升序排列
            //判断日志文件如果大于5，就要处理
            for (int i = 0; i < mFilesList.size() - 4; i++) {
                String Name = mFilesList.get(i); //得到链表最早的文件名
                File mFile = new File(mfolder, Name);  //得到最早的文件
                mFile.delete(); //删除
            }
        }
    }

        /**
         * 获得文件存储路径
         */
    private static String getFilePath(Context context) {

        if (Environment.MEDIA_MOUNTED.equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {//如果外部储存可用
            return context.getExternalFilesDir(null).getPath();//获得外部存储路径,默认路径为 /storage/emulated/0/Android/data/com.waka.workspace.logtofile/files/Logs/log_2016-03-14_16-15-09.log
        } else {
            return context.getFilesDir().getPath();//直接存在/data/data里，非root手机是看不到的
        }
    }

    private static final String VERBOSE = "VERBOSE";

    private static final String DEBUG = "DEBUG";

    private static final String INFO = "INFO";

    private static final String WARN = "WARN";

    private static final String ERROR = "ERROR";

    public static void v(String tag, String msg) {
        writeToFile(VERBOSE, tag, msg);
    }

    public static void d(String tag, String msg) {
        writeToFile(DEBUG, tag, msg);
    }

    public static void i(String tag, String msg) {
        writeToFile(INFO, tag, msg);
    }

    public static void w(String tag, String msg) {
        writeToFile(WARN, tag, msg);
    }

    public static void e(String tag, String msg) {
        writeToFile(ERROR, tag, msg);
    }

    public static void c(String tag, String msg) {
        writeCrashToFile("Crash", tag, msg);
    }

    /**
     * 将log信息写入文件中
     */
    private static void writeToFile(String type, String tag, String msg) {

        if (null == logPath) {
            Log.e(TAG, "logPath == null ，未初始化LogToFile");
            return;
        }

        String fileName = logPath + "/程序运行日志 " + dateFormat.format(new Date()) + ".log";//log日志名，使用时间命名，保证不重复
        String log = dateFormat.format(date) + " 日志类别 " + type + " " + tag + "\n" + msg + "\n\n";//log日志内容，可以自行定制

        //如果父路径不存在
        File file = new File(logPath);
        if (!file.exists()) {
            file.mkdirs();//创建父路径
        }

        FileOutputStream fos;//FileOutputStream会自动调用底层的close()方法，不用关闭
        BufferedWriter bw = null;
        try {

            fos = new FileOutputStream(fileName, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(log);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();//关闭缓冲流
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * 将Crash信息写入文件中
     */
    private static void  writeCrashToFile(String type, String tag, String msg) {

        if (null == logPath) {
            Log.e(TAG, "logPath == null ，未初始化LogToFile");
            return;
        }

        String fileName = logPath + "/程序崩溃日志 " + dateFormat.format(new Date()) + ".log";//log日志名，使用时间命名，保证不重复
        String log = dateFormat.format(date) + " 日志类别 " + type + " " + tag + "\n" + msg + "\n\n";//log日志内容，可以自行定制

        //如果父路径不存在
        File file = new File(logPath);
        if (!file.exists()) {
            file.mkdirs();//创建父路径
        }

        FileOutputStream fos;//FileOutputStream会自动调用底层的close()方法，不用关闭
        BufferedWriter bw = null;
        try {

            fos = new FileOutputStream(fileName, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(log);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();//关闭缓冲流
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
