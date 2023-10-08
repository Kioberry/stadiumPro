package com.wwdw.easytimeapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.base.BaseActivity;
import com.wwdw.easytimeapplication.bean.UserBean;
import com.wwdw.easytimeapplication.preseneter.LoginPresenter;
import com.wwdw.easytimeapplication.uitls.MyToast;
import com.wwdw.easytimeapplication.uitls.PermissionsUtils;
import androidx.annotation.Nullable;

public class LoginActivity extends BaseActivity {
    EditText usertNameEdit, usertPasswordEdit;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usertNameEdit = findViewById(R.id.tv_username);
        usertPasswordEdit = findViewById(R.id.tv_userpassword);
        loginPresenter = new LoginPresenter();
        isLoginMsg();
    }

    private void isLoginMsg() {
        if (loginPresenter.getIsLogin()) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            UserBean user = loginPresenter.getUser();
            if (!TextUtils.isEmpty(user.getName()) && !user.getName().equals("-1")) {
                usertNameEdit.setText(user.getName());
            }
        }
    }

    public void OnRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(intent, 99);
    }

    public void btnLoginOnClick(View view) {
        PermissionsUtils.photo(LoginActivity.this, new PermissionsUtils.OnPermissionsListener() {
            @Override
            public void suc() {
                loginPresenter.loginIn(new UserBean(usertNameEdit.getText().toString(), usertPasswordEdit.getText().toString()), new LoginPresenter.OnLoginResultListener() {
                    @Override
                    public void isSucLogin(UserBean bean) {
                        loginPresenter.saveUser(bean);
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void errorMsg(String msg) {
                MyToast.show(msg);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99) {
            if (resultCode == 12) {
                UserBean userBean = (UserBean) data.getSerializableExtra("userBean");
                usertNameEdit.setText(userBean.getName());
                usertPasswordEdit.setText(userBean.getPassword());
            }
        }
    }

}