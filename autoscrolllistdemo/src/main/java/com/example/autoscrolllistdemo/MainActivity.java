package com.example.autoscrolllistdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoscrolllistdemo.adapter.SimpleAutoPollAdapter;
import com.example.autoscrolllistdemo.decoration.MyItemDecoration;
import com.example.autoscrolllistdemo.decoration.VerticalDividerItemDecoration;
import com.example.autoscrolllistdemo.reposity.CommentReposity;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private AutoPollRecyclerView rvComment;
    private SimpleAutoPollAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvComment = findViewById(R.id.rv_comment);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvComment.setLayoutManager(linearLayoutManager);
        mAdapter = new SimpleAutoPollAdapter(CommentReposity.getCommentList());
        rvComment.setAdapter(mAdapter);
        rvComment.addItemDecoration(new MyItemDecoration());
        rvComment.start();
        findViewById(R.id.btn_add_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentBean commentBean = CommentReposity.getComment(mAdapter.getDatas().size());
                commentBean.setContent("手动添加的：" + commentBean.getContent());
                if (new Random().nextBoolean()) {
                    Toast.makeText(MainActivity.this, "添加到队尾！", Toast.LENGTH_SHORT).show();
                    mAdapter.addData(commentBean, mAdapter.getDatas().size());
                } else {
                    // 最后一个可见的Item，占位View，第一个
                    int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    mAdapter.addData(commentBean, (mAdapter.getActualDataPos(lastVisibleItemPosition) + 1) % mAdapter.getDatas().size());
                    Toast.makeText(MainActivity.this, "添加到下一条！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}