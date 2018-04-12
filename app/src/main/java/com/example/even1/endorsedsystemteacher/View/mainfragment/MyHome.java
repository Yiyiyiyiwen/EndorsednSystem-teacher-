package com.example.even1.endorsedsystemteacher.View.mainfragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.even1.endorsedsystemteacher.View.MyHome.mInfor;
import com.example.even1.endorsedsystemteacher.View.MyHome.mMessage;
import com.example.even1.endorsedsystemteacher.View.MyHome.mSet;
import com.example.even1.endorsedsystemteacher.View.MyHome.mVIP;
import com.example.even1.endorsedsystemteacher.R;

import static com.example.even1.endorsedsystemteacher.R.color.grey;
import static com.example.even1.endorsedsystemteacher.R.color.white;

public class MyHome extends Fragment implements View.OnClickListener {

    private LinearLayout linearLayout1, linearLayout2, linearLayout3,linearLayout4;
    private mInfor infor;
    private mSet set;
    private mMessage message;
    private mVIP vip;

    private FragmentManager fm;

    private TextView tm,ti,ts,tv;
    private ImageView im,ii,is,iv,head;

    private ScrollView scrollView;
    private LinearLayout top;

    private String nickname;
    private TextView name;
    private String imgurl;


    //创建实例
    public static MyHome newInstance() {
        MyHome fragment = new MyHome();
        Bundle args = new Bundle();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_home, container, false);

        linearLayout1 = (LinearLayout) view.findViewById(R.id.message);
        linearLayout2 = (LinearLayout) view.findViewById(R.id.infor);
        linearLayout3 = (LinearLayout) view.findViewById(R.id.set);
        linearLayout4 = (LinearLayout) view.findViewById(R.id.vip);
        tm = (TextView)view.findViewById(R.id.messagetext);
        ti = (TextView)view.findViewById(R.id.infortext);
        ts = (TextView)view.findViewById(R.id.settext);
        tv = (TextView)view.findViewById(R.id.viptext);
        im = (ImageView)view.findViewById(R.id.messagepic);
        ii = (ImageView)view.findViewById(R.id.inforpic);
        is = (ImageView)view.findViewById(R.id.setpic);
        iv = (ImageView)view.findViewById(R.id.vippic);

        head = (ImageView)view.findViewById(R.id.head);
        scrollView = (ScrollView)view.findViewById(R.id.scrollView);
        top = (LinearLayout)view.findViewById(R.id.top);

        name = (TextView)view.findViewById(R.id.nickname);
        init();
        return view;
    }

    private void init() {
        linearLayout3.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout1.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);
        fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        hideFragment(ft);
        if (message == null) {
            message = new mMessage();
            ft.add(R.id.framelayout, message);
        }
        ft.show(message);
        ft.commit();

        scrollView.scrollTo(0,0);
        top.setFocusable(true);
        top.setFocusableInTouchMode(true);
        top.requestFocus();

        //nickname = getArguments().getString("nickname");
        SharedPreferences sp = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        nickname = sp.getString("name",null);
        imgurl = sp.getString("img","");
        Glide
                .with(getActivity())
                .load(imgurl)
                .into(head);
        name.setText(nickname);
    }


    public void initUI(String nickname) {
        name.setText(nickname);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message:
                setTabSelection(0);
                break;
            case R.id.infor:
                setTabSelection(1);
                break;
            case R.id.set:
                setTabSelection(2);
                break;
            case R.id.vip:
                setTabSelection(3);
                break;
            default:
                break;
        }
    }

    private void setTabSelection(int index) {
        FragmentTransaction ft = fm.beginTransaction();
        hideFragment(ft);
        switch (index) {
            case 0:
                if (message == null) {
                    message = new mMessage();
                    ft.add(R.id.framelayout, message);
                } else {
                    ft.show(message);
                }
                linearLayout1.setBackgroundResource(R.drawable.allblueshape);
                linearLayout2.setBackgroundResource(R.drawable.blueshape);
                linearLayout3.setBackgroundResource(R.drawable.blueshape);
                linearLayout4.setBackgroundResource(R.drawable.blueshape);
                im.setImageResource(R.mipmap.messagewhite);
                tm.setTextColor(getResources().getColor(white));
                ii.setImageResource(R.mipmap.infor);
                ti.setTextColor(getResources().getColor(grey));
                is.setImageResource(R.mipmap.myset);
                ts.setTextColor(getResources().getColor(grey));
                iv.setImageResource(R.mipmap.vip);
                tv.setTextColor(getResources().getColor(grey));
                break;

            case 1:
                if (infor == null) {
                    infor = new mInfor();
                    ft.add(R.id.framelayout, infor);
                }
                ft.show(infor);
                linearLayout2.setBackgroundResource(R.drawable.allblueshape);
                linearLayout1.setBackgroundResource(R.drawable.blueshape);
                linearLayout3.setBackgroundResource(R.drawable.blueshape);
                linearLayout4.setBackgroundResource(R.drawable.blueshape);
                ii.setImageResource(R.mipmap.mywhite);
                ti.setTextColor(getResources().getColor(white));
                is.setImageResource(R.mipmap.myset);
                ts.setTextColor(getResources().getColor(grey));
                im.setImageResource(R.mipmap.mymessage);
                tm.setTextColor(getResources().getColor(grey));
                iv.setImageResource(R.mipmap.vip);
                tv.setTextColor(getResources().getColor(grey));
                break;
            case 2:
                if (set == null) {
                    set = new mSet();
                    ft.add(R.id.framelayout, set);
                }
                ft.show(set);
                linearLayout3.setBackgroundResource(R.drawable.allblueshape);
                linearLayout2.setBackgroundResource(R.drawable.blueshape);
                linearLayout1.setBackgroundResource(R.drawable.blueshape);
                linearLayout4.setBackgroundResource(R.drawable.blueshape);
                is.setImageResource(R.mipmap.setwhite);
                ts.setTextColor(getResources().getColor(white));
                ii.setImageResource(R.mipmap.infor);
                ti.setTextColor(getResources().getColor(grey));
                im.setImageResource(R.mipmap.mymessage);
                tm.setTextColor(getResources().getColor(grey));
                iv.setImageResource(R.mipmap.vip);
                tv.setTextColor(getResources().getColor(grey));
                break;
            case 3:
                if (vip == null) {
                    vip = new mVIP();
                    ft.add(R.id.framelayout, vip);
                }
                ft.show(vip);
                linearLayout4.setBackgroundResource(R.drawable.allblueshape);
                linearLayout3.setBackgroundResource(R.drawable.blueshape);
                linearLayout2.setBackgroundResource(R.drawable.blueshape);
                linearLayout1.setBackgroundResource(R.drawable.blueshape);
                is.setImageResource(R.mipmap.myset);
                ts.setTextColor(getResources().getColor(grey));
                ii.setImageResource(R.mipmap.infor);
                ti.setTextColor(getResources().getColor(grey));
                im.setImageResource(R.mipmap.mymessage);
                tm.setTextColor(getResources().getColor(grey));
                iv.setImageResource(R.mipmap.vipwhite);
                tv.setTextColor(getResources().getColor(white));
                break;
        }
        ft.commit();
    }

    //用于隐藏fragment
    private void hideFragment(FragmentTransaction ft) {
        if (message != null) {
            ft.hide(message);
        }
        if (infor != null) {
            ft.hide(infor);
        }
        if (set != null) {
            ft.hide(set);
        }
        if (vip != null) {
            ft.hide(vip);
        }
    }
}
