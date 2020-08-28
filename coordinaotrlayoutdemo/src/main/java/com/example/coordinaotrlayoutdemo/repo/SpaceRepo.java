package com.example.coordinaotrlayoutdemo.repo;

import android.graphics.Color;

import com.example.coordinaotrlayoutdemo.bean.SpaceBean;

import java.util.ArrayList;
import java.util.List;

public class SpaceRepo {
    public static List<SpaceBean> sSpace = new ArrayList<>();

    public static List<SpaceBean> getSpaces(){
        if (sSpace.isEmpty()){
            sSpace = createSpaces();
        }
        return sSpace;
    }

    private static List<SpaceBean> createSpaces(){
        List<SpaceBean> spaceBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            SpaceBean space = new SpaceBean();
            space.text = "第" + (i + 1) + "个Item";
            switch (i % 4){
                case 0 :{
                    space.color = Color.BLUE;
                    break;
                }
                case 1:{
                    space.color = Color.GREEN;
                    break;
                }
                case 2 :{
                    space.color = Color.RED;
                    break;
                }
                case 3 : {
                    space.color = Color.YELLOW;
                    break;
                }
            }
            spaceBeans.add(space);
        }
        return spaceBeans;
    }
}
