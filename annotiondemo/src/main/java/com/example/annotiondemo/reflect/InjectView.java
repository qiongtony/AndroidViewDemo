package com.example.annotiondemo.reflect;

import android.app.Activity;
import android.view.View;

import com.example.annotiondemo.annotation.BindView;

import java.lang.reflect.Field;

public class InjectView {

    public static void bind(Activity activity){
        Class<?> activityClass = activity.getClass();
        // 获取所有的成员变量
        Field[] fields = activityClass.getDeclaredFields();
        for (Field field : fields){
            // 包含@BindView注解
            if (field.isAnnotationPresent(BindView.class)){
                BindView bindView = field.getAnnotation(BindView.class);
                // 获取注解设置的值
                int id = bindView.value();
                // 找到View
                View view = activity.findViewById(id);
                // 忽略访问权限
                field.setAccessible(true);
                try {
                    // 给成员变量赋值
                    field.set(activity, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
