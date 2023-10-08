package com.wwdw.easytimeapplication.preseneter;

import com.wwdw.easytimeapplication.base.BaseApplion;
import com.wwdw.easytimeapplication.base.BasePresenter;
import com.wwdw.easytimeapplication.bean.UserBean;
import com.wwdw.easytimeapplication.model.RegisterModel;
import com.wwdw.easytimeapplication.uitls.MyToast;
import com.wwdw.easytimeapplication.uitls.SharedConfig;
import com.wwdw.easytimeapplication.uitls.SharedUtil;

import java.util.ArrayList;

public class RegisterPresenter extends BasePresenter {
    private RegisterModel registerModel;

    public RegisterPresenter() {
        if (registerModel == null) {
            registerModel = new RegisterModel();
        }
    }

    public boolean saveUser(UserBean userBean) {
        int i = registerModel.registerUser(userBean);
        if (i == -1) {
            MyToast.show("User already exists");
            return false;
        }

        if (i == -2) {
            MyToast.show("Insert data failed");
            return false;
        }

        if (i == -3) {
            MyToast.show("Program error");
            return false;
        }

        if (i == 1) {
            MyToast.show("login was successful");
            return true;
        }
        MyToast.show("Please fill in the information again");
        return false;
    }

    public boolean updateUser(UserBean userBean) {
        int i = registerModel.updateUser(userBean);
        if (i == -2) {
            MyToast.show("Update failed");
            return false;
        }

        if (i == -3) {
            MyToast.show("Program error");
            return false;
        }

        if (i == 1) {
            MyToast.show("success");
            return true;
        }
        MyToast.show("try again");
        return false;
    }

    public void getUserList(String type, OnUserListener listener) {
        registerModel.getUserList(type, new OnUserListener() {
            @Override
            public void suc(ArrayList<UserBean> list) {
                listener.suc(list);
            }

            @Override
            public void error(String msg) {
                listener.error(msg);
            }
        });

    }

    public boolean changePwd(String pwd) {
        int i = registerModel.changeUserPwd(pwd);
        if (i == -1 || i == 0) {
            MyToast.show("Modification failed, please try again");
            return false;
        } else {
            MyToast.show("Successfully modified, please log in again");
            return true;
        }
    }

    public boolean changeNickName(String nickName) {
        int i = registerModel.changeUserNickName(nickName);
        if (i == -1 || i == 0) {
            MyToast.show("Modification failed, please try again");
            return false;
        } else {
            MyToast.show("Successfully modified");
            return true;
        }
    }

    public int changePhoto(String pwd) {
        return registerModel.changeUserImg(pwd);
    }

    public void del(String id,  OnListener listener) {
        registerModel.delUser(id, new OnListener() {
            @Override
            public void suc(String msg) {
                listener.suc(msg);
            }

            @Override
            public void error(String msg) {
                listener.error(msg);
            }
        });
    }

    public UserBean getUserBean() {
        return registerModel.getUserById(SharedUtil.create(BaseApplion.application).getString(SharedConfig.id));
    }

    public interface OnUserListener {
        void suc(ArrayList<UserBean> list);

        void error(String msg);
    }

    public interface OnListener {
        void suc(String msg);

        void error(String msg);
    }
}
