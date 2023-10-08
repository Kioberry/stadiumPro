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

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<SelectTimeBean> list = new ArrayList<>();
    Context mContext;
    private String type;
    private final String userId;

    public OrderListAdapter(ArrayList<SelectTimeBean> mList, Context mContext,String type) {
        list.addAll(mList);
        this.mContext = mContext;
        userId = SharedUtil.create(BaseApplion.application).getString(SharedConfig.userName);
        this.type = type;
    }

    public void setList(ArrayList<SelectTimeBean> mList) {
        this.list = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewHodler sectionViewHodler = null;
        sectionViewHodler = new ItemViewHodler(LayoutInflater.from(mContext).inflate(R.layout.order_list_item_layout, parent, false), viewType);
        return sectionViewHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final SelectTimeBean itemBean = list.get(position);
        ItemViewHodler itemViewHodler = (ItemViewHodler) holder;
        if(type.equals("1")){
            itemViewHodler.deailTv.setText("Location：" + itemBean.getType().replace("1", "Swimming pool")
                    + "\r\nType：" + itemBean.getMark() +
                    "\r\nTime：" +itemBean.getYyTime()+
                    "\r\nTotal:￥" + itemBean.getSum() +
                    "\r\nStatus："+(itemBean.getIsPay().replace("0","NotPaid").replace("1","Paid")));
        }

        if(type.equals("2")){
            itemViewHodler.deailTv.setText("Location：" + itemBean.getType().replace("1", "Swimming pool")
                    + "\r\nType：" + itemBean.getMark() +
                    "\r\nTime：" + itemBean.getYyTime()+
                    "\r\nTotal:￥" + itemBean.getSum() +
                    "\r\nStatus："+(itemBean.getIsPay().replace("0","NotPaid").replace("1","Paid")));
        }

        if(type.equals("3")){
            itemViewHodler.deailTv.setText("Location：" + itemBean.getType().replace("1", "Swimming pool")
                    + "\r\nType：" + itemBean.getMark() +
                    "\r\nTime：" + itemBean.getYyTime()+
                    "\r\nTotal:￥" + itemBean.getSum() +
                    "\r\nStatus："+(itemBean.getIsPay().replace("0","NotPaid").replace("1","Paid")));
        }

        itemViewHodler.lay.setOnClickListener(new View.OnClickListener() {
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

        public TextView deailTv;
        public LinearLayout lay;
        public int viewType;

        public ItemViewHodler(@NonNull View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            deailTv = itemView.findViewById(R.id.deailTv);
            lay = itemView.findViewById(R.id.lay);
        }

        public int getViewType() {
            return viewType;
        }
    }

    public interface OnItemClickListeners {
        void itemBean(int pos, SelectTimeBean itemBean);
    }

    public OnItemClickListeners listeners;

    public void setOnItemClickListeners(OnItemClickListeners listener) {
        this.listeners = listener;
    }
}
