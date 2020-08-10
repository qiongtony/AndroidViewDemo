package com.example.coordinaotrlayoutdemo.repo;

import androidx.fragment.app.Fragment;

import com.example.coordinaotrlayoutdemo.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentRepo {
    public static List<Fragment> getFragments(){
        String[] stringArray = new String[]{"主页", "微博", "相册"};
        List<Fragment> fragmentList = new ArrayList<>();
        for (String content:
                stringArray) {
            MyFragment fragment = MyFragment.newInstance(content);
            fragmentList.add(fragment);
        }
        return fragmentList;
    }
}
