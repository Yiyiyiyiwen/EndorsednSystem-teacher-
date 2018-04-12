package com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.utils;

import android.content.Context;

import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.entity.CCTVContent;

/**
 * Created by moyin on 2018/4/9.
 */

public class CopyUtils {
    public static CCTVContent copyContent(CCTVContent target) {
        CCTVContent content = new CCTVContent();
        content.setId(target.getId());
        content.setStart(target.getStart());
        content.setEnd(target.getEnd());
        content.setColor(target.getColor());
        content.setType(target.getType());
        content.setContent(target.getContent());
        content.setNote(target.getNote());
        return content;
    }
}
