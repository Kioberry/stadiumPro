package com.wwdw.easytimeapplication.adapters;


import com.wwdw.easytimeapplication.bean.OrderTypeFragmentBean;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<OrderTypeFragmentBean> beanList ;

    public ViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<OrderTypeFragmentBean> beanList) {
        super(fm);
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return beanList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return beanList.size();
    }
}
