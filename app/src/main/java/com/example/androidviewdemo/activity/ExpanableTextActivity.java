package com.example.androidviewdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidviewdemo.R;
import com.example.androidviewdemo.view.MyExpandableTextView;

public class ExpanableTextActivity extends AppCompatActivity {
    public static final String TEXT = ".好嗨哟（表达很高兴和兴奋的状态）#.好嗨哟#（表达很高兴和兴奋的状态）.好嗨哟（表达很高兴和兴奋的状态）.好嗨哟（表达很高兴和兴奋的状态）";
    private MyExpandableTextView tvTopic;

    public static void start(Context context) {
        Intent intent = new Intent(context, ExpanableTextActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanable_text);
        tvTopic = findViewById(R.id.tvTopic);
        tvTopic.setTopicClickListener(new MyExpandableTextView.OnTopicClickListener() {
            @Override
            public void onClick(String topic) {
                Toast.makeText(ExpanableTextActivity.this, "点击了话题，内容为" + topic, Toast.LENGTH_SHORT).show();
            }
        });
        tvTopic.setExpandAndPackUpListener(new MyExpandableTextView.OnExpandAndPackUpListener() {
            @Override
            public void clickExpand() {
                tvTopic.initAction(true);
            }

            @Override
            public void clickPackUp() {
                tvTopic.initAction(false);
            }
        });
        tvTopic.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvTopic.setMessage(TEXT);
            }
        }, 1000);
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