package com.wwdw.easytimeapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import com.wwdw.easytimeapplication.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    public MySwipeRefreshLayout(@NonNull Context context) {
        super(context);
        init();
    }


    public MySwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setColorSchemeResources(
                R.color.app_yellow,
                R.color.app_yellow,
                R.color.app_yellow,
                R.color.app_yellow);
    }
}
