package com.example.hencoder09drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawableView extends View {
    private Drawable drawable;
    public DrawableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        drawable = new MeshDrawable();
        drawable.setBounds(0, 0, 400, 400);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawable.draw(canvas);
    }
}
