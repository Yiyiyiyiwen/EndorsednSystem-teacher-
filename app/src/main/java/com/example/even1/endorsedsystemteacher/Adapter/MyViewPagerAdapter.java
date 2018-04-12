package com.example.even1.endorsedsystemteacher.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.even1.endorsedsystemteacher.View.mainfragment.MyHome;

import java.util.List;

/**
 * Created by Even1 on 2018/1/28.
 */

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment>list;
    private String[] titles;
    private MyHome myhome = MyHome.newInstance();
    public MyViewPagerAdapter(FragmentManager fm,List<Fragment>list,String[] titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public MyHome getmyhome(){return myhome;}
}
