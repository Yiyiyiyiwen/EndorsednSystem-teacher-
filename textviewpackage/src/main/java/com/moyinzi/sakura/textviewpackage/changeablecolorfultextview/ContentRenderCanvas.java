package com.moyinzi.sakura.textviewpackage.changeablecolorfultextview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.entity.CCTVContent;
import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.utils.CCTVContentRender;
import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.utils.CCTVController;

/**
 * Created by moyin on 2018/4/9.
 */

public class ContentRenderCanvas extends View {

    private CCTVController controller = null;

    public ContentRenderCanvas(Context context) {
        super(context);
    }

    public ContentRenderCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ContentRenderCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (controller != null) {
            for (CCTVContent content : controller.mContentList) {
                CCTVContentRender render = controller.configure.getContentRenderMode().get(content.getType());
                render.exRender(controller, content, canvas);
            }
        }
    }

    public void fresh() {
        Log.i("content render", "fresh");
        postInvalidate();
    }

    public void bind(CCTVController controller) {
        this.controller = controller;
    }
}
