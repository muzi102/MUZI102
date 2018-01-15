package com.nxm.muzi102.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                 功能或Bug描述
 * 2018年1月15日19:11:03             lzx              seekbar滑动验证手机号码
 * *******************************************************************************************
 */

public class VerificationSeekBar extends android.support.v7.widget.AppCompatSeekBar {
    //这两个值为用算法使用的2空间复杂度
    private int index = 150;
    private boolean k = true;

    public VerificationSeekBar(Context context) {
        super(context);
    }

    public VerificationSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerificationSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            k = true;
            if (x - index > 20) {
                k = false;
                return true;
            }
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (!k) {
                return true;
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
