package com.wwdw.easytimeapplication.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.wwdw.easytimeapplication.base.BaseApplion;
import com.wwdw.easytimeapplication.bean.UserBean;
import com.wwdw.easytimeapplication.uitls.Config;
import com.wwdw.easytimeapplication.uitls.SharedConfig;
import com.wwdw.easytimeapplication.uitls.SharedUtil;


public class LoginModel {

    private final Config config;

    public LoginModel() {
        config = new Config();
    }

    public boolean saveUserInfo(UserBean userBean) {
        try {
            SharedUtil.create(BaseApplion.application).putString(SharedConfig.userName, userBean.getName());
            SharedUtil.create(BaseApplion.application).putString(SharedConfig.password, userBean.getPassword());
            SharedUtil.create(BaseApplion.application).putString(SharedConfig.nickName, userBean.getNickname());
            SharedUtil.create(BaseApplion.application).putString(SharedConfig.age, userBean.getAge());
            SharedUtil.create(BaseApplion.application).putString(SharedConfig.address, userBean.getAddress());
            SharedUtil.create(BaseApplion.application).putBoolean(SharedConfig.isLogin, true);
            SharedUtil.create(BaseApplion.application).putString(SharedConfig.id, userBean.getId());
            SharedUtil.create(BaseApplion.application).putString(SharedConfig.level, userBean.getUserLevelType());
            SharedUtil.create(BaseApplion.application).putString(SharedConfig.heard, TextUtils.isEmpty(userBean.getHeard()) ? "":userBean.getHeard());
            SharedUtil.create(BaseApplion.application).putString(SharedConfig.phone, TextUtils.isEmpty(userBean.getPhone()) ? "":userBean.getPhone());
            return true;
        } catch (Exception e) {
            e.fillInStackTrace();
            return false;
        }
    }

    public UserBean getUserByName(final UserBean userBean) {
        UserBean listBean = null;
        SQLiteDatabase db = config.getDb();
        try {
            Cursor cursorChild = db.query("t_user", new String[]{"Id", "userId", "name", "nickname", "password", "age", "address","heard","level","phone"}, "name = ? ", new String[]{userBean.getName()}, null, null, null);
            if (cursorChild.getCount() > 0) {
                while (cursorChild.moveToNext()) {
                    listBean = new UserBean();
                    listBean.setId(cursorChild.getString(0));
                    listBean.setUserId(cursorChild.getString(1));
                    listBean.setName(cursorChild.getString(2));
                    listBean.setNickname(cursorChild.getString(3));
                    listBean.setPassword(cursorChild.getString(4));
                    listBean.setAge(cursorChild.getString(5));
                    listBean.setAddress(cursorChild.getString(6));
                    listBean.setHeard(cursorChild.getString(7));
                    listBean.setPhone(cursorChild.getString(8));
                    listBean.setUserLevelType(cursorChild.getString(8));
                }
            }
            cursorChild.close();
            cursorChild = null;
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            db.close();
            db = null;
        }
        return listBean;
    }
}
