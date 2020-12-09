package com.example.autoscrolllistdemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoscrolllistdemo.CommentBean;
import com.example.autoscrolllistdemo.R;

import org.w3c.dom.Text;

import java.util.List;

public class SimpleAutoPollAdapter extends BaseAutoPollAdapter<CommentBean> {
    public SimpleAutoPollAdapter(List<CommentBean> datas) {
        super(datas);
    }

    @Override
    public RecyclerView.ViewHolder createNormalViewHolder(@NonNull ViewGroup parent) {
        return new AutoPollViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_auto_poll, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.w(getClass().getSimpleName(), "WWS TEST onBindViewHolder position = " + position);
        if (holder instanceof EmptyViewHolder){
            EmptyViewHolder viewHolder = (EmptyViewHolder)holder;
        }else if(holder instanceof AutoPollViewHolder){
            AutoPollViewHolder autoPollViewHolder = (AutoPollViewHolder) holder;
            CommentBean commentBean = mDatas.get(position - getHeaderLayoutCount());
            autoPollViewHolder.tvNickName.setText(commentBean.getNickName());
            autoPollViewHolder.tvContent.setText(commentBean.getContent());
        }
    }

    class AutoPollViewHolder extends RecyclerView.ViewHolder{
        TextView tvNickName;
        TextView tvContent;
        public AutoPollViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNickName = itemView.findViewById(R.id.tv_nickname);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

}
