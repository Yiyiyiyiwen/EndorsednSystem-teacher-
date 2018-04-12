package com.example.even1.endorsedsystemteacher.View.MyClass;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.even1.endorsedsystemteacher.Adapter.GridViewAdapter;
import com.example.even1.endorsedsystemteacher.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MyClass_detail extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private String[] name = {"班级成员","班级作业","班级成绩","成绩排行"};
    private int[] pic = {R.mipmap.classmember,R.mipmap.homework,R.mipmap.scores,R.mipmap.rank};

    private GridViewAdapter adapter;
    private ArrayList<HashMap<String, Object>> myList;

    private GridView gridView;
    private Toolbar toolbar;

    private ImageView imageview;
    private Dialog dialog;

    private LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4;
    private TextView mclassname,mowner;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class_detail);

        //gridView = (GridView)findViewById(R.id.gridview);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        imageview = (ImageView)findViewById(R.id.erweima);
        linearLayout1 = (LinearLayout)findViewById(R.id.classmember);
        linearLayout2 = (LinearLayout)findViewById(R.id.classhomework);
        linearLayout3 = (LinearLayout)findViewById(R.id.classgrade);
        linearLayout4 = (LinearLayout)findViewById(R.id.classrank);
        mclassname = (TextView)findViewById(R.id.classname);
        mowner = (TextView)findViewById(R.id.owner);

        init();
    }

    private void init() {
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*myList = getMenuAdapter(pic,name);
        adapter = new GridViewAdapter(this,myList,2);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);*/
        Context context = MyClass_detail.this;
        dialog = new Dialog(context,R.style.edit_AlertDialog_style);
        dialog.setContentView(R.layout.dialog);
        ImageView diaimageView = (ImageView) dialog.findViewById(R.id.imageView);
        diaimageView.setBackgroundResource(R.mipmap.erweima);
        dialog.setCanceledOnTouchOutside(true);//选择true的话点击其他地方可以使dialog消失，为false的话不会消失
        Window w = dialog.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        lp.y = 40;
        dialog.onWindowAttributesChanged(lp);
        diaimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        imageview.setOnClickListener(this);

        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        mclassname.setText(bundle.getString("classname"));
        mowner.setText(bundle.getString("owner"));
    }


    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.erweima:
                dialog.show();
                break;
            case R.id.classmember:
                Intent intentmember = new Intent(this, Class_Member.class);
                bundle.putInt("id",id);
                intentmember.putExtras(bundle);
                startActivity(intentmember);
                break;
            case R.id.classhomework:
                Intent intentwork = new Intent(this, Class_Homework.class);
                bundle.putInt("id",id);
                bundle.putInt("choose",1);
                intentwork.putExtras(bundle);
                startActivity(intentwork);
                break;
            case R.id.classgrade:
                Intent intentgrade = new Intent(this, Class_Homework.class);
                bundle.putInt("choose",2);
                bundle.putInt("id",id);
                intentgrade.putExtras(bundle);
                startActivity(intentgrade);
                break;
            case R.id.classrank:
                startActivity(new Intent(this, Class_Rank.class));
                break;
            case R.id.checkhomework:
                Intent intentcheck = new Intent(this, Class_Homework.class);
                bundle.putInt("choose",3);
                bundle.putInt("id",id);
                intentcheck.putExtras(bundle);
                startActivity(intentcheck);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                startActivity(new Intent(this, Class_Member.class));
                break;
            case 1:
                startActivity(new Intent(this, Class_Homework.class));
                break;
            case 2:
                startActivity(new Intent(this, Class_Grade.class));
                break;
            case 3:
                startActivity(new Intent(this, Class_Rank.class));
                break;
            default:
                break;
        }
    }
}
