package com.nxm.muzi102.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Properties;

/**
 * Created by Administrator on 2017/6/7.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private String logPath;
    //记录系统的错误处理器
    private Thread.UncaughtExceptionHandler mSysHandler;
    private Context context;

    private CrashHandler(Context context) {
        this.context = context;
        //初始化log存放位置
        logPath = Environment.getExternalStorageDirectory().toString() + "/log/" +
                context.getApplicationInfo().packageName;
    }

    private static CrashHandler instance;

    public static CrashHandler newInstance(Context context) {
        if (null == instance) {
            instance = new CrashHandler(context);
        }
        return instance;
    }

    public void init() {
        //记录系统原来的错误处理器
        mSysHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将该处理器作为系统的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //记录异常信息
        recordException(e);
        //将错误转给系统的异常处理器
        mSysHandler.uncaughtException(t, e);
    }

    private void recordException(final Throwable e) {
        new Thread() {
            @Override
            public void run() {
                //检测缓存文件夹是否存在
                File file = new File(logPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                //收集错误信息
                collectCrashDeviceInfo();
                saveCrashInfoToFile(e);

                //上传
            }
        }.start();
    }

    private Properties mDeviceCrashInfo;
    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";
    private static final String STACK_TRACE = "stack_trace";

    /**
     * 收集程序崩溃的程序版本以及设备信息
     */
    public void collectCrashDeviceInfo() {
        mDeviceCrashInfo = new Properties();
        try {
            //获取包管理器
            PackageManager pm = context.getPackageManager();
            //获取应用包信息（取出版本号以及版本名称）
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mDeviceCrashInfo.put(VERSION_NAME, pi.versionName == null ? "not set" : pi.versionName);
                mDeviceCrashInfo.put(VERSION_CODE, "" + pi.versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("m_tag", "Error while collect package info", e);
        }
        //使用反射来收集设备信息.在Build类中包含各种设备信息,
        //例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        Field[] fields = Build.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), "" + field.get(null));
            }
        } catch (Exception e) {
            Log.e("m_tag", "Error while collect crash info", e);
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return
     */
    private String saveCrashInfoToFile(Throwable ex) {
        //将异常信息写入输出流
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);
        //循环获取该异常的上一个异常信息
//        Throwable cause = ex.getCause();
//        while (cause != null) {
//            cause.printStackTrace(printWriter);
//            printWriter.append('\n');
//            printWriter.append('\r');
//            cause = cause.getCause();
//        }
        //转换全部的异常文本
        String result = info.toString();
        printWriter.close();
        mDeviceCrashInfo.put("EXCEPTION", ex.getLocalizedMessage());
        mDeviceCrashInfo.put(STACK_TRACE, result);
        try {
            Calendar c = Calendar.getInstance();
            String date = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE);
            String time = c.get(Calendar.HOUR_OF_DAY) + "_" + c.get(Calendar.MINUTE) + "_" + c.get(Calendar.SECOND);
            String fileName = logPath + "/crash-" + date + "-" + time + ".log";
            Log.e("m_tag", "=====>" + fileName);
            FileOutputStream trace = new FileOutputStream(fileName);
            mDeviceCrashInfo.store(trace, "");
            trace.flush();
            trace.close();
            return fileName;
        } catch (Exception e) {
            Log.e("m_tag", "an error occured while writing report file...", e);
        }
        return null;
    }
}
