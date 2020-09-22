package com.example.customlayoutmanagerdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private Context mContext;
    private List<AudioLinesItem> mDatas;
    public MyAdapter(Context context){
        mContext = context;

        mDatas = AudioLinesRepos.getDatas();
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_data, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
        if (position == 0){
            params.leftMargin =
            ItemConfigure.getDefault().getBorderItemHorizontalMargin();
        }else{
            params.leftMargin = 0;
        }
        if (position == (getItemCount() - 1)){
            params.rightMargin =
                    ItemConfigure.getDefault().getBorderItemHorizontalMargin();
        }else{
            params.rightMargin = 0;
        }
        holder.tvContent.setText(mDatas.get(position).getLines());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView tvContent;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);

            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.width =
//                    (int) (ScreenUtil.getScreenWidth() * 0.87f)
                getItemWidth()
            ;

        }
    }

    public static int getItemWidth(){
        return ItemConfigure.getDefault().getItemWidth();
//        return (int) ((ScreenUtil.getScreenWidth() - ScreenUtil.dp2Px(20)) / 1.1f);
    }
}
