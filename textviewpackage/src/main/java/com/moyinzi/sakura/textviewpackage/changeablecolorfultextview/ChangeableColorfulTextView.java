package com.moyinzi.sakura.textviewpackage.changeablecolorfultextview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moyinzi.sakura.textviewpackage.R;
import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.entity.CCTVConfigure;
import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.utils.CCTVController;

public class ChangeableColorfulTextView extends LinearLayout {

    private CCTVConfigure configure = null;
    private CCTVController controller;
    private TextView mTextView;
    private ContentRenderCanvas canvas;

    public ChangeableColorfulTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ChangeableColorfulTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ChangeableColorfulTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.layout_changeable_colorful_textview, null);
        addView(view);
        this.mTextView = (TextView) findViewById(R.id.cctv_content);
        this.canvas = (ContentRenderCanvas) findViewById(R.id.cctv_canvas);
    }

    public CCTVController build() {
        getConfigure();
        this.controller = new CCTVController(configure, canvas);
        return controller;
    }

    public CCTVConfigure getConfigure() {
        this.configure =  this.configure == null ? new CCTVConfigure((TextView) mTextView.findViewById(R.id.cctv_content)) : this.configure;
        return this.configure;
    }

    public void setConfigure(CCTVConfigure configure) {
        this.configure = configure;
    }
}
