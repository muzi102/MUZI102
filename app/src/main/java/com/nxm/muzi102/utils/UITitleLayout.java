package com.nxm.muzi102.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.nxm.muzi102.R;
import com.nxm.muzi102.comment.Constants;

/**
 * ******************************************************************************************************************
 * 修改日期                         修改人             任务名称                 功能或Bug描述
 * 2018年1月8日22:46:20             LZX                                      沉浸标题栏布局容器
 * 2018年1月8日22:46:20             LZX                                      修改状态栏透明
 *
 * ******************************************************************************************************************
 */
public class UITitleLayout extends LinearLayout {
    private Context context;
    public static int bg_color = R.color.main_theme;
    public static String BG_COLOR = "#0066ff";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public UITitleLayout(Context context) {
        super(context);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public UITitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(Context context) {
        this.context = context;
        initSystemBar();
//        createContentView();
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
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return res.getDimensionPixelSize(resourceId);
        }
        return Constants.ZERO;
    }

    /**
     * 设置沉浸式状态栏
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initSystemBar() {
        // 判断当前SDK版本号，如果是4.4以上，才支持沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = ((Activity) context).getWindow().getDecorView();
            //让应用主题内容占用系统状态栏的空间,注意:下面两个参数必须一起使用 stable 牢固的
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色为透明
            ((Activity) context).getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
