package com.example.even1.endorsedsystemteacher.View.CustomizedView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Even1 on 2018/3/1.
 */

public class MultiChoiceDialogFragment extends DialogFragment {

    private String title;

    private String[] items;

    private DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener;

    private DialogInterface.OnClickListener positiveCallback;

    public void show(String title, String[] items, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener,
                     DialogInterface.OnClickListener positiveCallback, FragmentManager fragmentManager) {
        this.title = title;
        this.items = items;
        this.onMultiChoiceClickListener = onMultiChoiceClickListener;
        this.positiveCallback = positiveCallback;
        show(fragmentManager, "MultiChoiceDialogFragment");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title).setMultiChoiceItems(items, null, onMultiChoiceClickListener)
                .setPositiveButton("确定", positiveCallback).setNegativeButton("取消",positiveCallback);
        return builder.create();
    }

}
