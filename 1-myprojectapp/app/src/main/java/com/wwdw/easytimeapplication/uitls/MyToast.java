package com.wwdw.easytimeapplication.uitls;

import android.content.Context;
import android.widget.Toast;
import com.wwdw.easytimeapplication.base.BaseApplion;

public class MyToast {
    public static void show(Context context,String content){
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
    public static void show(String content){
        Toast.makeText(BaseApplion.application, content, Toast.LENGTH_SHORT).show();
    }
}
