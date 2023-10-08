package com.wwdw.easytimeapplication.activities;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.adapters.SelectTimeListAdapter;
import com.wwdw.easytimeapplication.adapters.SelectTimeListTitleAdapter;
import com.wwdw.easytimeapplication.base.BaseActivity;
import com.wwdw.easytimeapplication.base.BaseApplion;
import com.wwdw.easytimeapplication.bean.HouseTypeBean;
import com.wwdw.easytimeapplication.bean.SelectTimeBean;
import com.wwdw.easytimeapplication.dialog.DiaogUtil;
import com.wwdw.easytimeapplication.service.ServiceManager;
import com.wwdw.easytimeapplication.uitls.CalendarUtil;
import com.wwdw.easytimeapplication.uitls.GridDividerItemDecoration;
import com.wwdw.easytimeapplication.uitls.MyToast;
import com.wwdw.easytimeapplication.uitls.SharedConfig;
import com.wwdw.easytimeapplication.uitls.SharedUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

public class GradViewActivity extends BaseActivity {
    public String date = CalendarUtil.getInstance().getYMD();
    private RecyclerView mrv, selectGv;
    private ArrayList<SelectTimeBean> mList = new ArrayList<>();
    private SelectTimeListAdapter adapter;
    private HouseTypeBean bean;
    private LinkedHashMap<String, ArrayList<SelectTimeBean>> map;
    private String name;
    private Spinner numSp;
    private TextView htTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grad_view);
        Intent intent = getIntent();
        bean = (HouseTypeBean) intent.getSerializableExtra("bean");
        name = bean.getName();
        setTitleLay(R.id.title_tv, R.id.back_lay, name);
//        setTitleLay(R.id.title_tv, R.id.back_lay, "Swimming pool" + name);
        selectGv = findViewById(R.id.selectGv);
        mrv = findViewById(R.id.mrv);
        htTv = findViewById(R.id.htTv);
        numSp = findViewById(R.id.numSp);
        GridLayoutManager managerM = new GridLayoutManager(GradViewActivity.this, 3);
        selectGv.addItemDecoration(new GridDividerItemDecoration(14, Color.parseColor("#00000000")));
        selectGv.setLayoutManager(managerM);

        adapter = new SelectTimeListAdapter(mList, this);
        selectGv.setAdapter(adapter);

        loadData();
        SelectTimeListTitleAdapter selectTimeListTitleAdapter = new SelectTimeListTitleAdapter(manager.getDefaultTimeList(), this);
        LinearLayoutManager managerTitle = new LinearLayoutManager(this);
        mrv.addItemDecoration(new GridDividerItemDecoration(14, Color.parseColor("#00000000")));
        mrv.setLayoutManager(managerTitle);
        mrv.setAdapter(selectTimeListTitleAdapter);

        adapter.setOnItemClickListeners(new SelectTimeListAdapter.OnItemClickListeners() {
            @Override
            public void itemBean(int pos, SelectTimeBean itemBean) {
                String state = itemBean.getState();
                if (state.equals("1")) {
                    MyToast.show("Has been booked");
                } else {
                    for (int i = 0; i < mList.size(); i++) {
                        SelectTimeBean selectTimeBean = mList.get(i);
                        if (!selectTimeBean.getState().equals("1")) {
                            selectTimeBean.setState("3");
                            mList.set(i, selectTimeBean);
                        }
                    }

                    SelectTimeBean selectTimeBean = mList.get(pos);
                    selectTimeBean.setState("2");
                    mList.set(pos, selectTimeBean);
                    adapter.setList(mList);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        Date date1 = CalendarUtil.getInstance().strToDate2(date);
        String s = CalendarUtil.getInstance().setAddDay(date1, 2);
        htTv.setText(s);
    }

    private void loadData() {
        ArrayList<SelectTimeBean> selectTimeList = manager.getSelectTimeList(bean.getType(), bean.getHouseType(), name, date);
        optState(selectTimeList, new ServiceManager.OnOrderListener() {
            @Override
            public void sucListener(ArrayList<SelectTimeBean> list) {
                mList.clear();
                mList.addAll(list);
                adapter.setList(mList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void errorListener(String msg) {
            }
        });
    }

    public void OnSelectClick(View view) {
        DiaogUtil.showSelectTimeDialogYMD(this, new DiaogUtil.OnSelectLisenter() {
            @Override
            public void OnOkBtn(String time) {
                date = time;
                mList.clear();
                Date date1 = CalendarUtil.getInstance().strToDate2(time);
                String s = CalendarUtil.getInstance().setAddDay(date1, 2);
                htTv.setText(s);
                loadData();
            }
        });
    }

    public void optState(ArrayList<SelectTimeBean> selectTimeList, ServiceManager.OnOrderListener listener) {
        String string = SharedUtil.create(BaseApplion.application).getString(SharedConfig.userName);
        String sql = "isPay = ? and userId = ? and houseTypeId = ? and houseId = ?";
        String[] arr = new String[]{"1", string,bean.getType(),bean.getHouseType()};
        manager.queryYYInfo(sql, arr,new ServiceManager.OnOrderListener() {
            @Override
            public void sucListener(ArrayList<SelectTimeBean> list) {
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    String yyTime = list.get(i).getYyTime();
                    if (!strings.contains(yyTime)) {
                        strings.add(yyTime);
                    }
                }

                for (int i = 0; i < selectTimeList.size(); i++) {
                    SelectTimeBean selectTimeBean = selectTimeList.get(i);
                    if (strings.contains(selectTimeBean.getDay() + " " + selectTimeBean.getTime())) {
                        selectTimeBean.setState("1");
                        selectTimeList.set(i, selectTimeBean);
                    }
                }
                listener.sucListener(selectTimeList);
            }

            @Override
            public void errorListener(String msg) {
                listener.sucListener(selectTimeList);
            }
        });

    }

    public void OnOrderClick(View view) {
        int selectedItemPosition = numSp.getSelectedItemPosition();
        int people = selectedItemPosition + 1;
        int index = -1;
        for (int i = 0; i < mList.size(); i++) {
            SelectTimeBean selectTimeBean = mList.get(i);
            if (selectTimeBean.getState().equals("2")) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            MyToast.show("Please select a time period");
            return;
        }

        SelectTimeBean selectTimeBean = mList.get(index);
        double sum = selectTimeBean.getMoney() * people;
        selectTimeBean.setPeople(people);
        selectTimeBean.setSum(sum);
        Intent intent = new Intent(this, SelectDetailActivity.class);
        intent.putExtra("bean", selectTimeBean);
        startActivityForResult(intent, 12);

    }
}