package com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.entity;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.handler.CCTVMenuHandler;
import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.listener.ContentChangeListener;
import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.utils.CCTVContentRender;
import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.utils.TextLayoutUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by moyin on 2018/4/9.
 */

public class CCTVConfigure {
    private TextView mTextView;
    private int mCursorHandleColor = 0xFF1379D6;
    private int mSelectedColor = 0xFFAFE1F4;
    private int mCursorHandleSize;
    private int menuId;
    private CCTVMenuHandler menuHandler;
    private List<CCTVContent> mContentList = new ArrayList<>();
    private Map<Integer, CCTVContentRender> contentRenderMode;
    private ContentChangeListener contentChangeListener;

    public CCTVConfigure(TextView mTextView) {
        this.mCursorHandleSize = TextLayoutUtil.dp2px(mTextView.getContext(), 24);
        this.mTextView = mTextView;
    }

    public TextView getmTextView() {
        return mTextView;
    }

    public CCTVConfigure setText(String text) {
        this.mTextView.setText(text);
        return this;
    }

    public int getmCursorHandleColor() {
        return mCursorHandleColor;
    }

    public CCTVConfigure setmCursorHandleColor(int mCursorHandleColor) {
        this.mCursorHandleColor = mCursorHandleColor;
        return this;
    }

    public int getmSelectedColor() {
        return mSelectedColor;
    }

    public CCTVConfigure setmSelectedColor(int mSelectedColor) {
        this.mSelectedColor = mSelectedColor;
        return this;
    }

    public int getmCursorHandleSize() {
        return mCursorHandleSize;
    }

    public CCTVConfigure setmCursorHandleSize(int mCursorHandleSize) {
        this.mCursorHandleSize = mCursorHandleSize;
        return this;
    }

    public int getMenuId() {
        return menuId;
    }

    public CCTVConfigure setMenu(int menuId, CCTVMenuHandler menuHandler) {
        this.menuId = menuId;
        this.menuHandler = menuHandler;
        return this;
    }

    public List<CCTVContent> getmContentList() {
        return mContentList;
    }

    public CCTVConfigure setmContentList(List<CCTVContent> mContentList) {
        this.mContentList = mContentList;
        return this;
    }

    public CCTVMenuHandler getMenuHandler() {
        return menuHandler;
    }

    public CCTVConfigure setContentRenderMode(Map<Integer,CCTVContentRender> contentRenderMode) {
        this.contentRenderMode = contentRenderMode;
        return this;
    }

    public Map<Integer, CCTVContentRender> getContentRenderMode() {
        return contentRenderMode;
    }

    public CCTVConfigure setContentChangeListener(ContentChangeListener contentChangeListener) {
        this.contentChangeListener = contentChangeListener;
        return this;
    }

    public ContentChangeListener getContentChangeListener() {
        return contentChangeListener;
    }
}
