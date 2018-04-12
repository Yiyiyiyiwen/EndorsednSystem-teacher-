package com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.Layout;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.entity.CCTVContent;
import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.handler.CCTVMenuHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by moyin on 2018/4/9.
 */

public class CCTVMenu {
    private final Map<String,CCTVMenuHandler.Action> actions = new HashMap<>();

    private final CCTVController controller;
    private final CCTVMenuHandler handler;

    private PopupWindow mWindow;

    private int mWidth;
    private int mHeight;

    public CCTVMenu(final CCTVController controller, final Context context, final int menuId, final CCTVMenuHandler handler) {
        this.controller = controller;
        this.handler = handler;
        // 解析弹出的菜单
        final View contentView = LayoutInflater.from(context).inflate(menuId, null);
        contentView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mWidth = contentView.getMeasuredWidth();
        mHeight = contentView.getMeasuredHeight();
        // 通过PopWindow弹出
        mWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mWindow.setClippingEnabled(false);

        actions.put("show", new CCTVMenuHandler.Action() {
            @Override
            public void done() {
                show();
            }
        });
        handler.bindClick(controller, contentView, actions);
    }


    /**
     * 显示弹窗的方法
     */
    public void show() {
        // 获取在当前窗口内的绝对坐标
        controller.mTextView.getLocationInWindow(controller.mLocation);
        // 定位弹窗位置
        Layout layout = controller.mTextView.getLayout();
        // 得到当前字符段的左边X坐标+Y坐标
        int posX = (int) layout.getPrimaryHorizontal(controller.mSelectionInfo.getStart() + controller.mLocation[0]);
        int posY = layout.getLineTop(layout.getLineForOffset(
                controller.mSelectionInfo.getStart())) + controller.mLocation[1] - mHeight - 16;
        // 设置边界值
        if (posX <= 0) posX = 16;
        if (posY < 0) posY = 16;
        if ((posX + mWidth) > TextLayoutUtil.getScreenWidth(controller.mContext)) {
            posX = TextLayoutUtil.getScreenWidth(controller.mContext) - mWidth - 16;
        }
        // 设置阴影效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWindow.setElevation(8f);
        }
        mWindow.showAtLocation(controller.mTextView, Gravity.NO_GRAVITY, posX, posY);
    }

    public void dismiss() {
        mWindow.dismiss();
    }

    public boolean isShowing() {
        return mWindow.isShowing();
    }

    public void addFlag(CCTVMenuHandler.Flag flag) {
        handler.addFlag(flag);
    }

    public void removeFlag(CCTVMenuHandler.Flag flag) {
        handler.removeFlag(flag);
    }
}
