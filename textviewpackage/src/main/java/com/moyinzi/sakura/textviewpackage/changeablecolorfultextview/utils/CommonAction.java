package com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.utils;

import android.graphics.Paint;
import android.view.View;

import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.entity.CCTVContent;

/**
 * Created by moyin on 2018/4/9.
 */

public class CommonAction {
    public static void build(final CCTVController controller, View view, int rid, final int type, final int color, final String note) {
        view.findViewById(rid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //contentRenderMode;
                CCTVContent content = CopyUtils.copyContent(controller.mSelectionInfo);
                content.setType(type);
                content.setColor(color);
                content.setNote(note);
                controller.mContentList.add(content);
                controller.resetSelectionInfo();
                controller.hideSelectView();
                controller.canvas.fresh();
            }
        });
    }
}
