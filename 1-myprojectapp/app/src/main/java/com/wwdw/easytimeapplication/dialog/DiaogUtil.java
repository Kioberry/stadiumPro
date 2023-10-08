package com.wwdw.easytimeapplication.dialog;

import android.app.Activity;
import android.view.View;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.uitls.CalendarUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DiaogUtil {
    private PopWindowUtils popWindowUtils;


    public static void showSelectTimeDialogYMD(Activity activity, final OnSelectLisenter lisenter) {
        Calendar selectedDate = Calendar.getInstance();
        CalendarUtil instance = CalendarUtil.getInstance();
        Date date1 = instance.strToDate2(instance.getYMD());
        selectedDate.setTime(date1);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        boolean[] booleans = {true, true, true, false, false, false};
        TimePickerView pvTime = new TimePickerBuilder(activity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v1) {
                String time = dateFormat.format(date);
                lisenter.OnOkBtn(time);
            }



        }).setSubmitColor(R.color.app_yellow)
                .setCancelColor(R.color.app_yellow)
                .setDate(selectedDate)
                .setType(booleans)
                .isCyclic(true)
                .build();
        pvTime.show();
    }

    public interface OnSelectLisenter {
        void OnOkBtn(String time);
    }

}
