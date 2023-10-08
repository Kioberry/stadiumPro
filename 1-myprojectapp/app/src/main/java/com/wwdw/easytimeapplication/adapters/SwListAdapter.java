package com.wwdw.easytimeapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.base.BaseApplion;
import com.wwdw.easytimeapplication.bean.HouseTypeBean;
import com.wwdw.easytimeapplication.uitls.SharedConfig;
import com.wwdw.easytimeapplication.uitls.SharedUtil;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SwListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<HouseTypeBean> list = new ArrayList<>();
    Context mContext;
    private final String userId;

    public SwListAdapter(ArrayList<HouseTypeBean> mList, Context mContext) {
        list.addAll(mList);
        this.mContext = mContext;
        userId = SharedUtil.create(BaseApplion.application).getString(SharedConfig.userName);
    }

    public void setList(ArrayList<HouseTypeBean> mList) {
        this.list = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewHodler sectionViewHodler = null;
        sectionViewHodler = new ItemViewHodler(LayoutInflater.from(mContext).inflate(R.layout.house_list_item_layout, parent, false), viewType);
        return sectionViewHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final HouseTypeBean itemBean = list.get(position);
        ItemViewHodler itemViewHodler = (ItemViewHodler) holder;
        Glide
                .with(mContext)
                .load(itemBean.getImg())
                .apply(new RequestOptions().circleCrop().placeholder(R.mipmap.loadfail_img))
                .into(itemViewHodler.img);
        itemViewHodler.timeTv.setText(itemBean.getTime());
        itemViewHodler.nameTv.setText(itemBean.getName());
        itemViewHodler.btnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listeners != null) {
                    listeners.itemBean(position, itemBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHodler extends RecyclerView.ViewHolder {

        public TextView btnTv, timeTv, nameTv;
        public ImageView img;
        public int viewType;

        public ItemViewHodler(@NonNull View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            btnTv = itemView.findViewById(R.id.btnTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            nameTv = itemView.findViewById(R.id.nameTv);
            img = itemView.findViewById(R.id.img);
        }

        public int getViewType() {
            return viewType;
        }
    }

    public interface OnItemClickListeners {
        void itemBean(int pos, HouseTypeBean itemBean);
    }

    public OnItemClickListeners listeners;

    public void setOnItemClickListeners(OnItemClickListeners listener) {
        this.listeners = listener;
    }
}
