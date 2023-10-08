package com.wwdw.easytimeapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.base.BaseActivity;

//选择场所类型
public class SelectHouseListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_house_list);
        setTitleLay(R.id.title_tv, R.id.back_lay, "Facility&Appointment");
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");

    }

    public void OnBtnClick(View view) {
        Intent intent = new Intent(this, SwimmingActivity.class);
        intent.putExtra("type","sw");
        startActivityForResult(intent, 41);
    }

    public void OnBtn2Click(View view) {
        Intent intent = new Intent(this, SwimmingActivity.class);
        intent.putExtra("type","fit");
        startActivityForResult(intent, 41);
    }

    public void OnBtn3Click(View view) {
        Intent intent = new Intent(this, SwimmingActivity.class);
        intent.putExtra("type","sport");
        startActivityForResult(intent, 41);
    }
}