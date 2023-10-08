package com.wwdw.easytimeapplication.uitls;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    //数据库表名
    public static final String T_USER = "t_user";
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //用户表
        db.execSQL("create table t_user("
                + "Id INTEGER PRIMARY KEY AUTOINCREMENT," // 自增id
                + "userId varchar,"//id
                + "name varchar,"//姓名
                + "nickname varchar,"//昵称
                + "password varchar,"//密码
                + "level varchar,"//用户等级
                + "age varchar,"//年龄
                + "sex varchar,"//性别
                + "address varchar,"// 地址
                + "phone varchar,"// 手机号
                + "esm varchar,"// 邮箱
                + "heard varchar,"// 头像
                + "time varchar)");//时间

        db.execSQL("create table t_pay_house("
                + "Id INTEGER PRIMARY KEY AUTOINCREMENT," // 自增id
                + "appNoId varchar,"//订单id
                + "userId varchar,"//id 收货人id
                + "userName varchar,"//收货姓名
                + "userPhone varchar,"//收货人电话
                + "userAddress varchar,"//收货人地址
                + "userMark varchar,"//收货人备注
                + "receivingTime varchar,"//预计收货时间
                + "addTime varchar,"//添加时间
                + "yyTime varchar,"//预约时间
                + "houseId varchar,"//添加的商品id
                + "houseTypeId varchar,"//添加的商品id
                + "people varchar default(0),"//人数
                + "money varchar default(0),"//商品总价格
                + "isSend varchar default(0),"//是否被发货  0 没返货；1 发货
                + "isPay varchar default(0),"//是否付款  0 没付款 ；1 付款
                + "isReturn varchar default(0),"//是否退单  0 没退单 ；1 退单
                + "isReceiving varchar default(0),"//是否确认收货  0 没确定 ；1 确认
                + "isBill varchar default(0),"//是否开票  0 不开票 ；1 开票
                + "isForm varchar default(0),"//支付方式  0 银行卡 ；1 微信，2 支付宝
                + "isUse varchar default(0),"//是否使用 0未使用；1 使用
                + "mark varchar)");//其他备注

        //vip 表
        db.execSQL("create table t_vip("
                + "Id INTEGER PRIMARY KEY AUTOINCREMENT," // 自增id
                + "userId varchar,"//用户Id
                + "type varchar,"//
                + "endDate varchar,"//VIP截止时间
                + "startDate varchar,"//VIP开始时间
                + "time varchar)");//




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
