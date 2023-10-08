package com.wwdw.easytimeapplication.uitls;

import android.Manifest;
import android.app.Activity;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import java.util.List;
import androidx.annotation.NonNull;

public class PermissionsUtils {
    public static void photo(final Activity activity, final OnPermissionsListener listener) {
        String[] strings = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
        XXPermissions.with(activity).permission(strings).request(new OnPermissionCallback() {
            @Override
            public void onGranted(List<String> permissions, boolean allGranted) {
                if (!allGranted) {
                    listener.errorMsg("获取部分权限成功，但部分权限未正常授予");
                    return;
                }
                listener.suc();
            }

            @Override
            public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                if (doNotAskAgain) {
                    listener.errorMsg("被永久拒绝授权，请手动授予打开权限");
                    // 如果是被永久拒绝就跳转到应用权限系统设置页面
                    XXPermissions.startPermissionActivity(activity, permissions);
                } else {
                    listener.errorMsg("获取权限失败");
                }
            }
        });
    }

    public interface OnPermissionsListener {
        void suc();

        void errorMsg(String msg);
    }
}
