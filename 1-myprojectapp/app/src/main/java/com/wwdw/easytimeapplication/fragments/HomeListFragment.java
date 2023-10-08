package com.wwdw.easytimeapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.activities.SelectHouseListActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//场馆展示
public class HomeListFragment extends Fragment {

    private static final String PARAM1 = "param1";
    private static final String PARAM2 = "param2";
    String mParam1, mParam2;
    private TextView titleTv;
    private ImageView img1, img2, img3;

    public static HomeListFragment newInstance(String param1, String param2) {
        HomeListFragment myFragment = new HomeListFragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleTv = view.findViewById(R.id.heard_title_tv);
        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        img3 = view.findViewById(R.id.img3);
        setImg();
        titleTv.setText(mParam1);
    }

    private void setImg() {
        RoundedCorners roundedCorners = new RoundedCorners(20);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        options.error(R.mipmap.loadfail_img);
        Glide.with(getActivity()).setDefaultRequestOptions(
                new RequestOptions()
                        .frame(4000000)
                        .centerCrop())
                .load(R.mipmap.bg_1)
                .apply(options)
                .into(img1);
        Glide.with(getActivity()).setDefaultRequestOptions(
                new RequestOptions()
                        .frame(4000000)
                        .centerCrop())
                .load(R.mipmap.bg_2)
                .apply(options)
                .into(img2);
        Glide.with(getActivity()).setDefaultRequestOptions(
                new RequestOptions()
                        .frame(4000000)
                        .centerCrop())
                .load(R.mipmap.bg_3)
                .apply(options)
                .into(img3);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
    }

    public void initListener() {
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectHouseListActivity.class);
                intent.putExtra("type","1");
                startActivityForResult(intent,31);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectHouseListActivity.class);
                intent.putExtra("type","2");
                startActivityForResult(intent,32);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectHouseListActivity.class);
                intent.putExtra("type","3");
                startActivityForResult(intent,33);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}