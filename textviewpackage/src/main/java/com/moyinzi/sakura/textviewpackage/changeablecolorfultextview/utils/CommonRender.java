package com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.widget.TextView;

import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.entity.CCTVContent;

import java.util.Map;

/**
 * Created by moyin on 2018/4/9.
 */

public class CommonRender {
    public static void build(Map<Integer, CCTVContentRender> contentRenderMode, int type, final Callback callback) {
        contentRenderMode.put(type, new CCTVContentRender() {
            @Override
            public void baseRender(CCTVController controller, CCTVContent content) {

            }

            @Override
            public void exRender(CCTVController controller, CCTVContent content, Canvas canvas) {
                Paint mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setTextSize(120);
                mPaint.setColor(content.getColor());

                TextView mTextView = controller.mTextView;
                int start = content.getStart();
                int end = content.getEnd();
                Layout layout = mTextView.getLayout();

                for (int i=start;i<end;i++) {
                    int line = layout.getLineForOffset(i);
                    // 得到该字符的X坐标
                    int x1 = (int) layout.getPrimaryHorizontal(i);
                    int x2 = (int) layout.getPrimaryHorizontal(i+1);
                    // 得到该字符的矩形区域
                    Rect rect = new Rect();
                    layout.getLineBounds(line, rect);
                    // 得到该字符的Y坐标
                    int y1 = rect.top;
                    int y2 = rect.bottom;
                    if (i == start) {
                        callback.draw(canvas, x1, y1, x2 ,y2, mPaint, 0);
                    } else if (i == end-1) {
                        callback.draw(canvas, x1, y1, x2 ,y2, mPaint, 1);
                    } else {
                        callback.draw(canvas, x1, y1, x2 ,y2, mPaint, -1);
                    }
                }
            }
        });
    }

    public interface Callback {
        void draw(Canvas canvas, int x1, int y1, int x2, int y2, Paint mPaint, int index);
    }
}
