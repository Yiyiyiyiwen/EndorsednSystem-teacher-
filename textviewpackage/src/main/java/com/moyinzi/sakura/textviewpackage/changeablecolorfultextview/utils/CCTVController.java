package com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.SparseArrayCompat;
import android.text.Layout;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.ContentRenderCanvas;
import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.entity.CCTVConfigure;
import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.entity.CCTVContent;
import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.handler.CCTVMenuHandler;
import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.listener.ContentChangeListener;
import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.listener.OnSelectListener;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by moyin on 2018/4/9.
 */

public class CCTVController {
    public final CCTVConfigure configure;
    public final ContentRenderCanvas canvas;

    public final TextView mTextView;
    public final Context mContext;
    private final int mSelectedColor;
    private final int mCursorHandleColor;
    private final int mCursorHandleSize;
    private final int menuId;
    public final List<CCTVContent> mContentList;

    // ---
    private static int DEFAULT_SELECTION_LENGTH = 1;
    private static final int DEFAULT_SHOW_DURATION = 100;

    /**
     * 可变文本的接口非常强大
     */
    private Spannable mSpannable;
    /**
     * 改变文本背景色
     */
    private BackgroundColorSpan mBgSpan;
    /**
     * 比HashMap<Integer,Object>更高效
     */
    private SparseArrayCompat<MyClickableSpan> clickSpanMap;

    private int mTouchX;
    private int mTouchY;
    public int[] mLocation = new int[2];

    /**
     * 记录每次选中后的信息实体类
     */
    public CCTVContent mSelectionInfo = new CCTVContent();
    public OnSelectListener mSelectListener;
    /**
     * 弹出Menu窗口
     */
    private CCTVMenu menu;
    /**
     * 两个选择游标
     */
    public CursorHandle mStartHandle;
    public CursorHandle mEndHandle;
    /**
     * 滚动界面时隐藏选中状态的标志
     */
    private boolean isHideWhenScroll;
    public boolean isHide = true;

    private ViewTreeObserver.OnPreDrawListener mOnPreDrawListener;
    /**
     * 滑动状态改变时的监听器
     */
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;

    //--

    public CCTVController(CCTVConfigure configure, ContentRenderCanvas canvas) {
        this.configure = configure;
        this.canvas = canvas;
        canvas.bind(this);

        mTextView = configure.getmTextView();
        mContext = mTextView.getContext();
        mSelectedColor = configure.getmSelectedColor();
        mCursorHandleColor = configure.getmCursorHandleColor();
        mCursorHandleSize = configure.getmCursorHandleSize();
        menuId = configure.getMenuId();
        mContentList = configure.getmContentList();
        init();
        canvas.fresh();
    }

    private void init() {
        // 由于 TextView 的文本的 BufferType 类型；
        // 是 SPANNABLE 时才可以设置 Span ，实现选中的效果；
        mTextView.setText(mTextView.getText(), TextView.BufferType.SPANNABLE);
        // 初始化保存标记对象的集合
        clickSpanMap = new SparseArrayCompat<>();
        // 将数据库中的标记全部载入到当前TextView中
                /*
        if (mContentList != null) {
            if (mTextView.getText() instanceof Spannable) {
                mSpannable = (Spannable) mTextView.getText();
            }
            for (final CCTVContent bean : mContentList) {
                TextPaint textPaint = getPaint(new TextPaint(
                        new Paint(Paint.ANTI_ALIAS_FLAG)), bean.getColor());
                MyClickableSpan clickSpan = new MyClickableSpan(textPaint) {
                    @Override
                    public void onClick(View widget) {
                        clickSelectSpan(bean.getStart(), bean.getEnd());
                    }
                };
                clickSpanMap.append(bean.getStart(), clickSpan);
                mSpannable.setSpan(clickSpan, bean.getStart(), bean.getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTextView.setText(mSpannable);
            }
            mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }
                */
        Log.i("controller", "init content");

        mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showSelectView(mTouchX, mTouchY);
                return true;
            }
        });

        mTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTouchX = (int) event.getX();
                mTouchY = (int) event.getY();
                return false;
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 当TextView有可点击部分时将屏蔽TextView的单击事件
                if (mTextView.getSelectionStart() == -1 && mTextView.getSelectionEnd() == -1) {
                    resetSelectionInfo();
                    hideSelectView();
                }
            }
        });
        /*
        // 设置当前TextView关联状态变化时的监听
        mTextView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                destroy();
            }
        });
        */

        mOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                if (isHideWhenScroll) {
                    isHideWhenScroll = false;
                    postShowSelectView(DEFAULT_SHOW_DURATION);
                }
                return true;
            }
        };
        mTextView.getViewTreeObserver().addOnPreDrawListener(mOnPreDrawListener);

        mOnScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (!isHideWhenScroll && !isHide) {
                    isHideWhenScroll = true;
                    if (menu != null) {
                        menu.dismiss();
                    }
                    if (mStartHandle != null) {
                        mStartHandle.dismiss();
                    }
                    if (mEndHandle != null) {
                        mEndHandle.dismiss();
                    }
                }
                // TODO 
                // canvas.fresh();
            }
        };
        mTextView.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener);

        menu = new CCTVMenu(this, mContext, menuId, configure.getMenuHandler());
    }

    /**
     * 延迟显示的方法
     *
     * @param duration:设置延迟的时间
     */
    private void postShowSelectView(int duration) {
        mTextView.removeCallbacks(mShowSelectViewRunnable);
        if (duration <= 0) {
            mShowSelectViewRunnable.run();
        } else {
            mTextView.postDelayed(mShowSelectViewRunnable, duration);
        }
    }

    /**
     * TextView显示时候的回调
     */
    private final Runnable mShowSelectViewRunnable = new Runnable() {
        @Override
        public void run() {
            if (isHide) return;
            if (menu != null) {
                menu.show();
            }
            if (mStartHandle != null) {
                showCursorHandle(mStartHandle);
            }
            if (mEndHandle != null) {
                showCursorHandle(mEndHandle);
            }
        }
    };

    /**
     * 隐藏选中状态的View
     */
    public void hideSelectView() {
        isHide = true;
        if (mStartHandle != null) {
            mStartHandle.dismiss();
        }
        if (mEndHandle != null) {
            mEndHandle.dismiss();
        }
        if (menu != null) {
            menu.dismiss();
        }
    }

    /**
     * 重置选择状态
     */
    public void resetSelectionInfo() {
        if (mSpannable != null && mBgSpan != null) {
            // 移除背景色
            mSpannable.removeSpan(mBgSpan);
            mBgSpan = null;
        }
    }

    /**
     * 自定义画笔调用的方法
     *
     * @param paint:传入定义好的画笔对象
     * @param color：画笔颜色
     * @return 返回定义好的TextPaint对象
     */
    @NonNull
    public TextPaint getPaint(TextPaint paint, int color) {
        WeakReference<TextPaint> mTextPaint = new WeakReference<>(paint);
        mTextPaint.get().setTextSize(mTextView.getTextSize());
        mTextPaint.get().setColor(color);
        return mTextPaint.get();
    }

    /**
     * 显示选中文本时的效果
     *
     * @param x:触碰屏幕时的X坐标
     * @param y:触碰屏幕时的Y坐标
     */
    private void showSelectView(int x, int y) {
        // 重置上一次选中的状态
        hideSelectView();
        resetSelectionInfo();
        isHide = false;
        // 新建左右游标
        if (mStartHandle == null) mStartHandle = new CursorHandle(true);
        if (mEndHandle == null) mEndHandle = new CursorHandle(false);
        // 默认选择一个字符
        int startOffset = TextLayoutUtil.getPreciseOffset(mTextView, x, y);
        int endOffset = startOffset + DEFAULT_SELECTION_LENGTH;

        if (mTextView.getText() instanceof Spannable) {
            mSpannable = (Spannable) mTextView.getText();
        }
        // 边界异常处理
        if (mSpannable == null || startOffset >= mTextView.getText().length()) {
            return;
        }
        selectText(startOffset, endOffset);
        showCursorHandle(mStartHandle);
        showCursorHandle(mEndHandle);
        menu.show();
        // 恢复初始值
        DEFAULT_SELECTION_LENGTH = 1;
    }

    /**
     * 选中文本的方法
     *
     * @param startPos:选择文本时的首字索引值
     * @param endPos:选择文本时的尾字索引值
     */
    public void selectText(int startPos, int endPos) {
        if (startPos != -1) {
            mSelectionInfo.setStart(startPos);
        }
        if (endPos != -1) {
            mSelectionInfo.setEnd(endPos);
        }
        if (mSelectionInfo.getStart() > mSelectionInfo.getEnd()) {
            int temp = mSelectionInfo.getStart();
            mSelectionInfo.setStart(mSelectionInfo.getEnd());
            mSelectionInfo.setEnd(temp);
        }

        if (mSpannable != null) {
            if (mBgSpan == null) {
                mBgSpan = new BackgroundColorSpan(mSelectedColor);
            }
            // 截取选中状态的文本
            mSelectionInfo.setContent(
                    mSpannable.subSequence(
                            mSelectionInfo.getStart(),
                            mSelectionInfo.getEnd()).toString());
            mSpannable.setSpan(mBgSpan,
                    mSelectionInfo.getStart(), mSelectionInfo.getEnd(),
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            // 设置监听选中接口回调时选择到的文字
            if (mSelectListener != null) {
                mSelectListener.onTextSelected(mSelectionInfo.getContent());
            }
        }
    }

    /**
     * 实现画线的方法
     */
    public void showUnderLine(final TextPaint paint) {
        // 将划线颜色信息保存到SelectionInfo中
        mSelectionInfo.setColor(paint.getColor());
        MyClickableSpan mClickableSpan;
        if (mSpannable != null) {
            if (clickSpanMap.get(mSelectionInfo.getStart()) != null) {
                //mClickableSpan = clickSpanMap.get(mTextView.getSelectionStart());
                mClickableSpan = clickSpanMap.get(mSelectionInfo.getStart());
                mClickableSpan.setTextPaint(paint);
                // 更新标记的方法
                CCTVContent content = getContent(mTextView.getSelectionStart());
                if (content != null) {
                    mContentList.remove(content);
                    CCTVContent update = CopyUtils.copyContent(mSelectionInfo);
                    update.setId(content.getId());
                    mContentList.remove(update);
                    configure.getContentChangeListener().update(update);
                }
            } else {
                mClickableSpan = new MyClickableSpan(paint) {
                    @Override
                    public void onClick(View widget) {
                        clickSelectSpan(mTextView.getSelectionStart(), mTextView.getSelectionEnd());
                    }
                };
                // 将选中状态的信息保存到MyClickableSpan中
                mClickableSpan.setInfo(mSelectionInfo);
                // 添加到ClickSpan集合中
                Log.i("insert", mSelectionInfo.getStart()+"");
                clickSpanMap.append(mSelectionInfo.getStart(), mClickableSpan);
                // 将标记信息存入到数据库中
                configure.getContentChangeListener().insert(mSelectionInfo, new ContentChangeListener.Callback() {
                    @Override
                    public void callback(CCTVContent content) {
                        mContentList.add(content);
                    }
                });
            }
            // 设置点击部分
            mSpannable.setSpan(mClickableSpan,
                    mSelectionInfo.getStart(), mSelectionInfo.getEnd(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTextView.setMovementMethod(LinkMovementMethod.getInstance());
            // Refresh
            mTextView.setText(mSpannable);
        }
    }

    /**
     * 给定首字符的索引值返回指定的标注对象
     * @param index:给定查找时的首字索引值
     * @return  返回指定的查找对象
     */
    public CCTVContent getContent(int index) {
        for (CCTVContent bean : this.mContentList) {
            if (bean.getStart() == index) {
                return bean;
            }
        }
        return null;
    }

    /**
     * 点击画线区域时调用的方法
     */
    private void clickSelectSpan(int start, int end) {
        // 设置TextView高亮部分背景颜色为透明
        mTextView.setHighlightColor(ContextCompat.getColor(mContext,
                android.R.color.transparent));
        // 将点击部分的信息保存到SelectionInfo中
        mSelectionInfo.setStart(start);
        mSelectionInfo.setEnd(end);
        mSelectionInfo.setContent(mTextView.getText().toString()
                .substring(start, end));
        // 弹出菜单
        isHide = false;
        menu.addFlag(CCTVMenuHandler.Flag.has_note);
        // 获取该ClickableSpan的坐标
        Layout layout = mTextView.getLayout();
        int line = layout.getLineForOffset(start);
        // 得到该字符的X坐标
        int offsetX = (int) layout.getPrimaryHorizontal(start);
        // 得到该字符的矩形区域
        Rect rect = new Rect();
        layout.getLineBounds(line, rect);
        // 得到该字符的Y坐标
        int offsetY = rect.top;
        DEFAULT_SELECTION_LENGTH = end - start;
        showSelectView(offsetX, offsetY);
    }

    /**
     * 删除下划线的方法
     */
    public void delUnderline() {
        //Log.i("delete info", mSelectionInfo.getStart()+"");
        //Log.i("delete", mTextView.getSelectionStart()+"");
        //MyClickableSpan mClickableSpan = clickSpanMap.get(mTextView.getSelectionStart());
        MyClickableSpan mClickableSpan = clickSpanMap.get(mSelectionInfo.getStart());
        hideSelectView();
        resetSelectionInfo();
        mSpannable.removeSpan(mClickableSpan);
        clickSpanMap.delete(mTextView.getSelectionStart());
        mTextView.setText(mSpannable);
        // 从数据库中删除数据
        CCTVContent content = getContent(mSelectionInfo.getStart());
        if (content != null) {
            mContentList.remove(content);
            configure.getContentChangeListener().delete(content);
        }
    }

    /**
     * 显示游标的方法
     *
     * @param cursorHandle:需要显示的游标对象
     */
    public void showCursorHandle(CursorHandle cursorHandle) {
        Layout layout = mTextView.getLayout();
        int offset = cursorHandle.isLeft ? mSelectionInfo.getStart() : mSelectionInfo.getEnd();
        cursorHandle.show((int) layout.getPrimaryHorizontal(offset),
                layout.getLineBottom(layout.getLineForOffset(offset)));
    }

    /**
     * 设置外部调用的监听接口
     *
     * @param selectListener:供外部传入的监听接口对象
     */
    public void setSelectListener(OnSelectListener selectListener) {
        mSelectListener = selectListener;
    }

    // inner class

    /**
     * 选中文本时的两端游标View
     */
    private class CursorHandle extends View {

        private Paint mPaint;
        private PopupWindow mPopupWindow;

        private int mCircleRadius = mCursorHandleSize / 2;
        private int mWidth = mCircleRadius * 2;
        private int mHeight = mCircleRadius * 2;
        private int mPadding = 25;

        private boolean isLeft;

        private CursorHandle(boolean isLeft) {
            super(mContext);

            this.isLeft = isLeft;

            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(mCursorHandleColor);
            // popWindow用于显示弹出菜单
            mPopupWindow = new PopupWindow(this);
            // 不允许PopWindow超出屏幕范围
            mPopupWindow.setClippingEnabled(false);
            mPopupWindow.setWidth(mWidth + mPadding * 2);
            mPopupWindow.setHeight(mHeight + mPadding / 2);

            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // 仿照原生游标图形 圆形+方块
            canvas.drawCircle(mPadding + mCircleRadius, mCircleRadius, mCircleRadius, mPaint);
            if (isLeft) {
                canvas.drawRect(mCircleRadius + mPadding, 0, mCircleRadius * 2 + mPadding, mCircleRadius, mPaint);
            } else {
                canvas.drawRect(mPadding, 0, mPadding + mCircleRadius, mCircleRadius, mPaint);
            }
        }

        /**
         * 记录触摸View时的坐标
         */
        private int mAdjustX;
        private int mAdjustY;

        private int mBeforeDragStart;
        private int mBeforeDragEnd;

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mBeforeDragStart = mSelectionInfo.getStart();
                    mBeforeDragEnd = mSelectionInfo.getEnd();
                    // 返回相对于当前View的相对坐标
                    mAdjustX = (int) event.getX();
                    mAdjustY = (int) event.getY();
                    break;
                // 拖拽触摸结束后显示弹出菜单
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    menu.show();
                    break;
                case MotionEvent.ACTION_MOVE:
                    menu.dismiss();
                    // 返回相对于屏幕的绝对坐标
                    int rawX = (int) event.getRawX();
                    int rawY = (int) event.getRawY();
                    update(rawX + mAdjustX - mWidth, rawY + mAdjustY - mHeight);
                    break;
            }
            return true;
        }

        /**
         * 随着触摸移动不断更新选中状态
         *
         * @param x:移动手指时的X坐标
         * @param y:移动手指时的Y坐标
         */
        private void update(int x, int y) {
            mTextView.getLocationInWindow(mLocation);
            int oldOffset;

            if (isLeft) {
                oldOffset = mSelectionInfo.getStart();
            } else {
                oldOffset = mSelectionInfo.getEnd();
            }

            y -= mLocation[1];

            int offset = TextLayoutUtil.getHysteresisOffset(mTextView, x, y, oldOffset);

            if (offset != oldOffset) {
                resetSelectionInfo();
                if (isLeft) {
                    // 处理如果出现用户将左边游标拖动到右边游标之后的调转情况
                    if (offset > mBeforeDragEnd) {
                        CursorHandle cursorHandle = getCursorHandle(false);
                        // 改变当前游标的方向
                        changeDirection();
                        // 改变右侧游标的方向
                        cursorHandle.changeDirection();
                        mBeforeDragStart = mBeforeDragEnd;
                        selectText(mBeforeDragEnd, offset);
                        cursorHandle.updateCursorHandle();
                    } else {
                        selectText(offset, -1);
                    }
                    updateCursorHandle();
                } else {
                    // 处理右边超过左边游标的情况
                    if (offset < mBeforeDragStart) {
                        CursorHandle cursorHandle = getCursorHandle(true);
                        cursorHandle.changeDirection();
                        changeDirection();
                        mBeforeDragEnd = mBeforeDragStart;
                        selectText(offset, mBeforeDragStart);
                        cursorHandle.updateCursorHandle();
                    } else {
                        selectText(mBeforeDragStart, offset);
                    }
                    updateCursorHandle();
                }
            }
        }

        /**
         * 更新游标选择区域的方法
         */
        private void updateCursorHandle() {
            mTextView.getLocationInWindow(mLocation);
            Layout layout = mTextView.getLayout();
            if (isLeft) {
                mPopupWindow.update((int) layout.getPrimaryHorizontal(mSelectionInfo.getStart()) - mWidth + getExtraX(),
                        layout.getLineBottom(layout.getLineForOffset(mSelectionInfo.getStart())) + getExtraY(), -1, -1);
            } else {
                mPopupWindow.update((int) layout.getPrimaryHorizontal(mSelectionInfo.getEnd()) + getExtraX(),
                        layout.getLineBottom(layout.getLineForOffset(mSelectionInfo.getEnd())) + getExtraY(), -1, -1);
            }
        }

        /**
         * 显示游标时调用的方法
         *
         * @param x:移动手指时的X坐标
         * @param y:移动手指时的Y坐标
         */
        public void show(int x, int y) {
            mTextView.getLocationInWindow(mLocation);
            int offset = isLeft ? mWidth : 0;
            mPopupWindow.showAtLocation(mTextView, Gravity.NO_GRAVITY, x - offset + getExtraX(), y + getExtraY());
        }

        public int getExtraX() {
            return mLocation[0] - mPadding + mTextView.getPaddingLeft();
        }

        public int getExtraY() {
            return mLocation[1] + mTextView.getPaddingTop();
        }

        /**
         * 改变方向的方法
         */
        private void changeDirection() {
            isLeft = !isLeft;
            invalidate();
        }

        /**
         * 返回游标类型
         *
         * @param isLeft:判断是否为起始游标
         * @return  返回指定的游标对象
         */
        private CursorHandle getCursorHandle(boolean isLeft) {
            if (mStartHandle.isLeft == isLeft) {
                return mStartHandle;
            } else {
                return mEndHandle;
            }
        }

        public void dismiss() {
            mPopupWindow.dismiss();
        }
    }
}
