package com.wwdw.easytimeapplication.uitls;

import android.app.Activity;

import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;

public class PhotoUtils {
    public void photo(Activity activity, int max, boolean multiple) {
        PictureSelector.create(activity)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine())
                .setSelectionMode(multiple ? SelectModeConfig.MULTIPLE : SelectModeConfig.SINGLE)
                .setCameraImageFormat("png")
                .isPreviewImage(true)
                .isDisplayCamera(true)
                .setImageSpanCount(4)
                .setMaxSelectNum(max)//; 图片最大选择数量
                .setMinSelectNum(1)//; 图片最小选择数量
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }
}
