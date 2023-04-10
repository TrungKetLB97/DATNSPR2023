package com.app.projectfinal_master.utils;

import android.view.View;

import java.util.List;

public interface ItemClickListener {
    void onClick(View view, List<Object> list, int position, boolean isLongClick);
}
