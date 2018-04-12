package com.example.even1.endorsedsystemteacher.View.MyClass;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


import com.example.even1.endorsedsystemteacher.Adapter.MyViewPagerAdapter;
import com.example.even1.endorsedsystemteacher.R;
import com.example.even1.endorsedsystemteacher.View.CustomizedView.FViewPager;

import java.util.ArrayList;
import java.util.List;

public class Submit_Condition extends AppCompatActivity {

    private TextView content,title,request,endtime;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private FViewPager viewPager;
    private List<Fragment> list;
    private MyViewPagerAdapter adapter;
    private String[] titles = {"按时提交","迟交","未提交"};
    private String mendtime,jianjie,brief;
    private int homeworkid;

    private ArrayList<View> mlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit__condition);

        content = (TextView)findViewById(R.id.content);
        title = (TextView)findViewById(R.id.title);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (FViewPager)findViewById(R.id.viewpager);
        request = (TextView)findViewById(R.id.request);
        endtime = (TextView)findViewById(R.id.endtime);

        init();
    }

    private void init() {
        Bundle bundle=getIntent().getExtras();
        mendtime = bundle.getString("endtime");
        homeworkid = bundle.getInt("id");
        jianjie = bundle.getString("jianjie");
        brief = bundle.getString("brief");

        content.setText(jianjie);
        request.setText(brief);
        endtime.setText(mendtime);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("作业");

        list = new ArrayList<>();
        list.add(new Ontime());
        list.add(new Late());
        list.add(new Absent());

        adapter = new MyViewPagerAdapter(getSupportFragmentManager(),list,titles);
        viewPager.setAdapter(adapter);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        tabLayout.setupWithViewPager(viewPager);

    }


}
