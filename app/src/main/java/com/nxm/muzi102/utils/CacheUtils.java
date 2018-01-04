package com.nxm.muzi102.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.nxm.muzi102.R;

import java.io.File;
import java.math.BigDecimal;

/**
 * **************************************************************************************************************
 * 修改日期                         修改人             任务名称                        功能或Bug描述
 * 2018年1月1日22:26:55             lzx              缓存工具类                         缓存工具类
 *
 * *************************************************************************************************************
 */
public class CacheUtils {
    /**
     * 保存软件的参数
     *
     * @param context
     * @param key
     * @param values
     */
    public static void putBoolen(Context context, String key, boolean values) {
        SharedPreferences sp = context.getSharedPreferences(context.getResources().getString(R.string.sp_key), Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, values).commit();
    }

    /**
     * 得到软件保存的参数
     *
     * @param context
     * @param key
     * @return
     */
    public static Boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(context.getResources().getString(R.string.sp_key), Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(context.getResources().getString(R.string.sp_key), Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void putString(Context context, String key, String values) {

        SharedPreferences sp = context.getSharedPreferences(context.getResources().getString(R.string.sp_key), Context.MODE_PRIVATE);
        sp.edit().putString(key, values).commit();
    }

    public static void putInt(Context context, String key, int values) {
        SharedPreferences sp = context.getSharedPreferences(context.getResources().getString(R.string.sp_key), Context.MODE_PRIVATE);
        sp.edit().putInt(key, values).commit();
    }

    public static Integer getInt(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(context.getResources().getString(R.string.sp_key), Context.MODE_PRIVATE);
        return sp.getInt(key, -1);
    }

    /**
     * 退出清除缓存
     *
     * @param context
     */
    public static void clear(Context context) {
        clearAllCache(context);
    }

    /**
     * 得到缓存大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Context context) throws Exception {

        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }


    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
