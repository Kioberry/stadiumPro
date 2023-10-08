package com.wwdw.easytimeapplication.uitls;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import com.wwdw.easytimeapplication.base.BaseApplion;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Config {
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
    Handler handler = new Handler();
    private DataBaseHelper helper;

    public void search(final Todo todo) {

        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                todo.onInOhter();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        todo.onInMain();
                    }
                });
            }
        });
    }

    public DataBaseHelper getHelper() {
        if (helper == null) {
            helper = new DataBaseHelper(BaseApplion.application, "database.db", null, 2);
        }
        return helper;
    }


    public SQLiteDatabase getDb() {
        SQLiteDatabase db = getHelper().getWritableDatabase();
        return db;
    }

    public interface Todo {
        void onInOhter();

        String onInMain();
    }

    public void outAppInfo() {
        SharedUtil.create(BaseApplion.application).clear();
        BaseApplion.getActivityManage().finishAll();
    }
}
