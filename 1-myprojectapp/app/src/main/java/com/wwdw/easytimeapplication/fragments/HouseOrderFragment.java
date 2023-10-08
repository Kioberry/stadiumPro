package com.wwdw.easytimeapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.adapters.ViewPagerAdapter;
import com.wwdw.easytimeapplication.base.BaseFragment;
import com.wwdw.easytimeapplication.bean.OrderTypeFragmentBean;
import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;
import java.util.ArrayList;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class HouseOrderFragment extends BaseFragment implements View.OnClickListener {

    private static final String PARAM1 = "param1";
    private static final String PARAM2 = "param2";
    String mParam1, mParam2;
    private MagicIndicator magicIndicator;
    private CommonNavigatorAdapter commonNavigatorAdapter;
    private ViewPager viewPager;
    public ArrayList<OrderTypeFragmentBean> beanList = new ArrayList<>();

    public static HouseOrderFragment newInstance(String param1, String param2) {
        HouseOrderFragment myFragment = new HouseOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM1, param1);
        bundle.putString(PARAM2, param2);
        myFragment.setArguments(bundle);
        return myFragment;
    }


    @Override
    public int getLayOutId() {
        return R.layout.frgment_house_order_layout;
    }

    @Override
    public void initView(View view) {
        if (getArguments() != null) {
            mParam1 = getArguments().getString(PARAM1);
            mParam2 = getArguments().getString(PARAM2);
        }

        magicIndicator = view.findViewById(R.id.tab_magic_lay);
        viewPager = view.findViewById(R.id.viewpager_lay);
    }

    @Override
    public void initData() {
        getFragmentList();
    }

    private void setAdapter() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigatorAdapter = new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return beanList == null ? 0 : beanList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                View customLayout = LayoutInflater.from(context).inflate(R.layout.simple_pager_title_layout, null);
                final TextView titleText = (TextView) customLayout.findViewById(R.id.title_text);

                OrderTypeFragmentBean bean = beanList.get(index);
                if (bean.getIndex() == -1) {
                    titleText.setText(" " + bean.getName() + " ");
                } else {
                    titleText.setText(bean.getName() + " (" + bean.getIndex() + ")");
                }

                commonPagerTitleView.setContentView(customLayout);
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextColor(Color.parseColor("#12B2B3"));
                        titleText.setTextSize(20);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextColor(Color.parseColor("#1E1F20"));
                        titleText.setTextSize(14);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });
                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setColors(Color.parseColor("#12B2B3"));
                indicator.setLineWidth(50);
                return indicator;
            }
        };

        commonNavigator.setAdapter(commonNavigatorAdapter);
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerAdapter adapters = new ViewPagerAdapter(getChildFragmentManager(), beanList);
        viewPager.setAdapter(adapters);
        viewPager.setOffscreenPageLimit(20);
        final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
//        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
//        fragmentContainerHelper.setDuration(300);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fragmentContainerHelper.handlePageSelected(position);
            }
        });
    }

    private void getFragmentList() {
        beanList.add(new OrderTypeFragmentBean(HouseOrderTypeFragment.newInstance("Paid","1"),"1", "Paid", -1));
        beanList.add(new OrderTypeFragmentBean(HouseOrderTypeFragment.newInstance("Finished","2"),"2", "Finished", -1));
        beanList.add(new OrderTypeFragmentBean(HouseOrderTypeFragment.newInstance("All","3"),"3", "All", -1));
        setAdapter();
    }


    @Override
    public void initListener() {
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            int currentItem = viewPager.getCurrentItem();
            beanList.get(currentItem).getFragment().onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
}