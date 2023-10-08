package com.wwdw.easytimeapplication.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.activities.ShowBigImgActivity;
import com.wwdw.easytimeapplication.adapters.AddLogPhotoAdapter;
import com.wwdw.easytimeapplication.bean.ImageBean;
import com.wwdw.easytimeapplication.service.ServiceManager;
import com.wwdw.easytimeapplication.uitls.Config;
import com.wwdw.easytimeapplication.uitls.PhotoUtils;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import me.nereo.multi_image_selector.MultiImageSelector;

public abstract class BaseActivity extends FragmentActivity {
    public static Context mContext;
//    private Unbinder unbinder;

    private ViewStub emptyView;
    //    protected LoadingDialog loadingDialog;
//    public LoadingSubmitDialog loadingSubmitDialog;
    public static final int REQUEST_CALL_PERMISSION = 10111; //拨号请求码
    private String telPhone;
    private Config config;
    public ServiceManager manager;
    public Handler handler;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置activity只能竖屏
//        ARouter.getInstance().inject(this);
        mContext = this;
        setTheme(R.style.MyAppTheme);
        //沉浸式状态栏
        initImmersionBar();
        //加入Activity管理器l
        BaseApplion.application.getActivityManage().addActivity(this);
//        loadingDialog = new LoadingDialog(mContext);

//        loadingSubmitDialog = new LoadingSubmitDialog(this);
        //加载当前页面
        setContentView(R.layout.base_activity_layout);
//        ((ViewGroup) findViewById(R.id.fl_content)).addView(getLayoutInflater().inflate(getLayoutId(), null));

//        EventBusUtils.register(this);
        //butterKnife 注册
//        unbinder = ButterKnife.bind(this);
        Html.fromHtml("&yen");
        manager = new ServiceManager();
        config = getConfig();
        config.getHelper();
        handler = new Handler();
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }


    /**
     * //     * 子类接受事件 重写该方法
     * //
     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventBus(Event event) {
//
//        if (event.getAction().equals("outLogin")) {
//            try {
////                OutAppDialog.getInstance().showOutAppDialog(mContext, "您的账号已在其他地方登录");
//                new OutAppDialog().showOutAppDialog(BaseActivity.this, "您的账号已在其他地方登录");
//            } catch (Exception e) {
//
//            }
//        }
//    }

    //***************************************空页面方法*************************************
    protected void hideEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
    }

    /**
     * 空页面被点击
     */
    protected void onPageClick() {
    }

    protected void setTextMsg(int id, String msg) {
        TextView textView = (TextView) findViewById(id);
        textView.setText(msg);
    }

    protected void setTitleLayView(int id) {
        LinearLayout backLay = (LinearLayout) findViewById(id);
        backLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void setTitleLay(int tvId, int layId, String msg) {
        setTextMsg(tvId, msg);
        setTitleLayView(layId);
    }


    /**
     * 沉浸栏颜色
     */
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()  //透明状态栏，不写默认透明色
                .fitsSystemWindows(false)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
                .init();  //必须
    }


    private final int mCoder = 1;
    private final int mCoders = 2;
    private int imgNum = 3;
    private ArrayList<ImageBean> imageBeanList = new ArrayList<>();
    private AddLogPhotoAdapter adapter;
    private ArrayList<String> imgPathList = new ArrayList<>();

    public void clearImgList() {
        imageBeanList.clear();
        if (imgPathList != null) {
            imgPathList.clear();
        }
    }

    public ArrayList<ImageBean> getImageBeanList() {
        for (int i = 0; i < imageBeanList.size(); i++) {
            if (!TextUtils.isEmpty(imageBeanList.get(i).getName())) {
                if (imageBeanList.get(i).getName().equals("add")) {
                    imageBeanList.remove(i);
                    break;
                }
            }
        }
        return imageBeanList;
    }

    public void setImgNum(int imgNum) {
        this.imgNum = imgNum;
    }

    // 图片 拍照 或 从手机相册中选择照片的返回逻辑
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            ArrayList<LocalMedia> localMedia = (ArrayList<LocalMedia>) PictureSelector.obtainSelectorList(data);
            if (imgPathList == null) {
                imgPathList = new ArrayList<>();
            } else {
                imgPathList.clear();
            }
            if (null != localMedia && localMedia.size() > 0) {
                for (int i = 0; i < localMedia.size(); i++) {
                    imgPathList.add(localMedia.get(i).getPath());
                }
                if (!childOptImg()) {
                    setImgList();
                } else {
                    chilImgList();
                }
            }
        }
    }

    public void chilImgList() {

    }

    private void setImgList() {
        imageBeanList.clear();
        for (int i = 0; i < imgPathList.size(); i++) {
            ImageBean imageBean = new ImageBean();
            imageBean.setPath(imgPathList.get(i));
            imageBeanList.add(imageBean);
        }

        if (imageBeanList.size() < imgNum) {
            ImageBean imageBean = new ImageBean();
            imageBean.setName("add");
            imageBeanList.add(imageBean);
        }
        if (adapter != null) {
            adapter.setList(imageBeanList);
            adapter.notifyDataSetChanged();
        }
    }

    public boolean childOptImg() {
        return false;
    }

    public ArrayList<String> getImgList() {
        return imgPathList;
    }


    // 6.0 以上的系统需要动态申请 读写权限
    private void testPermisson() {
        if (ContextCompat.checkSelfPermission(BaseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BaseActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, mCoder);
        } else {
            if (ContextCompat.checkSelfPermission(BaseActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(BaseActivity.this, new String[]{Manifest.permission.CAMERA}, 2);
            } else {
                startCamera();
            }
        }
    }

    // 用户是否授权的逻辑
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case mCoder:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    testPermisson();
                } else {
                    Toast.makeText(BaseActivity.this, "暂无权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case mCoders:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    testPermisson();
                } else {

                    Toast.makeText(BaseActivity.this, "暂无权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }

    private void startCamera() {
        PhotoUtils photoUtils = new PhotoUtils();
        photoUtils.photo(this, imgNum, true);
    }

    private MultiImageSelector getMultiImageSelectorOrigin() {
        return MultiImageSelector.create()
                .showCamera(true) // 是否显示相机. 默认为显示
                .count(imgNum) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                .selectPhoto(true) // 是否主动从相册中选择照片
                .setWaterMarkPrivacy(true)  // 是否添加水印
                .origin(imgPathList);//返回照片集合的路径
    }

    // 更新 已拍照或 选择的图片的数据
    public void setImgGridViewData(GridView gridView) {
        imageBeanList.clear();
        ImageBean imageBean = new ImageBean();
        imageBean.setName("add");
        if (getShopDetailHomeImgList() != null) {
            ArrayList<ImageBean> shopDetailHomeImgList = getShopDetailHomeImgList();
            imageBeanList.addAll(shopDetailHomeImgList);
            imgPathList = new ArrayList<>();
            for (int i = 0; i < shopDetailHomeImgList.size(); i++) {
                imgPathList.add(shopDetailHomeImgList.get(i).getPath());
            }
        }

        imageBeanList.add(imageBean);

        if (getShopDetailChildImgList() != null) {
            imageBeanList.clear();
            ArrayList<ImageBean> shopDetailChildImgList = getShopDetailChildImgList();
            imageBeanList.addAll(shopDetailChildImgList);
            imgPathList = new ArrayList<>();
            for (int i = 0; i < shopDetailChildImgList.size(); i++) {
                imgPathList.add(shopDetailChildImgList.get(i).getPath());
            }
        }

        adapter = new AddLogPhotoAdapter(BaseActivity.this, imageBeanList);
        gridView.setAdapter(adapter);
        adapter.setOnItemListeners(new AddLogPhotoAdapter.OnItemListeners() {
            @Override
            public void path(String path, String name) {
                if (TextUtils.isEmpty(name)) {
                    Intent intent = new Intent(BaseActivity.this, ShowBigImgActivity.class);
                    intent.putExtra("path", path);
                    startActivityForResult(intent, 202);
                } else if (name.equals("add")) {
                    testPermisson();
                }
            }
        });

        adapter.setOnItemDelListener(new AddLogPhotoAdapter.OnItemDelListener() {
            @Override
            public void delPath(int pos, String name) {
                if (TextUtils.isEmpty(name)) {
                    imgPathList.remove(pos);
                    setImgList();
                }
            }
        });
    }

    /**
     * 需要接收事件 重写该方法 并返回true
     */
    protected boolean regEvent() {
        return false;
    }

    public ArrayList<ImageBean> getShopDetailHomeImgList() {
        return null;
    }

    public ArrayList<ImageBean> getShopDetailChildImgList() {
        return null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //注册登录被挤下线广播
//        OutAppWatcherReceiver.registerOutAppWatcherReceiver(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.e("======","onPause");
        try {//关闭全局重新登录提示弹窗
//            OutAppDialog.getInstance().dismissOutAppDialog();
        } catch (Exception e) {
            Log.i("OutAppDialog", "关闭失败");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (unbinder != null) {
//            unbinder.unbind();
//        }
//        EventBusUtils.unregister(this);
//        if (loadingDialog != null) {
//            loadingDialog.dismiss();
//        }
        //将Activity从管理器移除
//        BaseApplication.getApplication().getActivityManage().removeActivity(this);

        try {//关闭全局重新登录提示弹窗
//            OutAppDialog.getInstance().dismissOutAppDialog();
        } catch (Exception e) {
            Log.i("OutAppDialog", "关闭失败");
        }

        try {
//            new PromptDiaogUtil().dialogDismiss();
        } catch (Exception e) {
            Log.i("PromptDiaogUtil", "关闭失败");
        }
//        Glide.with(getApplicationContext()).pauseRequests();
    }

}
