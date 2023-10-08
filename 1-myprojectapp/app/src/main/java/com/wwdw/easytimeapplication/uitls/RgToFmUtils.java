package com.wwdw.easytimeapplication.uitls;

import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.wwdw.easytimeapplication.base.BaseApplion;
import java.util.List;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class RgToFmUtils {

    private int showindex = 0;
    private int hideindex = 0;
    private List<Fragment> fragmentList;
    private FragmentManager supportFragmentManager;
    private int id;
    private RadioGroup radioGroup;
    private FragmentTransaction transaction;
    private Fragment fragment, currentFragment;

    public RgToFmUtils() {
    }

    private static RgToFmUtils mInstance;

//    public static RgToFmUtils getInstance() {
//        if (mInstance == null) {
//            //实例化对象
//            //加上一个同步锁，只能有一个执行路径进入
//            synchronized (RgToFmUtils.class) {
//                if (mInstance == null) {
//                    mInstance = new RgToFmUtils();
//                }
//            }
//        }
//        return mInstance;
//    }


    public void showTabToFragment(List<Fragment> fragmentList, RadioGroup radioGroup, FragmentManager supportFragmentManager, int id) {
        this.radioGroup = radioGroup;
        this.id = id;
        this.fragmentList = fragmentList;
        this.supportFragmentManager = supportFragmentManager;
        currentFragment = null;
        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);//初始化选中第一个
        addFragment(String.valueOf(0));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                radioButton.setChecked(true);
                int i = group.indexOfChild(radioButton);
                addFragment(String.valueOf(i));
            }
        });
    }

    public void showTabToFragment(List<Fragment> fragmentList, RadioGroup radioGroup, FragmentManager supportFragmentManager, int id,int pos) {
        this.radioGroup = radioGroup;
        this.id = id;
        this.fragmentList = fragmentList;
        this.supportFragmentManager = supportFragmentManager;
        currentFragment = null;
        ((RadioButton) radioGroup.getChildAt(pos)).setChecked(true);//初始化选中第一个
        addFragment(String.valueOf(pos));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                radioButton.setChecked(true);
                int i = group.indexOfChild(radioButton);
                addFragment(String.valueOf(i));
            }
        });
    }

    private void addFragment(String fTag) {
        try {
            //判断这个标签是否存在Fragment对象,如果存在则返回，不存在返回null
            fragment = supportFragmentManager.findFragmentByTag(fTag);
            // 如果这个fragment不存于栈中
            if (fragment == null) {
                //初始化Fragment事物
                transaction = supportFragmentManager.beginTransaction();
                //根据RaioButton点击的Button传入的tag，实例化，添加显示不同的Fragment
                fragment = fragmentList.get(Integer.parseInt(fTag));
                //在添加之前先将上一个Fragment隐藏掉
                if (currentFragment != null) {
                    transaction.hide(currentFragment);
                }
                transaction.add(id, fragment, fTag);
                transaction.commit();
                //更新可见
                currentFragment = fragment;
            } else {
                //如果添加的Fragment已经存在，则将隐藏掉的Fragment再次显示,其余当前
                transaction = supportFragmentManager.beginTransaction();
                transaction.show(fragment);
                transaction.hide(currentFragment);
                transaction.commit();
                //更新可见
                currentFragment = fragment;
            }
        }catch (Exception e){
            e.fillInStackTrace();
            Log.i("stf","--addFragment-->"+e.fillInStackTrace());
            BaseApplion.getActivityManage().finishAll();
        }
    }
}
