package com.wwdw.easytimeapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.activities.HyActivity;
import com.wwdw.easytimeapplication.base.BaseApplion;
import com.wwdw.easytimeapplication.base.BaseFragment;
import com.wwdw.easytimeapplication.bean.UserBean;
import com.wwdw.easytimeapplication.preseneter.RegisterPresenter;
import com.wwdw.easytimeapplication.service.ServiceManager;
import com.wwdw.easytimeapplication.uitls.SharedConfig;
import com.wwdw.easytimeapplication.uitls.SharedUtil;
import java.util.ArrayList;
import androidx.annotation.Nullable;

public class MyFragment extends BaseFragment implements View.OnClickListener {

    private static final String PARAM1 = "param1";
    private static final String PARAM2 = "param2";
    String mParam1, mParam2;
    private TextView outAppTv, nickTv, pwdTv, accountTv, timeTv;
    private LinearLayout lay, item0Tv, item1Tv, item2Tv, hyLay, InfoLay;
    private TextView nameTv;
    private final int mCoder = 11;
    private final int mCoders = 12;
    private ArrayList<String> listPhotoPath;
    private ImageView photoUrlImg;
    private RegisterPresenter registerPresenter;

    public static MyFragment newInstance(String param1, String param2) {
        MyFragment myFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM1, param1);
        bundle.putString(PARAM2, param2);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(PARAM1);
            mParam2 = getArguments().getString(PARAM2);
        }
    }


    @Override
    public int getLayOutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView(View view) {
        lay = view.findViewById(R.id.user_info_lay);
        outAppTv = view.findViewById(R.id.outAppClick_tv);
        accountTv = view.findViewById(R.id.accountTv);
        nickTv = view.findViewById(R.id.nickTv);
        pwdTv = view.findViewById(R.id.pwdTv);
        timeTv = view.findViewById(R.id.timeTv);
        nameTv = view.findViewById(R.id.my_name_tv);
        item0Tv = view.findViewById(R.id.item_0_tv);
        item1Tv = view.findViewById(R.id.item_1_tv);
        item2Tv = view.findViewById(R.id.item_2_tv);
        hyLay = view.findViewById(R.id.hyLay);
        InfoLay = view.findViewById(R.id.InfoLay);
        photoUrlImg = view.findViewById(R.id.my_heard_img);
    }

    @Override
    public void initData() {
        setNickName();
        registerPresenter = new RegisterPresenter();
        setPhotoUrl();
        //查询用户开通VIP的时间
        setVipInfo();
    }

    private void setVipInfo() {
        manager.loadVip(new ServiceManager.OnVipListener() {
            @Override
            public void sucListener(ArrayList<UserBean> list) {
                if (list == null || list.size() == 0) {
                    hyLay.setVisibility(View.GONE);
                    InfoLay.setVisibility(View.VISIBLE);
                    timeTv.setText("");
                } else {
                    timeTv.setText("Expiry Date: " + list.get(0).getEndDate());
                    hyLay.setVisibility(View.VISIBLE);
                    InfoLay.setVisibility(View.GONE);
                }
            }

            @Override
            public void errorListener(String msg) {

            }
        });
    }

    private void setNickName() {
        String nickName = SharedUtil.create(BaseApplion.application).getString(SharedConfig.nickName);
        nameTv.setText("Account:" + nickName);
        accountTv.setText(nickName);
        nickTv.setText(SharedUtil.create(BaseApplion.application).getString(SharedConfig.phone));
    }

    private void setPhotoUrl() {
        String string = SharedUtil.create(BaseApplion.application).getString(SharedConfig.heard, "1");
        Glide
                .with(getActivity())
                .load(string)
                .apply(new RequestOptions().circleCrop().placeholder(R.mipmap.heard))
                .into(photoUrlImg);
    }

    @Override
    public void initListener() {
        lay.setOnClickListener(this);
        outAppTv.setOnClickListener(this);
        hyLay.setOnClickListener(this);
        InfoLay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.outAppClick_tv) {
            BaseApplion.getActivityManage().finishAll();
            SharedUtil.create(BaseApplion.application).clear();
        } else if (id == R.id.hyLay) {
            Intent intent = new Intent(getActivity(), HyActivity.class);
            if (TextUtils.isEmpty(timeTv.getText().toString())) {
                intent.putExtra("state", "new");//new
            } else {
                intent.putExtra("state", "add");//add
            }
            startActivityForResult(intent, 16);
        } else if (id == R.id.InfoLay) {
            Intent intent = new Intent(getActivity(), HyActivity.class);
            intent.putExtra("state", "new");//new
            startActivityForResult(intent, 16);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 204) {
            if (resultCode == 12) {
                setPhotoUrl();
                setNickName();
            }
        }

        if (requestCode == 206) {
            setPhotoUrl();
            setNickName();
        }
        if (requestCode == 16) {
            if(resultCode == 24){
                setVipInfo();
            }
        }
    }
}