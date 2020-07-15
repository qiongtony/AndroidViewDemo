package com.example.hencoder08_animation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private FancyFlipView fancyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fancyView = findViewById(R.id.fancy_view);

//        ObjectAnimator topFlipAnimator = ObjectAnimator.ofFloat(fancyView, "cameraTopFlip", -45);
//        topFlipAnimator.setDuration(1000);
//
//        ObjectAnimator bottomFlipAnimator = ObjectAnimator.ofFloat(fancyView, "cameraBottomFlip", 45);
//        bottomFlipAnimator.setDuration(1000);
//
//        ObjectAnimator rotateFlipAnimator = ObjectAnimator.ofFloat(fancyView, "rotateFlip", 270);
//        rotateFlipAnimator.setDuration(1500);
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playSequentially(bottomFlipAnimator, rotateFlipAnimator, topFlipAnimator);
//        animatorSet.setStartDelay(1000L);
//        animatorSet.start();
    }
}
