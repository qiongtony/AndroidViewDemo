package com.example.nestedscrolldemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.nestedscrolldemo.adapter.MyAdapter;
import com.example.nestedscrolldemo.snaphelper.GallerySnapHelper;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private GallerySnapHelper mHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mMyAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(mMyAdapter);
        mHelper = new GallerySnapHelper();
        mHelper.attachToRecyclerView(mRecyclerView);
//        GallerySnapHelper2 snapHelper2 = new GallerySnapHelper2();
//        snapHelper2.attachToRecyclerView(mRecyclerView);

    }
}
