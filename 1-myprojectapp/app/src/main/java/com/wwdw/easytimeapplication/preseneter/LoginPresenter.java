package com.wwdw.easytimeapplication.preseneter;

import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.wwdw.easytimeapplication.base.BaseApplion;
import com.wwdw.easytimeapplication.base.BasePresenter;
import com.wwdw.easytimeapplication.bean.UserBean;
import com.wwdw.easytimeapplication.model.LoginModel;
import com.wwdw.easytimeapplication.uitls.MyToast;
import com.wwdw.easytimeapplication.uitls.SharedConfig;
import com.wwdw.easytimeapplication.uitls.SharedUtil;

import java.util.ArrayList;

public class LoginPresenter extends BasePresenter {
    private LoginModel loginModel;

    public LoginPresenter() {
        if (loginModel == null) {
            loginModel = new LoginModel();
        }
    }

    public boolean saveUser(UserBean userBean) {
        return loginModel.saveUserInfo(userBean);
    }

    public UserBean getUser() {
        String string = SharedUtil.create(BaseApplion.application).getString(SharedConfig.userName, "-1");
        return new UserBean(string, "");
    }

    public boolean getIsLogin() {
        return SharedUtil.create(BaseApplion.application).getBoolean(SharedConfig.isLogin, false);
    }

    public void loginIn(UserBean userBean, OnLoginResultListener loginResultListener) {

        if (TextUtils.isEmpty(userBean.getName())) {
            MyToast.show("Please enter an account");
            return;
        }

        if (TextUtils.isEmpty(userBean.getPassword())) {
            MyToast.show("Please input a password");
            return;
        }

        UserBean queryBean = loginModel.getUserByName(userBean);
        Log.i("stf", "--getUserByName-->" + new Gson().toJson(queryBean));
        if (queryBean == null) {
            MyToast.show("The user does not exist");
            return;
        }

        if (queryBean.getName().equals(userBean.getName()) && queryBean.getPassword().equals(userBean.getPassword())) {
            MyToast.show("Login succeeded");
            loginResultListener.isSucLogin(queryBean);
            return;
        }
        MyToast.show("Account or password error");
    }

    public interface OnLoginResultListener {
        void isSucLogin(UserBean bean);
    }

    public void optDB() {


    }
}
