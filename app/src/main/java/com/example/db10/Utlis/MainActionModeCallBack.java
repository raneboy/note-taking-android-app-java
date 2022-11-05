package com.example.db10.Utlis;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.example.db10.R;

public abstract class MainActionModeCallBack implements ActionMode.Callback {

    ActionMode actionMode;
    MenuItem itemCount;
    MenuItem itemShare;

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {

        this.actionMode = mode;
        //this.itemCount = menu.findItem(R.id.menu_main_action_mode_checked_count);
        this.itemShare = menu.findItem(R.id.menu_main_action_mode_share);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mode = null;
    }

    public void setCount(String checkedCount){
        if(checkedCount != null)
        this.itemCount.setTitle(checkedCount);
    }
}
