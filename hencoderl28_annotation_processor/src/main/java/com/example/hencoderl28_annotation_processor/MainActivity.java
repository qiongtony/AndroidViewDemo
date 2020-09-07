package com.example.hencoderl28_annotation_processor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hencoderl28_lib.Binding;
import com.example.hencoderl28_lib_annotation.BindView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Binding.bind(this);

        mTextView.setText("通过annotationProcessor进行绑定");

        mTextView.setOnClickListener(view -> {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        });
    }
}
