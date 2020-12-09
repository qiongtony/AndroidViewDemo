package com.example.autoscrolllistdemo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoscrolllistdemo.R;

import java.util.List;

public abstract class BaseAutoPollAdapter<T> extends RecyclerView.Adapter {
    protected List<T> mDatas;
    private static final int DEFAULT_ITEM_VIEW = 1;
    private static final int FOOTER_ITEM_VIEW_TYPE = 2;
    private static final int NORAML_ITEM_VIEW = 0;

    public BaseAutoPollAdapter(List<T> datas) {
        mDatas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.w(getClass().getSimpleName(), "WWS TEST onCreateViewHolder viewType = " + viewType);
        if (viewType == DEFAULT_ITEM_VIEW || viewType == FOOTER_ITEM_VIEW_TYPE) {
           /* View view = new View(parent.getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(layoutParams);*/
            return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false));
        } else {
            return createNormalViewHolder(parent);
        }
    }

    protected int getHeaderLayoutCount(){
        return 0;
    }

    protected int getFooterLayoutCount(){
        return 0;
    }

    public abstract RecyclerView.ViewHolder createNormalViewHolder(@NonNull ViewGroup parent);

    @Override
    public int getItemCount() {
        return (mDatas == null || mDatas.size() == 0) ? 0 : getHeaderLayoutCount() +  mDatas.size() + getFooterLayoutCount();
    }

    public int getActualDataPos(int holderPos) {
        if (getHeaderLayoutCount() == 0){
            return holderPos;
        }
        int itemViewType = getItemViewType(holderPos);
        if (itemViewType == DEFAULT_ITEM_VIEW || itemViewType == FOOTER_ITEM_VIEW_TYPE) {
            return holderPos - 1;
        } else {
            return holderPos - 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int pos = position % getItemCount();
        Log.w(getClass().getSimpleName(), "WWS TEST getItemViewType position = " + position);
        if (getHeaderLayoutCount() != 0 && pos == 0) {
            return DEFAULT_ITEM_VIEW;
        }
        if (getFooterLayoutCount() != 0 && pos == (getItemCount() - 1)) {
            return FOOTER_ITEM_VIEW_TYPE;
        }
        return NORAML_ITEM_VIEW;
    }

    public void addData(T bean, int pos) {
        mDatas.add(pos, bean);
//        notifyItemChanged(pos + 1, getItemCount()- pos);
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }
}
