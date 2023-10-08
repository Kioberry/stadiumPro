package com.wwdw.easytimeapplication.activities;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.base.BaseActivity;
import com.wwdw.easytimeapplication.base.BaseApplion;
import com.wwdw.easytimeapplication.fragments.HouseOrderFragment;
import com.wwdw.easytimeapplication.fragments.MyFragment;
import com.wwdw.easytimeapplication.fragments.HomeListFragment;
import com.wwdw.easytimeapplication.uitls.MyToast;
import com.wwdw.easytimeapplication.uitls.RgToFmUtils;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private RadioGroup radioGroup;
    private MyFragment myFragment;
    private HomeListFragment newsListFragment;
    private HouseOrderFragment houseOrderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        int index = intent.getIntExtra("index",0);
        radioGroup = findViewById(R.id.main_radiogroup);
        new RgToFmUtils().showTabToFragment(fragmentList(), radioGroup, getSupportFragmentManager(), R.id.ft_content,index);
    }

    private void initView() {
    }

    public List<Fragment> fragmentList() {
        List<Fragment> list = new ArrayList<>();
        myFragment = MyFragment.newInstance("user", "0");
        newsListFragment = HomeListFragment.newInstance("home", "0");
        houseOrderFragment = HouseOrderFragment.newInstance("order", "0");
        list.add(newsListFragment);
        list.add(houseOrderFragment);
        list.add(myFragment);
        return list;
    }

    @Override
    public void onBackPressed() {
        outApp();
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 2000;
    private static long lastClickTime;

    public void outApp() {
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            MyToast.show("Press again to exit");
            lastClickTime = curClickTime;
        } else {
            BaseApplion.getActivityManage().finishAll();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (newsListFragment != null) {
            newsListFragment.onActivityResult(requestCode, resultCode, data);
        }

        if (myFragment != null) {
            myFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}