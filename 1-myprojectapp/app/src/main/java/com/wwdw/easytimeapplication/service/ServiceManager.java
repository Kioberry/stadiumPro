package com.wwdw.easytimeapplication.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wwdw.easytimeapplication.base.BaseApplion;
import com.wwdw.easytimeapplication.bean.SelectTimeBean;
import com.wwdw.easytimeapplication.bean.UserBean;

import com.wwdw.easytimeapplication.uitls.CalendarUtil;
import com.wwdw.easytimeapplication.uitls.Config;
import com.wwdw.easytimeapplication.uitls.RandomNumUtils;
import com.wwdw.easytimeapplication.uitls.SharedConfig;
import com.wwdw.easytimeapplication.uitls.SharedUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class ServiceManager {


    public ServiceManager() {

    }


    public ArrayList<SelectTimeBean> getSelectTimeList(String type, String houseId, String name, String day) {
//        private String type;
//        private String name;
//        private String time;
//        private String day;
//        private boolean isCheck;
//        private String state;//1 已经选过 ，2 正在选中， 3 未选中
        ArrayList<SelectTimeBean> list = getDefaultTimeList();
        LinkedHashMap<String, ArrayList<SelectTimeBean>> map = new LinkedHashMap<>();
        //今天
        for (int i = 0; i < list.size(); i++) {
            SelectTimeBean selectTimeBean = list.get(i);
            selectTimeBean.setDay(day);
            selectTimeBean.setType(type);
            selectTimeBean.setHouesType(houseId);
            selectTimeBean.setName(name);
            if (map.containsKey(day)) {
                ArrayList<SelectTimeBean> value = map.get(day);
                value.add(selectTimeBean);
                map.put(day, value);
            } else {
                ArrayList<SelectTimeBean> list1 = new ArrayList<>();
                list1.add(selectTimeBean);
                map.put(day, list1);
            }
        }

        //明天
        Date date = CalendarUtil.getInstance().strToDate(day);
        day = CalendarUtil.getInstance().setAddDay(date, 1);
        List<SelectTimeBean> selectList = deepCopy(list);
        for (int i = 0; i < selectList.size(); i++) {
            SelectTimeBean selectTimeBean = selectList.get(i);
            selectTimeBean.setDay(day);
            selectTimeBean.setType(type);
            selectTimeBean.setName(name);
            if (map.containsKey(day)) {
                ArrayList<SelectTimeBean> value = map.get(day);
                value.add(selectTimeBean);
                map.put(day, value);
            } else {
                ArrayList<SelectTimeBean> list1 = new ArrayList<>();
                list1.add(selectTimeBean);
                map.put(day, list1);
            }
        }
        //后天
        List<SelectTimeBean> selectList2 = deepCopy(list);
        Date date2 = CalendarUtil.getInstance().strToDate(day);
        day = CalendarUtil.getInstance().setAddDay(date2, 1);
        for (int i = 0; i < selectList2.size(); i++) {
            SelectTimeBean selectTimeBean = selectList2.get(i);
            selectTimeBean.setDay(day);
            selectTimeBean.setType(type);
            selectTimeBean.setName(name);
            if (map.containsKey(day)) {
                ArrayList<SelectTimeBean> value = map.get(day);
                value.add(selectTimeBean);
                map.put(day, value);
            } else {
                ArrayList<SelectTimeBean> list1 = new ArrayList<>();
                list1.add(selectTimeBean);
                map.put(day, list1);
            }
        }
        ArrayList<SelectTimeBean> tempList = new ArrayList<>();
        for (String key : map.keySet()) {
            tempList.addAll(map.get(key));
        }
        return tempList;
    }

    public ArrayList<SelectTimeBean> getDefaultTimeList() {
        ArrayList<SelectTimeBean> list = new ArrayList<>();
        list.add(new SelectTimeBean("09:00-10:00"));
        list.add(new SelectTimeBean("10:00-11:00"));
        list.add(new SelectTimeBean("11:00-12:00"));
        list.add(new SelectTimeBean("12:00-13:00"));
        list.add(new SelectTimeBean("13:00-14:00"));
        list.add(new SelectTimeBean("14:00-15:00"));
        list.add(new SelectTimeBean("15:00-16:00"));
        list.add(new SelectTimeBean("16:00-17:00"));
        list.add(new SelectTimeBean("17:00-18:00"));
        list.add(new SelectTimeBean("18:00-19:00"));
        list.add(new SelectTimeBean("19:00-20:00"));
        list.add(new SelectTimeBean("20:00-21:00"));
        return list;
    }

    public static <E> List<E> deepCopy(List<E> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<E> dest = (List<E>) in.readObject();
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
            return src;
        }
    }

    public void saveYYinfo(SelectTimeBean bean, OnListener listener) {
        Config config = new Config();
        config.search(new Config.Todo() {
            private long index = -1;

            @Override
            public void onInOhter() {
                SQLiteDatabase db1 = config.getDb();
                try {
                    ContentValues cv = new ContentValues();
                    cv.put("yyTime", bean.getDay() + " " + bean.getTime());
                    cv.put("houseId", bean.getHouesType());
                    cv.put("houseTypeId", bean.getType());
                    cv.put("people", bean.getPeople());
                    cv.put("money", bean.getSum());
                    cv.put("isPay", bean.getIsPay());
                    cv.put("isForm", bean.getIsForm());
                    cv.put("appNoId", RandomNumUtils.getShopAppNo());
                    cv.put("userId", SharedUtil.create(BaseApplion.application).getString(SharedConfig.userName));
                    cv.put("userName", SharedUtil.create(BaseApplion.application).getString(SharedConfig.nickName));
                    cv.put("mark", bean.getName());

//                    cv.put("yyTime", userBean.getName());
//                    cv.put("houseId", userBean.getNickname());
//                    cv.put("houseTypeId", userBean.getPassword());
//                    cv.put("people", userBean.getAge());
//                    cv.put("money", userBean.getAddress());
//                    cv.put("isPay", userBean.getPhone());
//                    cv.put("isForm", userBean.getEsm());
//                    cv.put("appNoId", userBean.getSex());
//                    cv.put("userId", userBean.getUserLevelType());
//                    cv.put("userName", userBean.getUserLevelType());
//                    cv.put("isUse", userBean.getUserLevelType());
                    index = db1.insert("t_pay_house", null, cv);
                    db1.close();
                    db1 = null;
                    cv = null;
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }

            @Override
            public String onInMain() {
                if (index != -1 && index != 0) {
                    listener.sucListener("success");
                } else {
                    listener.sucListener("fail");
                }
                return null;
            }
        });
    }

    public void queryYYInfo(String sql, String[] arr, OnOrderListener listener) {
        Config config = new Config();
        ArrayList<SelectTimeBean> list = new ArrayList<>();
        config.search(new Config.Todo() {
            @Override
            public void onInOhter() {
                SQLiteDatabase db = config.getDb();
                Cursor cursorChild = db.query("t_pay_house", new String[]{"Id", "appNoId", "userId", "userName", "yyTime", "houseId", "houseTypeId", "people", "money", "isPay", "isForm", "isUse", "mark"}, sql, arr, null, null, null);
                if (cursorChild.getCount() > 0) {
                    while (cursorChild.moveToNext()) {
                        SelectTimeBean listBean = new SelectTimeBean();
                        listBean.setId(cursorChild.getString(0));
                        listBean.setAppNoId(cursorChild.getString(1));
                        listBean.setUserId(cursorChild.getString(2));
                        listBean.setUserName(cursorChild.getString(3));
                        listBean.setYyTime(cursorChild.getString(4));
                        listBean.setType(cursorChild.getString(5));
                        listBean.setHouesType(cursorChild.getString(6));
                        listBean.setPeople(cursorChild.getInt(7));
                        listBean.setMoney(cursorChild.getDouble(8));
                        listBean.setSum(cursorChild.getDouble(8));
                        listBean.setIsPay(cursorChild.getString(9));
                        listBean.setIsForm(cursorChild.getInt(10));
                        listBean.setIsUse(cursorChild.getInt(11));
                        listBean.setMark(cursorChild.getString(12));
                        list.add(listBean);
                    }
                }
                cursorChild.close();
                cursorChild = null;
                db.close();
                db = null;
            }

            @Override
            public String onInMain() {
                listener.sucListener(list);
                return null;
            }
        });
    }


    public void saveVip(String endTime, String startTime, String type, OnListener listener) {
        Config config = new Config();
        config.search(new Config.Todo() {
            private long index = -1;

            @Override
            public void onInOhter() {
                SQLiteDatabase db1 = config.getDb();
                try {
                    ContentValues cv = new ContentValues();
                    cv.put("userId", SharedUtil.create(BaseApplion.application).getString(SharedConfig.userName));
                    cv.put("endDate", endTime);
                    cv.put("startDate", startTime);
                    cv.put("type", type);
                    index = db1.insert("t_vip", null, cv);
                    db1.close();
                    db1 = null;
                    cv = null;
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }

            @Override
            public String onInMain() {
                if (index != -1 && index != 0) {
                    listener.sucListener("success");
                } else {
                    listener.sucListener("fail");
                }
                return null;
            }
        });
    }

    public void upVip(String endTime, String type, OnListener listener) {
        Config config = new Config();
        config.search(new Config.Todo() {
            private long index = -1;

            @Override
            public void onInOhter() {
                SQLiteDatabase db1 = config.getDb();
                try {
                    ContentValues cv = new ContentValues();
                    cv.put("endDate", endTime);
                    cv.put("type", type);
                    index = db1.update("t_vip", cv, "userId = ? ", new String[]{SharedUtil.create(BaseApplion.application).getString(SharedConfig.userName)});
                    db1.close();
                    db1 = null;
                    cv = null;
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }

            @Override
            public String onInMain() {
                if (index != -1 && index != 0) {
                    listener.sucListener("success");
                } else {
                    listener.sucListener("fail");
                }
                return null;
            }
        });
    }

    public void loadVip(OnVipListener listener) {
        Config config = new Config();
        ArrayList<UserBean> list = new ArrayList<>();
        config.search(new Config.Todo() {
            @Override
            public void onInOhter() {
                SQLiteDatabase db = config.getDb();
                String sql = "userId = ?";
                String[] arr = new String[]{SharedUtil.create(BaseApplion.application).getString(SharedConfig.userName)};
                Cursor cursorChild = db.query("t_vip", new String[]{"Id", "userId", "endDate", "type", "startDate"}, sql, arr, null, null, null);
                if (cursorChild.getCount() > 0) {
                    while (cursorChild.moveToNext()) {
                        UserBean listBean = new UserBean();
                        listBean.setVipId(cursorChild.getString(0));
                        listBean.setUserId(cursorChild.getString(1));
                        listBean.setEndDate(cursorChild.getString(2));
                        listBean.setVipType(cursorChild.getString(3));
                        listBean.setStartDate(cursorChild.getString(4));
                        list.add(listBean);
                    }
                }
                cursorChild.close();
                cursorChild = null;
                db.close();
                db = null;
            }

            @Override
            public String onInMain() {
                listener.sucListener(list);
                return null;
            }
        });
    }


    public interface OnListener {
        void sucListener(String msg);

        void errorListener(String msg);
    }

    public interface OnOrderListener {
        void sucListener(ArrayList<SelectTimeBean> list);

        void errorListener(String msg);
    }

    public interface OnVipListener {
        void sucListener(ArrayList<UserBean> list);

        void errorListener(String msg);
    }


























}
