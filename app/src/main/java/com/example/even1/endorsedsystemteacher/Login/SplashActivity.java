package com.example.even1.endorsedsystemteacher.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.even1.endorsedsystemteacher.MainActivity;
import com.example.even1.endorsedsystemteacher.R;

/**
 * Created by Even1 on 2018/3/19.
 */

public class SplashActivity extends Activity {
    private static final int GO_HOME = 0;//去主页
    private static final int GO_LOGIN = 1;//去登录页

    private int number=0;
    /**
     * 跳转判断
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME://去主页
                    /*number++;
                    if(number==5){
                        //清除用户登录记录
                        SharedPreferences sp = getSharedPreferences("userInfo",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.commit();
                        number=0;
                    }*/
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case GO_LOGIN://去登录页
                    Intent intent2 = new Intent(SplashActivity.this, Login.class);
                    startActivity(intent2);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*if (UserManage.getInstance().hasUserInfo(this))//自动登录判断，SharePrefences中有数据，则跳转到主页，没数据则跳转到登录页
        {
            mHandler.sendEmptyMessageDelayed(GO_HOME, 0);
        } else {
            mHandler.sendEmptyMessageAtTime(GO_LOGIN, 0);
        }*/
        mHandler.sendEmptyMessageAtTime(GO_LOGIN, 0);
    }
}
