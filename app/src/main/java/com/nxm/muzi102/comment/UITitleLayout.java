package com.nxm.muzi102.comment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.nxm.muzi102.R;


/**
 * ******************************************************************************************************************
 * 修改日期                          修改人             任务名称                 功能或Bug描述
 * 2017年12月18日22:05:23            xmz                                      沉浸标题栏布局容器
 *
 */
public class UITitleLayout extends LinearLayout {
    private Context context;
    public static int bg_color = R.color.common_gray;
    public static String BG_COLOR = "#D3D5DA";
    public UITitleLayout(Context context) {
        super(context);
        init(context);
    }

    public UITitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        initSystemBar();
        createContentView();
    }
    /**
     * 创建标题控件的布局内容
     */
    private void createContentView() {
        setOrientation(VERTICAL);
        setBackgroundResource(bg_color);
        // 设置状态栏背景控件的高度
        View vStatusBar = new View(context);
        vStatusBar.setBackgroundColor(Color.parseColor(BG_COLOR));
        int w = LayoutParams.MATCH_PARENT;
        int h = getStatusBarHeight();
        LayoutParams layoutParams = new LayoutParams(w, h);
        vStatusBar.setLayoutParams(layoutParams);
        addView(vStatusBar, Constants.ZERO);

    }
    /**
     * 获取状态栏的高度
     */
    private int getStatusBarHeight() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return Constants.ZERO;
        }
        Resources res = getResources();
        int resourceId = res.getIdentifier("status_bar_height","dimen", "android");
        if (resourceId > 0) {
            return res.getDimensionPixelSize(resourceId);
        }
        return Constants.ZERO;
    }
    /**
     * 设置沉浸式状态栏
     */
    private void initSystemBar() {
        // 判断当前SDK版本号，如果是4.4以上，才支持沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = ((Activity) context).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
