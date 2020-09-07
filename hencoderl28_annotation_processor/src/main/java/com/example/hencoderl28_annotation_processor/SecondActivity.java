package com.example.hencoderl28_annotation_processor;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hencoderl28_lib.Binding;
import com.example.hencoderl28_lib_annotation.BindView;

public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.text1)
    TextView mTextView1;

    @BindView(R.id.text3)
    TextView mTextView3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Binding.bind(this);

        mTextView1.setText("SecondActivity的text1");

        mTextView3.setText("SecondActivity的text3");
    }
}
