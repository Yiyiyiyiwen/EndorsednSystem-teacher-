package com.example.even1.endorsedsystemteacher;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.even1.endorsedsystemteacher.Adapter.MyViewPagerAdapter;
import com.example.even1.endorsedsystemteacher.View.CustomizedView.NoScrollViewPager;
import com.example.even1.endorsedsystemteacher.View.mainfragment.BookShelf;
import com.example.even1.endorsedsystemteacher.View.mainfragment.MyClass;
import com.example.even1.endorsedsystemteacher.View.mainfragment.MyHome;
import com.example.even1.endorsedsystemteacher.View.mainfragment.Search;
import com.example.even1.endorsedsystemteacher.View.mainfragment.Stacks;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private NoScrollViewPager viewPager;

    private List<Fragment>list;
    private MyViewPagerAdapter adapter;
    private String[] titles = {"书架","书库","班级","搜索","我的"};
    KeyEvent event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();

        init();
    }

    private void init() {
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (NoScrollViewPager)findViewById(R.id.viewpager);

        list = new ArrayList<>();
        list.add(new BookShelf());
        list.add(new Stacks());
        list.add(new MyClass());
        list.add(new Search());
        list.add(new MyHome());

        adapter = new MyViewPagerAdapter(getSupportFragmentManager(),list,titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        tabLayout.setupWithViewPager(viewPager);

        /*MyHome myHome = new MyHome();
        Bundle bundle = new Bundle();
        bundle.putString("nickname",nickname);
        myHome.setArguments(bundle);*/

    }
    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
