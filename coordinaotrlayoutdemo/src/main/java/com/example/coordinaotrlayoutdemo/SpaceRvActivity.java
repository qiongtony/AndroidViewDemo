package com.example.coordinaotrlayoutdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.coordinaotrlayoutdemo.adapter.SpaceAdapter;
import com.example.coordinaotrlayoutdemo.decoration.SpacingItemDecoration;
import com.example.coordinaotrlayoutdemo.util.ScreenUtil;

/**
 * 带间距的等分item
 */
public class SpaceRvActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private SpaceAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_rv);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new SpaceAdapter(this);
        recycler.setAdapter(mAdapter);
        recycler.addItemDecoration(new SpacingItemDecoration((int) ScreenUtil.dp2Px(50), true));
    }
}