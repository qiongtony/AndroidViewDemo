package com.example.customlayoutmanagerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private SnapHelper mSnapHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.recycler);
        mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(recycler);
        recycler.setLayoutManager(new MyLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        MyAdapter myAdapter = new MyAdapter(this);
        recycler.setAdapter(myAdapter);
    }
}
