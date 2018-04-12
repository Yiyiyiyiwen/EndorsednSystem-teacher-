package com.example.even1.endorsedsystemteacher.View.CustomizedView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Even1 on 2018/1/31.
 */

public class FViewPager extends ViewPager {
    public FViewPager(Context context) {
        super(context);
    }
    public FViewPager(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
