package com.wwdw.easytimeapplication.base;

import android.app.Application;
import com.wwdw.easytimeapplication.BuildConfig;
import com.wwdw.easytimeapplication.uitls.CrashHandler;
import com.wwdw.easytimeapplication.uitls.CrashHandlerManage;
import androidx.multidex.MultiDex;

public class BaseApplion extends Application {
    public static BaseApplion application;
    public static ActivityManage activityManage;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        MultiDex.install(this);
        if(BuildConfig.DEBUG){
//            registerActivityLifecycleCallbacks(new ShowClassNameCallBack());
            CrashHandlerManage.getInstance()
                    .init(getApplicationContext());
            CrashHandler.getInstance().setDelayTime(1000 * 60 * 1).init(getApplicationContext());
        }
    }

    public static ActivityManage getActivityManage() {
        if (activityManage == null) {
            activityManage = new ActivityManage();
        }
        return activityManage;
    }
}
