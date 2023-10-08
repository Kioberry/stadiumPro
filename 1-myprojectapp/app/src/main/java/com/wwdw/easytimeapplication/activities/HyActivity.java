package com.wwdw.easytimeapplication.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.base.BaseActivity;

public class HyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hy);
        setTitleLay(R.id.title_tv, R.id.back_lay, "Membership");

        ImageView img1 = findViewById(R.id.img1);
        TextView monTv = findViewById(R.id.monTv);
        TextView yearTv = findViewById(R.id.yearTv);

        RoundedCorners roundedCorners = new RoundedCorners(20);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        options.error(R.mipmap.loadfail_img);
        Glide.with(this).setDefaultRequestOptions(
                new RequestOptions()
                        .frame(4000000)
                        .centerCrop())
                .load(R.mipmap.bg_1)
                .apply(options)
                .into(img1);

        Intent intent = getIntent();
        String state = intent.getStringExtra("state");
        monTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(HyActivity.this, SelectDetailActivity.class);
                intent1.putExtra("state",state);
                intent1.putExtra("type","mon");
                intent1.putExtra("money","49");
                startActivityForResult(intent1,12);
            }
        });
        yearTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(HyActivity.this, SelectDetailActivity.class);
                intent1.putExtra("state",state);
                intent1.putExtra("type","year");
                intent1.putExtra("money","389");
                startActivityForResult(intent1,12);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode  == 12){
            if(resultCode == 24){
                setResult(24);
                finish();
            }
        }
    }
}