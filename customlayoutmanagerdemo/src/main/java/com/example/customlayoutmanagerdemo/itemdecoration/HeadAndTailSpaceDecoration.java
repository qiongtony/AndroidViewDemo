package com.example.customlayoutmanagerdemo.itemdecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customlayoutmanagerdemo.utils.ScreenUtil;

/**
 * 头尾增加间距的ItemDecoration
 */
public class HeadAndTailSpaceDecoration extends RecyclerView.ItemDecoration {
    private int mMargin;

    public HeadAndTailSpaceDecoration(){
        this((int) ScreenUtil.dp2Px(8));
    }

    public HeadAndTailSpaceDecoration(int margin){
        this.mMargin = margin;
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
          RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
          if (layoutManager == null || !(layoutManager instanceof LinearLayoutManager)){
              return;
          }
          int currentItemPos = parent.getChildAdapterPosition(view);
          if (currentItemPos == 0){
              if (((LinearLayoutManager) layoutManager).getOrientation() == RecyclerView.HORIZONTAL) {
                  outRect.left = mMargin;
              }else{
                  outRect.top = mMargin;
              }
          }
          if (currentItemPos == (layoutManager.getItemCount() - 1)){
              if (((LinearLayoutManager) layoutManager).getOrientation() == RecyclerView.HORIZONTAL) {
                  outRect.right = mMargin;
              }else{
                  outRect.bottom = mMargin;
              }
          }
    }
}
