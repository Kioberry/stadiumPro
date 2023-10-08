package com.wwdw.easytimeapplication.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.wwdw.easytimeapplication.base.BaseApplion;
import com.wwdw.easytimeapplication.base.BaseModel;
import com.wwdw.easytimeapplication.bean.UserBean;
import com.wwdw.easytimeapplication.contants.UserLevel;
import com.wwdw.easytimeapplication.preseneter.RegisterPresenter;
import com.wwdw.easytimeapplication.uitls.Config;
import com.wwdw.easytimeapplication.uitls.DataBaseHelper;
import com.wwdw.easytimeapplication.uitls.LogUtils;
import com.wwdw.easytimeapplication.uitls.SharedConfig;
import com.wwdw.easytimeapplication.uitls.SharedUtil;

import java.util.ArrayList;


public class RegisterModel extends BaseModel {


    private final Config config;

    public RegisterModel() {
        config = getConfig();
    }

    public boolean saveUserInfo(UserBean userBean) {
        try {
            SharedUtil.create(BaseApplion.application).putString(SharedConfig.userName, userBean.getName());
            SharedUtil.create(BaseApplion.application).putString(SharedConfig.password, userBean.getPassword());
            return true;
        } catch (Exception e) {
            e.fillInStackTrace();
            return false;
        }
    }


    public int registerUser(UserBean userBean) {
        ArrayList<UserBean> userBeanArrayList = new ArrayList<>();
        SQLiteDatabase db = config.getDb();
        try {
            Cursor cursorChild = db.query("t_user", new String[]{"Id", "name", "password"}, "name = ?", new String[]{userBean.getName()}, null, null, null);
            if (cursorChild.getCount() > 0) {
                while (cursorChild.moveToNext()) {
                    UserBean listBean = new UserBean();
                    listBean.setId(cursorChild.getString(0));
                    listBean.setName(cursorChild.getString(1));
                    listBean.setPassword(cursorChild.getString(2));
                    userBeanArrayList.add(listBean);
                }
            }
            cursorChild.close();
            cursorChild = null;
            db.close();
            db = null;
            if (userBeanArrayList.size() > 0) {
                return -1;
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            return -3;
        }

        SQLiteDatabase db1 = config.getDb();
        try {
            ContentValues cv = new ContentValues();
            cv.put("name", userBean.getName());
            cv.put("nickname", userBean.getNickname());
            cv.put("password", userBean.getPassword());
            cv.put("age", userBean.getAge());
            cv.put("address", userBean.getAddress());
            cv.put("phone", userBean.getPhone());
            cv.put("esm", userBean.getEsm());
            cv.put("sex", userBean.getSex());
            cv.put("level", userBean.getUserLevelType());
            if (!TextUtils.isEmpty(userBean.getHeard())) {
                cv.put("heard", userBean.getHeard());
            }
            long farawayNewWater = db1.insert("t_user", null, cv);
            db1.close();
            db1 = null;
            cv = null;
            if (farawayNewWater == -1 || farawayNewWater == 0) {
                return -2;
            } else {
                return 1;
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            return -3;
        }
    }

    public int updateUser(UserBean userBean) {
        SQLiteDatabase db1 = config.getDb();
        try {
            ContentValues cv = new ContentValues();
            cv.put("phone", userBean.getPhone());
            cv.put("esm", userBean.getEsm());
            cv.put("sex", userBean.getSex());
            if (!TextUtils.isEmpty(userBean.getHeard())) {
                cv.put("heard", userBean.getHeard());
            }
            long farawayNewWater = db1.update(DataBaseHelper.T_USER, cv, "Id = ? ", new String[]{userBean.getId()});
            db1.close();
            db1 = null;
            cv = null;
            LogUtils.i("stf", "--farawayNewWater---->" + farawayNewWater);
            if (farawayNewWater == -1 || farawayNewWater == 0) {
                return -2;
            } else {
                return 1;
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            return -3;
        }
    }

    public UserBean getUserById(final String id) {
        UserBean listBean = null;
        SQLiteDatabase db = config.getDb();
        try {
            Cursor cursorChild = db.query("t_user", new String[]{"Id", "userId", "name", "nickname", "password", "age", "address", "heard", "phone", "esm", "sex"}, "Id = ? ", new String[]{id}, null, null, null);
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
                    listBean.setEsm(cursorChild.getString(9));
                    listBean.setSex(cursorChild.getString(10));
                    listBean.setUserLevelType(UserLevel.level_2);
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

    public void getUserList(String level, RegisterPresenter.OnUserListener listener) {
        ArrayList<UserBean> list = new ArrayList<>();
        config.search(new Config.Todo() {
            @Override
            public void onInOhter() {
                SQLiteDatabase db = config.getDb();
                try {
                    Cursor cursorChild = db.query("t_user", new String[]{"Id", "userId", "name", "nickname", "password", "age", "address", "heard", "phone", "esm", "sex", "level"}, "level = ? ", new String[]{level}, null, null, "Id desc");
                    list.clear();
                    if (cursorChild.getCount() > 0) {
                        while (cursorChild.moveToNext()) {
                            UserBean listBean = new UserBean();
                            listBean.setId(cursorChild.getString(0));
                            listBean.setUserId(cursorChild.getString(1));
                            listBean.setName(cursorChild.getString(2));
                            listBean.setNickname(cursorChild.getString(3));
                            listBean.setPassword(cursorChild.getString(4));
                            listBean.setAge(cursorChild.getString(5));
                            listBean.setAddress(cursorChild.getString(6));
                            listBean.setHeard(cursorChild.getString(7));
                            listBean.setPhone(cursorChild.getString(8));
                            listBean.setEsm(cursorChild.getString(9));
                            listBean.setSex(cursorChild.getString(10));
                            listBean.setUserLevelType(cursorChild.getString(11));
                            list.add(listBean);
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
            }

            @Override
            public String onInMain() {
                if (listener != null) {
                    LogUtils.i("stf","---getUserList-->"+new Gson().toJson(list));
                    listener.suc(list);
                }
                return null;
            }
        });
    }

    public UserBean getUserByName(final UserBean userBean) {
        UserBean listBean = null;
        SQLiteDatabase db = config.getDb();
        try {
            Cursor cursorChild = db.query("t_user", new String[]{"Id", "userId", "name", "nickname", "password", "age", "address", "heard"}, "name = ? ", new String[]{userBean.getName()}, null, null, null);
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
                    listBean.setUserLevelType(UserLevel.level_2);
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

    public int changeUserPwd(String pwd) {
        String id = SharedUtil.create(BaseApplion.application).getString(SharedConfig.id);
        SQLiteDatabase db = config.getDb();
        ContentValues cv = new ContentValues();
        cv.put("password", pwd);
        int t_user = db.update("t_user", cv, "Id = ? ", new String[]{id});
        db.close();
        cv.clear();
        db = null;
        cv = null;
        return t_user;
    }

    public int changeUserNickName(String pwd) {
        String id = SharedUtil.create(BaseApplion.application).getString(SharedConfig.id);
        SQLiteDatabase db = config.getDb();
        ContentValues cv = new ContentValues();
        cv.put("nickname", pwd);
        int t_user = db.update("t_user", cv, "Id = ? ", new String[]{id});
        db.close();
        cv.clear();
        db = null;
        cv = null;
        return t_user;
    }

    public int changeUserImg(String url) {
        String id = SharedUtil.create(BaseApplion.application).getString(SharedConfig.id);
        SQLiteDatabase db = config.getDb();
        ContentValues cv = new ContentValues();
        cv.put("address", url);
        int t_user = db.update("t_user", cv, "Id = ? ", new String[]{id});
        db.close();
        cv.clear();
        db = null;
        cv = null;
        return t_user;
    }

    public void delUser(String id, RegisterPresenter.OnListener listener) {
        config.search(new Config.Todo() {

            private int t_user;

            @Override
            public void onInOhter() {
                SQLiteDatabase db = config.getDb();
                t_user = db.delete("t_user", "Id = ? ", new String[]{id});
                db.close();
                db = null;
            }

            @Override
            public String onInMain() {
                if(t_user != -1 || t_user != 0){
                    listener.suc("成功");
                }else {
                    listener.error("失败");
                }
                return null;
            }
        });

    }
}
