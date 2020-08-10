package com.example.coordinaotrlayoutdemo.repo;

import java.util.ArrayList;
import java.util.List;

public class UserRepo {
    public static List<User> sUsers = new ArrayList<>();


    public static List<User> getUsers(){
        if (sUsers.isEmpty()){
            sUsers = createUsers();
        }
        return sUsers;
    }

    private static List<User> createUsers(){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            User user = new User("这是第" + i + "个item");
            users.add(user);
        }
        return users;
    }

    public static class User{
        public String content;

        public User(String content) {
            this.content = content;
        }
    }
}
