package com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.utils;

import android.graphics.Canvas;
import android.widget.TextView;

import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.entity.CCTVContent;

/**
 * Created by moyin on 2018/4/9.
 */

public interface CCTVContentRender {
    void baseRender(CCTVController controller, CCTVContent content);
    void exRender(CCTVController controller, CCTVContent content, Canvas canvas);
}
