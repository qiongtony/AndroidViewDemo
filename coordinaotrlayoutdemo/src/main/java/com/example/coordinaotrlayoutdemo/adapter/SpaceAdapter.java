package com.example.coordinaotrlayoutdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coordinaotrlayoutdemo.R;
import com.example.coordinaotrlayoutdemo.bean.SpaceBean;
import com.example.coordinaotrlayoutdemo.repo.SpaceRepo;
import com.example.coordinaotrlayoutdemo.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class SpaceAdapter extends RecyclerView.Adapter<SpaceAdapter.SpaceHolder> {
    List<SpaceBean> mSpaceList = new ArrayList<>();
    private Context mContext;
    public SpaceAdapter(Context context){
        mContext = context;
        mSpaceList = SpaceRepo.getSpaces();
    }

    @NonNull
    @Override
    public SpaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SpaceHolder(LayoutInflater.from(mContext).inflate(R.layout.item_space, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SpaceHolder holder, int position) {
        holder.itemView.setBackgroundColor(mSpaceList.get(position).color);
        holder.tvContent.setText(mSpaceList.get(position).text);
    }

    @Override
    public int getItemCount() {
        return mSpaceList.size();
    }

    public class SpaceHolder extends RecyclerView.ViewHolder{
        TextView tvContent;
        public SpaceHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
