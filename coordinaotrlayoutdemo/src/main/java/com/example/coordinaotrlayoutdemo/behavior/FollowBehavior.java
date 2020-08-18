package com.example.coordinaotrlayoutdemo.behavior;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class FollowBehavior extends CoordinatorLayout.Behavior<TextView> {

    public FollowBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *  判断child的布局是否依赖该View
     *
     * @param parent
     * @param child
     * @param dependency            可能要依赖的View
     * @return                      true表示依赖，false表示不依赖
     */
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency) {
        return dependency instanceof Button;
    }

    /**
     * 依赖的View位置发生了改变，这时候child可以进行相应的处理
     * 在这里我们获取到依赖View的移动位置，child跟着移动
     * @param parent
     * @param child
     * @param dependency
     * @return              表示是否改变了child的size或者pos，true改变了，false没改变
     */
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency) {
        child.setX(dependency.getX());
        child.setY(dependency.getY() + 150);
        return true;
    }
}
