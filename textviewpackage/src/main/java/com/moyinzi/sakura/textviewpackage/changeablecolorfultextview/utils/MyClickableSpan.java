package com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.utils;

import android.text.TextPaint;
import android.text.style.ClickableSpan;

import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.entity.CCTVContent;

/**
 * 自定义ClickableSpan用于动态调整画线颜色
 */

public abstract class MyClickableSpan extends ClickableSpan {

    private float mTextSize;
    private int mColor;
    private TextPaint mPaint;
    private CCTVContent mInfo;

    public MyClickableSpan(TextPaint paint) {
        mPaint = paint;
        mColor = paint.getColor();
        mTextSize = paint.getTextSize();
    }

    /**
     * Makes the text underlined and in the link color.
     */
    @Override
    public void updateDrawState(TextPaint ds) {
        ds.set(mPaint);
        ds.setColor(mColor);
        ds.setTextSize(mTextSize);
        ds.setUnderlineText(true);
    }

    /**
     * 更新文本画笔的方法
     * @param paint
     */
    public void setTextPaint(TextPaint paint) {
        mPaint = paint;
        mColor = paint.getColor();
        mTextSize = paint.getTextSize();
        updateDrawState(mPaint);
    }

    public CCTVContent getInfo() {
        return mInfo;
    }

    public void setInfo(CCTVContent info) {
        mInfo = info;
    }

    @Override
    public String toString() {
        return "MyClickableSpan{" +
                "mTextSize=" + mTextSize +
                ", mColor=" + mColor +
                ", mPaint=" + mPaint +
                ", mInfo=" + mInfo +
                '}';
    }
}
