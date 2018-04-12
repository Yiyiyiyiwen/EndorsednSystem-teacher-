package com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.handler;

import android.view.View;

import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.entity.CCTVContent;
import com.moyinzi.sakura.textviewpackage.changeablecolorfultextview.utils.CCTVController;

import java.util.Map;
import java.util.Set;

/**
 * Created by moyin on 2018/4/9.
 */

public interface CCTVMenuHandler {

    void addFlag(Flag flag);

    void removeFlag(Flag flag);

    public enum Flag {
        has_note
    }

    public interface Action {
        void done();
    }

    void bindClick(final CCTVController controller, final View view, final Map<String, Action> actions);

}
