package com.example.androidviewdemo.repo;

import com.example.androidviewdemo.activity.ExpanableTextActivity;

import java.util.ArrayList;
import java.util.List;

public class DataRepo {
    private static List<String> datas;

    public static List<String> getDatas(){
        if (datas != null){
            return datas;
        }
        datas = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            datas.add(ExpanableTextActivity.TEXT);
        }
        return datas;
    }
}
