package com.wwdw.easytimeapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.bean.ImageBean;
import java.util.ArrayList;

public class AddLogPhotoAdapter extends BaseAdapter {

    private Context mContext;
    private ViewHolder holder;
    private ArrayList<ImageBean> list;

    public AddLogPhotoAdapter(Context context, ArrayList<ImageBean> moduleBeansList) {
        mContext = context;
        this.list = moduleBeansList;
    }

    public void setList(ArrayList<ImageBean> moduleBeansList) {
        this.list = moduleBeansList;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.img_gv, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ImageBean imageBean = list.get(position);
        if (TextUtils.isEmpty(imageBean.path)) {
            holder.mImageView.setImageResource(R.mipmap.bg_add_img);
        } else {
            String path = list.get(position).path;
            holder.mImageView.setImageBitmap(decodeBitmap(path, 200, 200));
        }

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listeners != null) {
                    listeners.path(imageBean.path, imageBean.name);
                }
            }
        });

        holder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (delListener != null) {
                    delListener.delPath(position, imageBean.name);
                }
                return false;
            }
        });
        return convertView;
    }

    class ViewHolder {
        private ImageView mImageView;

        public ViewHolder(View convertView) {
            mImageView = (ImageView) convertView.findViewById(R.id.item);
        }
    }

    public interface OnItemListeners {
        void path(String path, String name);
    }

    public interface OnItemDelListener {
        void delPath(int pos, String name);
    }

    public OnItemListeners listeners;
    public OnItemDelListener delListener;

    public void setOnItemListeners(OnItemListeners listeners) {
        this.listeners = listeners;
    }

    public void setOnItemDelListener(OnItemDelListener listener) {
        this.delListener = listener;
    }

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
