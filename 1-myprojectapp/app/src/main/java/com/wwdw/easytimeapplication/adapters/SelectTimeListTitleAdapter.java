package com.wwdw.easytimeapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wwdw.easytimeapplication.R;
import com.wwdw.easytimeapplication.base.BaseApplion;
import com.wwdw.easytimeapplication.bean.SelectTimeBean;
import com.wwdw.easytimeapplication.uitls.SharedConfig;
import com.wwdw.easytimeapplication.uitls.SharedUtil;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectTimeListTitleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<SelectTimeBean> list = new ArrayList<>();
    Context mContext;
    private final String userId;

    public SelectTimeListTitleAdapter(ArrayList<SelectTimeBean> mList, Context mContext) {
        list.addAll(mList);
        this.mContext = mContext;
        userId = SharedUtil.create(BaseApplion.application).getString(SharedConfig.userName);
    }

    public void setList(ArrayList<SelectTimeBean> mList) {
        this.list = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewHodler sectionViewHodler = null;
        sectionViewHodler = new ItemViewHodler(LayoutInflater.from(mContext).inflate(R.layout.select_time_item_layout, parent, false), viewType);
        return sectionViewHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final SelectTimeBean itemBean = list.get(position);
        ItemViewHodler itemViewHodler = (ItemViewHodler) holder;
        itemViewHodler.timeTv.setText(itemBean.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHodler extends RecyclerView.ViewHolder {

        public TextView timeTv;
        public LinearLayout lay;
        public int viewType;

        public ItemViewHodler(@NonNull View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            lay = itemView.findViewById(R.id.lay);
            timeTv = itemView.findViewById(R.id.timeTv);
        }

        public int getViewType() {
            return viewType;
        }
    }
}
