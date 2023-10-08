package com.wwdw.easytimeapplication.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.adapters.SwListAdapter;
import com.wwdw.easytimeapplication.base.BaseActivity;
import com.wwdw.easytimeapplication.bean.HouseTypeBean;
import com.wwdw.easytimeapplication.contants.HouseType;
import com.wwdw.easytimeapplication.uitls.DividerItemDecorationUtil;
import com.wwdw.easytimeapplication.view.MyRecycleView;
import java.util.ArrayList;

public class SwimmingActivity extends BaseActivity {

    private MyRecycleView mrv;
    ArrayList<HouseTypeBean> mList = new ArrayList<>();
    private SwListAdapter swListAdapter;
    private TextView sumTv,houseNameTv,houseMarkTv,houseTipTv;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swimming);
        ImageView img1 = findViewById(R.id.img1);
        mrv = findViewById(R.id.mrv);
        sumTv = findViewById(R.id.sumTv);
        houseTipTv = findViewById(R.id.houseTipTv);
        houseNameTv = findViewById(R.id.houseNameTv);
        houseMarkTv = findViewById(R.id.houseMarkTv);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        int img = 0;
        if(type.equals("sw")){
            img = R.mipmap.bg_1;
            houseTipTv.setText("Swimming Pool");
            houseNameTv.setText("Swimming Pool");
            setTitleLay(R.id.title_tv, R.id.back_lay, "Swimming");
            houseMarkTv.setText(getResources().getString(R.string.swmark));

            HouseTypeBean houseTypeBean = new HouseTypeBean();
            houseTypeBean.setImg(R.mipmap.ic_1);
            houseTypeBean.setName("ChooseGeneral Use");
            houseTypeBean.setTime("Time:09:00 - 21:00");
            houseTypeBean.setHouseType(HouseType.sw_1);
            houseTypeBean.setType(HouseType.sw);

            HouseTypeBean houseTypeBean2 = new HouseTypeBean();
            houseTypeBean2.setImg(R.mipmap.ic_1);
            houseTypeBean2.setName("Lane Swim");
            houseTypeBean2.setTime("Time:09:00 - 21:00");
            houseTypeBean2.setHouseType(HouseType.sw_2);
            houseTypeBean2.setType(HouseType.sw);

            HouseTypeBean houseTypeBean3 = new HouseTypeBean();
            houseTypeBean3.setImg(R.mipmap.ic_1);
            houseTypeBean3.setName("Lessons");
            houseTypeBean3.setTime("Time:09:00 - 21:00");
            houseTypeBean3.setHouseType(HouseType.sw_3);
            houseTypeBean3.setType(HouseType.sw);

            HouseTypeBean houseTypeBean4 = new HouseTypeBean();
            houseTypeBean4.setImg(R.mipmap.ic_1);
            houseTypeBean4.setName("Team Events");
            houseTypeBean4.setTime("Time:09:00 - 21:00");
            houseTypeBean4.setHouseType(HouseType.sw_4);
            houseTypeBean4.setType(HouseType.sw);
            mList.add(houseTypeBean);
            mList.add(houseTypeBean2);
            mList.add(houseTypeBean3);
            mList.add(houseTypeBean4);
        }

        if(type.equals("fit")){
            img = R.mipmap.bg_2;
            houseTipTv.setText("Fitness Room");
            houseNameTv.setText("Fitness Room");
            setTitleLay(R.id.title_tv, R.id.back_lay, "Fitness Room");
            houseMarkTv.setText(getResources().getString(R.string.fit));

            HouseTypeBean houseTypeBean = new HouseTypeBean();
            houseTypeBean.setImg(R.mipmap.ic_2);
            houseTypeBean.setName("Dynamic Fitness");
            houseTypeBean.setTime("Time:09:00 - 21:00");
            houseTypeBean.setHouseType(HouseType.fit_1);
            houseTypeBean.setType(HouseType.fit);

            HouseTypeBean houseTypeBean2 = new HouseTypeBean();
            houseTypeBean2.setImg(R.mipmap.ic_2);
            houseTypeBean2.setName("Fit For Life");
            houseTypeBean2.setTime("Time:09:00 - 21:00");
            houseTypeBean2.setHouseType(HouseType.fit_2);
            houseTypeBean2.setType(HouseType.fit);

            mList.add(houseTypeBean);
            mList.add(houseTypeBean2);
        }

        if(type.equals("sport")){
            img = R.mipmap.bg_3;
            houseTipTv.setText("Sports hall");
            houseNameTv.setText("Sports hall");
            houseMarkTv.setText(getResources().getString(R.string.sport));
            setTitleLay(R.id.title_tv, R.id.back_lay, "Sports hall");
            HouseTypeBean houseTypeBean = new HouseTypeBean();
            houseTypeBean.setImg(R.mipmap.ic_3);
            houseTypeBean.setName("Shape Masters");
            houseTypeBean.setTime("Time:09:00 - 21:00");
            houseTypeBean.setHouseType(HouseType.sp_1);
            houseTypeBean.setType(HouseType.sp);

            HouseTypeBean houseTypeBean2 = new HouseTypeBean();
            houseTypeBean2.setImg(R.mipmap.ic_3);
            houseTypeBean2.setName("Gym Life");
            houseTypeBean2.setTime("Time:09:00 - 21:00");
            houseTypeBean2.setHouseType(HouseType.sp_2);
            houseTypeBean2.setType(HouseType.sp);

            mList.add(houseTypeBean);
            mList.add(houseTypeBean2);
        }

        RoundedCorners roundedCorners = new RoundedCorners(20);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        options.error(R.mipmap.loadfail_img);
        Glide.with(this).setDefaultRequestOptions(
                new RequestOptions()
                        .frame(4000000)
                        .centerCrop())
                .load(img)
                .apply(options)
                .into(img1);

        LinearLayoutManager managerMrv = new LinearLayoutManager(this);
        managerMrv.setOrientation(LinearLayoutManager.VERTICAL);
        mrv.addItemDecoration(new DividerItemDecorationUtil(this, false));
        mrv.setLayoutManager(managerMrv);


        sumTv.setText("Capacity："+mList.size());
        swListAdapter = new SwListAdapter(mList, this);
        mrv.setAdapter(swListAdapter);

        swListAdapter.setOnItemClickListeners(new SwListAdapter.OnItemClickListeners() {
            @Override
            public void itemBean(int pos, HouseTypeBean itemBean) {
                Intent intent = new Intent(SwimmingActivity.this, GradViewActivity.class);
                intent.putExtra("bean",itemBean);
                startActivityForResult(intent,12);
            }
        });
    }
}