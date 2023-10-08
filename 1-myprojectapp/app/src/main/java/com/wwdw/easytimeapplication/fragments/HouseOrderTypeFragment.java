package com.wwdw.easytimeapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.adapters.OrderListAdapter;
import com.wwdw.easytimeapplication.base.BaseApplion;
import com.wwdw.easytimeapplication.base.BaseLazyFragment;
import com.wwdw.easytimeapplication.bean.SelectTimeBean;
import com.wwdw.easytimeapplication.service.ServiceManager;
import com.wwdw.easytimeapplication.uitls.DividerItemDecorationUtil;
import com.wwdw.easytimeapplication.uitls.LogUtils;
import com.wwdw.easytimeapplication.uitls.MyToast;
import com.wwdw.easytimeapplication.uitls.SharedConfig;
import com.wwdw.easytimeapplication.uitls.SharedUtil;
import com.wwdw.easytimeapplication.view.MySwipeRefreshLayout;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HouseOrderTypeFragment extends BaseLazyFragment {

    private static final String PARAM1 = "param1";
    private static final String PARAM2 = "param2";
    String mParam1, mParam2;
    private RecyclerView mrv;
    private OrderListAdapter adapter;
    private MySwipeRefreshLayout swip;
    private int upDataIndex = -1;
    protected boolean hidden;

    public static HouseOrderTypeFragment newInstance(String param1, String param2) {
        HouseOrderTypeFragment graphicFragment = new HouseOrderTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM1, param1);
        bundle.putString(PARAM2, param2);
        graphicFragment.setArguments(bundle);
        return graphicFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(PARAM1);
            mParam2 = getArguments().getString(PARAM2);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public int getLayOutIdLazy() {
        return R.layout.fragment_shard;
    }

    @Override
    public void initViewLazy(View view) {
        swip = view.findViewById(R.id.swip_rf);
        mrv = view.findViewById(R.id.mrv);
    }

    @Override
    public void initObjLzay() {

    }

    @Override
    public void initDataLazy() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mrv.addItemDecoration(new DividerItemDecorationUtil(getActivity(), false));
        mrv.setLayoutManager(manager);
        getData();
    }

    @Override
    public void initListenerLazy() {
        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 这里的作用是防止下拉刷新的时候还可以上拉加载
                getData();
            }
        });
    }

    public void getData() {

        String string = SharedUtil.create(BaseApplion.application).getString(SharedConfig.userName);
        LogUtils.i("stf", "--optState--->" + string);
        String sql = null;
        String[] arr = null;
        if (mParam2.equals("1")) {
            sql = "isPay = ? and userId = ?";
            arr = new String[]{"1", string};
        }

        if (mParam2.equals("2")) {
            sql = "isUse = ? and userId = ?";
            arr = new String[]{"1", string};
        }

        if (mParam2.equals("3")) {
            sql =  null;
            arr = null;
        }
        if(manager == null){
            manager = new ServiceManager();
        }
        manager.queryYYInfo(sql, arr, new ServiceManager.OnOrderListener() {
            @Override
            public void sucListener(ArrayList<SelectTimeBean> list) {
                setAdapter(list);
            }

            @Override
            public void errorListener(String msg) {
                MyToast.show(msg);
                getHandlers().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swip.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    public void setAdapter(ArrayList<SelectTimeBean> mList) {
        if (adapter == null) {
            adapter = new OrderListAdapter(mList, getActivity(), mParam2);
            mrv.setAdapter(adapter);
        } else {
            adapter.setList(mList);
//            if (upDataIndex != -1) {//局部刷新
//                adapter.notifyItemChanged(upDataIndex,R.id.shard_follow_tv);
//                upDataIndex = -1;
//            } else {
//                adapter.notifyDataSetChanged();
//            }
            adapter.notifyDataSetChanged();
            getHandlers().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swip.setRefreshing(false);
                }
            }, 1000);
        }
        if (adapter != null) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 201) {
            if (resultCode == 12) {
                getData();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        EventBus.clearCaches();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(Object event) {
        Log.i("stf", "-onEvent--->" + event);
        if ((boolean) event) {
            getData();
        }
    }
}