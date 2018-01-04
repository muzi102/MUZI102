package com.nxm.muzi102.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                 功能或Bug描述
 * 2018年1月4日22:07:10            lzx               权限工具                  检查权限的工具类
 * *******************************************************************************************
 */
public class CheckPermission {

    private final Context context;
    private final Activity activity;

    //构造器
    public CheckPermission(Context context, Activity mActivity) {
        this.context = context.getApplicationContext();
        this.activity = mActivity;
    }

    //检查权限	时，判断系统的权限集合
    public boolean permissionSet(String[] permissions) {
        for (String permission : permissions) {
            if (isLackPermission(permission)) {//是否添加完全部权限集合
                return true;
            }
        }
        return false;
    }

    //检查系统权限是，判断当前是否缺少权限(PERMISSION_DENIED:权限是否足够)
    private boolean isLackPermission(String permission) {
//        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
        return ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 多权限申请
     */
    public boolean checkPermission(Activity activity, String[] permission) {
        boolean isCheckPermission = false;
        //检查权限
        for (int i = 0; i < permission.length; i++) {
            String strPermission = permission[i];
            if (ContextCompat.checkSelfPermission(activity, strPermission) != PackageManager.PERMISSION_GRANTED) {
                Log.e("nxm17117", strPermission +
                        (ContextCompat.checkSelfPermission(activity, strPermission) != PackageManager.PERMISSION_GRANTED));
                //没有权限
                isCheckPermission = true;
                return isCheckPermission;
            }
        }
        isCheckPermission = false;
        return isCheckPermission;
    }

}
