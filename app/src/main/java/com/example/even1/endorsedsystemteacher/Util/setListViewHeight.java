package com.example.even1.endorsedsystemteacher.Util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * Created by Even1 on 2018/3/1.
 */

public class setListViewHeight {

    public static void setListViewHeightBasedOnChildren(ListView listView,int number) {
        if(number==0){
            SimpleAdapter adapter = (SimpleAdapter) listView.getAdapter();
            if (adapter == null) {
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View listItem = adapter.getView(i, null, listView);
                listItem.measure(0, 0);
                //计算总高度
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            //计算分割线高度
            params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
            //给listview设置高度
            listView.setLayoutParams(params);
        }
        else{
            ArrayAdapter adapter = (ArrayAdapter)listView.getAdapter();
            if (adapter == null) {
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View listItem = adapter.getView(i, null, listView);
                listItem.measure(0, 0);
                //计算总高度
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            //计算分割线高度
            params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
            //给listview设置高度
            listView.setLayoutParams(params);
        }


    }
}
