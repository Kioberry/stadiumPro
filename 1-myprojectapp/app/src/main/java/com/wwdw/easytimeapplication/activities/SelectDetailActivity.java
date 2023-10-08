package com.wwdw.easytimeapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.base.BaseActivity;
import com.wwdw.easytimeapplication.bean.SelectTimeBean;
import com.wwdw.easytimeapplication.bean.UserBean;
import com.wwdw.easytimeapplication.service.ServiceManager;
import com.wwdw.easytimeapplication.uitls.CalendarUtil;
import com.wwdw.easytimeapplication.uitls.MyToast;
import java.util.ArrayList;
import java.util.Date;

public class SelectDetailActivity extends BaseActivity {

    private RadioGroup radioGroup;
    private SelectTimeBean bean;
    private String state, type, money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_detail);
        setTitleLay(R.id.title_tv, R.id.back_lay, "Payment");
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if(TextUtils.isEmpty(type)){
            bean = (SelectTimeBean) intent.getSerializableExtra("bean");
        }else {
            state = intent.getStringExtra("state");
            money = intent.getStringExtra("money");
        }
        TextView deailTv = findViewById(R.id.deailTv);
        radioGroup = findViewById(R.id.rg);
        if (TextUtils.isEmpty(type)) {
            deailTv.setText("Location：" + bean.getType().replace("1", "Swimming pool").replace("2", "Fitness Room").replace("3", "Sports hall")
                    + "\r\nType：" + bean.getName() +
                    "\r\nTime：" + bean.getDay() + " " + bean.getTime() +
                    "\r\nTotal:$" + bean.getSum() +
                    "\r\nStatus：NotPaid");
        } else {
            deailTv.setText("Total:$" + money + "\r\n" + "Status:NotPaid" + "\r\nType：" + type.replace("mon","Monthly").replace("year","Annually"));
        }
    }

    public void btnCancleClick(View view) {
        if (TextUtils.isEmpty(type)) {
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            int checkIndex = 0;
            if (checkedRadioButtonId == R.id.cardTv) {
                checkIndex = 0;
            }
            if (checkedRadioButtonId == R.id.wechatTv) {
                checkIndex = 1;
            }
            if (checkedRadioButtonId == R.id.alipayTv) {
                checkIndex = 2;
            }

            bean.setIsForm(checkIndex);
            bean.setIsPay("0");
            manager.saveYYinfo(bean, new ServiceManager.OnListener() {
                @Override
                public void sucListener(String msg) {
                    finish();
                }

                @Override
                public void errorListener(String msg) {

                }
            });
        }
    }

    public void btnOkClick(View view) {
        if (TextUtils.isEmpty(type)) {
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            int checkIndex = 0;
            if (checkedRadioButtonId == R.id.cardTv) {
                checkIndex = 0;
            }
            if (checkedRadioButtonId == R.id.wechatTv) {
                checkIndex = 1;
            }
            if (checkedRadioButtonId == R.id.alipayTv) {
                checkIndex = 2;
            }

            bean.setIsForm(checkIndex);
            bean.setIsPay("1");
            manager.saveYYinfo(bean, new ServiceManager.OnListener() {
                @Override
                public void sucListener(String msg) {
                    MyToast.show(msg);
                    Intent intent = new Intent(SelectDetailActivity.this, HomeActivity.class);
                    intent.putExtra("index", 1);
                    startActivity(intent);
                    finish();

                }

                @Override
                public void errorListener(String msg) {

                }
            });
        }

        if (!TextUtils.isEmpty(type)) {
            manager.loadVip(new ServiceManager.OnVipListener() {
                @Override
                public void sucListener(ArrayList<UserBean> list) {
                    if (list == null || list.size() == 0) {
                        //new
                        String endTime = "";
                        String startTime = CalendarUtil.getInstance().getYMD();
                        if (type.equals("mon")) {
                            endTime = CalendarUtil.getInstance().setAddMonth(new Date(), 1);
                        }

                        if (type.equals("year")) {
                            endTime = CalendarUtil.getInstance().setAddYear(new Date(), 1);
                        }
                        manager.saveVip(endTime, startTime, type, new ServiceManager.OnListener() {
                            @Override
                            public void sucListener(String msg) {
                                MyToast.show(msg);
                                setResult(24);
                                finish();
                            }

                            @Override
                            public void errorListener(String msg) {
                                MyToast.show(msg);
                            }
                        });

                    } else {
                        //add
                        UserBean bean = list.get(0);
                        String endTime = bean.getEndDate();
                        Date date = CalendarUtil.getInstance().strToDate2(endTime);
                        if (type.equals("mon")) {
                            endTime = CalendarUtil.getInstance().setAddMonth(date, 1);
                        }

                        if (type.equals("year")) {
                            endTime = CalendarUtil.getInstance().setAddYear(date, 1);
                        }
                        manager.upVip(endTime, type, new ServiceManager.OnListener() {
                            @Override
                            public void sucListener(String msg) {
                                MyToast.show(msg);
                                setResult(24);
                                finish();
                            }

                            @Override
                            public void errorListener(String msg) {
                                MyToast.show(msg);
                            }
                        });

                    }
                }

                @Override
                public void errorListener(String msg) {
                    MyToast.show(msg);
                }
            });
        }

    }

}