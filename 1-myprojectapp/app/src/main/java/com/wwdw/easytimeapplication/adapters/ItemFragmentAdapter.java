package com.wwdw.easytimeapplication.adapters;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;


public class ItemFragmentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> names;
    private List<Fragment> fragments;
    private Context context;

    public ItemFragmentAdapter(FragmentManager fm, ArrayList<String> names, List<Fragment> fragments, Context context) {
        super(fm);
        this.names = names;
        this.fragments = fragments;
        this.context = context;
    }

    public void setList(ArrayList<String> names, List<Fragment> fragments){
        this.names = names;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return names.get(position);
    }
}
