package com.wwdw.easytimeapplication.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.uitls.AppTools;

public class PopWindowUtils extends PopupWindow {

    private Activity mActivity;
    private ContentView conV;
    private View view;
    private boolean canable = true;
    private boolean isDissmiss = true;//是否关闭弹窗标识

    public PopWindowUtils(Activity activity) {
        super(activity);
        mActivity = activity;
        setWidth(AppTools.getScreenWidth(activity) - AppTools.dip2px(activity, 66));
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(false);
        setOutsideTouchable(false);
        setCanable(canable);
        isDissmiss = true;
        //添加弹出、弹入的动画
        setAnimationStyle(R.style.popupwindow);
        setBackgroundDrawable(new ColorDrawable(-00000));
    }

    public PopWindowUtils setCanable(boolean canable) {
        this.canable = canable;
        return this;
    }

    public PopWindowUtils setWidthSize(int width) {
        setWidth(width);
        return this;
    }

    public PopWindowUtils setHeightSize(int height) {
        setHeight(height);
        return this;
    }

    public PopWindowUtils setFocusables(boolean focus) {
        setFocusable(focus);
        return this;
    }


    public PopWindowUtils setConView(int contentView) {
        view = LayoutInflater.from(mActivity).inflate(contentView, null);
        setContentView(view);

        if (conV != null) {
            conV.getContentView(view, this);
        }

        return this;
    }


    public PopWindowUtils setConView(View contentView) {
        setContentView(contentView);
        conV.getContentView(contentView, this);
        return this;
    }

    //添加 布局的时候 同时绘制 宽高
    public PopWindowUtils setConViewMeasureSpec(int layId) {
        View mContentView = LayoutInflater.from(mActivity).inflate(layId, null);
        mContentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        setContentView(mContentView);
        if (conV != null) {
            conV.getContentView(mContentView, this);
        }
        return this;
    }

    public int getMeasuredHeight() {
        return this.getContentView().getMeasuredHeight();
    }

    public int getMeasuredWidth() {
        return this.getContentView().getMeasuredWidth();
    }

    public void showInCenter(View view) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        mActivity.getWindow().setAttributes(lp);
        if (AppTools.getModelXDL()){//演示设备
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public void showDropDown(View view) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        mActivity.getWindow().setAttributes(lp);
        if (AppTools.getModelXDL()){//演示设备
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        showAsDropDown(view);

    }

    public void showDropDownAlpha1(View view) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 1f;
        mActivity.getWindow().setAttributes(lp);
        if (AppTools.getModelXDL()){//演示设备
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        setWidth(AppTools.getScreenWidth(mActivity)/2);
//        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        showAsDropDown(view);
    }


    public void showDropDownFilt(View view) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.5f;
//        if (bgAlpha == 1) {
//            //不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
//            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        } else {
//            //此行代码主要是解决在华为手机上半透明效果无效的bug
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        }
        mActivity.getWindow().setAttributes(lp);

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        int screenHeight = wm.getDefaultDisplay().getHeight();

        setHeight(screenHeight - location[1] - view.getHeight());
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        showAtLocation(view, Gravity.NO_GRAVITY, 0, location[1] + view.getHeight());

    }

    public void showBottom(View view) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        if (AppTools.getModelXDL()){//演示设备
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        mActivity.getWindow().setAttributes(lp);
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 设置显示在v上方(以v的左边距为开始位置)
     *
     * @param v
     */
    public void showUp2(View v) {

        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        mActivity.getWindow().setAttributes(lp);
        if (AppTools.getModelXDL()){//演示设备
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        //获取需要在其上方显示的控件的位置信息
        //获取自身的长宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupHeight = view.getMeasuredHeight();
        int popupWidth = view.getMeasuredWidth();

        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //在控件上方显示
        showAtLocation(v, Gravity.NO_GRAVITY, (location[0]) - popupWidth / 2, location[1]);
    }

    /**
     * 显示在控件的右边
     */
    public void showViewRight(Context context, View view) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        if (AppTools.getModelXDL()){//演示设备
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        mActivity.getWindow().setAttributes(lp);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        showAtLocation(view, Gravity.NO_GRAVITY, 100, 0);
//        showAsDropDown(view, mShowMorePopupWindowWidth, mShowMorePopupWindowHeight,Gravity.NO_GRAVITY);
    }

    public void showBrowseImgnCenter(View view) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0f;
        mActivity.getWindow().setAttributes(lp);
        showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    /**
     * 第一次安装app时授权码密码输入框
     */
    public void showPwdCenter(View view) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        if (AppTools.getModelXDL()){//演示设备
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        mActivity.getWindow().setAttributes(lp);
        setCanable(false);
        setFocusables(true);
        setOutsideTouchable(false);
        showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    /**
     * 点击弹窗以外的地方 控制是否关闭弹窗
     * 注：这里主要用来做popupwindow里放edittext 焦点问题
     * 该方法为不调用的话 默认为true
     */
    public void setEditDissmiss(boolean isDissmissFlag) {
        isDissmiss = isDissmissFlag;
    }

    @Override
    public void dismiss() {
        if (isDissmiss) {//这里做标识 是为了控制点击外部是否关闭弹窗
            super.dismiss();
            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.alpha = 1f;
            mActivity.getWindow().setAttributes(lp);
        }
    }

    public PopWindowUtils setOnViewClickListener(ContentView conV) {
        this.conV = conV;
        return this;
    }
//
//    public PopWindowUtils setConView(int contentView, String title) {
//        view = LayoutInflater.from(mActivity).inflate(contentView, null);
//        if (!TextUtils.isEmpty(title)) {
//            TextView textView = view.findViewById(R.id.tv_title);
//            textView.setText(title);
//        }
//        setContentView(view);
//
//        if (conV != null) {
//            conV.getContentView(view, this);
//        }
//        return this;
//    }

    public interface ContentView {
        void getContentView(View contentView, PopWindowUtils popWindow);
    }
}
