package com.example.autoscrolllistdemo.decoration;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getAdapter() == null){
            throw new IllegalArgumentException("adapter is null,please setAdapter!");
        }
        int childPosition = parent.getChildAdapterPosition(view);
        Log.w(getClass().getSimpleName(), "WWS DDDD getItemOffsets childPosition = " + childPosition + " height = " + parent.getMeasuredHeight());
        if (childPosition == 0){
            outRect.top = parent.getMeasuredHeight();
        }

        if (childPosition == (parent.getAdapter().getItemCount() - 1)){
            outRect.bottom = parent.getMeasuredHeight();
        }
    }
}
