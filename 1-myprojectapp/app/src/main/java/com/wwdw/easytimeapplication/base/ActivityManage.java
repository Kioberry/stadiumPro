package com.wwdw.easytimeapplication.base;

import android.app.Activity;
import java.util.ArrayList;
import java.util.Stack;

public class ActivityManage {

    //保存所有创建的Activity
   // private ArrayList<Activity> allActivities = new ArrayList<>();
    private Stack<Activity> allActivities = new Stack<>();

    /**
     * 添加Activity到管理器
     *
     * @param activity activity
     */
    public void addActivity(Activity activity) {
        if (activity != null) {
            allActivities.add(activity);
        }
    }


    /**
     * 从管理器移除Activity
     *
     * @param activity activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            allActivities.remove(activity);
            activity.finish();
        }
    }

    /**
     * 关闭所有Activity
     */
    public void finishAll() {
        for (Activity activity : allActivities) {
            activity.finish();
        }

    }
    /**
     * 关闭所有Activity
     */
    public Activity getCurrentActivity() {
       return allActivities.lastElement();

    }


    public ArrayList<Activity> getAllActivities() {
        ArrayList<Activity> list = new ArrayList<>();
        if(allActivities.size()>0){
            for (Activity a:allActivities) {
                list.add(a);
            }
        }
        return list;
    }
}
