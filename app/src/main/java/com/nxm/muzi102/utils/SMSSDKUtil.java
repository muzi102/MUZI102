package com.nxm.muzi102.utils;

import android.util.Log;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * mob短信验证工具
 * <p>
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称
 * 2018年1月16日16:27:00            lzx              mob短信验证工具
 * *******************************************************************************************
 */

public class SMSSDKUtil {
    //私有的构造函数
    private SMSSDKUtil() {
    }

    public static final SMSSDKUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //定义的静态内部类
    private static class SingletonHolder {
        private static final SMSSDKUtil INSTANCE = new SMSSDKUtil();  //创建实例的地方
    }

    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    Log.e("2018116", "发送成功" + result);
                    smsskEventHandlerInterface.SMSSDKResult(CKey.WHAT_ONE, "发送成功");
                } else {
                    // TODO 处理错误的结果
                    Log.e("2018116", "发送失败" + result);
                    smsskEventHandlerInterface.SMSSDKResult(CKey.WHAT_TWO, "发送失败");
                }

                // 用完回调要注销，否则会造成泄露
                SMSSDK.unregisterEventHandler(this);
            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    Log.e("2018116", "验证成功" + result);
                    smsskEventHandlerInterface.SMSSDKResult(CKey.WHAT_THERE, "验证成功");
                } else {
                    // TODO 处理错误的结果
                    Log.e("2018116", "验证失败" + result);
                    smsskEventHandlerInterface.SMSSDKResult(CKey.WHAT_FOUR, "验证失败");
                }

                // 用完回调要注销，否则会造成泄露
                SMSSDK.unregisterEventHandler(this);
            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }


    public interface SMSSKEventHandlerInterface {
        //CKey.WHAT_ONE-发送成功 ，CKey.WHAT_TWO-发送失败 ， CKey.WHAT_THERE-验证成功 ，CKey.WHAT_FOUR-验证失败
        void SMSSDKResult(int result, String textContent);
    }

    public SMSSKEventHandlerInterface getSmsskEventHandlerInterface() {
        return smsskEventHandlerInterface;
    }

    public void setSmsskEventHandlerInterface(SMSSKEventHandlerInterface smsskEventHandlerInterface) {
        this.smsskEventHandlerInterface = smsskEventHandlerInterface;
    }

    private SMSSKEventHandlerInterface smsskEventHandlerInterface;

}
