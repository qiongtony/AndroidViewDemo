package com.example.nestedscrolldemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nestedscrolldemo.IconRepo;
import com.example.nestedscrolldemo.R;
import com.example.nestedscrolldemo.bean.IconBean;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private List<IconBean> iconList;
    private Context mContext;

    public MyAdapter(Context context) {
        mContext = context;
        iconList = IconRepo.getIconBeanList();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_icon, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        IconBean icon = iconList.get(position);
        holder.ivIcon.setImageResource(icon.resId);
        holder.tvName.setText(icon.name);
    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{
        ImageView ivIcon;
        TextView tvName;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
