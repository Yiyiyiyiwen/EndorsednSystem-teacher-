package com.example.even1.endorsedsystemteacher.View;

import android.app.Dialog;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.example.even1.endorsedsystemteacher.Data.MyPage;
import com.example.even1.endorsedsystemteacher.R;
import com.example.even1.endorsedsystemteacher.Util.CharsetDetector;
import com.example.even1.endorsedsystemteacher.View.CustomizedView.FlipperLayout;
import com.example.even1.endorsedsystemteacher.View.CustomizedView.ReadView;

import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class Reading extends AppCompatActivity implements View.OnClickListener, FlipperLayout.TouchListener {
    private static final int MSG_DRAW_TEXT = 1;
    CharBuffer buffer = CharBuffer.allocate(8000);
    boolean oneIsLayout;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DRAW_TEXT:

                    FlipperLayout rootLayout = (FlipperLayout) findViewById(R.id.container);

                    View recoverView = LayoutInflater.from(Reading.this).inflate(R.layout.view_new, null);
                    View view1 = LayoutInflater.from(Reading.this).inflate(R.layout.view_new, null);
                    View view2 = LayoutInflater.from(Reading.this).inflate(R.layout.view_new, null);

                    rootLayout.initFlipperViews(Reading.this, view2, view1, recoverView);

                    final ReadView readView1 = (ReadView) view1.findViewById(R.id.textview);
                    final ReadView readView2 = (ReadView) view2.findViewById(R.id.textview);
                    final FloatingActionButton floatingActionButton1 = (FloatingActionButton)view1.findViewById(R.id.floatingActionButton);
                    final FloatingActionButton floatingActionButton2 = (FloatingActionButton)view2.findViewById(R.id.floatingActionButton);
                    floatingActionButton1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showdialog();
                        }
                    });
                    floatingActionButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showdialog();
                        }
                    });
                    buffer.position(0);

                    //填充第一页的文本
                    readView1.setText(buffer);

                    //填充第二页的文本
                    ViewTreeObserver vto1 = readView1.getViewTreeObserver();
                    vto1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            if (oneIsLayout)
                                return;

                            int charNum = readView1.getCharNum();


                            MyPage page = new MyPage();
                            page.setPageSize(charNum);
                            page.setStartPosition(charNum);
                            page.setId(1);

                            //将第一页的数据存储在数据库中，如果该数据不存在
                            if (isSavePage(1)) {
                                page.update(1);
                            } else {
                                page.save();
                            }

                            buffer.position(charNum);
                            readView2.setText(buffer);

                            oneIsLayout = true;
                        }
                    });


                    ViewTreeObserver vto2 = readView2.getViewTreeObserver();
                    vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            int charNum = readView2.getCharNum();
                            if (charNum == 0)
                                return;

                            //将第二页的数据存储在数据库中, 如果该数据不存在
                            MyPage page = new MyPage();
                            page.setPageSize(charNum);
                            page.setStartPosition(charNum + getStartPosition(1));
                            page.setId(2);

                            if (isSavePage(2)) {
                                page.update(2);
                            } else {
                                page.save();
                            }
                        }
                    });
                    break;
            }
        }
    };


    private void showdialog(){
        View inflater = LayoutInflater.from(this).inflate(R.layout.anim_dialog,null);
        Dialog dialog = new Dialog(this,R.style.BottomDialog);
        //将布局设置给Dialog
        dialog.setContentView(inflater);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        inflater.measure(0, 0);
        lp.height = inflater.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    //该页是否存储
    private boolean isSavePage(int pageNo) {
        return DataSupport.find(MyPage.class, pageNo) != null;
    }

    //获取该页的结束位置
    private int getStartPosition(int pageNo) {
        if (pageNo < 1) {
            return 0;
        }

        if (isSavePage(pageNo)) {
            return DataSupport.find(MyPage.class, pageNo).getStartPosition();
        }
        return 0;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        final FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });
        new ReadingThread().start();
    }

    @Override
    public View createView(final int direction, final int index) {
        View newView;
        if (direction == FlipperLayout.TouchListener.MOVE_TO_LEFT) { //下一页
            buffer.position(getStartPosition(index));
            newView = LayoutInflater.from(this).inflate(R.layout.view_new, null);
            final ReadView readView = (ReadView) newView.findViewById(R.id.textview);
            readView.setText(buffer);
            ViewTreeObserver vto2 = readView.getViewTreeObserver();
            /*readView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(Reading.this, "123", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });*/
            vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int charNm = readView.getCharNum();

                    MyPage page = new MyPage();
                    page.setPageSize(charNm);

                    page.setStartPosition(getStartPosition(index) + charNm);
                    page.setBookId(index + 1);
                    page.setId(index + 1);

                    if (isSavePage(index + 1)) {
                        page.update(index + 1);
                    } else {
                        page.save();
                    }
                }
            });
        } else {
            buffer.position(getStartPosition(index-2));
            newView = LayoutInflater.from(this).inflate(R.layout.view_new, null);
            final ReadView readView = (ReadView) newView.findViewById(R.id.textview);
            readView.setText(buffer);
        }

        return newView;
    }

    @Override
    public boolean whetherHasNextPage() {
        return true;
    }

    @Override
    public boolean currentIsLastPage() {
        return true;
    }

    @Override
    public void onClick(View v) {
    }

    private class ReadingThread extends Thread {
        public void run() {
            BufferedReader reader = null;
            InputStream in = null;
            AssetManager assets = getAssets();
            try {
                in = assets.open("text.txt");

                Charset charset = CharsetDetector.detect(in);
                reader = new BufferedReader(new InputStreamReader(in, charset));
                reader.read(buffer);

                mHandler.obtainMessage(MSG_DRAW_TEXT).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
