package com.example.coreprogramming.lesson6;

import java.util.List;

public class ListTest {
    public static List<Integer> foo(List<Integer> list){
        for (int i = 0; i < list.size(); i++){
            list.set(i, list.get(i)  *  2);
        }
        return list;
    }
}
