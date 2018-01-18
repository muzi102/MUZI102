package com.nxm.muzi102.utils;


import android.text.TextUtils;

/**
 * *******************************************************************************************
 * 修改日期                          修改人             任务名称                 功能或Bug描述
 * 2018年1月16日08:54:39             lzx              CommonUtils通用工具类
 * *******************************************************************************************
 */

public class CommonUtils {
    private static CommonUtils instance = null;

    private CommonUtils() {
    }

    public static CommonUtils getInstance() {
        if (null == instance) {
            instance = new CommonUtils();
        }
        return instance;
    }

    /**
     * 验证手机格式
     */
    public boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number) || number.length() != 11) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    /**
     * 验证密码格式
     */
    public boolean isPassword(String password) {
        String num = "^(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$)\\S{6,16}$";
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return password.matches(num);
        }
    }

    /**
     * 会员名称长度验证
     */
    public boolean isHuiYuan(String password) {
        if (TextUtils.isEmpty(password) || password.length() < 5 || password.length() > 16) {
            return false;
        } else {
            return true;
        }
    }
}
