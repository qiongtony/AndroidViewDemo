package com.example.coordinaotrlayoutdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.coordinaotrlayoutdemo.adapter.MyAdapter;
import com.example.coordinaotrlayoutdemo.layoutmanager.FlowLayoutManager;

public class CustomLayoutManagerActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private MyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_manager);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new FlowLayoutManager());
        mAdapter = new MyAdapter(this);
        recycler.setAdapter(mAdapter);

    }

}