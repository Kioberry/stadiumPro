package com.wwdw.easytimeapplication.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.base.BaseActivity;

// 图片展示大图
public class ShowBigImgActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_big_img);
        setTitleLay(R.id.title_tv, R.id.back_lay, "图片");
        String path = getIntent().getStringExtra("path");
        ImageView imageView = findViewById(R.id.img_view);
        imageView.setImageBitmap(decodeBitmap(path,700,800));
    }

    public void imgOnclick(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    //对图片压缩处理
    public Bitmap decodeBitmap(String path, int maxWidth, int maxHeight) {
        // 配置转换的信息
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 只解析宽高，不解析内容
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts); // 得到到像素信息是 null ，但是可以得到图像的实际宽高
        // 分别计算宽度的比例和高度的比例
        int w = (int) Math.ceil(opts.outWidth / maxWidth);
        int h = (int) Math.ceil(opts.outHeight / maxHeight);
        if (w > 1 || h > 1) {
            // 该属性接收值必须要 >1  可以实现按照 1/opts.inSampleSize 的大小来解析该图片
            opts.inSampleSize = w > h ? w : h;
        }
        opts.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
        return bitmap;
    }
}