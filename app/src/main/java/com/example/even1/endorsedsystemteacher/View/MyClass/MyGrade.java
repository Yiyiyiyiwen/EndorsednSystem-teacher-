package com.example.even1.endorsedsystemteacher.View.MyClass;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.even1.endorsedsystemteacher.R;
import com.example.even1.endorsedsystemteacher.View.CustomizedView.MyListview;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.even1.endorsedsystemteacher.R.color.white;

public class MyGrade extends AppCompatActivity {
    private TextView title;
    private FoldingCell foldingCell1,foldingCell2;
    private Toolbar toolbar;
    private MyListview listview1,listview2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_grade);

        foldingCell1 = (FoldingCell)findViewById(R.id.folding_cell1);
        foldingCell2 = (FoldingCell)findViewById(R.id.folding_cell2);
        title = (TextView)findViewById(R.id.title);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        listview1 = (MyListview)findViewById(R.id.listview1);
        listview2 = (MyListview)findViewById(R.id.listview2);
        init();
    }

    private void init() {
        title.setText("成绩详情");
        title.setTextColor(Color.BLACK);
        toolbar.setBackgroundResource(white);
        foldingCell1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell1.toggle(false);
            }
        });
        foldingCell2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell2.toggle(false);
            }
        });

        SimpleAdapter adapter1 = new SimpleAdapter(this,getData(),R.layout.gradeitem,
                new String[]{"zhailu","pizhu"},
                new int[]{R.id.zhailu,R.id.pizhu});
        listview1.setAdapter(adapter1);
        listview2.setAdapter(adapter1);
        Drawable drawable1 = getResources().getDrawable(R.drawable.listviewline1);
        listview1.setDivider(drawable1);
        listview1.setDividerHeight(5);
    }

    private List<HashMap<String, Object>> getData() {
        ArrayList<HashMap<String, Object>> myList = new ArrayList<>();
        for(int i=0;i<5;i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("zhailu",getString(R.string.zhailu));
            map.put("pizhu",getString(R.string.ganxiang));
            myList.add(map);
        }
        return myList;
    }
}
