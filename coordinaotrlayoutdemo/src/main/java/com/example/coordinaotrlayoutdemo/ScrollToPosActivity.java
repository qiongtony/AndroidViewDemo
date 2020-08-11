package com.example.coordinaotrlayoutdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coordinaotrlayoutdemo.adapter.MyAdapter;
import com.example.coordinaotrlayoutdemo.decoration.DividerItemDecoration;

public class ScrollToPosActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private int scrollY = 0;

    public static void start(Context context){
        Intent intent = new Intent(context, ScrollToPosActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_to_position);
        findViewById(R.id.btn_scroll_to_pos_with_offset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 超了会滑到底吗，也会，那其实用scrollBy和这个是一样的效果
                mLayoutManager.scrollToPositionWithOffset(4, 0);
            }
        });
        findViewById(R.id.btn_scroll_to_400).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 一直都是0
                Log.i("WWS", "scrollY = " + scrollY);
                // 超了，也会滑到底
                recyclerView.scrollBy(0, dp2px(800) - scrollY);
            }
        });

        recyclerView = findViewById(R.id.recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY += dy;
            }
        });
//        mLayoutManager = new  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        MyAdapter adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private int dp2px(int dp){
       return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }
}
