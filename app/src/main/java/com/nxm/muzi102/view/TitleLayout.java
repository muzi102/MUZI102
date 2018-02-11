package com.nxm.muzi102.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nxm.muzi102.R;

/**
 * Created by Asus on 2018/2/9.
 */

public class TitleLayout extends LinearLayout {
    private Context context;
    private RelativeLayout titlebar_back;
    private TextView titlebar_title;

    public TitleLayout(Context context) {
        super(context);
        init(context);
    }

    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        createContentView(context);
    }

    private void createContentView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.titlebar, this);
        titlebar_back = findViewById(R.id.titlebar_back);
        titlebar_title = findViewById(R.id.titlebar_title);
        titlebar_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) getContext()).finish();
            }
        });
    }
}
