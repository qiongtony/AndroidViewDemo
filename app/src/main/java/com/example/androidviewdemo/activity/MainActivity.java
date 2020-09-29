package com.example.androidviewdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidviewdemo.R;
import com.example.androidviewdemo.util.ScreenUtil;
import com.example.androidviewdemo.view.BulletView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String[] CONTENTS = new String[]{"黑白世…：他为啥不给我返", "吴小浅…：这东西咋做啊！", "不忘初心（不忘记最初的心愿）", "2.道路千万条，安全第一条（源于电影《流浪地球》）",
            "3.柠檬精（喜欢酸别人，嫉妒别人）", "4.好嗨哟（表达很高兴和兴奋的状态）", "5.是个狼人（“比狠人再狠‘一点’”）", "6.雨女无瓜（“与你无关”的谐音）", "7.硬核（很强硬、很彪悍）"};
    private BulletView mBulletView;
    private Runnable mRunnable =new Runnable() {
        @Override
        public void run() {
            mBulletView.addText(addText());
            mBulletView.postDelayed(mRunnable, 1000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBulletView = findViewById(R.id.view_bullet);
        mBulletView.setLoopMode(true);
        for (int i = 0; i < 5; i++){
            mBulletView.addText(addText());
        }
        findViewById(R.id.jump_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClassSevenTestActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btnJumpExpanableTextPage).setOnClickListener(v -> {
            ExpanableTextActivity.start(MainActivity.this);
        });
    }

    private TextView addText(){
        TextView textView = new TextView(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(lp);
        textView.setBackgroundResource(R.drawable.common_tv_bg);
        int paddingSize = (int) ScreenUtil.dp2Px(6);
        textView.setPadding(paddingSize, paddingSize, paddingSize, paddingSize);
        textView.setTextColor(0xff5d92ff);
        textView.setText(CONTENTS[new Random().nextInt(CONTENTS.length)]);
        return textView;
    }
}
