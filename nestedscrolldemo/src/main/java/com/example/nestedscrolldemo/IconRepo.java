package com.example.nestedscrolldemo;

import com.example.nestedscrolldemo.bean.IconBean;

import java.util.ArrayList;
import java.util.List;

public class IconRepo {
    private static List<IconBean> sIconBeanList = new ArrayList<>();

    public static List<IconBean> getIconBeanList(){
        if (!sIconBeanList.isEmpty()){
            return sIconBeanList;
        }
        IconBean iconBean = new IconBean(R.drawable.t1135335, "Android机器人");
        sIconBeanList.add(iconBean);

        iconBean = new IconBean(R.drawable.t1135338, "记事本");
        sIconBeanList.add(iconBean);

        iconBean = new IconBean(R.drawable.t1135341, "谷歌地图");
        sIconBeanList.add(iconBean);

        iconBean = new IconBean(R.drawable.p1135453, "钥匙串");
        sIconBeanList.add(iconBean);

        iconBean = new IconBean(R.drawable.p1135462, "播放器");
        sIconBeanList.add(iconBean);

        iconBean = new IconBean(R.drawable.p1135463, "录音机");
        sIconBeanList.add(iconBean);
        return sIconBeanList;
    }
}
