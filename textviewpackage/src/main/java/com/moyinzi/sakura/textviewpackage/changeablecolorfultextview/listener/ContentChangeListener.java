package com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.listener;

import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.entity.CCTVContent;

/**
 * Created by moyin on 2018/4/9.
 */

public interface ContentChangeListener {
    public interface Callback {
        void callback(CCTVContent content);
    }
    void insert(CCTVContent content, Callback callback);
    void delete(CCTVContent content);
    void update(CCTVContent content);
}
