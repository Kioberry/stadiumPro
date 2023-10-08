package com.wwdw.easytimeapplication.bean;

import java.io.Serializable;
import androidx.fragment.app.Fragment;

public class OrderTypeFragmentBean implements Serializable {
    public Fragment fragment;
    public String fragmentType;
    public String name;
    public int index = -1;
    public String mark;

    public OrderTypeFragmentBean(Fragment fragment, String fragmentType, String name, int index) {
        this.fragment = fragment;
        this.fragmentType = fragmentType;
        this.name = name;
        this.index = index;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getFragmentType() {
        return fragmentType;
    }

    public void setFragmentType(String fragmentType) {
        this.fragmentType = fragmentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
