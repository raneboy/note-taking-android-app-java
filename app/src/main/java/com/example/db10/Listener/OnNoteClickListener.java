package com.example.db10.Listener;

import android.view.View;

public interface OnNoteClickListener {
    void onClick(View view, int position);
    boolean onLongClick(View view, int position);
}
