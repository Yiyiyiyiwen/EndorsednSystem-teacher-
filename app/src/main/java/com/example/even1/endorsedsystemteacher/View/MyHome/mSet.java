package com.example.even1.endorsedsystemteacher.View.MyHome;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.even1.endorsedsystemteacher.R;
import com.example.even1.endorsedsystemteacher.Util.setListViewHeight;

public class mSet extends Fragment implements AdapterView.OnItemClickListener{

    private ListView listView;
    private String[] name = {"账户安全","个人偏好","清除缓存"};
    private ArrayAdapter<String> adapter;

    private setListViewHeight setListViewHeight;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_m_set,container,false);

        listView = (ListView)view.findViewById(R.id.listview);
        init();
        return view;
    }

    private void init() {
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,name);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        setListViewHeight.setListViewHeightBasedOnChildren(listView,1);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:

                break;
            case 1:
                showMultiChoiceDialog();
                break;
            default:
                break;
        }
    }

    private void showMultiChoiceDialog() {
        final String items[] = {"武侠小说", "推理小说", "悬疑小说", "历史小说", "军事小说", "言情小说",
                "科幻小说", "网游小说", "穿越小说", "玄幻小说"};
        final boolean checkedItems[] = {false, false, false, false,false, false, false,false, false, false};
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("请选择你喜爱的作品风格")//设置对话框的标题
                .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checkedItems[which] = isChecked;
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            if (checkedItems[i]) {
                                //Toast.makeText(getActivity(), "选中了" + i, Toast.LENGTH_SHORT).show();
                            }
                        }
                        dialog.dismiss();
                    }

                }).create();

        dialog.show();
    }
}
