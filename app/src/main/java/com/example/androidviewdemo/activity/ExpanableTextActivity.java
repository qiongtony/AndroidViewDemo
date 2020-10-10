package com.example.androidviewdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidviewdemo.R;
import com.example.androidviewdemo.adapter.MyAdapter;
import com.example.androidviewdemo.repo.DataRepo;
import com.example.androidviewdemo.view.ExpandableTitleTextView;

public class ExpanableTextActivity extends AppCompatActivity {
    public static final String TEXT = ".好嗨哟（表达很高兴和兴奋的状态）#.好嗨哟#（表达很高兴和兴奋的状态）.好嗨哟（表达很高兴和兴奋的状态）.好嗨哟（表达很高兴和兴奋的状态）";
    private ExpandableTitleTextView tvTopic;
    private RecyclerView recycler;

    public static void start(Context context) {
        Intent intent = new Intent(context, ExpanableTextActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanable_text);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycler.setAdapter(new MyAdapter(DataRepo.getDatas()));
        tvTopic = findViewById(R.id.tvTopic);
        tvTopic.setTopicClickListener(new ExpandableTitleTextView.OnTopicClickListener() {
            @Override
            public void onClick(String topic) {
                Toast.makeText(ExpanableTextActivity.this, "点击了话题，内容为" + topic, Toast.LENGTH_SHORT).show();
            }
        });
        tvTopic.setExpandAndPackUpListener(new ExpandableTitleTextView.OnExpandAndPackUpListener() {
            @Override
            public void clickExpand() {
                Toast.makeText(ExpanableTextActivity.this, "点击了展开", Toast.LENGTH_SHORT).show();
                tvTopic.initAction(true);
            }

            @Override
            public void clickPackUp() {
                Toast.makeText(ExpanableTextActivity.this, "点击了收起", Toast.LENGTH_SHORT).show();
                tvTopic.initAction(false);
            }
        });
        tvTopic.setMessage(TEXT);
//        tvTopic.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                tvTopic.setText(TEXT);
//                tvTopic.setMessage(TEXT);
//            }
//        }, 1000);
    }

    protected void showKeyboard(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            if (getCurrentFocus() == null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                imm.showSoftInput(getCurrentFocus(), 0);
            }
        } else {
            if (getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}