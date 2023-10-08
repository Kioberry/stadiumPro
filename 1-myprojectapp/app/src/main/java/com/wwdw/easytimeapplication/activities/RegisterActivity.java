package com.wwdw.easytimeapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.base.BaseActivity;
import com.wwdw.easytimeapplication.bean.UserBean;
import com.wwdw.easytimeapplication.contants.UserLevel;
import com.wwdw.easytimeapplication.preseneter.RegisterPresenter;
import com.wwdw.easytimeapplication.uitls.ImageUtil;
import com.wwdw.easytimeapplication.uitls.MyToast;
import com.wwdw.easytimeapplication.uitls.PermissionsUtils;
import com.wwdw.easytimeapplication.uitls.PhotoUtils;
import com.wwdw.easytimeapplication.view.RoundImageView;
import java.util.ArrayList;

public class RegisterActivity extends BaseActivity {

    private EditText emEdit, userNameEdit, passwordEdit, phoneEdit, addressEdit;
    private RegisterPresenter presenter;
    private RadioGroup rg;
    private RoundImageView addImg;
    private ImageView addImg2;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitleLay(R.id.title_tv,R.id.back_lay,"Registered Users");
        userNameEdit = findViewById(R.id.tv_username);
        passwordEdit = findViewById(R.id.tv_userpassword);
        emEdit = findViewById(R.id.tv_em);
        phoneEdit = findViewById(R.id.tv_phone);
        rg = findViewById(R.id.rg_rg);
        addImg = findViewById(R.id.addImg);
        addImg2 = findViewById(R.id.addImg2);
//        addressEdit = findViewById(R.id.tv_userAddress);
        presenter = new RegisterPresenter();
        addImg.setVisibility(View.GONE);
        addImg2.setVisibility(View.VISIBLE);
        ImageUtil.showImageView(RegisterActivity.this, R.mipmap.add_img,addImg2);
        addImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                path = "";
                PermissionsUtils.photo(RegisterActivity.this, new PermissionsUtils.OnPermissionsListener() {
                    @Override
                    public void suc() {
                        PhotoUtils photoUtils = new PhotoUtils();
                        photoUtils.photo(RegisterActivity.this, 1,true);
                    }

                    @Override
                    public void errorMsg(String msg) {

                    }
                });
            }
        });
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                path = "";
                PermissionsUtils.photo(RegisterActivity.this, new PermissionsUtils.OnPermissionsListener() {
                    @Override
                    public void suc() {
                        PhotoUtils photoUtils = new PhotoUtils();
                        photoUtils.photo(RegisterActivity.this, 1,true);
                    }

                    @Override
                    public void errorMsg(String msg) {

                    }
                });
            }
        });
    }


    public void btnRegisterOnClick(View view) {
        String userName = userNameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String em = emEdit.getText().toString();
        String phone = phoneEdit.getText().toString();
        String address = "beijing";
        String age = "24";
        if(TextUtils.isEmpty(userName)){
            MyToast.show("Account cannot be empty");
            return;
        }

        if(TextUtils.isEmpty(password)){
            MyToast.show("Password cannot be empty");
            return;
        }

        if(TextUtils.isEmpty(phone)){
            MyToast.show("Contact information cannot be empty");
            return;
        }

        UserBean userBean = new UserBean();
        userBean.setName(userName);
        userBean.setPassword(password);
        userBean.setEsm(em);
        userBean.setNickname(userName);
        userBean.setAddress(address);
        userBean.setAge(age);
        userBean.setPhone(phone);
        userBean.setUserLevelType(UserLevel.level_2);
        if(!TextUtils.isEmpty(path)){
            userBean.setHeard(path);
        }
        int checkedRadioButtonId = rg.getCheckedRadioButtonId();
        if(checkedRadioButtonId == R.id.rb_1){
            userBean.setSex("Man");
        }else {
            userBean.setSex("Woman");
        }

        if(presenter.saveUser(userBean)){
            Intent intent = new Intent();
            intent.putExtra("userBean",userBean);
            setResult(12,intent);
            finish();
        }
    }

    @Override
    public boolean childOptImg() {
        return true;
    }

    @Override
    public void chilImgList() {
        ArrayList<String> imgList = getImgList();
        Log.i("stf","--回调了-->"+imgList);
        if(imgList.size() !=0){
            path = imgList.get(0);
            addImg.setVisibility(View.VISIBLE);
            addImg2.setVisibility(View.GONE);
            ImageUtil.showImageView(RegisterActivity.this, path,addImg,R.mipmap.loadfail_img);
        }
    }

}