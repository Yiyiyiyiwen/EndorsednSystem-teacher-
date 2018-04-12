package com.example.even1.endorsedsystemteacher.View.StackFragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.even1.endorsedsystemteacher.Adapter.MyViewPagerAdapter;
import com.example.even1.endorsedsystemteacher.R;

import java.util.ArrayList;
import java.util.List;

public class Rank_Detail extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyViewPagerAdapter adapter;
    private List<Fragment>list;
    private String[] titles = {"优秀书籍","优秀读者"};

    private Toolbar toolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank__detail);
        
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        init();
    }

    private void init() {
        list = new ArrayList<>();
        /*list.add(new Good_Books());
        list.add(new Good_Readers());*/
        adapter = new MyViewPagerAdapter(getSupportFragmentManager(),list,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("");
    }
}
